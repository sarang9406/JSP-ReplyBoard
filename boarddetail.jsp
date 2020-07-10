<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8");%>
<% response.setContentType("text/html; charset=UTF-8"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>boarddetail</title>
</head>
<body>
	<table border ="1">
		<tr>
			<th>제목</th>
			<td>${dto.title }</td>
		</tr>
		<tr> 
			<th>내용</th>
			<td><textarea rows = "10" cols ="40" readonly = "readonly">${dto.content }</textarea></td>
		</tr>
		<tr>
			<th>작성자</th>
			<td> ${dto.writer }</td>
		</tr>
		<tr>
			<td colspan ="3" align = "right">
				<input type = "button" value ="답글달기" onclick = "location.href = 'controller.do?command=reply&boardno=${dto.boardno}'">
				<input type = "button" value ="수정" onclick = "location.href='controller.do?command=update&boardno=${dto.boardno}'">
				<input type ="button" value ="삭제" onclick ="location.href ='controller.do?command=delete&boardno=${dto.boardno}'">
				<input type ="button" value ="목록으로" onclick = "location.href = 'controller.do?command=list'">	
			</td>
		</tr>
	</table>
</body>
</html>