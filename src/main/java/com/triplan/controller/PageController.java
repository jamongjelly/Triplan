//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.triplan.controller;

import com.triplan.domain.cs.QuestionVO;
import com.triplan.domain.item.ItemGroupVO;
import com.triplan.domain.item.PaymentVO;
import com.triplan.dto.response.customer.ItemRoomResponseDTO;
import com.triplan.dto.response.customer.MemberCouponDTO;
import com.triplan.dto.response.customer.ReservationDTO;
import com.triplan.enumclass.item.ItemCategory;
import com.triplan.security.CurrentMember;
import com.triplan.security.MemberPrincipal;
import com.triplan.service.inf.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PageController {

    private final MemberCouponService memberCouponService;
    private final ItemGroupService itemGroupService;
    private final ItemService itemService;
    private final PaymentService paymentService;
    private final ReservationService reservationService;
    private final QuestionService questionService;

    @GetMapping("/warning")
    public String warning() {
        return "warning";
    }

    @GetMapping
    public String index(@CurrentMember MemberPrincipal currentMember, Model model) {
        model.addAttribute("member", currentMember);
        return "index";
    }

    @GetMapping("/main")
    public String main(@CurrentMember MemberPrincipal currentMember, Model model) {
        model.addAttribute("member", currentMember);
        return "index";
    }

    // Customer Service (CS)
    @GetMapping("/notice")
    public String notice(@CurrentMember MemberPrincipal currentMember, Model model) {
        model.addAttribute("member", currentMember);
        return "cs/notice";
    }

    @GetMapping("/notcon")
    public String notice_content(@CurrentMember MemberPrincipal currentMember, Model model) {
        model.addAttribute("member", currentMember);
        return "cs/notice_content";
    }

    @GetMapping("/notupd")
    public String notice_update(@CurrentMember MemberPrincipal currentMember, Model model) {
        model.addAttribute("member", currentMember);

        if (currentMember == null) {
            return "member/login";
        } else {
            return "cs/notice_update";
        }
    }

    @GetMapping("/notwri")
    public String notice_write(@CurrentMember MemberPrincipal currentMember, Model model) {
        model.addAttribute("member", currentMember);

        if (currentMember == null) {
            return "member/login";
        } else {
            return "cs/notice_write";
        }
    }

    @GetMapping("/qna")
    public String qna(@CurrentMember MemberPrincipal currentMember, Model model) {
        model.addAttribute("member", currentMember);
        return "cs/qna";
    }

    @GetMapping("/qnacon")
    public String qnacon(
            @RequestParam(required = false) Integer questionId,
            @CurrentMember MemberPrincipal currentMember,
            Model model
    ) {
        model.addAttribute("member", currentMember);

        if (questionId == null) {
            return "cs/qna";
        } else {
            QuestionVO questionVO = questionService.getQuestion(questionId);
            model.addAttribute("question", questionVO);
            return "cs/qna_content";
        }
    }

    @GetMapping("/qnaupd")
    public String qnaupd(@CurrentMember MemberPrincipal currentMember, Model model) {
        model.addAttribute("member", currentMember);

        if (currentMember == null) {
            return "member/login";
        } else {
            return "cs/qna_update";
        }
    }

    @GetMapping("/qnawri")
    public String qnawri(@CurrentMember MemberPrincipal currentMember, Model model) {
        model.addAttribute("member", currentMember);

        if (currentMember == null) {
            return "member/login";
        } else {
            return "cs/qna_write";
        }
    }


    // Item
    @GetMapping("/pay") // 예약/결제창
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public String pay(Model model,
                      @RequestParam Integer itemGroupId,
                      @RequestParam Integer itemId,
                      @RequestParam String startDate,
                      @RequestParam String endDate,
                      @CurrentMember MemberPrincipal currentMember
    ) {
        // URL로 받아올거 : memberId, sellerId, itemId, itemGroupId, startDate, endDate
        // 프론트로 넘겨줄거 :  쿠폰목록, 아이템그룹정보, 아이템정보, 상품 시작/종료기간, memberId, sellerId
        Integer memberId = currentMember.getMemberId();
        Integer sellerId = itemGroupService.getItemGroup(itemGroupId).getSellerId();

        // 내 쿠폰 목록 - 쿠폰id, 쿠폰명, 할인금액
        List<MemberCouponDTO> couponList = memberCouponService.myAvailableCouponList(memberId);
        model.addAttribute("member", currentMember);
        model.addAttribute("couponList", couponList);
        // 아이템 그륩 정보 - 상품그룹명
        ItemGroupVO itemGroupVO = itemGroupService.getItemGroup(itemGroupId);
        model.addAttribute("itemGroup", itemGroupVO);
        // 아이템 정보 - 상품명
        ItemRoomResponseDTO itemRoomResponseDTO = itemService.getDetailRoomByItemId(ItemCategory.ROOM, itemId);
        model.addAttribute("item", itemRoomResponseDTO);
        // 상품 시작, 종료 기간
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        // memberId
        model.addAttribute("memberId", memberId);
        // sellerId
        model.addAttribute("sellerId", sellerId);

        return "item/pay";
    }

    @GetMapping("/payAfter")
    public String payAfter(@CurrentMember MemberPrincipal currentMember, Model model) {
        model.addAttribute("member", currentMember);

        if (currentMember == null) {
            return "member/login";
        } else {
            return "item/payAfter";
        }
    }

    @GetMapping("pay/payInfo/{paymentId}/{itemId}")
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public String paymentCheck(
            @PathVariable("paymentId") Integer paymentId,
            @PathVariable("itemId") Integer itemId, Model model,
            @CurrentMember MemberPrincipal currentMember
    ) {
        Integer memberId = currentMember.getMemberId();

        PaymentVO paymentVO = paymentService.read(paymentId, memberId); // 결제테이블 정보
        ReservationDTO reservationDTO = reservationService.select(paymentVO.getResId(), memberId); // 예약테이블 정보
        String name = paymentService.readNameByItem(itemId); // 상품이름

        model.addAttribute("member", currentMember);
        model.addAttribute("payment" , paymentVO);
        model.addAttribute("reservation", reservationDTO);
        model.addAttribute("itemName", name);

        return "item/payAfter";
    }

    @GetMapping("/product")
    public String product(@CurrentMember MemberPrincipal currentMember, Model model) {
        model.addAttribute("member", currentMember);
        return "item/product";
    }

    @GetMapping("/prodet")
    public String prodet(@CurrentMember MemberPrincipal currentMember, Model model) {
        model.addAttribute("member", currentMember);
        return "item/product_detail";
    }

    @GetMapping("/proqnacon")
    public String proqnacon(
            @RequestParam(required = false) Integer questionId,
            @CurrentMember MemberPrincipal currentMember, Model model) {
        model.addAttribute("member", currentMember);

        if (questionId != null) {
            QuestionVO questionVO = questionService.getQuestion(questionId);
            if (questionVO.getQuestionId() != null) {
                model.addAttribute("question", questionVO);
                return "item/product_detail_qna_content";
            }
        }

        return "warning";
    }

    @GetMapping("/proqnaupd")
    public String proqnaupd(@CurrentMember MemberPrincipal currentMember, Model model) {
        model.addAttribute("member", currentMember);

        if (currentMember == null) {
            return "member/login";
        } else {
            return "item/product_detail_qna_update";
        }
    }

    @GetMapping("/proqnawri")
    public String proqnawri(@CurrentMember MemberPrincipal currentMember, Model model) {
        model.addAttribute("member", currentMember);

        if (currentMember == null) {
            return "member/login";
        } else {
            return "item/product_detail_qna_write";
        }
    }


    // Member
    @GetMapping("/join")
    public String join() {
        return "member/join";
    }

    @GetMapping("/login")
    public String login() {
        return "member/login";
    }

    @GetMapping("/memwithdraw")
    public String memwithdraw(@CurrentMember MemberPrincipal currentMember, Model model) {
        model.addAttribute("member", currentMember);

        if (currentMember == null) {
            return "member/login";
        } else {
            return "member/member_withdraw";
        }
    }

    @GetMapping("/mypagecoupon")
    public String mypagecoupon(@CurrentMember MemberPrincipal currentMember, Model model) {
        model.addAttribute("member", currentMember);

        if (currentMember == null) {
            return "member/login";
        } else {
            return "member/mypage_coupon";
        }
    }

    @GetMapping("/mypagecoupondisabled")
    public String mypagecoupondisabled(@CurrentMember MemberPrincipal currentMember, Model model) {
        model.addAttribute("member", currentMember);

        if (currentMember == null) {
            return "member/login";
        } else {
            return "member/mypage_coupon_disabled";
        }
    }

    @GetMapping("/mypagecouponexpired")
    public String mypagecouponexpired(@CurrentMember MemberPrincipal currentMember, Model model) {
        model.addAttribute("member", currentMember);

        if (currentMember == null) {
            return "member/login";
        } else {
            return "member/mypage_coupon_expired";
        }
    }

    @GetMapping("/mypagemember")
    public String mypage(@CurrentMember MemberPrincipal currentMember, Model model) {
        model.addAttribute("member", currentMember);

        if (currentMember == null) {
            return "member/login";
        } else {
            return "member/mypage_modify";
        }
    }

    @GetMapping("/myReservation")
    public String myReservation(@CurrentMember MemberPrincipal currentMember, Model model) {
        model.addAttribute("member", currentMember);

        if (currentMember == null) {
            return "member/login";
        } else {
            return "member/mypage_reservation";
        }
    }

    @GetMapping("/wishlist")
    public String wishlist(@CurrentMember MemberPrincipal currentMember, Model model) {
        model.addAttribute("member", currentMember);

        if (currentMember == null) {
            return "member/login";
        } else {
            return "member/wishlist";
        }
    }

}
