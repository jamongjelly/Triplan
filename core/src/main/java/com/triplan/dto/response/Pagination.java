package com.triplan.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Pagination <T> {

    // 입력 받을 페이징 정보
    private Integer pageSize;
    private Integer currentPage;

    // DB 함수로 집계할 페이징 정보
    private Integer totalElements;

    // 계산할 페이징 정보
    private Integer totalPages;
    private Integer startPage;
    private Integer endPage;
    private Boolean hasPrev;
    private Boolean hasNext;

    // 데이터
    private List<T> list;

    public Pagination(
            Integer pageSize,
            Integer currentPage,
            Integer totalElements,
            List<T> list)
    {
        this.pageSize = pageSize;
        this.currentPage = currentPage;
        this.totalElements = totalElements;
        this.list = list;

        // 현재 페이지를 pageSize 로 나눈 후 소수점은 내림하는 효과
        // ex) (15 / 10) * 10 + 1 = 1 * 10 + 1 = 11
        startPage = (currentPage - 1) / pageSize * pageSize + 1;

        // 계산된 마지막 페이지
        int calculatedEndPage = startPage + pageSize - 1;

        totalPages = totalElements / pageSize                   // 아이템으로 꽉 채워진 페이지 수
                + (totalElements % pageSize == 0 ? 0 : 1);      // 나머지가 있을 경우 덜 채워진 잔여 1페이지 추가

        // 실제 마지막 페이지
        endPage = Math.min(calculatedEndPage, totalPages);

        // Prev, Next 버튼 활성화 관련 속성 초기화
        hasPrev = 1 < startPage;
        hasNext = endPage < totalPages;
    }

}
