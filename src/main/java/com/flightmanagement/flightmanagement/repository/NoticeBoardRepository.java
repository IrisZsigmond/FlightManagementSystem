package com.flightmanagement.flightmanagement.repository;

import org.springframework.stereotype.Repository;

/**
 * In-memory repository for managing NoticeBoard entities.
 * <p>
 * This class extends the generic BaseRepositoryInMemory and provides
 * CRUD operations specifically for NoticeBoard objects.
 */
@Repository
public class NoticeBoardRepository extends BaseRepositoryInMemory<com.flightmanagement.flightmanagement.model.NoticeBoard, String> {

    /**
     * Constructor that configures how the repository extracts the ID of a NoticeBoard.
     */
    public NoticeBoardRepository() {
        super(com.flightmanagement.flightmanagement.model.NoticeBoard::getId);
    }
}
