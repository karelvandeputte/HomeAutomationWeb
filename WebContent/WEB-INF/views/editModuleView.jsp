<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
 <head>
    <meta charset="UTF-8">
    <title>Edit Module</title>
 </head>
 <body>
 
    <jsp:include page="_header.jsp"></jsp:include>
    <jsp:include page="_menu.jsp"></jsp:include>
 
    <h3>Edit Module</h3>
 
    <p style="color: red;">${errorString}</p>
 
    <c:if test="${not empty module}">
       <form method="POST" action="doEditModule">
          <input type="hidden" name="id" value="${module.ID}" />
          <table border="0">
             <tr>
                <td>ID</td>
                <td style="color:red;">${module.ID}</td>
             </tr>
             <tr>
                <td>Name</td>
                <td><input type="text" name="name" value="${module.name}" /><input type="hidden" name="namePrevious" value="${module.name}" /></td>
             </tr>
             <tr>
                <td>Number</td>
                <td><input type="text" name="number" value="${module.number}" /></td>
             </tr>
             <tr>
                <td>Number</td>
                <td><input type="text" name="port" value="${module.port}" /></td>
             </tr>
             <tr>
                <td colspan = "2">
                    <input type="submit" value="Submit" />
                    <a href="${pageContext.request.contextPath}/ModuleList">Cancel</a>
                </td>
             </tr>
          </table>
       </form>
    </c:if>
 
    <jsp:include page="_footer.jsp"></jsp:include>
 
 </body>
</html>