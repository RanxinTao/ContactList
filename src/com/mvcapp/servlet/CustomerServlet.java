package com.mvcapp.servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvcapp.dao.CriteriaCustomer;
import com.mvcapp.dao.CustomerDAO;
import com.mvcapp.dao.factory.CustomerDAOFactory;
import com.mvcapp.entity.Customer;

@WebServlet("*.do")
public class CustomerServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private CustomerDAO customerDAO = CustomerDAOFactory.getInstance().getCustomerDAO();

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	/*protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String method = req.getParameter("method");
		
		switch(method) {
			case "add": addCustomer(req, resp); break;
			case "query": query(req, resp); break;
			case "delete": delete(req, resp); break;
			case "update": update(req, resp); break;
		}
	}*/
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String servletPath = req.getServletPath();
		String methodName = servletPath.substring(1);
		methodName = methodName.substring(0, methodName.length()-3);
		
		try {
			Method method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
			method.invoke(this, req, resp);
		} catch (Exception e) {
			resp.sendRedirect("error.jsp");
		}
	}

	private void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String forwardPath = "/error.jsp";
		//1. get id
		String idStr = req.getParameter("id");
		
		//2. call customerDAO.get(id) to retrieve customer object with that id
		try {
			Customer customer = customerDAO.get(Integer.parseInt(idStr));
			if(customer != null) {
				forwardPath = "/updatecustomer.jsp";
				//3. put the customer object into request
				req.setAttribute("customer", customer);
			}
		} catch (NumberFormatException e) {}
		//4. forward to updatecustomer.jsp
		req.getRequestDispatcher(forwardPath).forward(req, resp);
	}
	
	private void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//1. retrieve parameters: id, name, address, phone, oldName
		String id = req.getParameter("id");
		String name = req.getParameter("name");
		String address = req.getParameter("address");
		String phone = req.getParameter("phone");
		String oldName = req.getParameter("oldName");
		
		//2. check if the name exists
		//2.1. check if name and oldName are same
		if(!oldName.equalsIgnoreCase(name)) {
			//2.2. if not same, call customerDAO.getCountByName() to know if the name exists in the database
			long count = customerDAO.getCountByName(name);
			//2.3. if the return value > 0, forward to newcustomer.jsp
			if(count > 0) {
				//2.3.1. display an error message in updatecustomer.jsp page (by request.setAttribute)
				req.setAttribute("message", "Name \"" + name + "\" exists, please enter a different name!");
				//2.3.2. keep the values in the form from previous submission by forwarding to updatecustomer.jsp
				req.getRequestDispatcher("/updatecustomer.jsp").forward(req, resp);
				//2.3.3. return, end the function
				return;
			}
		}
		//3. if not exists, encapsulate parameters into a Customer object
		Customer customer = new Customer(name, address, phone);
		customer.setId(Integer.parseInt(id));
		//4. call customerDAO.update()
		customerDAO.update(customer);
		//5. redirect to query.do
		resp.sendRedirect("query.do");
	}

	private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String idStr = req.getParameter("id");
		
		//prevent invalid id String (which can not be converted to an integer)
		try {
			int id = Integer.parseInt(idStr);
			customerDAO.delete(id);
		} catch (Exception e) {}	
		resp.sendRedirect("query.do");
	}

	private void query(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//1. get parameters of fuzzy query
		String name = req.getParameter("name");
		String phone = req.getParameter("phone");
		String address = req.getParameter("address");
		
		//2. encapsulate parameters into a CriteriaCustomer object
		CriteriaCustomer cc = new CriteriaCustomer(name, address, phone);
		//3. call customerDAO.getListByCriteria() to retrieve a list of all the customers
		List<Customer> customers = customerDAO.getListByCriteria(cc);
		//4. put the list into request
		req.setAttribute("customers", customers);
		//5. forward to index.jsp
		req.getRequestDispatcher("/index.jsp").forward(req, resp);
	}

	private void addCustomer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//1. retrieve parameters
		String name = req.getParameter("name");
		String address = req.getParameter("address");
		String phone = req.getParameter("phone");
		
		//2. check if the name exists
		//2.1. call customerDAO.getCountByName() to know if the name exists in the database
		long count = customerDAO.getCountByName(name);
		//2.2. if the return value > 0, forward to newcustomer.jsp
		if(count > 0) {
			//2.2.1. display an error message in newcustomer.jsp page (by request.setAttribute)
			req.setAttribute("message", "Name \"" + name + "\" exists, please enter a different name!");
			//2.2.2. keep the values in the form from previous submission by forwarding to newcustomer.jsp
			req.getRequestDispatcher("/newcustomer.jsp").forward(req, resp);
			//2.2.3. return, end the function	
			return;
		}
		//3. if not exists, encapsulate parameters into a Customer object
		Customer customer = new Customer(name, address, phone);
		//4. call customerDAO.save()
		customerDAO.save(customer);
		//5. redirect to success.jsp
		resp.sendRedirect("success.jsp");
	}
	
}
