package com.test.service.emp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.jws.WebService;

import com.test.domain.Employee;
import com.test.domain.EmployeeResponse;
import com.test.domain.GetEmployeeRequest;
import com.test.domain.GetEmployeeeResponse;

//Service Implementation
@WebService(endpointInterface = "com.test.service.emp.EmloyeeInt")
public class EmployeeImpl implements EmloyeeInt {

	static ArrayList<Employee> emps = new ArrayList<Employee>();

	static Map<String, Employee> empMap = new HashMap<String, Employee>();

	static {
		for (int i = 0; i <= 10; i++) {
			Employee emp = new Employee("id_" + i, "name" + i, "email" + i,
					"deptId" + i);
			emps.add(emp);
			empMap.put("id_" + i, emp);
		}
	}
	@Override
	public GetEmployeeeResponse getEmployee(GetEmployeeRequest emp) {
		GetEmployeeeResponse employeeeResponse = new GetEmployeeeResponse();
		// your logic
		try {
			Employee employee = empMap.get(emp.getEmpId());
			if (employee == null) {
				// negative scenario
				employeeeResponse.setErrorCode("00");
				employeeeResponse.setErrorMsg("invalid emp id");

			} else {
				// positive scenarioo
				employeeeResponse.setStatusCode("01");
				employeeeResponse.setStatusMsg("sucess");
				employeeeResponse.setEmp(employee);
			}
		} catch (Exception ex) {
			employeeeResponse.setErrorCode("00");
			employeeeResponse.setErrorMsg("error details are ::"
					+ ex.getMessage());
		}
		return employeeeResponse;
	}

	@Override
	public EmployeeResponse getEmployeesByDeptId(int departmentId) {
		EmployeeResponse employeeResponse = new EmployeeResponse();
		employeeResponse.setEmps(emps);
		return employeeResponse;
	}

	@Override
	public GetEmployeeeResponse addEmployee(Employee emp) {
		GetEmployeeeResponse employeeeResponse = new GetEmployeeeResponse();
		if(empMap.containsKey(emp.getId())){
			employeeeResponse.setErrorCode("111");
			employeeeResponse.setErrorMsg("emp already exists..");
		}else{
			employeeeResponse.setErrorCode("success");
			employeeeResponse.setErrorMsg("emp created..");
			emps.add(emp);
			empMap.put(emp.getId(), emp);
		}
		return employeeeResponse;
	}

	@Override
	public Employee updateEmployee(Employee emp) {
		empMap.put(emp.getId(), emp);
		return emp;
	}

	@Override
	public String deleteEmployee(int employeeId) {
		empMap.remove(employeeId);
		return "success";
	}

	

	@Override
	public Employee getEmployeeByEmpId(Employee emp) {
		return empMap.get(emp.getId());
	}
}