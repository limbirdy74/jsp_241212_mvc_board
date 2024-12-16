package com.jbedu.board.command;

import com.jbedu.board.dao.BoardDao;
import com.jbedu.board.dto.BoardDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class BContentCommand implements BCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String bnum = request.getParameter("bnum"); 
		BoardDao boardDao = new BoardDao();
		boardDao.up_hit(bnum);
		BoardDto bDto = boardDao.content_view(bnum);
		request.setAttribute("boardDto", bDto);

	}

}
