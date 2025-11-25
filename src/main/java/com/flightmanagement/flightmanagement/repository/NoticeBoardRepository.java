package com.flightmanagement.flightmanagement.repository;

import com.flightmanagement.flightmanagement.model.NoticeBoard;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@Primary
public interface NoticeBoardRepository extends JpaRepository<NoticeBoard, String> {

    // Custom query for findByDate
    List<NoticeBoard> findByDate(LocalDate date);
}
