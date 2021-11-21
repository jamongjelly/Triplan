package com.triplan.service;

import com.triplan.domain.ItemScheduleVO;
import com.triplan.domain.ReservationVO;
import com.triplan.dto.ReservationDTO;
import com.triplan.dto.response.Pagination;
import com.triplan.exception.ResourceNotFoundException;
import com.triplan.mapper.ItemScheduleMapper;
import com.triplan.mapper.ReservationMapper;
import com.triplan.service.inf.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationMapper reservationMapper;
    private final ItemScheduleMapper itemScheduleMapper;

    @Transactional
    public void insert(ReservationDTO reservationDTO) {
        ReservationVO reservationVO = reservationDTO.toVO();
        reservationMapper.insert(reservationVO);
    }

    @Override
    public ReservationDTO select(Integer resId) {
        ReservationVO reservationVO = reservationMapper.select(resId);
        return ReservationDTO.of(reservationVO);
    }

    @Override
    public void update(Integer resId, ReservationDTO reservationDTO) {
        reservationDTO.setResId(resId);
        reservationMapper.update(reservationDTO);
        // name, phone 외의 모든 컬럼이 null이어도
        // DB에 들어있던 item_category 같은 값이 null로
        // 바뀌지 않는다
    }

    @Override
    public void delete(Integer resId) {
        reservationMapper.delete(resId);
    }

    @Override
    @Transactional
    public Integer reserve(
            Integer memberCouponId,
            ReservationDTO reservationDTO,
            Integer itemId, LocalDateTime startDate, LocalDateTime endDate
    ) {
        Integer result = -1;
        ReservationVO reservationVO = reservationDTO.toVO();
        Integer resId = null;

        // 쿠폰 사용 처리
        if (memberCouponId != null) {
            if(memberCouponId == 0) {
                // 쿠폰 적용 안 한 경우
                reservationMapper.insert(reservationVO);
                // 생성된 res_id 불러오기
                resId = reservationVO.getResId();
                // resId - resId 매칭하여 RESERVATION_ITEM 테이블에 insert
//                reservationMapper.insertResItem(resId, itemScheduleId);
                result = 1;
            } else {
                // 쿠폰 적용 한 경우 : 사용 가능한 쿠폰인지 확인(유효 기간, 사용 여부)
                Integer memberId = reservationVO.getMemberId();
                result = reservationMapper.checkCoupon(memberCouponId);
                if (result == 1) {
                    result = reservationMapper.checkMemberCoupon(memberCouponId, memberId);
                    if (result == 1) {
                        // result == 0 : 쿠폰 사용 불가 - 유효기관 경과 or 이미 사용한 쿠폰
                        // result == 1 : 쿠폰 사용 처리(MEMBER_COUPON)
                        reservationMapper.insert(reservationVO);
                        resId = reservationVO.getResId();
//                        reservationMapper.insertResItem(resId, itemScheduleId);
                        reservationMapper.useCoupon(resId, memberCouponId, memberId);
                    }
                }
            }

            // 지정된 기간에 해당하는 ItemScheduleVO를 가져온 뒤 재고수량이 0이면 예외 발생
            List<ItemScheduleVO> itemScheduleVOList = itemScheduleMapper.selectByDate(itemId, startDate, endDate)
                    .stream().peek(itemScheduleVO -> {
                       if (itemScheduleVO.getStock() == 0) {
                           throw new ResourceNotFoundException(itemId + "번 상품의 재고가 부족합니다.");
                       }
                    }).collect(Collectors.toList());
            
            // 저장완료 후 아이템 스케줄 추가 + 상품 수량 설정
            for (ItemScheduleVO itemScheduleVO : itemScheduleVOList) {
                Integer itemScheduleId = itemScheduleVO.getItemScheduleId();

                reservationMapper.insertResItem(resId, itemScheduleId);
                reservationMapper.updateStockByItemSchedule(itemScheduleId);
                reservationMapper.updateSalesVolumeByItem(itemScheduleId);
            }
        }

//        return result;
        return resId;
    }

    @Override
    @Transactional
    public Integer cancel(Integer resId, ReservationDTO reservationDTO) {
        Integer result = -1;
        reservationDTO.setResId(resId);
        Integer memberId = reservationMapper.getMemberId(resId);
        // 쿠폰 반환 처리
        result = reservationMapper.checkExpiredCoupon(resId);
        if (result != -1 && result != 0) {
            // 쿠폰 유효 기간이 지나지(result == 0) 않았으면 반환 처리
            reservationMapper.giveBackCoupon(resId, memberId);
        }
        reservationMapper.cancel(reservationDTO);
        return result;
    }

    @Override
    public List<ReservationDTO> myResList(Integer memberId) {
        List<ReservationVO> reservationVOList = reservationMapper.myResList(memberId);
        List<ReservationDTO> reservationDTOList = reservationVOList.stream()
                .map(ReservationDTO::of).collect(Collectors.toList());
        // ReservationDTO :: of 란?
        // 람다식에서 Reservation -> ReservationDTO.of(memberId) 처럼 들어오는 매개변수랑 of 매개변수랑 같을 때 :: 을 사용해 처리
        // https://github.com/jamongjelly/Triplan/blob/edu_vo-dto/seller/src/main/java/com/triplan/service/ItemGroupServiceImpl.java
        return reservationDTOList;
    }

    @Override
    public List<ReservationDTO> myUpcomingResList(Integer memberId) {
        List<ReservationVO> reservationVOList = reservationMapper.myUpcomingResList(memberId);
        List<ReservationDTO> reservationDTOList = reservationVOList.stream()
                .map(ReservationDTO::of).collect(Collectors.toList());
        return reservationDTOList;
    }

    @Override
    public List<ReservationDTO> myPastResList(Integer memberId) {
        List<ReservationVO> reservationVOList = reservationMapper.myPastResList(memberId);
        List<ReservationDTO> reservationDTOList = reservationVOList.stream()
                .map(ReservationDTO::of).collect(Collectors.toList());
        return reservationDTOList;
    }

    @Override
    public List<ReservationDTO> myCancelledResList(Integer memberId) {
        List<ReservationVO> reservationVOList = reservationMapper.myCancelledResList(memberId);
        List<ReservationDTO> reservationDTOList = reservationVOList.stream()
                .map(ReservationDTO::of).collect(Collectors.toList());
        return reservationDTOList;
    }

    /* 페이징 처리 */
    @Override
    public Pagination<ReservationDTO> myPagedResList(Integer memberId, Integer pageSize, Integer currentPage) {
        List<ReservationDTO> pagedResList = reservationMapper.myPagedResList(pageSize, currentPage);
        Integer totalReservations = reservationMapper.count(memberId);

        return new Pagination<>(pageSize, currentPage, totalReservations, pagedResList);
    }

}
