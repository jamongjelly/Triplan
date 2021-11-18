package com.triplan.service;

import com.triplan.domain.ReservationVO;
import com.triplan.dto.ReservationDTO;
import com.triplan.mapper.ReservationMapper;
import com.triplan.service.inf.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationMapper reservationMapper;

    @Override
    public ReservationDTO getReservation(Integer resId) {
        ReservationVO reservationVO = reservationMapper.select(resId);
        return ReservationDTO.of(reservationVO);
    }

    @Override
    public Integer getCountReservation(Integer sellerId) {
        Integer resCount = reservationMapper.selectCount(sellerId);
        return resCount;
    }

    @Override
    public void update(Integer resId, ReservationDTO reservationDTO) {
        reservationDTO.setResId(resId);
        reservationMapper.update(reservationDTO);
    }

    @Override
    public void delete(Integer resId) {
        reservationMapper.delete(resId);
    }

    @Override
    public Integer reserve(ReservationDTO reservationDTO) {
        ReservationVO reservationVO = reservationDTO.toVO();
        Integer result = reservationMapper.insert(reservationVO);
        return result;
    }

}
