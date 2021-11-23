package com.triplan.controller;

import com.triplan.domain.cs.QuestionVO;
import com.triplan.service.inf.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping("/write")
    public String register(QuestionVO questionVO) {
        questionService.create(questionVO);
        return "redirect:/questions/qnaBoard";
    }

    @PutMapping("/{questionId}")
    public String update(@PathVariable Integer questionId, @RequestBody QuestionVO questionVO) {
        questionService.update(questionId, questionVO);
        return "수정 완료";
    }

    @GetMapping
    public String readQuestion(@RequestParam Integer questionId, Model model) {
        model.addAttribute("question", questionService.getQuestion(questionId));

        return "cs/qna_content";
    }

    @GetMapping("/qnaBoard")
    public String questionList(
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "1") Integer currentPage, Model model)
    {
        model.addAttribute("pagination", questionService.listFromCustomerToAdmin(pageSize, currentPage));
        return "cs/qna";
    }

    @GetMapping({"/qnawri"})
    public String qnawri() {
        return "cs/qna_write";
    }

}







