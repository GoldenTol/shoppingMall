package com.shopping.controller;

import com.shopping.dto.ProductDto;
import com.shopping.dto.StudentDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "thymeleaf/rest/")
public class ThymeleafRestController {
    // http://localhost:8989/thymeleaf/rest/ex01

    @GetMapping(value = "ex01")
    public ProductDto thymeleafExam01(){
        ProductDto bean = new ProductDto();
        bean.setName("사과");
        bean.setPrice(1000);
        bean.setRegDate(LocalDateTime.now());

        return bean;
    }

    @GetMapping(value = "ex02")
    public List<ProductDto> thymeleafExam02(){
        List<ProductDto> beanList = new ArrayList<ProductDto>();

        String[] names = {"사과", "배", "감", "오렌지"};
        for (int i = 0; i < names.length; i++) {
            ProductDto bean = new ProductDto();
            bean.setName(names[i]);
            bean.setId((Long)(i +1L));
            bean.setPrice(1000*(i+1));
            bean.setRegDate(LocalDateTime.now());
            if(i%2 == 0){
                bean.setDescription("달고 맛있어요.");
            }else{
                bean.setDescription("별로 맛없어요.");
            }
            beanList.add(bean);
        }

        return beanList;
    }

    @GetMapping(value = "ex03")
    public List<StudentDto> thymeleafExam03(){
        List <StudentDto> beanList = new ArrayList<StudentDto>();

        String [] ids = {"lee", "kim", "son"};
        String [] names = {"이정선", "김경선", "손흥식"};
        int [][] jumsu = {{10, 60, 80}, {30, 40, 70}, {20, 90, 50}};

        for (int i = 0; i < names.length; i++) {
            StudentDto bean = new StudentDto(ids[i], names[i], jumsu[i][0], jumsu[i][1], jumsu[i][2]);
            beanList.add(bean);
        }

        return beanList;
    }

    @GetMapping(value = "ex04")
    public List<StudentDto> thymeleafExam04(){
        List <StudentDto> beanList = new ArrayList<StudentDto>();

        String [] ids = {null, null, null, null, null, null};
        String [] names = {"이주현", "손병훈", "강호사", "최경숙", "윤미현", "김준혁"};
        int [][] jumsu = {{10, 60, 80}, {30, 40, 70}, {20, 90, 50}, {10, 60, 80}, {30, 40, 70}, {20, 90, 50}};

        for (int i = 0; i < names.length; i++) {
            StudentDto bean = new StudentDto(ids[i], names[i], jumsu[i][0], jumsu[i][1], jumsu[i][2]);
            beanList.add(bean);
        }

        return beanList;
    }
}

/*
@GetMapping(value = "ex01")
    public String thymeleafExam01(){
        return null;
    }
*/