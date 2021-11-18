package com.triplan.service;

import com.triplan.domain.QuestionVO;
import com.triplan.dto.response.Pagination;
import com.triplan.enumclass.QuestionType;
import com.triplan.mapper.QuestionMapper;
import com.triplan.service.inf.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionMapper questionMapper;

    @Override
    public void create(QuestionVO questionVO) {
        questionMapper.insert(questionVO);
    }

    @Override
    public QuestionVO getQuestion(Integer questionId) {
        QuestionVO result = questionMapper.select(questionId);
        return result;
    }

    @Override
    public void update(Integer questionId, QuestionVO questionVO) {
        questionVO.setQuestionId(questionId);
        questionMapper.update(questionVO);
    }

    @Override
    public void delete(Integer questionId) {
        questionMapper.delete(questionId);
    }

    @Override
    public Pagination<QuestionVO> listByItemGroupId(Integer pageSize, Integer currentPage, Integer itemGroupId) {
        List<QuestionVO> questionListByItemGroupIdListPage = questionMapper.listByItemGroupId(pageSize, currentPage, itemGroupId);

        int totalReviews = questionMapper.countByItemGroupId(QuestionType.CUSTOMER);

        return new Pagination<>(pageSize, currentPage, totalReviews, questionListByItemGroupIdListPage);
    }

    @Override
    public Pagination<QuestionVO> listBySellerId(Integer pageSize, Integer currentPage, Integer sellerId) {
        List<QuestionVO> questionListBySellerIdListPage = questionMapper.listBySellerId(pageSize, currentPage, sellerId);

        int totalReviews = questionMapper.countBySellerId(QuestionType.SELLER);

        return new Pagination<>(pageSize, currentPage, totalReviews, questionListBySellerIdListPage);
    }

}