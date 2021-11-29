package com.triplan.service.inf;

import com.triplan.dto.response.customer.CouponDTO;

public interface CouponService {
    
    void insert(CouponDTO couponDTO);

    CouponDTO select(Integer couponId);

    void update(Integer couponId, CouponDTO couponDTO);

    void delete(Integer couponId);

}
