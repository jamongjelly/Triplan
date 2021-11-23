package com.triplan.service;

import com.triplan.domain.cs.NoticeVO;
import com.triplan.dto.response.Pagination;
import com.triplan.enumclass.cs.NoticeTarget;
import com.triplan.mapper.cs.NoticeMapper;
import com.triplan.service.inf.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {

    private final NoticeMapper noticeMapper;

    @Override
    public void noticeInsert(NoticeVO noticeVO) {
        noticeMapper.insert(noticeVO);
    }

    @Override
    public NoticeVO noticeSelect(Integer noticeId) {
        return noticeMapper.select(noticeId);
    }

    @Override
    public void noticeUpdate(NoticeVO noticeVO) {
        noticeMapper.update(noticeVO);
    }

    @Override
    public void noticeDelete(Integer noticeId) {
        noticeMapper.delete(noticeId);
    }

    @Override
    public Pagination<NoticeVO> noticeList(NoticeTarget noticeTarget, Integer pageSize, Integer currentPage) {
        Integer startRow = (currentPage - 1) * pageSize;
        List<NoticeVO> noticeList = noticeMapper.noticeList(noticeTarget.name(), startRow, pageSize);

        Integer count = noticeMapper.count(noticeTarget);
        return new Pagination<>(pageSize, currentPage, count, noticeList);
    }

}
