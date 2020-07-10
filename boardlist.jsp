<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8");%>
<% response.setContentType("text/html; charset=UTF-8"); %>
<%@ taglib prefix ="c" uri ="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>boardlist.jsp</title>
</head>
<body>
	<table border ="1">
		<col width ="100">
		<col width ="300">
		<col width ="100">
		<col width ="100">
		<tr>
			<th>글번호</th>
			<th>제목</th>
			<th>작성자</th>
			<th>작성일</th>
		</tr>
		<c:choose>	
			<c:when test = "${empty list }">
			<tr>
				<td colspan = "4" align="center">----작성된 글이 없습니다-----</td>
			</tr>
			</c:when>
			<c:otherwise>
				<c:forEach items ="${list }" var = "biz">
				<tr>
					<td>${biz.boardno }</td>
					<td>
						<c:forEach begin="1" end="${biz.titletab}">
						&nbsp;
						</c:forEach>
						<a href = 'controller.do?command=detail&boardno=${biz.boardno }'>${biz.title }</a>
					</td>
					<td>${biz.writer}</td>
					<td>${biz.regdate }</td>
				</tr>
				</c:forEach>
			</c:otherwise>
		</c:choose>
		
		<tr>
			<td colspan ="4" align = "right">
				<input type= "button" value ="글작성" onclick = "location.href='controller.do?command=insert'" >
			</td>
		</tr>
		
	</table>
</body>
</html>