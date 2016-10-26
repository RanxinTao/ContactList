package com.mvcapp.test;

import java.util.List;

import org.junit.Test;

import com.mvcapp.dao.CriteriaCustomer;
import com.mvcapp.dao.CustomerDAO;
import com.mvcapp.dao.impl.CustomerDAOJDBCImpl;
import com.mvcapp.entity.Customer;

public class CustomerDAOJDBCImplTest {
	
	private CustomerDAO customerDAO = new CustomerDAOJDBCImpl();

	@Test
	public void testGetAll() {
		List<Customer> customers = customerDAO.getAll();
		System.out.println(customers);
	}

	@Test
	public void testSave() {
		Customer customer = new Customer();
		customer.setAddress("Atlanta");
		customer.setName("Jerry");
		customer.setPhone("4046335252");
		
		customerDAO.save(customer);
	}

	@Test
	public void testGetInt() {
		Customer customer = customerDAO.get(2);
		System.out.println(customer);
	}

	@Test
	public void testDelete() {
		customerDAO.delete(1);
	}

	@Test
	public void testGetCount() {
		long count = customerDAO.getCountByName("Jerry");
		System.out.println(count);
	}

	@Test
	public void testGetListByCriteria() {
		CriteriaCustomer cc = new CriteriaCustomer("k", null, null);
		List<Customer> customers = customerDAO.getListByCriteria(cc);
		System.out.println(customers);
	}
}
