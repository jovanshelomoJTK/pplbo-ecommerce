package com.pplbo.orderservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pplbo.orderservice.model.Test;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {

    List<Test> findByName(String name);

    List<Test> findAll();

}
