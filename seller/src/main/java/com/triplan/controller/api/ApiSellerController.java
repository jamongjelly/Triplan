package com.triplan.controller.api;

import com.triplan.domain.SellerVO;
import com.triplan.service.inf.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/sellers")
@RequiredArgsConstructor
public class ApiSellerController {

    private final SellerService sellerService;

    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
    public String register(@Valid @RequestPart("seller") SellerVO sellerVO,
                           @Valid @RequestPart("file") List<MultipartFile> files) {
        sellerService.register(sellerVO, files);
        return "판매자 추가 성공";
    }

    @GetMapping("/{sellerId}")
    public SellerVO getSeller(@PathVariable Integer sellerId) {
        return sellerService.getSeller(sellerId);
    }

    @PutMapping("/{sellerId}")
    public String update(@Valid @PathVariable Integer sellerId,
                         @Valid @RequestBody SellerVO sellerVO,
                         @Valid @RequestParam("file") List<MultipartFile> files) {
        sellerService.update(sellerId, sellerVO, files);
        return "판매자 수정 성공";
    }

    @DeleteMapping("/{sellerId}")
    public String delete(@PathVariable Integer sellerId) {
        sellerService.delete(sellerId);
        return "판매자 삭제 성공";
    }

}