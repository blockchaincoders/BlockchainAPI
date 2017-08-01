package com.abs.service;

import com.abs.dao.PersistenceDaoApi;
import com.abs.entity.CustomerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ManagerImpl implements ManagerApi {
	
	@Autowired
    private PersistenceDaoApi persistenceDao;

	@Transactional
	public void addCustomer(CustomerEntity Customer) {
		persistenceDao.addCustomer(Customer);
	}

	@Transactional
	public List<CustomerEntity> getAllCustomers() {
		return persistenceDao.getAllCustomers();
	}

	@Transactional
	public void deleteCustomer(Integer CustomerId) {
		persistenceDao.deleteCustomer(CustomerId);
	}

	@Transactional
	public List<CustomerEntity> verifyCustomer(String username, String password) {

		return persistenceDao.fetchCustomerByUsernamePassword(username,password);
	}

	public void setPersistenceDaoApi(PersistenceDaoApi persistenceDao) {
		this.persistenceDao = persistenceDao;
	}
}
