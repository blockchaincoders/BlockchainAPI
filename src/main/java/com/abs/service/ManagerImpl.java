package com.abs.service;

import java.util.List;

import com.abs.dao.PersistenceDaoApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abs.dao.PersistenceDaoApi;
import com.abs.entity.CustomerEntity;

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

	public void setPersistenceDaoApi(PersistenceDaoApi persistenceDao) {
		this.persistenceDao = persistenceDao;
	}
}
