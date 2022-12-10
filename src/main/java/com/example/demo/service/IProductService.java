package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Product;
import com.example.demo.model.Student;

public interface IProductService {
	
	Integer saveProduct(Product p);
	List<Product> getAllProducts();
	
	//Session 70th
	public Product getOneProduct(Integer id);
	
	//Session 71th
	void deleteProduct(Integer id);
	
	//Session 71 Update product 
	boolean isProductExist(Integer id);
	
	public void updateProduct(Product p);
	
	//Session 72
	Integer updateProdCodeById(Integer prodId,String prodCode);
	
	//Session 73 student module task
	Integer saveStudent(Student s);
	
	public Student getOneStudent(Integer id);
}
