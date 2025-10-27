package com.flightmanagement.flightmanagement.repository;

import com.flightmanagement.flightmanagement.model.Luggage;
import com.flightmanagement.flightmanagement.model.NoticeBoard;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*In-memory repository for managing NoticeBoard entities.
 * This class extends the generic BaseRepositoryInMemory and provides CRUD operations
 * specifically for NoticeBoard objects*/
@Repository
public class NoticeBoardRepositoryInMemory extends BaseRepositoryInMemory<NoticeBoard, String> {

    public NoticeBoardRepositoryInMemory() {
        super(NoticeBoard::getId);
    }
}
