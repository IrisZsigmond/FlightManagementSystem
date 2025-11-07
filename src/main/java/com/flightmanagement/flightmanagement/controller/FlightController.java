package com.flightmanagement.flightmanagement.controller;

import com.flightmanagement.flightmanagement.model.Flight;
import com.flightmanagement.flightmanagement.service.FlightService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * This class handles all web requests related to "flights".
 *
 * It is a Spring MVC (Model-View-Controller software design pattern) Controller responsible for:
 * - Receiving HTTP requests (GET/POST) from the browser
 * - Calling the appropriate methods in the FlightService (the business layer)
 * - Passing data to the view (HTML templates) using the Model
 * - Returning the logical name of the Thymeleaf template to render
 */
@Controller // Singleton Bean: Marks this class as a Spring MVC Controller
@RequestMapping("/flights") // All methods in this class will start with URL path "/flights"
public class FlightController {

    // A reference to the service layer that manages Flight objects
    private final FlightService flights;

    /**
     * Constructor-based dependency injection.
     * Spring automatically provides (injects) a FlightService instance here when creating this controller.
     *
     * @param flights the FlightService bean provided by Spring
     */
    public FlightController(FlightService flights) {
        this.flights = flights;
    }

    /**
     * Displays a list of all flights.
     *
     * This method handles GET requests to "/flights".
     * It retrieves all flights from the service and adds them to the Model so they can be displayed in the view.
     *
     * @param model a Spring-provided container for passing data to the view
     * @return the name of the Thymeleaf template to render ("flights/index")
     */
    @GetMapping // Handles HTTP GET requests to "/flights"
    public String index(Model model) {
        // Add a list of all flights to the model under the key "flights"
        model.addAttribute("flights", flights.findAll());

        // Return the logical name of the template to display: templates/flights/index.html
        return "flights/index";
    }

    /**
     * Displays a form for creating a new Flight.
     *
     * This method handles GET requests to "/flights/new".
     * It prepares an empty Flight object that the form can bind its input fields to.
     *
     * @param model a Spring-provided container for data passed to the view
     * @return the name of the Thymeleaf template to render ("flights/form")
     */
    @GetMapping("/new") // Handles GET requests to "/flights/new"
    public String form(Model model) {
        // Create an empty Flight object with default (null or 0) values.
        // This allows Thymeleaf to bind form fields like th:field="*{name}" without errors.
        model.addAttribute("flight", new Flight(null, null, null, 0, null, null));

        // Return the logical view name: templates/flights/form.html
        return "flights/form";
    }

    /**
     * Processes the submitted flight creation form.
     *
     * This method handles POST requests to "/flights".
     * Spring automatically binds the form fields to a new Flight object using @ModelAttribute.
     *
     * After saving the new flight via the service, it redirects back to the flight list.
     *
     * @param flight the Flight object built automatically from form input
     * @return redirect instruction to "/flights" (list page)
     */
    @PostMapping // Handles HTTP POST requests to "/flights"
    public String create(@ModelAttribute Flight flight) {
        // Delegate saving logic to the FlightService
        flights.save(flight);

        // Redirect to the list page to avoid resubmitting the form on browser refresh
        return "redirect:/flights";
    }

    /**
     * Deletes an existing flight.
     *
     * This method handles POST requests to "/flights/{id}/delete".
     * The {id} part of the URL is extracted using @PathVariable and used to identify which flight to remove.
     *
     * @param id the unique identifier of the flight to delete
     * @return redirect instruction to "/flights" (list page)
     */
    @PostMapping("/{id}/delete") // Handles POST requests like "/flights/ABC123/delete"
    public String delete(@PathVariable String id) {
        // Delegate the deletion to the service layer
        flights.delete(id);

        // Redirect back to the list of flights
        return "redirect:/flights";
    }
}
