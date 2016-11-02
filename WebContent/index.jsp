<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Customer Management App</title>
<script type="text/javascript" src="scripts/jquery-3.1.1.js"></script>
<script type="text/javascript">

	$(function(){
		$(".delete").click(function(){
			var content = $(this).parent().parent().find("td:eq(1)").text();
			var flag = confirm("Are you sure you want to delete " + content + "'s record?");
			return flag;
		});
	});

</script>
</head>
<body>

	<form action="query.do" method="post">
		<table>
			<tr>
				<td>Customer Name:</td>
				<td><input type="text" name="name"/></td>
			</tr>
			<tr>
				<td>Address:</td>
				<td><input type="text" name="address"/></td>
			</tr>
			<tr>
				<td>Phone:</td>
				<td><input type="text" name="phone"/></td>
			</tr>
			<tr>
				<td><input type="submit" value="Query"></td>
				<td><a href="newcustomer.jsp">Create new Customer</a></td>
			</tr>
		</table>
	</form>
	
	<br><br>
	
	<c:if test="${!empty requestScope.customers }">
	
		<hr>
		<br><br>
	
		<table border="1" cellpadding="10" cellspacing="0">
			<tr>
				<th>ID</th>
				<th>Name</th>
				<th>Address</th>
				<th>Phone</th>
				<th>Update\Delete</th>
			</tr>
			
			<c:forEach items="${requestScope.customers }" var="customer">
			
				<tr>
					<td>${customer.id }</td>
					<td>${customer.name }</td>
					<td>${customer.address }</td>
					<td>${customer.phone }</td>
					<td>
						<c:url value="/edit.do" var="editurl">
							<c:param name="id" value="${customer.id }"></c:param>
						</c:url>
						<a href="${editurl }">Update</a>
						<c:url value="/delete.do" var="deleteurl">
							<c:param name="id" value="${customer.id }"></c:param>
						</c:url>
						<a href="${deleteurl }" class="delete">Delete</a>
					</td>
				</tr>
			
			</c:forEach>		
		</table>
	
	</c:if>

</body>
</html>