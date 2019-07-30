package com.test.service.emp;

import javax.xml.ws.Endpoint;

//Endpoint publisher
public class Publisher{

	public static void main(String[] args) {
	   Endpoint.publish("http://localhost:8082/ws/emp", 
			   new EmployeeImpl());
	   //refer http://localhost:9999/ws/hello?wsdl
    }

}