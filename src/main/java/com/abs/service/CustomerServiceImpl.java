package com.abs.service;

import com.abs.dao.CustomerDaoApi;
import com.abs.entity.CustomerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerServiceApi {
	
	@Autowired
    private CustomerDaoApi customerDao;

	@Transactional
	public void addCustomer(CustomerEntity Customer) {
		customerDao.addCustomer(Customer);
	}

	@Transactional
	public List<CustomerEntity> getAllCustomers() {
		return customerDao.getAllCustomers();
	}

	@Transactional
	public void deleteCustomer(Integer CustomerId) {
		customerDao.deleteCustomer(CustomerId);
	}

	@Transactional
	public List<CustomerEntity> verifyCustomer(String username, String password) {

		return customerDao.fetchCustomerByUsernamePassword(username,password);
	}

	@Transactional
	public CustomerEntity fetchCustomerById(Integer customerId) {

		return customerDao.fetchCustomerById(customerId);
	}

	@Transactional
	public int updatePassword(Integer customerId,String newPass){

		return customerDao.updatePassword(customerId,newPass);
	}


	public void setPersistenceDaoApi(CustomerDaoApi persistenceDao) {
		this.customerDao = persistenceDao;
	}
}
