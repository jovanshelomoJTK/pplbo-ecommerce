package com.pplbo.productservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pplbo.productservice.model.Brand;
import com.pplbo.productservice.repository.BrandRepository;

@Service
public class BrandService {

    @Autowired
    private BrandRepository brandRepository;

    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    public Optional<Brand> getBrandById(Long id) {
        return brandRepository.findById(id);
    }

    public Brand createBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    // public Brand updateBrand(Long id, Brand brandDetails) {
    //     Brand brand = brandRepository.findById(id).orElseThrow(() -> new RuntimeException("Brand not found"));
    //     brand.setBrandName(brandDetails.getBrandName());
    //     brand.setLogo(brandDetails.getLogo());
    //     return brandRepository.save(brand);
    // }

    public Brand partialUpdateBrand(Long id, Brand brandDetails) {
        Brand brand = brandRepository.findById(id).orElseThrow(() -> new RuntimeException("Brand not found"));

        if (brandDetails.getBrandName() != null) {
            brand.setBrandName(brandDetails.getBrandName());
        }
        if (brandDetails.getLogo() != null) {
            brand.setLogo(brandDetails.getLogo());
        }

        return brandRepository.save(brand);
    }

    public void deleteBrand(Long id) {
        Brand brand = brandRepository.findById(id).orElseThrow(() -> new RuntimeException("Brand not found"));
        brandRepository.delete(brand);
    }
}
