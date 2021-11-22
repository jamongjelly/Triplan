package com.triplan.controller.api;

import com.triplan.dto.response.customer.CouponDTO;
import com.triplan.service.inf.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/coupons")
@RequiredArgsConstructor
public class ApiCouponController {

    private final CouponService couponService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String insert(@Valid @RequestBody CouponDTO couponDTO) {
        couponService.insert(couponDTO);
        return "couponCreated";
    }

    @GetMapping("/{couponId}")
    public CouponDTO select(@PathVariable Integer couponId) {
        return couponService.select(couponId);
    }

    @PutMapping("/{couponId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String update(@PathVariable Integer couponId, @Valid @RequestBody CouponDTO couponDTO) {
        couponService.update(couponId, couponDTO);
        return "couponUpdated";
    }

    @DeleteMapping("/{couponId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(@PathVariable Integer couponId) {
        couponService.delete(couponId);
        return "couponDeleted";
    }

}
