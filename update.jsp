<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8");%>
<% response.setContentType("text/html; charset=UTF-8"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>update.jsp</title>
</head>
<body>
	<form action ="controller.do" method ="post">
	<input type = "hidden" name ="command" value ="updateafter">
	<input type ="hidden" name ="boardno" value ="${dto.boardno }">
	
		<table border ="1">
			<tr>
				<th>제목</th>
				<td><input type ="text" value ="${dto.title }" name ="title"></td>
			</tr>
			<tr>
				<th>내용</th>
				<td><textarea rows="10" cols="40" name ="content">${dto.content }</textarea>
				</td>
			</tr>
			<tr>	
				<th>작성자</th>
				<td><input type="text" name="writer" value ="${dto.writer }"></td>
			</tr>
			<tr>
				<td colspan ="3" align ="right">
					<input type ="submit" value ="확인">
					<input type ="button" value ="취소" onclick = "location.href = 'controller.do?command=detail&boardno=${dto.boardno}'"> 
				</td>
			</tr>
		</table>
	</form>

</body>
</html>