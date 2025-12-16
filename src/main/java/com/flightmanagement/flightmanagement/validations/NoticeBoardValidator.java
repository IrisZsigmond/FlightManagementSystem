package com.flightmanagement.flightmanagement.validations;

import com.flightmanagement.flightmanagement.model.NoticeBoard;
import com.flightmanagement.flightmanagement.repository.NoticeBoardRepository;
import com.flightmanagement.flightmanagement.service.FlightService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class NoticeBoardValidator {

    private final NoticeBoardRepository noticeBoardRepository;
    private final FlightService flightService;

    public NoticeBoardValidator(NoticeBoardRepository noticeBoardRepository,
                                FlightService flightService) {
        this.noticeBoardRepository = noticeBoardRepository;
        this.flightService = flightService;
    }

    // 1. Existence
    public NoticeBoard requireExisting(String id) {
        return noticeBoardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "NoticeBoard not found: " + id));
    }

    // 2. Unique ID (CREATE)
    public void assertIdUnique(String id) {
        if (noticeBoardRepository.existsById(id)) {
            throw new IllegalStateException("NoticeBoard ID already exists: " + id);
        }
    }

    // 3. Unique Date (CREATE)
    public void assertDateUnique(LocalDate date) {
        if (!noticeBoardRepository.findByDate(date).isEmpty()) {
            throw new IllegalStateException(
                    "A NoticeBoard for date " + date + " already exists."
            );
        }
    }

    // 4. Unique date for UPDATE (exclude own ID)
    public void assertDateUniqueForUpdate(LocalDate date, String currentId) {
        var existingBoards = noticeBoardRepository.findByDate(date);
        if (!existingBoards.isEmpty() && !existingBoards.get(0).getId().equals(currentId)) {
            throw new IllegalStateException(
                    "Another NoticeBoard already exists for date " + date
            );
        }
    }

    // 5. Cannot delete if flights attached
    public void assertCanBeDeleted(String id) {
        if (!flightService.findByNoticeBoardId(id).isEmpty()) {
            throw new IllegalStateException(
                    "Cannot delete NoticeBoard '" + id + "' because flights are attached."
            );
        }
    }
}
