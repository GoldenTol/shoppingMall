package com.shopping.repository;

import com.shopping.dto.MemberFormDto;
import com.shopping.entity.Cart;
import com.shopping.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

@SpringBootTest
@Transactional
public class CartTest {
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    CartRepository cartRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName("카트와 회원 매핑 테스트")
    public void findCartAndMemberTest(){
        Member member=createMember();
        memberRepository.save(member);

        Cart cart=new Cart();
        cart.setMember(member);
        cartRepository.save(cart);

        em.flush();
        em.clear();

        Cart savedCart=cartRepository.findById(cart.getId())
                .orElseThrow(EntityNotFoundException::new);

        System.out.println(savedCart);
    }

    @Autowired
    PasswordEncoder passwordEncoder;

    private Member createMember() {
        MemberFormDto dto=new MemberFormDto();
        dto.setPassword("1234");
        dto.setAddress("가산동");
        dto.setName("김만식");
        dto.setEmail("aa@naver.com");

        return Member.createMember(dto, passwordEncoder);
    }
}
