package com.jbedu.board.command;

import com.jbedu.board.dao.BoardDao;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class BModifyCommand implements BCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String bnum = request.getParameter("bnum");
		String btitle = request.getParameter("btitle");
		String bname = request.getParameter("bname");
		String bcontent = request.getParameter("bcontent");
		BoardDao boardDao = new BoardDao(); 
		boardDao.board_modify(bnum, btitle, bname, bcontent);

	}

}
