<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 
<!DOCTYPE html>
<html>
 <head>
    <meta charset="UTF-8">
    <title>Create Relais</title>
 </head>
 <body>
 
    <jsp:include page="_header.jsp"></jsp:include>
    <jsp:include page="_menu.jsp"></jsp:include>
    
    <h3>Create Relais</h3>
    
    <p style="color: red;">${errorString}</p>
    
    <form method="POST" action="doCreateRelais">
       <table border="0">
          <tr>
             <td>Name</td>
             <td>
              <input type="hidden" name="moduleID" value="${moduleID}" />
              <input type="text" name="name" value="${relais.name}" /></td>
          </tr>
          <tr>
             <td>Description</td>
             <td>
              <input type="text" name="description" value="${relais.description}" /></td>
          </tr>
          <tr>
             <td>Number</td>
             <td><input type="text" name="number" value="${relais.number}" /></td>
          </tr>
          <tr>
             <td>Port</td>
             <td><input type="text" name="port" value="${relais.port}" /></td>
          </tr>
          <tr>
             <td colspan="2">                  
                 <input type="submit" value="Submit" />
                 <a href="RelaisList">Cancel</a>
             </td>
          </tr>
       </table>
    </form>
    
    <jsp:include page="_footer.jsp"></jsp:include>
    
 </body>
</html>