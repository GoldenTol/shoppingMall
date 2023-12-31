package com.shopping.service;

import com.shopping.dto.MainProductDto;
import com.shopping.dto.ProductFormDto;
import com.shopping.dto.ProductImageDto;
import com.shopping.dto.ProductSearchDto;
import com.shopping.entity.Product;
import com.shopping.entity.ProductImage;
import com.shopping.repository.ProductImageRepository;
import com.shopping.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductImageService productImageService;

    // 상품등록
    public Long saveProduct(ProductFormDto dto, List<MultipartFile>uploadedFile) throws Exception{
        Product product=dto.createProduct();
        productRepository.save(product);

        // 상품에 들어가는 각 이미지들
        for (int i = 0; i < uploadedFile.size(); i++) {
            ProductImage productImage=new ProductImage();
            productImage.setProduct(product);

            if(i==0){
                productImage.setRepImageYesNo("Y");
            }else{
                productImage.setRepImageYesNo("N");
            }
            productImageService.saveProductImage(productImage, uploadedFile.get(i));
        }

        return product.getId().longValue();
    }

    private final ProductImageRepository productImageRepository;

    public ProductFormDto getProductDetail(Long productId){
        List<ProductImage> productImageList=productImageRepository.findByProductIdOrderByIdAsc(productId);

        List<ProductImageDto> productImageDtoList=new ArrayList<ProductImageDto>();

        for(ProductImage productImg : productImageList){
            ProductImageDto productImgDto=ProductImageDto.of(productImg);

            System.out.println("ImageUrl");
            System.out.println(productImg.getImageUrl());

            productImageDtoList.add(productImgDto);

        }

        Product product=productRepository.findById(productId).orElseThrow(EntityNotFoundException::new);

        ProductFormDto dto=ProductFormDto.of(product);


        dto.setProductImageDtoList(productImageDtoList);

        return dto;
    }

    public Long updateProduct(ProductFormDto dto, List<MultipartFile>uploadedFile) throws Exception {
        Product product=productRepository.findById(dto.getId()).orElseThrow(EntityNotFoundException::new);

        product.updateProduct(dto);

        List<Long>productImageIds=dto.getProductImageIds();

        for (int i = 0; i < uploadedFile.size(); i++) {
            productImageService.updateProductImage(productImageIds.get(i), uploadedFile.get(i));
        }

        return product.getId();
    }

    public Page<Product>getAdminProductPage(ProductSearchDto dto, Pageable pageable) {
        return productRepository.getAdminProductPage(dto, pageable);
    }

    public Page<MainProductDto>getMainProductPage(ProductSearchDto dto, Pageable pageable){
        return productRepository.getMainProductPage(dto, pageable);
    }
}
