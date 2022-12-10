package com.example.demo.rest;

import java.time.Instant;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.ProductNotFoundException;
import com.example.demo.model.Product;
import com.example.demo.service.IProductService;
import com.example.demo.util.ProductUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "This is for Products operations")
@RestController
@RequestMapping("/rest/products")
public class ProductRestController {
	private static final Logger LOG = LoggerFactory.getLogger(ProductRestController.class);
	
	
	@Autowired
	private IProductService service;
	
//	@PostMapping
//	public ResponseEntity<String> saveProduct(
//			@RequestBody Product product)
//	{   
//		Integer id = service.saveProduct(product);
//		return new ResponseEntity<String>("Product saved "+id, HttpStatus.OK);
//		
//	}
	//above Code changes with try-catch block as follow
	@ApiOperation("this is to save operation") //optional
	@PostMapping("/save")
	public ResponseEntity<String> saveProduct(
	  @RequestBody Product product )
	{  
		LOG.info("ENTERED INTO SAVEPRODUCT METHOD");
		
		ResponseEntity<String> resp = null;
		try {
			Integer id = service.saveProduct(product);
//resp = new ResponseEntity<String>( new StringBuffer().append("Product").append(id)
//		.append("'Saved").toString(), HttpStatus.CREATED);
			resp = new ResponseEntity<String>(
					"Product '"+id+"' saved ",
					HttpStatus.CREATED); //201
			LOG.info("PRODUCT SAVED WITH ID {}",id);
			
		} catch (Exception e) {
			LOG.error("UNABLE TO SAVE PRODUCT {}", e.getMessage());
			
			resp= new ResponseEntity<String>("Unable to process save product",
					HttpStatus.INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
		LOG.info("ABOUT TO LEAVE SAVEPRODUCT METHOD");
		
		return resp;
	}
	
	//below code can written as below also
	/*
	 * @GetMapping 
	 * public ResponseEntity<List<Product>> getAllProducts(){
	 * List<Product> list = service.getAllProducts(); return new
	 * ResponseEntity<List<Product>>(list, HttpStatus.OK);
	 * 
	 * }
	 */
	@GetMapping("/all")
	public ResponseEntity<List<Product>> getAllProducts(){
		 ResponseEntity<List<Product>> resp = null;
		try {
			List<Product> list = service.getAllProducts();
			resp = new ResponseEntity<List<Product>>(list, HttpStatus.OK);
			
		} catch (Exception e) {
			//Even body is optional but NOT status ResponseEntity<>(body, status)
			resp = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); //500
			e.printStackTrace();
		}
		
		return resp;
	}
	
		
	@GetMapping("getOneProd/{id}")
	public ResponseEntity<?> getOneProduct( @PathVariable Integer id)
	{
		ResponseEntity<?> resp = null;
		try {
			
			 Product p = service.getOneProduct(id);
			 resp = new ResponseEntity<Product>(p, HttpStatus.OK);
		}
		catch(ProductNotFoundException pne) {
			//Send to Custome Exception Handler
			// throw it to handler
			throw pne;
		}
		catch (Exception e) {
			resp = new ResponseEntity<String>("Unable to fetch product", 
					HttpStatus.INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
		return resp;
	}
	
	
			
	@DeleteMapping("/remove/{id}")
	public ResponseEntity<String> deleteProduct( @PathVariable Integer id){
		ResponseEntity<String> resp = null;
		try {
			service.deleteProduct(id);
			resp = new ResponseEntity<String>("Product deleted id is "+ id, HttpStatus.OK);
			
		} catch(ProductNotFoundException pnfe) {
			throw pnfe;
		}catch (Exception e) {
			resp = new ResponseEntity<String>("Unable to delete Product"+ id, 
					HttpStatus.INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
		
		return resp;
		}
			
		/*
		 * @PutMapping("/{id}") 
		 * public ResponseEntity<String> updateProduct(
		 *
		 * @PathVariable Integer id,
		 * 
		 * @RequestBody Product product ) { if(service.isProductExist(id)) {
		 * product.setProdId(id); service.updateProduct(product); return
		 * ResponseEntity.ok("Product Updated");
		 * 
		 * } else { //write ur custome response OR //***throw back to exception
		 * handler(Good approach) throw new
		 * ProductNotFoundException("Product not exist to do update!!"); } //return
		 * null;
		 * 
		 * }
		 */
	
	//2nd way of writing put mapping as below
	@PutMapping("/{id}")
	public ResponseEntity<String> updateProduct(
		@PathVariable Integer id,
		@RequestBody Product product
			)
	
	{   ResponseEntity<String> resp = null;
		try {
		Product db = service.getOneProduct(id);
		ProductUtil.copyNonNullValue(db, product);
		service.updateProduct(db);
		//resp = new ResponseEntity<String>("Product updated !!", HttpStatus.RESET_CONTENT);
		  resp = ResponseEntity.ok("Product updated !!"); //its same as above line
		
	} catch(ProductNotFoundException pnef) {
		throw pnef;
	}catch (Exception e) {
		resp = new ResponseEntity<String>("Unable to update Product"+ id, 
				HttpStatus.INTERNAL_SERVER_ERROR);
	    e.printStackTrace();
	}
		
		//copy non null values below logic we can also write in separate class
//		if(product.getProdCode()!=null)
//			db.setProdCode(product.getProdCode());
//		if(product.getProdCost()!=null)
//			db.setProdCost(product.getProdCost());
		//using above if logic from another class using java8 interface i.e 
		// interfaceName.methodName()
		
		return resp;
	}
	
	//@PatchMapping is used to update product info partially 
	@PatchMapping("/{id}/{code}")
	public ResponseEntity<String> updateProductCode(
			@PathVariable Integer id,
			@PathVariable String code)
	{
		ResponseEntity<String> resp = null;
		try {
			service.updateProdCodeById(id, code);
			resp = new ResponseEntity<String>(
					"Product updated !!", 
					HttpStatus.PARTIAL_CONTENT); //206
			
		} catch (Exception e) {
			resp = new ResponseEntity<String>("Unable to update Product code"+ id, 
					HttpStatus.INTERNAL_SERVER_ERROR);
			e.printStackTrace();
			
		}
		
		return resp;
	}
	
	//Test
	@GetMapping(value ="/healthcheck/{format}")
	@ResponseBody
	public String healthcheck(@RequestParam String format){
		String resp = null;
//		ResponseEntity<String> resp = null;
		if(null!=format && format.equals("short")) {
			resp = "{'status':'OK'}";
		}else if (null!=format && format.equals("full")) {
			String currentTime = Instant.now().toString();
			resp = "{'currentTime':"+currentTime+",'status':'OK'}";
//			resp = new ResponseEntity<String>(currentTime, HttpStatus.OK);
		}
		return resp;
		
	}
	

}
