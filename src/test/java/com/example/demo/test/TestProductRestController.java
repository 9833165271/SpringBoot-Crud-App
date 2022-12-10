package com.example.demo.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource("classpath:application-test.properties")
public class TestProductRestController {
	@Autowired
	private MockMvc mockMvc; //entry  <---Request
	
	@Test
	public void testSaveProduct() throws Exception {
		//1. Create dummy Http project
		MockHttpServletRequestBuilder request= MockMvcRequestBuilders
		.post("/rest/products/save")
		.contentType(MediaType.APPLICATION_JSON)
		.content("{\"prodCode\":\"ABC\",\"prodCost\":3.3}");
		
		//2.Excute request and get result
		MvcResult result = mockMvc.perform(request).andReturn();
		
		//3.Read response object from result 
		MockHttpServletResponse response =  result.getResponse();
		
		//validate / Assert details
		//expected status, actual status 
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		
		//If it is not saved 
		if(!response.getContentAsString().contains("saved")) {
			fail();
		}
		
	}
	
	@Test
	public void testGetAllProducts() throws Exception {
		//#1. Create one Http request (URL, Method Type, Header, Body)
		MockHttpServletRequestBuilder request= MockMvcRequestBuilders
				.get("/rest/products/all");
		
		//#2. Excute Request and get result 
		MvcResult result = mockMvc.perform(request).andReturn();
		
		//#3. Read response Objects from result
		MockHttpServletResponse response =  result.getResponse();
		
		//#4. Validate Response (Assert data)
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals("application/json", response.getContentType());
		String resBody = response.getContentAsString();
		if( resBody==null || resBody.length()==0) {
			fail();
		}
	}
	
	@Test
	@Disabled
	public void testDeleteProductExist() throws Exception {
		//.1 Create one Http request (URL, Method Type, Header, Body)
		MockHttpServletRequestBuilder request = 
				MockMvcRequestBuilders.delete("/rest/products/remove/1");
		
		//#2. Excute Request and get result 
		MvcResult result = mockMvc.perform(request).andReturn();

		//#3. Read response Objects from result
		MockHttpServletResponse response =  result.getResponse();

		//#4. Validate Response (Assert data)
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
	
	@Test
	@Disabled
	public void testDeleteProductNotExist() throws Exception {
		//.1 Create one Http request (URL, Method Type, Header, Body)
		MockHttpServletRequestBuilder request = 
				MockMvcRequestBuilders.delete("/rest/products/remove/2022");
		
		//#2. Excute Request and get result 
		MvcResult result = mockMvc.perform(request).andReturn();

		//#3. Read response Objects from result
		MockHttpServletResponse response =  result.getResponse();

		//#4. Validate Response (Assert data)
		assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
		if(!response.getContentAsString().contains("Deleted")) {
			fail();
		}
	}
	
	@Test
	@Disabled
	public void testUpdateProduct() throws Exception {
		//1. Create request 
		MockHttpServletRequestBuilder request =
				MockMvcRequestBuilders
				.put("/rest/products/modify/2")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"prodCode\":\"SAMSUNG TV\",\"prodCost\":50000.0}");
		
		//2.Excute request and result 
		MvcResult result =mockMvc.perform(request).andReturn();
		
		//3.Read response  objects from Result
		MockHttpServletResponse response = result.getResponse();
		
		//4.Validate 
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		if(response.getContentAsString().contains("Updated")) {
			fail();
		}
		
	}
	
	@Test
	@Disabled
	public void testUpdateProductNotExist() throws Exception {
		//1. Create request 
		MockHttpServletRequestBuilder request =
				MockMvcRequestBuilders
				.put("/rest/products/modify/2")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"prodCode\":\"LG TV\",\"prodCost\":500.0}");

		//2.Excute request and result 
		MvcResult result =mockMvc.perform(request).andReturn();

		//3.Read response  objects from Result
		MockHttpServletResponse response = result.getResponse();

		//4.Validate 
		assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
		if(response.getContentAsString().contains("Not exist")) {
			fail();
		}
	}
	
	@Test
	@Disabled
	public void testGetOneProductExist() throws Exception {
		//.1 Create one Http request (URL, Method Type, Header, Body)
		MockHttpServletRequestBuilder request = 
				MockMvcRequestBuilders.delete("/rest/products/getOneProd/1");

		//#2. Excute Request and get result 
		MvcResult result = mockMvc.perform(request).andReturn();

		//#3. Read response Objects from result
		MockHttpServletResponse response =  result.getResponse();

		//#4. Validate Response (Assert data)
		assertEquals(HttpStatus.OK.value(), response.getStatus());		
	}
	
	@Test
	@Disabled
	public void testGetOneProductNotExist() throws Exception {
		//.1 Create one Http request (URL, Method Type, Header, Body)
		MockHttpServletRequestBuilder request = 
				MockMvcRequestBuilders.delete("/rest/products/getOneProd/2022");

		//#2. Excute Request and get result 
		MvcResult result = mockMvc.perform(request).andReturn();

		//#3. Read response Objects from result
		MockHttpServletResponse response =  result.getResponse();

		//#4. Validate Response (Assert data)
		assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
		if(!response.getContentAsString().contains("Not exist")) {
			fail();
		}
	}

}
