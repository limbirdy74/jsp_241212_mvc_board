package com.jbedu.board.command;

import java.util.List;

import com.jbedu.board.dao.BoardDao;
import com.jbedu.board.dto.BoardDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class BListCommand implements BCommand {
	
	public void execute(HttpServletRequest request, HttpServletResponse response) { // void. 반환하지 않고 response(?request) 객체에 실어주기만 하면 된다

		BoardDao boardDao = new BoardDao();
		List<BoardDto> bDtos = boardDao.board_list();
		request.setAttribute("boardList", bDtos);
	}


}
