package com.triplan.service;

import com.triplan.domain.PaymentVO;
import com.triplan.mapper.MemberMapper;
import com.triplan.mapper.PaymentMapper;
import com.triplan.service.inf.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentMapper paymentMapper;
    private final MemberMapper memberMapper;

    @Override
    @Transactional
    public void create(PaymentVO paymentVO) {
        Integer memberId = 1;
        paymentMapper.insert(paymentVO);
        Long yearPayment = paymentMapper.getYearPayment(memberId);
        memberMapper.updateGrade(memberId,yearPayment);
    }

    @Override
    public PaymentVO read(Integer paymentId) {
        return paymentMapper.select(paymentId);
    }

    @Override
    public void update(Integer paymentId, PaymentVO paymentVO) {
        paymentVO.setPaymentId(paymentId);
        paymentMapper.update(paymentVO);
    }

    @Override
    public void delete(Integer paymentId) {
        paymentMapper.delete(paymentId);
    }


}
