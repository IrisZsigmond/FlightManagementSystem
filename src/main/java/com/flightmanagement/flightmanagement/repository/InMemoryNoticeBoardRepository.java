package com.flightmanagement.flightmanagement.repository;

import com.flightmanagement.flightmanagement.model.NoticeBoard;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryNoticeBoardRepository extends AbstractRepository<NoticeBoard> {

    private final List<NoticeBoard> noticeBoards = new ArrayList<>();

    @Override
    public void save(NoticeBoard noticeBoard) {
        noticeBoards.add(noticeBoard);
    }

    @Override
    public List<NoticeBoard> findAll() {
        return noticeBoards;
    }

    @Override
    public Optional<NoticeBoard> findById(String id) {
        return noticeBoards.stream()
                .filter(n -> n.getId().equals(id))
                .findFirst();
    }

    @Override
    public void delete(String id) {
        noticeBoards.removeIf(n -> n.getId().equals(id));
    }

    @Override
    public void update(String id, NoticeBoard updatedNoticeBoard) {
        for (NoticeBoard noticeBoard : noticeBoards) {
            if (noticeBoard.getId().equals(id)) {
                noticeBoard.setDate(updatedNoticeBoard.getDate());
                noticeBoard.setFlightsOfTheDay(updatedNoticeBoard.getFlightsOfTheDay());
                break;
            }
        }
    }

}
