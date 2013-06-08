<%@ page import="java.util.*" %>

<html>
	<body>
	    Login al sistema<br/><br/>
		<form action="${pageContext.request.contextPath}/loginController" method="post">
			<table>
				<tr>
					<td>Login</td>
					<td><input type="text" name="LOGIN" value="${requestScope.LOGIN}"></td>
				</tr>
				<tr>
					<td>Password</td>
					<td><input type="text" name="PASSWORD" value="${requestScope.PASSWORD}"></td>
				</tr>
				<tr><td colspan="2"></td></tr>
				<tr><td colspan="2"></td></tr>
				<tr>
					<td><input type="submit" name="ACTION" value="CONFIRMAR"></td>
					<td><input type="submit" name="ACTION" value="CANCELAR"></td>
				</tr>
			</table>
		</form>	
		<script>
			<%
				List<String> mensajes = (List<String>)request.getAttribute("MENSAJES");
				StringBuffer sb = new StringBuffer();
				if (mensajes != null) {
					for (String mensaje : mensajes) {
						sb.append(mensaje);
					}
				}
				if (sb.length() > 0) {
			%>
					window.alert('<%=sb.toString()%>');
			<%
				}
			%>
		</script>
	</body>
</html>