<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html>
<html>
 <head>
    <meta charset="UTF-8">
    <title>Relais List</title>
 </head>
 <body>
 
    <jsp:include page="_header.jsp"></jsp:include>
    <jsp:include page="_menu.jsp"></jsp:include>
 
    <h3>Relais List</h3>
 
    <p style="color: red;">${errorString}</p>
 
    <table border="1" cellpadding="5" cellspacing="1" >
       <tr>
          <th>ID</th>
          <th>Name</th>
          <th>Number</th>
          <th>Edit</th>
          <th>Delete</th>
       </tr>
       <c:forEach items="${relaisList}" var="relais" >
          <tr>
             <td>${relais.ID}</td>
             <td>${relais.name}</td>
             <td>${relais.number}</td>
             <td>
                <a href="editRelais?moduleID=${moduleID}&id=${relais.ID}">Edit</a>
             </td>
             <td>
                <a href="deleteRelais?moduleID=${moduleID}&id=${relais.ID}">Delete</a>
             </td>
          </tr>
       </c:forEach>
       <tr>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
       </tr>
       <tr>
          <td><a href="CreateRelais?moduleID=${moduleID}" >Create Relais</a></td>
          <td>&nbsp;</td>
          <td><a href="UpdateModuleRelais?moduleID=${moduleID}" >Update relais in module</a></td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
       </tr>
    </table>
 
    
 
    <jsp:include page="_footer.jsp"></jsp:include>
 
 </body>
</html>