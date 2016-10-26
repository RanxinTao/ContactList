package com.mvcapp.dao.impl;

import java.util.List;

import com.mvcapp.dao.CriteriaCustomer;
import com.mvcapp.dao.CustomerDAO;
import com.mvcapp.dao.DAO;
import com.mvcapp.entity.Customer;

public class CustomerDAOJDBCImpl extends DAO<Customer> implements CustomerDAO {

	@Override
	public List<Customer> getAll() {
		String sql = "SELECT id, name, address, phone FROM customers";
		return getList(sql);
	}

	@Override
	public void save(Customer customer) {
		String sql = "INSERT INTO customers(name, address, phone) VALUES(?, ?, ?)";
		update(sql, customer.getName(), customer.getAddress(), customer.getPhone());
	}

	@Override
	public Customer get(int id) {
		String sql = "SELECT id, name, address, phone FROM customers WHERE id = ?";
		return get(sql, id);
	}

	@Override
	public void delete(int id) {
		String sql = "DELETE FROM customers WHERE id = ?";
		update(sql, id);
	}

	@Override
	public long getCountByName(String name) {
		String sql = "SELECT count(id) FROM customers WHERE name = ?";
		return getValue(sql, name);
	}

	@Override
	public List<Customer> getListByCriteria(CriteriaCustomer cc) {
		String sql = "SELECT id, name, address, phone FROM customers "
				+ "WHERE name LIKE ? AND address LIKE ? AND phone LIKE ?";
		return getList(sql, cc.getName(), cc.getAddress(), cc.getPhone());
	}

	@Override
	public void update(Customer customer) {
		String sql = "UPDATE customers SET name = ?, address = ?, phone = ? WHERE id = ?";
		update(sql, customer.getName(), customer.getAddress(), customer.getPhone(), customer.getId());
	}
	
}
