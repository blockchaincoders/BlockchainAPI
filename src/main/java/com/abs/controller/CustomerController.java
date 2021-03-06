package com.abs.controller;

import com.abs.entity.CustomerEntity;
import com.abs.entity.UserProfileBean;
import com.abs.service.CustomerServiceApi;
import com.abs.utils.AppUtils;
import com.abs.utils.Constant;
import com.abs.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CustomerController {
	
	@Autowired
	private CustomerServiceApi customerService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String listCustomers(ModelMap map) 
	{
		map.addAttribute("customer", new CustomerEntity());
		map.addAttribute("customerList", customerService.getAllCustomers());
		
		return "editEmployeeList";
	}


	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addCustomer(@ModelAttribute(value="customer") CustomerEntity customer, BindingResult result)
	{
		customerService.addCustomer(customer);
		return "redirect:/";
	}

	@RequestMapping("/delete/{customerId}")
	public String deleteEmployee(@PathVariable("customerId") Integer customerId)
	{
		customerService.deleteCustomer(customerId);
		return "redirect:/";
	}

	@CrossOrigin
	@ResponseBody
	@RequestMapping(value = "/addCustomer", method = { RequestMethod.POST }, produces = Constant.APPLICATION_JSON)
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
			customerService.addCustomer(customerEntity);
			response.setStatusCode("00");
			response.setStatusValue("OK");
		}catch (Exception e) {
			response.setStatusCode("99");
			response.setStatusValue("Error:"+e.getMessage());
		}
		return AppUtils.convertToJson(response);
	}

	@CrossOrigin
	@ResponseBody
	@RequestMapping(value = "/login", method = { RequestMethod.POST }, produces = Constant.APPLICATION_JSON)
	public String verifyCustomer(HttpServletRequest request,String userName, String password)
	{
		Response response = new Response();
		List<CustomerEntity> customerEntity = null;
		try {

			customerEntity= customerService.verifyCustomer(userName,password);
			if(customerEntity.size()>0){

				for(CustomerEntity ce:customerEntity){
					request.getSession().setAttribute(Constant.ID_CUSTOMER_KEY,ce.getId());
					break;
				}

				response.setStatusCode("00");
				response.setStatusValue("OK");
			}else{
				response.setStatusCode("420");
				response.setStatusValue("Username/Password invalid");
			}
		}catch (Exception e) {
			response.setStatusCode("99");
			response.setStatusValue("Error:"+e.getMessage());
		}
		return AppUtils.convertToJson(response);
	}

	@CrossOrigin
	@ResponseBody
	@RequestMapping(value = "/fetchUserProfile", method = { RequestMethod.POST }, produces = Constant.APPLICATION_JSON)
	public String fetchUserCustomerProfile(HttpServletRequest request)
	{
		Response response = new Response();
		try {

			Integer idCustomer =(Integer) request.getSession(false).getAttribute(Constant.ID_CUSTOMER_KEY);
//			Integer idCustomer =Integer.valueOf(183);

			CustomerEntity customerEntity= customerService.fetchCustomerById(idCustomer);
			if(customerEntity!=null){

				//create user profile
				UserProfileBean userProfile=new UserProfileBean();
				userProfile.setFirstName(customerEntity.getFirstname());
				userProfile.setLastName(customerEntity.getLastname());
				userProfile.setEmail(customerEntity.getEmail());
				userProfile.setAddress("Lahore");//todo change the customer entity add address city country
				userProfile.setCity_or_town("Johar Town");
				userProfile.setCountry("Pakistan");
				userProfile.setUserName(customerEntity.getUserName());

				response.setData(userProfile);
				response.setStatusCode("00");
				response.setStatusValue("OK");
			}else{
				response.setStatusCode("420");
				response.setStatusValue("Username/Password invalid");
			}
		}catch (Exception e) {
			response.setStatusCode("99");
			response.setStatusValue("Error:"+e.getMessage());
		}
		return AppUtils.convertToJson(response);
	}

@CrossOrigin
	@ResponseBody
	@RequestMapping(value = "/changePassword", method = { RequestMethod.POST }, produces = Constant.APPLICATION_JSON)
	public String changeCustomerPassword(HttpServletRequest request,String oldPass,String newPass)
	{
		Response response = new Response();
		try {

			Integer idCustomer =(Integer) request.getSession(false).getAttribute(Constant.ID_CUSTOMER_KEY);
//			Integer idCustomer =Integer.valueOf(183);

			CustomerEntity customerEntity= customerService.fetchCustomerById(idCustomer);
			if(customerEntity!=null && customerEntity.getPassword().equals(oldPass)){

				if(customerService.updatePassword(idCustomer,newPass)>0){
					response.setStatusCode("00");
					response.setStatusValue("OK");
				}else{
					response.setStatusCode("420");
					response.setStatusValue("Error while updating password");
				}
			}else{
				response.setStatusCode("420");
				response.setStatusValue("Username/Password invalid");
			}
		}catch (Exception e) {
			response.setStatusCode("99");
			response.setStatusValue("Error:"+e.getMessage());
		}
		return AppUtils.convertToJson(response);
	}


	/*public void setCustomerService(CustomerServiceApi customerService) {
		this.customerService = customerService;
	}*/
}
