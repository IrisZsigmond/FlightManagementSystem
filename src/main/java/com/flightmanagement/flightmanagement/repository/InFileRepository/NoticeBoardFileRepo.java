package com.flightmanagement.flightmanagement.repository.InFileRepository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.flightmanagement.flightmanagement.config.AppDataProperties;
import com.flightmanagement.flightmanagement.model.NoticeBoard;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * File-backed repository for NoticeBoard.
 * Loads/saves notice boards into noticeboards.json under the runtime data directory.
 */
@Repository
public class NoticeBoardFileRepo extends InFileRepository<NoticeBoard, String> {

    public NoticeBoardFileRepo(AppDataProperties props, ResourceLoader resourceLoader) {
        super(
                "noticeboards.json",
                new TypeReference<List<NoticeBoard>>() {},
                NoticeBoard::getId,
                props,
                resourceLoader
        );
    }
}
