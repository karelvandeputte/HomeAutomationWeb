<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html>
<html>
 <head>
    <meta charset="UTF-8">
    <title>Module List</title>
 </head>
 <body>
 
    <jsp:include page="_header.jsp"></jsp:include>
    <jsp:include page="_menu.jsp"></jsp:include>
 
    <h3>Module List</h3>
 
    <p style="color: red;">${errorString}</p>
 
    <table border="1" cellpadding="5" cellspacing="1" >
       <tr>
          <th>ID</th>
          <th>Name</th>
          <th>Number</th>
          <th>Manage relais</th>
          <th>Edit</th>
          <th>Delete</th>
       </tr>
       <c:forEach items="${moduleList}" var="module" >
          <tr>
             <td>${module.ID}</td>
             <td>${module.name}</td>
             <td>${module.number}</td>
             <td>
                <a href="RelaisList?moduleID=${module.ID}">Manage relais</a>
             </td>
             <td>
                <a href="editModule?id=${module.ID}">Edit</a>
             </td>
             <td>
                <a href="deleteModule?id=${module.ID}">Delete</a>
             </td>
          </tr>
       </c:forEach>
    </table>
 
    <a href="CreateModule" >Create Module</a>
 
    <jsp:include page="_footer.jsp"></jsp:include>
 
 </body>
</html>