package com.example.demo.repo;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Product;

//@Repository
public interface ProductRepository
		extends JpaRepository<Product, Integer> {
	
	@Transactional
	@Modifying
	@Query("UPDATE Product SET prodCode=:prodCode WHERE prodId=:prodId")
	public Integer updateProdCodeById(Integer prodId,String prodCode);

}
