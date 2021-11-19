package com.triplan.controller.api;

import com.triplan.domain.PaymentVO;
import com.triplan.dto.seller.response.PaymentResponseDTO;
import com.triplan.service.inf.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class ApiPaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public String create(@RequestBody PaymentVO paymentVO){
        paymentService.create(paymentVO);
        return "결제정보입력";
    }

    @GetMapping("/{paymentId}")
    public PaymentVO read(@PathVariable Integer paymentId){
        return paymentService.read(paymentId);
    }

    @PutMapping("/{paymentId}")
    public String update(@PathVariable Integer paymentId, @RequestBody PaymentVO paymentVO){
        paymentService.update(paymentId, paymentVO);
        return "결제정보수정";
    }

    @DeleteMapping("/{paymentId}")
    public String delete(@PathVariable Integer paymentId){
        paymentService.delete(paymentId);
        return "결제정보삭제";
    }

    // 메인 페이지
    // 매출 (Daily, Weekly)
    @GetMapping("/sellers/{sellerId}")
    public List<PaymentResponseDTO> readSellerId(@PathVariable Integer sellerId){
        return paymentService.readSellerId(sellerId);
    }
}
