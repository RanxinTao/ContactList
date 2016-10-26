package com.mvcapp.dao;

import java.util.List;

import com.mvcapp.entity.Customer;

public interface CustomerDAO {
	
	public List<Customer> getAll();
	
	public void save(Customer customer);
	
	public void update(Customer customer);
	
	public Customer get(int id);
	
	public void delete(int id);
	
	public long getCountByName(String name);
	
	/** 
	 * @param criteriaCustomer object
	 * @return a list of all customers that match the query criteria
	 */
	public List<Customer> getListByCriteria(CriteriaCustomer cc);
	
}
