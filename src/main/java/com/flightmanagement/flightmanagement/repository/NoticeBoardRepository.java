package com.flightmanagement.flightmanagement.repository;

import com.flightmanagement.flightmanagement.model.NoticeBoard;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@Primary
public interface NoticeBoardRepository extends JpaRepository<NoticeBoard, String> {

    List<NoticeBoard> findByDate(LocalDate date);

    List<NoticeBoard> findByDate(LocalDate date, Sort sort);

    boolean existsByDate(LocalDate date);
    boolean existsByDateAndIdNot(LocalDate date, String id);
}