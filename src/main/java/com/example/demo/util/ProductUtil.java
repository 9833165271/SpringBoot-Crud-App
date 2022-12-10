package com.example.demo.util;

import com.example.demo.model.Product;

public interface ProductUtil {
	
	//JDK#8 --static methods in interface 
	public static void copyNonNullValue(
			Product db, Product request)
	{
		if(request.getProdCode()!=null)
			db.setProdCode(request.getProdCode());
		if(request.getProdCost()!=null)
			db.setProdCost(request.getProdCost());
	}
}
