package com.triplan.mapper;

import com.triplan.domain.RoomVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoomMapper {
    
    void roomInsert(RoomVO roomVO);



}
