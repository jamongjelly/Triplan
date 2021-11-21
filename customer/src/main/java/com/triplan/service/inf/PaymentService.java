package com.triplan.service.inf;

import com.triplan.domain.PaymentVO;

public interface PaymentService {

    void create(PaymentVO paymentVO, Integer memberId);

    PaymentVO read(Integer paymentId);

    void update(Integer paymentId, PaymentVO paymentVO);

    void delete(Integer paymentId);

    String readNameByItem(Integer resId);
}
