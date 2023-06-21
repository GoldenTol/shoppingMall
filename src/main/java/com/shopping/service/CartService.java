package com.shopping.service;

import com.shopping.dto.CartDetailDto;
import com.shopping.dto.CartOrderDto;
import com.shopping.dto.CartProductDto;
import com.shopping.dto.OrderDto;
import com.shopping.entity.Cart;
import com.shopping.entity.CartProduct;
import com.shopping.entity.Member;
import com.shopping.entity.Product;
import com.shopping.repository.CartProductRepository;
import com.shopping.repository.CartRepository;
import com.shopping.repository.MemberRepository;
import com.shopping.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final CartProductRepository cartProductRepository;

    public Long addCart(CartProductDto cartProductDto, String email){
        Member member = memberRepository.findByEmail(email);
        Long memberId = member.getId();

        Cart cart = cartRepository.findByMemberId(memberId);
        if(cart == null){
            cart = Cart.createCart(member);
            cartRepository.save(cart);
        }

        Long productId = cartProductDto.getProductId();
        Product product = productRepository.findById(productId).orElseThrow(EntityNotFoundException::new);

        Long cartId = cart.getId();

        CartProduct savedCartProduct = cartProductRepository.findByCartIdAndProductsId(cartId, productId);

        int count = cartProductDto.getCount();

        if(savedCartProduct != null){
            savedCartProduct.addCount(cartProductDto.getCount());
            cartProductRepository.save(savedCartProduct);
            return savedCartProduct.getId();
        }else{
            savedCartProduct = CartProduct.createCartProduct(cart, product, count);
        }

        cartProductRepository.save(savedCartProduct);

        return savedCartProduct.getId();
    }

    @Transactional(readOnly = true)
    public List<CartDetailDto> getCartList(String email){
        Member member =  memberRepository.findByEmail(email);

        Long memberId = member.getId();
        Cart cart = cartRepository.findByMemberId(memberId);

        List<CartDetailDto> cartDetailDtoList = new ArrayList<>();

        if(cart == null){
            return cartDetailDtoList;
        }else{
            cartDetailDtoList = cartProductRepository.findCartDetailDtoList(cart.getId());
            return cartDetailDtoList;
        }
    }

    public void deleteCartProduct(Long cartProductId){
        CartProduct cartProduct = cartProductRepository.findById(cartProductId).orElseThrow(EntityNotFoundException::new);
        cartProductRepository.delete(cartProduct);
    }

    @Transactional(readOnly = true)
    public boolean validateCartProduct(Long cartProductId, String email) {
        Member current = memberRepository.findByEmail(email);
        CartProduct cartProduct = cartProductRepository.findById(cartProductId)
                .orElseThrow(EntityNotFoundException::new);

        Member saved = cartProduct.getCart().getMember();

        if (StringUtils.equals(current.getEmail(), saved.getEmail())) {
            return true;
        } else {
            return false;
        }
    }

    public void updateCartProductCount(Long cartProductId, int count){
        CartProduct cartProduct = cartProductRepository.findById(cartProductId).orElseThrow(EntityNotFoundException::new);
        cartProduct.updateCount(count);
        cartProductRepository.save(cartProduct);
    }

    private final OrderService orderService;

    public Long orderCartProduct(List<CartOrderDto> cartOrderDtoList, String email){
        List<OrderDto> orderDtoList = new ArrayList<>();

        for(CartOrderDto dto : cartOrderDtoList){
            Long productId = dto.getCartProductId();
            CartProduct cartProduct = cartProductRepository.findById(productId).orElseThrow(EntityNotFoundException::new);

            OrderDto orderDto = new OrderDto();
            orderDto.setProductId(cartProduct.getProducts().getId());
            orderDto.setCount(cartProduct.getCount());
            orderDtoList.add(orderDto);
        }

        Long orderId = orderService.orders(orderDtoList, email);

        for(CartOrderDto dto : cartOrderDtoList){
            Long productId = dto.getCartProductId();
            CartProduct cartProduct = cartProductRepository.findById(productId).orElseThrow(EntityNotFoundException::new);

            cartProductRepository.delete(cartProduct);
        }

        return orderId;
    }
}
