package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Product;
import com.example.service.DisneyScrappingService;

@RestController()
public class DisneyScrapController {
	
	@Autowired
	DisneyScrappingService disneyScrappingService;

	
	@GetMapping("/{product_id}/{quantity}")
	public ResponseEntity<List<Product>> showMyBag(@PathVariable String product_id,@PathVariable String quantity){
		Product product=disneyScrappingService.findProduct(product_id, quantity);
		if(!product.getProduct_name().equals("Page Not Found"))
			disneyScrappingService.addToCart(product);		
		return new ResponseEntity<>(disneyScrappingService.showAllProducts(),HttpStatus.OK);
	}
	

}
