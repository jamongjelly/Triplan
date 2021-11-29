package com.triplan.mapper.member;

import com.triplan.domain.member.MemberCouponVO;
import com.triplan.dto.response.customer.MemberCouponDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MemberCouponMapper {

    void insert(MemberCouponVO memberCouponVO);

    MemberCouponVO select(Integer memberCouponId);

    void update(MemberCouponDTO memberCouponDTO);

    void delete(Integer memberCouponId);

    Integer ownCoupon(@Param("couponId") Integer couponId, @Param("memberId") Integer memberId);

    List<MemberCouponDTO> myCouponList(Integer memberId);

    List<MemberCouponDTO> myAvailableCouponList(Integer memberId);

    List<MemberCouponDTO> myUsedCouponList(Integer memberId);

    List<MemberCouponDTO> myUnavailableCouponList(Integer memberId);
}
