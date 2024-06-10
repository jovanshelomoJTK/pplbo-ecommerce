package com.pplbo.productservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pplbo.productservice.model.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

    // List<Test> findByName(String name);
    List<Brand> findAll();

}
