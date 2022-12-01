package com.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.dao.ProductDao;
import com.ecommerce.entity.Product;

@Service
public class ProductService {
	
	@Autowired
	ProductDao productDao;
	
	public List<Product> getAllProducts(){
		return productDao.getAllProducts();
	}
	
	public Product addProduct(Product prod) {
		return productDao.save(prod);
	}
	
	public Optional<Product> findById(int id) {
		return productDao.findById(id);
	}
	
	public Product getProductById(int id) {
		return productDao.getProductById(id);
	}
	
	public List<Product> getActiveProducts(){
		return productDao.getActiveProducts();
	}
	
	public Product getProductByIdAndIsActive(int id, boolean val) {
		return productDao.getProductByIdAndIsActive(id, val);
	}
	
	public List<Product> findAllById(List<Integer> ids){
		return productDao.findAllById(ids);
	}
}
