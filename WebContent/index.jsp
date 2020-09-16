<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@page import="java.util.*"%>
<%@page import="idv.cm.db.UserVO"%>
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
<dialog id="newDialog">
  <form action="hello" method="post" name="newdata">
    <p><label><em>New User</em></label></p>
    <p><label>Name : </label><input type="text" id="new_name" name="name" placeholder=""></p>
    <p><label>Pass : </label><input type="text" id="new_pass" name="pass" placeholder=""></p>
    <p><label>Note : </label><input type="text" id="new_note" name="note" placeholder=""></p>
    <menu>
      <button value="cancel">Cancel</button>
      <input type="submit" value="New" name="submitAction"></input>
    </menu>
  </form>
</dialog>
<dialog id="updateDialog">
  <form action="hello" method="post" name="updata">
    <p><label><em>Update</em></label></p>
    <p hidden><label>ID : </label><input type="text" name="id" id="up_id" placeholder=""></p>
    <p><label>Name : </label><input type="text" name="name" id="up_name" placeholder=""></p>
    <p><label>Pass : </label><input type="text" name="pass" id="up_pass"placeholder=""></p>
    <p><label>Note : </label><input type="text" name="note" id="up_note"placeholder=""></p>
    <menu>
      <input type="submit" value="Delete" name="submitAction"></input>
      <input type="submit" value="Update" name="submitAction"></input>
    </menu>
  </form>
</dialog>

<menu>
  <button id="newUser">New User</button>
</menu>

<output aria-live="polite"></output>

	<form action="hello" method="post">
	<input type="submit" value="Refresh" class="load">
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
		HashSet<UserVO> valueSet = (HashSet<UserVO>) request.getAttribute("listA");	
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
		Iterator<UserVO> its = valueSet.iterator();
		while(its.hasNext()){
			UserVO user = its.next();
		    
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
    <%-- 
    <c:forEach items="${listA}" var="user">
 	<tr>
    <td><c:out value="${user.getUserName()}"/></td>
    <td><c:out value="${user.getUserPass()}"/></td>
    <td><c:out value="${user.getUserNote()}"/></td>
  	</tr>
  	</c:forEach>   
    --%>
	
	</tbody>
	</table>
	</form>


	<script>
	
		let tableClick=document.querySelector('.query_db');	
		tableClick.addEventListener('click', load);
		const updateDialog = document.getElementById('updateDialog');
		const input_id = document.querySelector('#up_id');
		const input_name = document.querySelector('#up_name');
		const input_pass = document.querySelector('#up_pass');
		const input_note = document.querySelector('#up_note');
		function load(item){
			var row = item.path[1];
			var id ="";
			var row_value="";
			   for (var j = 0; j < row.cells.length; j++) { 
				   input_id.value = row.cells[0].innerHTML;
				   input_name.value = row.cells[1].innerHTML;
				   input_pass.value = row.cells[2].innerHTML;
				   input_note.value = row.cells[3].innerHTML;
                   row_value += row.cells[j].innerHTML; 
                   row_value += " | "; 
       
               } 
 			   console.log('value[0] - '+input_id.value);
 			   console.log('value[1] - '+input_name.value);
 			   console.log('value[2] - '+input_pass.value);
 			   console.log('value[3] - '+input_note.value);
		       console.log(row_value);
		       updateDialog.showModal();
			   openCheck(updateDialog);
			   console.log(updateDialog.returnValue);
		}
		
		const newButton = document.getElementById('newUser');
		const outputBox = document.querySelector('output');
		const newDialog = document.getElementById('newDialog');
		newDialog.returnValue = 'newOne!';
		
		function openCheck(newDialog){
			if(newDialog.open){
				console.log('Dialog open');
			}else{
				console.log('Dialog closed');
			}
		}
		
		function handleUserInput(returnValue){
			if(returnValue==='Cancel'||returnValue==null){
				alert('returnValue '+returnValue);
			}else if(returnValue==='Confirm'){
				alert('returnValue '+returnValue);
			}else{
				alert('returnValue '+returnValue);
			}
		}
		
	
		
			newButton.addEventListener('click',function onopen(){
			newDialog.showModal();
			openCheck(newDialog);
			console.log(newDialog.returnValue);
			
			//handleUserInput(dialog.returnValue);
		});

			newDialog.addEventListener('close', function onClose() {
		
			let name = form.elements.name.value;
			let pass = form.elements.pass.value;
			let note = form.elements.note.value;
			console.log(name +' '+pass+' '+note);
			/*
			var newform = [name,pass,note];
			*/
			outputBox.value = newDialog.returnValue + " button clicked - " + (new Date()).toString();
			console.log(outputBox.value);
		});
		
		
	</script>
</body>
</html>