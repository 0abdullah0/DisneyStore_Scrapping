package com.example.service;

import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Product;
import com.example.repository.ProductRepo;

@Service
public class DisneyScrappingService {
	
	@Autowired
	ProductRepo productRepo;
	
	public Product findProduct(String product_id,String quantity){
		Product product=new Product();
		 try {
			 
			 Document webPage = Jsoup
				        .connect("https://www.shopdisney.co.uk/"+product_id+".html?quantity="+quantity+"")
				        .ignoreHttpErrors(true)
				        .get();
			
			 String nameOfProduct = webPage.getElementsByTag("title").get(0).text();

			 if(nameOfProduct.equals("Page Not Found"))
			 {
				 product.setProduct_name(nameOfProduct);
				 return product;
			 }
			 else
			 {
				 String priceofProduct = webPage.getElementsByClass("prices").get(0).
							getElementsByClass("price").get(0).getElementsByClass("sales").get(0).text();
				 product.setProduct_name(nameOfProduct);
				 product.setProduct_price(priceofProduct);
				 product.setProduct_quantity(Integer.parseInt(quantity));
				 return product;	 
			 }
			 
 			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return product;
	}
	
	public void addToCart(Product product) {
		productRepo.save(product);
	}
	
	public List<Product> showAllProducts(){
		return productRepo.findAll();
	}

}
