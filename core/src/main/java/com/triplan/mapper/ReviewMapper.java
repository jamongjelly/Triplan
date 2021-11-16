package com.triplan.mapper;

import com.triplan.domain.ReviewVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReviewMapper {

    void insert(ReviewVO reviewVO);

    ReviewVO select(Integer reviewId);

    void update(ReviewVO reviewVO);

    void delete(Integer reviewId);

    List<ReviewVO> list();


}
