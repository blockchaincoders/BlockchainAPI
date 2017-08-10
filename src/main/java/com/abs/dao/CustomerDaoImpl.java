package com.abs.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.abs.entity.CustomerEntity;

@Repository
public class CustomerDaoImpl implements CustomerDaoApi {

	@Autowired
	private SessionFactory sessionFactory;

	public void addCustomer(CustomerEntity customer) {
		this.sessionFactory.getCurrentSession().save(customer);
	}

	@SuppressWarnings("unchecked")
	public List<CustomerEntity> getAllCustomers() {
		return this.sessionFactory.getCurrentSession().createQuery("from CustomerEntity").list();
	}

	public void deleteCustomer(Integer customerId) {
		CustomerEntity customer = (CustomerEntity) sessionFactory.getCurrentSession().load(
				CustomerEntity.class, customerId);
		if (null != customer) {
			this.sessionFactory.getCurrentSession().delete(customer);
		}
	}

	public List<CustomerEntity> fetchCustomerByUsernamePassword(String username, String password) {
		String query=new StringBuilder()
				.append("from CustomerEntity as ce where ce.userName = '").append(username)
				.append("'").append(" and ce.password = '").append(password).append("'").toString();
		return this.sessionFactory.getCurrentSession().createQuery(query).list();

	}

	public CustomerEntity fetchCustomerById(Integer customerId){
		String query=new StringBuilder()
				.append("from CustomerEntity as ce where ce.id = ").append(customerId).toString();
		List<CustomerEntity> customerEntityList = this.sessionFactory.getCurrentSession().createQuery(query).list();
		return customerEntityList.get(0);

	}

	@Override
	public int updatePassword(Integer customerId,String newPass) {
		String query=new StringBuilder()
				.append("update CustomerEntity as ce set ce.password='").append(newPass)
				.append("' where ce.id = ").append(customerId).toString();
		int updateRecCount = this.sessionFactory.getCurrentSession().createQuery(query).executeUpdate();
		return updateRecCount;
	}
}
