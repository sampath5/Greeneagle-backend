package com.ecommerce.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecommerce.entity.Product;
import com.ecommerce.repository.ProductRepo;

@Component
public class ProductDao {

	@Autowired
	ProductRepo prodRepo;
	
	public Product save(Product prod) {
		return prodRepo.save(prod);
	}
	
	public List<Product> getAllProducts() {
		return prodRepo.findProducts();
	}
	
	public Optional<Product> findById(int id) {
		return prodRepo.findById(id);
	}
	
	public List<Product> findAll() {		
		return prodRepo.findAll();
	}
	
	public List<Product> getActiveProducts(){
		return prodRepo.findByIsActiveTrue();
	}
	
	public List<Product> findAllById(List<Integer> ids){
		return prodRepo.findAllById(ids);
	}
	
	public Product getProductByIdAndIsActive(Integer id,boolean val) {
		return prodRepo.findByProductIdAndIsActiveTrue(id);
	}
	
	public Product getProductById(int id) {
		return prodRepo.getById(id);
	}
}
