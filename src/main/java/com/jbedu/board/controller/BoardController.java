package com.jbedu.board.controller;

import java.io.IOException;
import java.util.List;

import com.jbedu.board.dao.BoardDao;
import com.jbedu.board.dto.BoardDto;

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
//		System.out.println("doGet 호출");
//		
//		request.setCharacterEncoding("utf-8");
//		
//		String viewPage = null;  // 글 목록 페이지 파일 이름
//		//http://localhost:8888/jsp_241212_mvc_board/list.jsp
//		String uri = request.getRequestURI();  // /jsp_241212_mvc_board/list.jsp
//		String conPath = request.getContextPath(); // /jsp_241212_mvc_board
//		String com = uri.substring(conPath.length()); //uri - conPath(길이) -> /list.jsp
//		
//		System.out.println(uri);
//		System.out.println(conPath);
//		System.out.println(com);
//		
//		if (com.equals("/list.do")) {
//			BoardDao boardDao = new BoardDao();   // 어렵네. 이해가
//			List<BoardDto> bDtos = boardDao.board_list();
//			request.setAttribute("boardList", bDtos);
//			
//			viewPage = "list.jsp";
//		} else if (com.equals("/write_form.do")) {
//			viewPage="write_form.jsp";
//		} else if (com.equals("/write.do")) {
//			BoardDao boardDao = new BoardDao(); 
//			String btitle = request.getParameter("btitle");
//			String bname = request.getParameter("bname");
//			String bcontent = request.getParameter("bcontent");
//			
//			boardDao.board_write(btitle, bname, bcontent);
//			
//			viewPage = "list.do";  // 주의!! list.jsp는 안됨 . list.do 
//		}			
//		
//		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
//		dispatcher.forward(request, response);
		actionDo(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		request.setCharacterEncoding("utf-8");
//		
//		String viewPage = "";  // 글 목록 페이지 파일 이름
//		
//		String uri = request.getRequestURI();  
//		String conPath = request.getContextPath(); 
//		String com = uri.substring(conPath.length()); 
//		
//		if (com.equals("/write.do")) {
//			BoardDao boardDao = new BoardDao(); 
//			String btitle = request.getParameter("btitle");
//			String bname = request.getParameter("bname");
//			String bcontent = request.getParameter("bcontent");
//			
//			boardDao.board_write(btitle, bname, bcontent);
//			
//			viewPage = "list.do";  // 주의!! list.jsp는 안됨 . list.do 
//		}
//		
//		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
//		dispatcher.forward(request, response);
		actionDo(request, response);  // get, post 이든 하나의 코드로
	}
	
	private void actionDo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("actionDo 호출");
		
		request.setCharacterEncoding("utf-8");
		
		String viewPage = null;  // 글 목록 페이지 파일 이름
		//http://localhost:8888/jsp_241212_mvc_board/list.jsp
		String uri = request.getRequestURI();  // /jsp_241212_mvc_board/list.jsp
		String conPath = request.getContextPath(); // /jsp_241212_mvc_board
		String com = uri.substring(conPath.length()); //uri - conPath(길이) -> /list.jsp
		
		if (com.equals("/list.do")) {
			BoardDao boardDao = new BoardDao();   // 어렵네. 이해가
			List<BoardDto> bDtos = boardDao.board_list();
			request.setAttribute("boardList", bDtos);
			
			viewPage = "list.jsp";
		} else if (com.equals("/write_form.do")) {
			viewPage="write_form.jsp";
		} else if (com.equals("/write.do")) {
			BoardDao boardDao = new BoardDao(); 
			String btitle = request.getParameter("btitle");
			String bname = request.getParameter("bname");
			String bcontent = request.getParameter("bcontent");
			
			boardDao.board_write(btitle, bname, bcontent);
			
			viewPage = "list.do";  // 주의!! list.jsp는 안됨 . list.do 
		} else if (com.equals("/content_view.do")) {
			String bnum = request.getParameter("bnum"); //유저가 글내용 보기를 원하는 클릭한 글의 번호
			
			BoardDao boardDao = new BoardDao();
			BoardDto bDto = boardDao.content_view(bnum, "0");
			//boardDao.up_hit(bnum); // 가능하지만 추천은 안함. 글내용보기에서는 업데이트 안되고 글목록에서 확인됨
			request.setAttribute("boardDto", bDto);
			
			viewPage="content_view.jsp";
		} else if (com.equals("/delete.do")) {
			String bnum = request.getParameter("bnum"); //유저가 삭제를 원하는 글 번호
			
			BoardDao boardDao = new BoardDao();
			boardDao.board_delete(bnum);
			
			viewPage="list.do";
		} else if (com.equals("/modify_form.do")) {
			String bnum = request.getParameter("bnum"); // 수정할 글의 내용을 보내줌
			
			BoardDao boardDao = new BoardDao();
			BoardDto bDto = boardDao.content_view(bnum,"1");
			
			request.setAttribute("boardDto", bDto);
			
			viewPage="modify_form.jsp";
		} else if (com.equals("/modify.do")) {
			
			String bnum = request.getParameter("bnum");
			String btitle = request.getParameter("btitle");
			String bname = request.getParameter("bname");
			String bcontent = request.getParameter("bcontent");
			BoardDao boardDao = new BoardDao(); 
			boardDao.board_modify(bnum, btitle, bname, bcontent);
			
			viewPage = "list.do";  // 주의!! list.jsp는 안됨 . list.do 
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
		dispatcher.forward(request, response);
	}

}
