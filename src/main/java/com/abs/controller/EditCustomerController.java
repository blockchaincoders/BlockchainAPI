package com.abs.controller;

import com.abs.entity.CustomerEntity;
import com.abs.service.ManagerApi;
import com.abs.utils.AppUtils;
import com.abs.utils.Constant;
import com.abs.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class EditCustomerController {
	
	@Autowired
	private ManagerApi manager;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String listCustomers(ModelMap map) 
	{
		map.addAttribute("customer", new CustomerEntity());
		map.addAttribute("customerList", manager.getAllCustomers());
		
		return "editEmployeeList";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addCustomer(@ModelAttribute(value="customer") CustomerEntity customer, BindingResult result)
	{
		manager.addCustomer(customer);
		return "redirect:/";
	}

	@RequestMapping("/delete/{customerId}")
	public String deleteEmployee(@PathVariable("customerId") Integer customerId)
	{
		manager.deleteCustomer(customerId);
		return "redirect:/";
	}

	@ResponseBody
	@RequestMapping(value = "/addCustomer", method = { RequestMethod.GET }, produces = Constant.APPLICATION_JSON)
	public String addCustomer(String firstName, String lastName, String userName, String email, String mobileNo, String password)
	{
		Response response = new Response();
		CustomerEntity customerEntity = new CustomerEntity();
		try {
			customerEntity.setFirstname(firstName);
			customerEntity.setLastname(lastName);
			customerEntity.setUserName(userName);
			customerEntity.setEmail(email);
			customerEntity.setTelephone(mobileNo);
			customerEntity.setPassword(password);
			manager.addCustomer(customerEntity);
			response.setStatusCode("0");
			response.setStatusValue("OK");
		}catch (Exception e) {
			response.setStatusCode("99");
			response.setStatusValue("Error:"+e.getMessage());
		}
		return AppUtils.convertToJson(response);
	}


	public void setManager(ManagerApi manager) {
		this.manager = manager;
	}
}
