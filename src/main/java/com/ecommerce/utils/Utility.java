package com.ecommerce.utils;

import org.springframework.stereotype.Component;

import com.ecommerce.entity.Product;
import com.ecommerce.model.Item;

@Component
public class Utility {

	public Product copyToProduct(Item item) {
		Product product=new Product();
		product.setActive(item.isActive());
		product.setBrand(item.getBrand());
		product.setCategory(item.getCategory());
		product.setDescription(item.getDiscription());
		product.setModel(item.getModel());
		product.setPrice(item.getPrice());
		product.setPrimaryImage(product.getPrimaryImage());
		product.setProductName(item.getItemName());
		product.setQuantity(item.getQuantity());
		
		return product;
	}
	
	public Item copyToItem(Product product) {
		Item item=new Item();
		item.setActive(product.isActive());
		item.setBrand(product.getBrand());
		item.setCategory(product.getCategory());
		item.setDiscription(product.getDescription());
		item.setItemName(product.getProductName());
		item.setModel(product.getModel());
		item.setPrice(product.getPrice());
		item.setPrimaryImage(product.getPrimaryImage());
		item.setQuantity(product.getQuantity());
		
		return item;
	}
	
	
}
