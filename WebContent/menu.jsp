<%@ page import="java.util.*" %>

<html>
	<body>
	    Menu Principal<br/><br/>
		<form action="${pageContext.request.contextPath}/menuController" method="post">
			<table>
				<tr>
					<td>Gestion de usuarios - Opciones disponibles</td>
				</tr>
				<tr>
					<td></td>
				</tr>
				<tr>
					<td><input type="submit" name="ACTION" value="CREAR_USUARIO"></td>
				</tr>
				<tr>
					<td><input type="submit" name="ACTION" value="SALIR"></td>
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