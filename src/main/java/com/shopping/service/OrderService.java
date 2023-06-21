package com.shopping.service;

import com.shopping.dto.OrderDto;
import com.shopping.dto.OrderHistDto;
import com.shopping.dto.OrderProductDto;
import com.shopping.entity.*;
import com.shopping.repository.MemberRepository;
import com.shopping.repository.OrderRepository;
import com.shopping.repository.ProductImageRepository;
import com.shopping.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;

    public Long order(OrderDto orderDto, String email) {
        Product prduct=productRepository.findById(orderDto.getProductId())
                .orElseThrow(EntityNotFoundException::new);

        Member member=memberRepository.findByEmail(email);

        List<OrderProduct>orderProductList=new ArrayList<>();

        OrderProduct orderProduct=OrderProduct.createOrderProduct(prduct, orderDto.getCount());

        orderProductList.add(orderProduct);

        Order order=Order.createOrder(member, orderProductList);

        orderRepository.save(order);

        return order.getId();
    }

    private final ProductImageRepository productImageRepository;

    public Page<OrderHistDto> getOrderList(String email, Pageable pageable){
        List<Order> orders = orderRepository.findOrders(email, pageable);

        Long totalCount = orderRepository.countOrder(email);


        List<OrderHistDto> orderHistDtos = new ArrayList<>();

        for(Order order : orders){
            OrderHistDto orderHistDto = new OrderHistDto(order);
            List<OrderProduct> orderProducts = order.getOrderProducts();

            for (OrderProduct bean : orderProducts){
                ProductImage productImage = productImageRepository.findByProductIdAndRepImageYesNo(bean.getProduct().getId(), "Y");

                OrderProductDto beanDto = new OrderProductDto(bean, productImage.getImageUrl());

                orderHistDto.addOrderProductDto(beanDto);
            }

            orderHistDtos.add(orderHistDto);
        }

        return new PageImpl<OrderHistDto>(orderHistDtos, pageable, totalCount);
    }

    public void cancelOrder(Long orderId){
        Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);
        order.cancelOrder();
    }

    public boolean validateOrder(Long orderId, String email){
        Member curMember = memberRepository.findByEmail(email);
        Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);
        Member savedMember = order.getMember();

        if(StringUtils.equals(curMember.getEmail(), savedMember.getEmail())){
            return true;
        }else{
            return false;
        }
    }

    public Long orders(List<OrderDto> orderDtoList, String email){
        Member member = memberRepository.findByEmail(email);

        List<OrderProduct> orderProductList = new ArrayList<>();

        for(OrderDto dto : orderDtoList){
            Long productid = dto.getProductId();
            Product product = productRepository.findById(productid).orElseThrow(EntityNotFoundException::new);
            int count = dto.getCount();
            OrderProduct orderProduct = OrderProduct.createOrderProduct(product, count);

            orderProductList.add(orderProduct);
        }

        Order order = Order.createOrder(member, orderProductList);
        orderRepository.save(order);

        return order.getId();
    }
}
