package com.shopping.controller;

import com.shopping.dto.CartDetailDto;
import com.shopping.dto.CartOrderDto;
import com.shopping.dto.CartProductDto;
import com.shopping.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping(value = "/cart")
    public @ResponseBody ResponseEntity addCart(@RequestBody @Valid CartProductDto cartProductDto, BindingResult error, Principal principal){
        if(error.hasErrors()){
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = new ArrayList<>();

            for(FieldError err : fieldErrors){
                sb.append(err.getDefaultMessage());
            }

            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }
        String email = principal.getName();
        Long cartProductId = 0L;

        try {
            cartProductId = cartService.addCart(cartProductDto, email);
            return new ResponseEntity<Long>(cartProductId, HttpStatus.OK);
        }catch (Exception err){
            err.printStackTrace();
            return new ResponseEntity<String>(err.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/cart")
    public String orderHistory(Principal principal, Model model){
        String email = principal.getName();
        List<CartDetailDto> cartDetailDtoList = cartService.getCartList(email);
        model.addAttribute("cartProducts", cartDetailDtoList);

        return "cart/cartList";
    }

    @DeleteMapping(value = "/cartProduct/{cartProductId}")
    public @ResponseBody ResponseEntity deleteCartProduct(@PathVariable("cartProductId") Long cartProductId, Principal principal){ //카트에서 특정 상품 삭제

        String email = principal.getName();
        if(cartService.validateCartProduct(cartProductId, email)==false){
            return new ResponseEntity<String>("삭제 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }
        cartService.deleteCartProduct(cartProductId);
        return new ResponseEntity<Long>(cartProductId, HttpStatus.OK);
    }

    @PatchMapping(value = "/cartProduct/{cartProductId}")
    public @ResponseBody ResponseEntity updateCartProduct(@PathVariable("cartProductId") Long cartProductId, int count, Principal principal){

        if(count <= 0){
            return new ResponseEntity<String>("수량은 최소 1개 이상이어야 합니다.", HttpStatus.BAD_REQUEST);
        }
        String email = principal.getName();
        if(cartService.validateCartProduct(cartProductId, email)==false){
            return new ResponseEntity<String>("수정 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }
        cartService.updateCartProductCount(cartProductId, count);
        return new ResponseEntity<Long>(cartProductId, HttpStatus.OK);
    }

    @PostMapping(value = "/cart/orders")
    public @ResponseBody ResponseEntity orderCartProduct(@RequestBody CartOrderDto cartOrderDto, Principal principal){
        List<CartOrderDto> cartOrderDtoList = cartOrderDto.getCartOrderDtoList();

        if(cartOrderDtoList == null || cartOrderDtoList.size() == 0){
            return new ResponseEntity<>("주문할 상품을 선택해 주세요", HttpStatus.BAD_REQUEST);
        }

        String email = principal.getName();

        for(CartOrderDto dto : cartOrderDtoList){
            boolean bool = cartService.validateCartProduct(dto.getCartProductId(), email);

            if(bool == false){
                return new ResponseEntity<String>("주문 권한이 없습니다.", HttpStatus.FORBIDDEN);
            }
        }

        Long orderId = cartService.orderCartProduct(cartOrderDtoList, email);

        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }
}
