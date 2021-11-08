package com.triplan.service;

import com.sun.org.apache.bcel.internal.generic.INEG;
import com.triplan.domain.FlightVO;
import com.triplan.domain.ItemVO;
import com.triplan.domain.RoomVO;
import com.triplan.dto.customer.request.ItemFlightRequestDTO;
import com.triplan.dto.customer.request.ItemRoomRequestDTO;
import com.triplan.dto.customer.response.ItemFlightResponseDTO;
import com.triplan.dto.customer.response.ItemRoomResponseDTO;
import com.triplan.enumclass.ItemCategory;
import com.triplan.mapper.FlightMapper;
import com.triplan.mapper.ItemMapper;
import com.triplan.mapper.RoomMapper;
import com.triplan.service.inf.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    @Autowired
    private final ItemMapper itemMapper;
    private final FlightMapper flightMapper;
    private final RoomMapper roomMapper;

    @Override
    public void itemSave(ItemVO itemVO) {
        itemMapper.insert(itemVO);
    }

    @Override
    public ItemVO itemRead(Integer itemId) {
        return itemMapper.select(itemId);
    }

    @Override
    public void itemModify(Integer itemId, ItemVO itemVO) {
        itemVO.setItemId(itemId);
        itemMapper.update(itemVO);
    }

    @Override
    public void itemRemove(Integer itemId) {
        itemMapper.delete(itemId);
    }

    @Override
    public ItemRoomResponseDTO getDetailRoomByItemId(ItemCategory room, Integer itemId) {

        ItemVO itemVO = itemMapper.getItemByItemId(room,itemId);
        if(Objects.isNull(itemVO)){
            return null;
        }
        RoomVO roomVO = roomMapper.getRoomByItemId(itemId);

        ItemRoomResponseDTO itemRoomResponseDTO = ItemRoomResponseDTO.of(itemVO);
        itemRoomResponseDTO.setRoomVO(roomVO);

        return itemRoomResponseDTO;
    }

    @Override
    public ItemFlightResponseDTO getDetailFlightByItemId(ItemCategory flight, Integer itemId) {

        ItemVO itemVO = itemMapper.getItemByItemId(flight,itemId);
        if(Objects.isNull(itemVO)){
            return null;
        }
        FlightVO flightVO = flightMapper.getFlightByItemId(itemId);

        ItemFlightResponseDTO itemFlightResponseDTO = ItemFlightResponseDTO.of(itemVO);
        itemFlightResponseDTO.setFlightVO(flightVO);

        return itemFlightResponseDTO;
    }

    @Override
    public void insertItemRoom(ItemRoomRequestDTO itemRoomRequestDTO) {

        ItemVO itemVO = itemRoomRequestDTO.toItemVO();
        RoomVO roomVO = itemRoomRequestDTO.toRoomVO();

        itemMapper.insert(itemVO);
        roomVO.setItemId(itemVO.getItemId());
        roomMapper.insert(roomVO);

    }

    @Override
    public void insertItemFlight(ItemFlightRequestDTO itemFlightRequestDTO) {

        ItemVO itemVO = itemFlightRequestDTO.toItemVO();
        FlightVO flightVO = itemFlightRequestDTO.toFlightVO();

        itemMapper.insert(itemVO);
        flightVO.setItemId(itemVO.getItemId());
        flightMapper.insert(flightVO);

    }
}
