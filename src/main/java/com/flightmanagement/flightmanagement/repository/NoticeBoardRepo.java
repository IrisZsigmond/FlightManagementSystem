package com.flightmanagement.flightmanagement.repository;

import org.springframework.stereotype.Repository;
import com.flightmanagement.flightmanagement.model.NoticeBoard;

/**In-memory repository for managing NoticeBoard entities.
 * This class extends the generic BaseRepositoryInMemory
 * and provides CRUD operations specifically for NoticeBoard objects*/
@Repository
public class NoticeBoardRepo extends BaseRepositoryInMemory<NoticeBoard, String> {

    public NoticeBoardRepo() {
        super(NoticeBoard::getId);
    }
}
