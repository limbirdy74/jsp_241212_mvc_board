package com.jbedu.board.controller;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("*.do")  // 컨트롤러 기능의 클래스로 서버가 인식하도록 함. .do 로 끝나는 것은 내가 먼저 받겠다. 무조건 내꺼야
public class BoardController extends HttpServlet {  // controller 는 상속받아야 한다
	
	public BoardController() {
		super();
	}

	@Override // 소스로 자동으로 만듬
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("doGet 호출");
		
		request.setCharacterEncoding("utf-8");
		
		String viewPage = "list.jsp";  // 글 목록 페이지 파일 이름
		//http://localhost:8888/jsp_241212_mvc_board/list.jsp
		String uri = request.getRequestURI();  // http://localhost:8888/jsp_241212_mvc_board/list.jsp
		String conPath = request.getContextPath(); // http://localhost:8888
		String com = uri.substring(conPath.length()); //uri - conPath(길이) -> /jsp_241212_mvc_board/list.jsp
		
		System.out.println(uri);
		System.out.println(conPath);
		System.out.println(com);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
		dispatcher.forward(request, response);
		 
		
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}
	
	

}
