package com.triplan.dto.customer.reponse;

import com.triplan.domain.ItemVO;
import com.triplan.domain.QuestionVO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDTO {

    private Integer questionId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private Boolean hide;
    private Integer memberId;
    private Integer qnaCategoryId;
    private Integer itemGroupId;
    private String type;

    public static QuestionDTO of(QuestionVO vo) {

        return QuestionDTO.builder()
                .questionId(vo.getQuestionId())
                .title(vo.getTitle())
                .content(vo.getContent())
                .createdAt(vo.getCreatedAt())
                .hide(vo.getHide())
                .memberId((vo.getMemberId()))
                .qnaCategoryId((vo.getQnaCategoryId()))
                .itemGroupId(vo.getItemGroupId())
                .type(vo.getType())
                .build();
    }
}