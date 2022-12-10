package com.example.demo.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.ProductNotFoundException;
import com.example.demo.model.Product;
import com.example.demo.model.Student;
import com.example.demo.repo.ProductRepository;
import com.example.demo.repo.StudentRepository;
import com.example.demo.service.IProductService;

import lombok.var;

@Service
public class ProductServiceImpl implements IProductService {
	
	@Autowired
	private ProductRepository repo;
	
	@Autowired
	private StudentRepository srepo;

	@Override
	public Integer saveProduct(Product p) {
		//JDK 10: Local variable type inference
		//Best datatype is selected by java compiler 
		var cost = p.getProdCost();
		
		if(cost!=null && cost>0.0) {
			var gst = cost*12.0/100;
			var disc = cost*20.0/100;
			
			p.setProdGst(gst);
			p.setProdDisc(disc);
		}
		
		
		
		p=repo.save(p);
		
		return p.getProdId();
	}

	@Override
	public List<Product> getAllProducts() {
		List<Product> list = repo.findAll();
		return list;
	}

	@Override
	public Product getOneProduct(Integer id) {
		/*
		//Try to read object from DB 
		Optional<Product> opt =repo.findById(id);
		
		if(opt.isPresent()) { //if object is present 
			Product p = opt.get();
		return p;
		}else { //object not exist 
			throw new ProductNotFoundException("Product '"+id+"'Not exist");
		} */
		//Using java 8 above code in a single line 
		
		return repo.findById(id).orElseThrow(()->new ProductNotFoundException(
				"Product'"+id+"'Not exist"));
		
		
		
		
		
		
/*  Old style of null checking 
 * String s = <Runtime from DB or any >; null
 * if(s!=null){
 *  sysout(s.length())
 *  }
 *  ### new style of null checking 
 *  
 *  Optional<String> opt = <Runtime from DB or any >; null
 *  if(opt.isPresent()){
 *    sysout(opt.get().length());
 *  }
 *  isPresent() checks about null and get() returns actual object
 * */		
		}

	@Override
	public void deleteProduct(Integer id) {
		Product p = getOneProduct(id); //calling above getOneProduct() mtd to check product exist or not if yes then delete it 
		repo.delete(p);
		
		//OR below mtd but recommended one is above one 
		/*repo.delete(repo.findById(id).orElseThrow(()->new ProductNotFoundException(
				"Product'"+id+"'Not exist"))); 
				*/
	}

	@Override
	public boolean isProductExist(Integer id) {
		return repo.existsById(id);
		 
	}

	@Override
	public void updateProduct(Product p) {
        var cost = p.getProdCost();
		
        if(cost!=null && cost>0.0) {
        	var gst = cost * 12.0/100;
    		var disc = cost * 20.0/100;
    		
    		p.setProdGst(gst);
    		p.setProdDisc(disc);
        }
		
		
		
		p=repo.save(p);
		
		//return p.getProdId();
		
	}

	@Override
	@Transactional
	public Integer updateProdCodeById(Integer prodId, String prodCode) 
	{	
		if(!repo.existsById(prodId)) //if not exist then throw exception 
		{
			throw new ProductNotFoundException(
					new StringBuffer()
					.append("Product'").append(prodId).append("Not exist").toString()
					);
		}
		else
		return repo.updateProdCodeById(prodId, prodCode);
		
	}

	@Override
	public Integer saveStudent(Student s) {
	    s=srepo.save(s);
		return s.getSid();
	}

	@Override
	public Student getOneStudent(Integer id) {
		//try to read object from db
		Optional<Student> opt = srepo.findById(id);
		if(opt.isPresent()) {
			Student s = opt.get();
		    return s;
		}
		else {
			throw new ProductNotFoundException("Student '"+id+"'Not exist");
		}
			
		//return null;
	}
}