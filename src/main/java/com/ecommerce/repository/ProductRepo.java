package com.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ecommerce.entity.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {

	@Query(value="select * from product",nativeQuery =true)
	List<Product> findProducts();
	
	Product findByProductIdAndIsActiveTrue(Integer id);
	
	
	
	Product findByProductId(int id);
	
	List<Product> findByIsActiveTrue();
	
//	@Query(value="Update table Product set quantity=")
//	Product UpdateProduct();
}
