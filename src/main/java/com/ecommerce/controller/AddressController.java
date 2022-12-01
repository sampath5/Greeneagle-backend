package com.ecommerce.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.entity.Address;
import com.ecommerce.entity.User;
import com.ecommerce.model.AddressModel;
import com.ecommerce.model.ResponseModel;
import com.ecommerce.service.AddressService;
import com.ecommerce.service.UserService;

@RestController
@CrossOrigin
public class AddressController {

	@Autowired
	AddressService addService;
	
	@Autowired
	UserService userService;
	
	@GetMapping("/address/getAll")
	public ResponseEntity<List<Address>> getAllAddress(){
		String email=SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		User user= userService.getUserByEmail(email);
		List<Address> addList=addService.getAllByUser(user);
		addList.forEach(x->x.setUser(null));
		return new ResponseEntity(addList,HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/address/add")
	public ResponseEntity<ResponseModel> addAddress(@Valid @RequestBody AddressModel addressModel,Errors result){
		if (result.hasErrors()) {
			return new ResponseEntity<ResponseModel>(new ResponseModel(result.getFieldError().getDefaultMessage(),"Bad Request"), HttpStatus.BAD_REQUEST);
		}
		
		String email=SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		User user= userService.getUserByEmail(email);
		Address address = new Address();
		address.setAptNo(addressModel.getAptNo());
		address.setCity(addressModel.getCity());
		address.setState(addressModel.getState());
		address.setStreet(addressModel.getStreet());
		address.setUser(user);
		address.setZip(addressModel.getZip());
		addService.addAddress(address);
		return new ResponseEntity<ResponseModel>(new ResponseModel("Successfully added address",""),HttpStatus.ACCEPTED );
	}
	
	@GetMapping("/address/get")
	public ResponseEntity<AddressModel> getAddressById(@RequestParam int id){
		String email=SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		User user= userService.getUserByEmail(email);
		
		Address add=addService.getAddressById(id);
		if(add!=null) {
			AddressModel addModel=new AddressModel();
			addModel.setAptNo(add.getAptNo());
			addModel.setCity(add.getCity());
			addModel.setState(add.getState());
			addModel.setStreet(add.getStreet());
			
			addModel.setZip(add.getZip());
			return new ResponseEntity<>(addModel, HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping("/address/update")
	public ResponseEntity<Address> updateAddress(@RequestBody Address addModel){
		Address add=new Address();
		String email=SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		User user= userService.getUserByEmail(email);
		if(addService.getAddressesCount()<=0) {
			add.setAddressId(1);
		}
		if(user!=null) {
			add.setAptNo(addModel.getAptNo());
			add.setCity(addModel.getCity());
			add.setState(addModel.getState());
			add.setStreet(addModel.getStreet());
			add.setZip(addModel.getZip());
			add.setUser(user);
			
			return new ResponseEntity(addService.updateAddress(add),HttpStatus.ACCEPTED);
		}
		return new ResponseEntity(new ResponseModel("","Bad request"),HttpStatus.BAD_REQUEST);
	}
	
}
