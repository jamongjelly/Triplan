package com.triplan.domain;

import com.triplan.enumclass.Type;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
@Builder
public class ReservationVO {
    private Long resId;
    private Long memberId;
    private Long itemId;
    private Long totalPrice;
    private Long discountPrice;
    private Long finalPrice;
    private Date resDate;
    private Long numberOfPerson;
    private String coupon;
    private String name;
    private String phone;
    private Type type;
    private boolean usage1;
    private Date completeAt;
    private boolean paymentUsage;
    private Date createdAt;
    private Date updatedAt;
}