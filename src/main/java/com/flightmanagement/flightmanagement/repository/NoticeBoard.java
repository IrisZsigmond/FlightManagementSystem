package com.flightmanagement.flightmanagement.repository;

import org.springframework.stereotype.Repository;

/*In-memory repository for managing NoticeBoard entities.
 * This class extends the generic BaseRepositoryInMemory and provides CRUD operations
 * specifically for NoticeBoard objects*/
@Repository
public class NoticeBoard extends BaseRepositoryInMemory<com.flightmanagement.flightmanagement.model.NoticeBoard, String> {

    public NoticeBoard() {
        super(com.flightmanagement.flightmanagement.model.NoticeBoard::getId);
    }
}
