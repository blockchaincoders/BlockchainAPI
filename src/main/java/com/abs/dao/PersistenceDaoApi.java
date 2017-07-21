package com.abs.dao;

import java.util.List;
import com.abs.entity.CustomerEntity;

public interface PersistenceDaoApi
{
    public void addCustomer(CustomerEntity customer);
    public List<CustomerEntity> getAllCustomers();
    public void deleteCustomer(Integer customerId);
}