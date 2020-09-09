<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="java.util.*"%>
<%@page import="idv.cm.UserBean"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Query MyAccount</title>
<style type="text/css">
	body{
		margin:100px;
		background-color:grey;
	}
	tr.title{
		font-size:150%;
		background-color:yellow;
		border:5px solid #ddd;
	}
	table { 
            border-collapse: collapse;
            width: 100%; 
        } 
  
        th, 
        td { 
            padding: 8px; 
            text-align: left; 
            border-bottom: 1px solid #ddd; 
        } 
	
	
</style>
</head>
<body>

	<input type="Button" value="Load From Data" class="load">
	<form action="MyServlet" method="post">
	<table class="query_db">
	<thead>
	<tr class="title">
		<th>Account</th>
		<th>Password</th>
		<th>Note</th>
	</tr>
	</thead>
	<%
	List<UserBean> list = (List<UserBean>) request.getAttribute("listA");	
    String name="";
    String phone="";
    String note="";

    for (int i = 0; i < list.size(); i++) {
    name = list.get(i).getUserName();
    phone=list.get(i).getUserPhone();
    note=list.get(i).getUserNote();

    %>
    <tr>
        <td><%= name %></td>
    </tr>
    <tr>
        <td><%= phone %></td>
    </tr>
    <tr>
        <td><%= note %></td>
    </tr>
    <% } %> 

	
	<tbody>
	
	<c:forEach items="${list}" var="user">
 	
 	<tr>
    <td><c:out value="${user.getUserName()}"/></td>
    <td><c:out value="${user.getUserPhone()}"/></td>
    <td><c:out value="${user.getUserNote()}"/></td>
  	</tr>
  	</c:forEach>   

	</tbody>
	</table>
	</form>
	

	<script>
		
		let id1 = document.getElementById("#1");
		let id2 = document.getElementById("#2");
		let tableClick=document.querySelector('.query_db');
		//let loadbtn = document.querySelector('.load');
		
		
		tableClick.addEventListener('click', load);
		
		function load(item){
			var row = item.path[1];
			var row_value="";
			   for (var j = 0; j < row.cells.length; j++) { 
				   
                   row_value += row.cells[j].innerHTML; 
                   row_value += " | "; 
            
               } 
 
		       console.log(row_value);
		}
	
	</script>
</body>
</html>