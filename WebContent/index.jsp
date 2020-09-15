<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
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
	td#id{
		display:none;
	}
	
	textarea {
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
	<div class="search">
	<textarea placeholder ="Search for name"></textarea>
	<textarea placeholder ="Search for pass" ></textarea>
	<textarea placeholder ="Search for note" ></textarea>
	</div>
	
	<%-- 
   <form action="hello" method="post">
   <input type="submit" value="Submit(JSTL)" class="load">
   <table class="query_db1">
	<thead>	
	<tr class="title">
		<th>Account</th>
		<th>Password</th>
		<th>Note</th>
	</tr>
	</thead>
	<tbody>
   <sql:setDataSource var="snapshot" driver="com.mysql.jdbc.Driver"
     url="jdbc:mysql://localhost:3306/db_morgan"
     user="root"  password="1234"/>
     <sql:query dataSource="${snapshot}" var="result">
		SELECT * from account;
	</sql:query>
  <c:forEach var="row" items="${result.rows}">
<tr>
   <td><c:out value="${row._id}"/></td>
   <td><c:out value="${row.user}"/></td>
   <td><c:out value="${row.password}"/></td>
   <td><c:out value="${row.note}"/></td>
</tr>
</c:forEach>
   </tbody>
   </table>
   </form>
 --%>
	<br>
	<form action="hello" method="post">
	<input type="Button" value="Button(JS)" class="load">
	<input type="submit" value="Submit(form)" class="load">
	<table class="query_db">
	<thead>	
	<tr class="title">
		<th>Account</th>
		<th>Password</th>
		<th>Note</th>
	</tr>
	</thead>
	<tbody>
	<%
	//Hashtable<Integer,UserBean> table = (Hashtable<Integer,UserBean>) request.getAttribute("listA");
	HashSet<UserBean> valueSet = (HashSet<UserBean>) request.getAttribute("listA");	
	int id=-1;
    String name="";
    String pass="";
    String note="";
    if(valueSet==null ||valueSet.size()==0){
    	out.print("valueSet = "+valueSet);
    	out.println("\n"+"table not ready");
    	return;
    }
	out.println(valueSet.size()+"\n");
	
	//LinkedHashSet<UserBean> users = (LinkedHashSet)table.values();
	Iterator<UserBean> its = valueSet.iterator();
	while(its.hasNext()){
		UserBean user = its.next();
	    
		id = user.get_id();
		name = user.getUserName();
		pass = user.getUserPass();
		note = user.getUserNote();
		%>
		 <tr>
		 
		 <td id='id'><%= id %></td>
        <td><%= name %></td>
    
        <td><%= pass %></td>
  
        <td><%= note %></td>
   		 </tr>
   		 <% } %>
	


   
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
			var id ="";
			var row_value="";
			   for (var j = 0; j < row.cells.length; j++) { 
				   id = row.cells[0].innerHTML; 
                   row_value += row.cells[j].innerHTML; 
                   row_value += " | "; 
       
               } 
 			   console.log('id - '+id);
		       console.log(row_value);
		}
	
	</script>
</body>
</html>