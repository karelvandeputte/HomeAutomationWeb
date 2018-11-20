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
 
    <jsp:include page="../_header.jsp"></jsp:include>
    <jsp:include page="../_menu.jsp"></jsp:include>
 
    <h3>Relais List</h3>
 
    <p style="color: red;">${errorString}</p>
 
    <table border="1" cellpadding="5" cellspacing="1" >
       <tr>
          <th>ID</th>
          <th>Module</th>
          <th>Name</th>
          <th>Number</th>
          <th>Status</th>
          <th>&nbsp;</th>
          <th>Query</th>
          <th>Toggle</th>
          <th>ON</th>
          <th>OFF</th>
       </tr>
       <c:forEach items="${relaisList}" var="relais" >
          <tr>
             <td>${relais.ID}</td>
             <td>${relais.moduleName}</td>
             <td>${relais.name}</td>
             <td>${relais.number}</td>
             <td>${relais.status}</td>
             <td>&nbsp;</td>
             <td>
                <a href="${pageContext.request.contextPath}/mqtt/queryRelais?id=${relais.ID}&mode=query">Query status</a>
             </td>
             <td>
                <a href="${pageContext.request.contextPath}/mqtt/queryRelais?id=${relais.ID}&mode=toggle">Toggle</a>
             </td>
             <td>
                <a href="${pageContext.request.contextPath}/mqtt/queryRelais?id=${relais.ID}&mode=on">ON</a>
             </td>
             <td>
                <a href="${pageContext.request.contextPath}/mqtt/queryRelais?id=${relais.ID}&mode=off">OFF</a>
             </td>
          </tr>
       </c:forEach>
    </table>
 
    
 
    <jsp:include page="../_footer.jsp"></jsp:include>
 
 </body>
</html>