package com.flightmanagement.flightmanagement.controller;

import com.flightmanagement.flightmanagement.dto.AirportEmployeeForm;
import com.flightmanagement.flightmanagement.mapper.AirportEmployeeMapper;
import com.flightmanagement.flightmanagement.model.AirportEmployee;
import com.flightmanagement.flightmanagement.service.AirportEmployeeService;
import com.flightmanagement.flightmanagement.service.AirportEmployeeServiceImpl;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/airportemployees")
public class AirportEmployeeController {

    private final AirportEmployeeService service;
    private final AirportEmployeeMapper mapper;

    public AirportEmployeeController(AirportEmployeeService service,
                                     AirportEmployeeMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    // list + sort + filter
    @GetMapping
    public String index(
            Model model,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String department,
            @RequestParam(required = false) String designation,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String dir
    ){
        // whitelist sort
        if (!sort.equals("id") && !sort.equals("name") && !sort.equals("department") && !sort.equals("designation")) {
            sort = "id";
        }
        if (!dir.equalsIgnoreCase("asc") && !dir.equalsIgnoreCase("desc")) {
            dir = "asc";
        }

        Sort.Direction direction = dir.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort springSort = Sort.by(direction, sort);

        // FILTER + SORT together (through service)
        model.addAttribute("employees", service.search(name, department, designation, springSort));

        // Logic for sorting toggles/arrows
        model.addAttribute("sort", sort);
        model.addAttribute("dir", dir);
        model.addAttribute("reverseDir", dir.equalsIgnoreCase("asc") ? "desc" : "asc");

        // Retain filter values in the form after submission
        model.addAttribute("filterName", name);
        model.addAttribute("filterDepartment", department);
        model.addAttribute("filterDesignation", designation);

        return "airportemployees/index";
    }

    // create form
    @GetMapping("/new")
    public String form(Model model) {
        if (!model.containsAttribute("airportEmployeeForm")) {
            model.addAttribute("airportEmployeeForm", new AirportEmployeeForm());
        }
        return "airportemployees/new";
    }

    // create submit
    @PostMapping
    public String create(
            @Valid @ModelAttribute("airportEmployeeForm") AirportEmployeeForm form,
            BindingResult result,
            Model model,
            RedirectAttributes ra
    ) {
        // 1) Bean Validation pe DTO
        if (result.hasErrors()) {
            return "airportemployees/new";
        }

        try {
            // 2) DTO -> Entity
            AirportEmployee e = mapper.toEntity(form);

            // 3) service (include validator)
            service.save(e);

            ra.addFlashAttribute("success", "Airport employee created.");
            return "redirect:/airportemployees";

        } catch (IllegalArgumentException | IllegalStateException ex) {
            String msg = ex.getMessage() != null ? ex.getMessage() : "Could not create airport employee.";

            // mapping on fields if possible
            if (msg.contains("id already exists") || msg.contains("Airport employee id")) {
                result.rejectValue("id", "duplicate", msg);
            } else {
                result.reject("globalError", msg);
            }

            return "airportemployees/new";
        }
    }

    // edit form
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable String id,
                       Model model,
                       RedirectAttributes ra) {
        try {
            AirportEmployee e = ((AirportEmployeeServiceImpl) service).getById(id);
            AirportEmployeeForm form = mapper.toForm(e);

            model.addAttribute("airportEmployeeForm", form);
            model.addAttribute("employeeId", id);
            model.addAttribute("employee", e);
            return "airportemployees/edit";

        } catch (RuntimeException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
            return "redirect:/airportemployees";
        }
    }

    // edit submit
    @PostMapping("/{id}")
    public String update(
            @PathVariable String id,
            @Valid @ModelAttribute("airportEmployeeForm") AirportEmployeeForm form,
            BindingResult result,
            Model model,
            RedirectAttributes ra
    ) {
        // 1) Bean Validation
        if (result.hasErrors()) {
            AirportEmployee existing = ((AirportEmployeeServiceImpl) service).getById(id);
            model.addAttribute("employeeId", id);
            model.addAttribute("employee", existing);
            return "airportemployees/edit";
        }

        try {
            AirportEmployee existing = ((AirportEmployeeServiceImpl) service).getById(id);

            mapper.updateEntityFromForm(existing, form);

            service.update(id, existing);

            ra.addFlashAttribute("success", "Airport employee updated.");
            return "redirect:/airportemployees";

        } catch (IllegalArgumentException | IllegalStateException ex) {
            String msg = ex.getMessage() != null ? ex.getMessage() : "Could not update airport employee.";

            result.reject("globalError", msg);

            AirportEmployee existing = ((AirportEmployeeServiceImpl) service).getById(id);
            model.addAttribute("employeeId", id);
            model.addAttribute("employee", existing);

            return "airportemployees/edit";
        }
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id, RedirectAttributes ra) {
        try {
            boolean deleted = service.delete(id);
            if (deleted) {
                ra.addFlashAttribute("success", "Airport employee deleted.");
            } else {
                ra.addFlashAttribute("error", "Airport employee not found or already deleted.");
            }
        } catch (Exception ex) {
            ra.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/airportemployees";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable String id,
                       Model model,
                       RedirectAttributes ra) {
        try {
            AirportEmployee employee = ((AirportEmployeeServiceImpl) service).getById(id);
            model.addAttribute("employee", employee);
            return "airportemployees/view";
        } catch (RuntimeException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
            return "redirect:/airportemployees";
        }
    }
}
