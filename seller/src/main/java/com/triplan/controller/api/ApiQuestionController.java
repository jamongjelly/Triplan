package com.triplan.controller.api;

import com.triplan.domain.QuestionVO;
import com.triplan.dto.customer.reponse.QuestionDTO;
import com.triplan.dto.response.Pagination;
import com.triplan.service.inf.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
public class ApiQuestionController {

    private final QuestionService questionService;

    /* CRUD 메서드 */
    //문의 사항 작성
    @PostMapping
    public String register(@RequestBody QuestionVO questionVO) {
        questionService.create(questionVO);
        System.out.println(" C : Create 실행");
        return "board/list";
    }

    // 문의 글 하나 상세 조회
    @GetMapping("/{questionId}")
    public QuestionVO getQuestion(@PathVariable int questionId) {
        System.out.println(" C : getQ 실행@@@@@@@@@@@@");

        return questionService.getQuestion(questionId);
    }

    // 문의 사항 글 수정
    @PutMapping("/{questionId}")
    public String update(@PathVariable Integer questionId, @RequestBody QuestionVO questionVO) {
        questionService.update(questionId, questionVO);
        return "수정 완료";
    }

    // 문의 사항 글 삭제
    @DeleteMapping("/{questionId}")
    public String delete(@PathVariable Integer questionId) {
        questionService.delete(questionId);
        return "문의글 삭제 완료";
    }

    /* 리스트 조회 메서드 -> 다 페이징 */
    // 상품 상세 페이지 : SELLER to ADMIN 문의 사항 목록
    @GetMapping("/itemGroups/{itemGroupId}")
    public Pagination<QuestionVO> questionListByItemGroupId(
            @PathVariable Integer itemGroupId,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "1") Integer currentPage
    ) {
        return questionService.listByItemGroupId(pageSize, currentPage, itemGroupId);
    }

    // 판매자 페이지 : 판매자에게 물어보는 고객의 질문 목록
    @GetMapping("/sellers/{sellerId}")
    public Pagination<QuestionVO> questionListBySellerId(
            @PathVariable Integer sellerId,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "1") Integer currentPage
    ) {
        return questionService.listBySellerId(pageSize, currentPage, sellerId);
    }

}







