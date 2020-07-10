package com.answer.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.answer.biz.AnswerBiz;
import com.answer.dto.AnswerDto;


@WebServlet("/controller.do")
public class AnswerController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
    public AnswerController() {
        super();
     
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		String command = request.getParameter("command");
		AnswerBiz biz = new AnswerBiz();
		
		if(command.equals("list")) {
			List<AnswerDto> list = biz.selectList();
			
			request.setAttribute("list",list);
			
			dispatch("boardlist.jsp", request, response);
		} else if(command.equals("insert")) {
			response.sendRedirect("insert.jsp");
		} else if (command.equals("insertafter")) {
			String title = request.getParameter("title");
			String writer = request.getParameter("writer");
			String content = request.getParameter("content");
			AnswerDto dto = new AnswerDto(title, content, writer);
			int res = biz.insert(dto);
			
			if(res>0) {
				jsResponse( "글 작성 성공","controller.do?command=list", response);
			} else {
				jsResponse("글작성 실패","controller.do?command=insert", response);
			}
		} else if (command.equals("detail")) {
			int boardno = Integer.parseInt(request.getParameter("boardno"));
			AnswerDto dto =biz.selectOne(boardno);
			
			request.setAttribute("dto", dto);
			dispatch("boarddetail.jsp", request, response);
		} else if (command.equals("update")) {
			int boardno = Integer.parseInt(request.getParameter("boardno"));
			AnswerDto dto =biz.selectOne(boardno);
			
			request.setAttribute("dto", dto);
			dispatch("update.jsp", request, response);
		} else if(command.equals("updateafter")) {
			int boardno = Integer.parseInt(request.getParameter("boardno"));
			String title = request.getParameter("title");
			String content =request.getParameter("content");
			String writer = request.getParameter("writer");
			AnswerDto dto = new AnswerDto(boardno, title, content, writer);
			int res = biz.update(dto);
			if(res>0) {
				jsResponse("수정성공", "controller.do?command=list", response);
			} else {
				jsResponse("수정실패", "controller.do?command=list", response);
			}
		} else if(command.equals("reply")) {
			int boardno = Integer.parseInt(request.getParameter("boardno"));
			AnswerDto dto = biz.selectOne(boardno);
			
			request.setAttribute("dto", dto);
			dispatch("reply.jsp", request, response);
		} else if(command.equals("replyafter")) {
			int boardno = Integer.parseInt(request.getParameter("boardno"));
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			String writer = request.getParameter("writer");
			
			AnswerDto dto = new AnswerDto(boardno, title, content, writer);
			
			int res = biz.reply(dto);
			
			if(res>0) {
				jsResponse("작성완료", "controller.do?command=list", response);
			} else {
				jsResponse("작성실패", "controller.do?command=detail&boardno="+boardno, response);
			}
		} else if(command.equals("delete")) {
			int boardno = Integer.parseInt(request.getParameter("boardno"));
			
			int res = biz.delete(boardno);
			PrintWriter pw = response.getWriter();
			if(res>0) {
				jsResponse("삭제성공", "controller.do?command=list", response);
			} else {
				jsResponse("삭제실패", "controller.do?command=detail&boardno="+boardno, response);
			}
		}
		
		PrintWriter pw = response.getWriter();
		pw.append("<h1 style = 'color:salmon'>WRONG PAGE</h1>");
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	public void dispatch (String url, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher disptach = request.getRequestDispatcher(url);
		disptach.forward(request, response);
	}
	
	public void jsResponse(String msg, String url, HttpServletResponse response) throws IOException {
		String s = "<script type ='text/javascript'> "
				+ " alert('"+msg+"'); " 
				+ " location.href = '"+url+"';"
				+ " </script>";
		response.getWriter().append(s);
	}

}
