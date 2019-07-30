package com.test.service.emp;

import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import com.test.domain.Employee;
import com.test.domain.EmployeeResponse;
import com.test.domain.GetEmployeeRequest;
import com.test.domain.GetEmployeeeResponse;

//Service Endpoint Interface
@WebService
@HandlerChain(file="handler-chain.xml")
@SOAPBinding(style = Style.RPC)
public interface EmloyeeInt{

	@WebMethod
	public GetEmployeeeResponse getEmployee(GetEmployeeRequest emp);

	@WebMethod
    public EmployeeResponse getEmployeesByDeptId(int departmentId);
	
	@WebMethod
    public GetEmployeeeResponse addEmployee(Employee emp);

	@WebMethod
    public Employee updateEmployee(Employee emp);

	@WebMethod
    public String deleteEmployee(int employeeId) ;
	
	@WebMethod
	public Employee getEmployeeByEmpId(Employee emp) ;

}