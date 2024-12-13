package com.jbedu.board.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.jbedu.board.dto.BoardDto;

public class BoardDao {  // DB 접속용 class  data access object
	
	public List<BoardDto> board_list() {  // 모든 게시판 글 목록 가져오기
		
		String sql = "SELECT * FROM mvc_board ORDER BY bnum DESC";  // 게시판은 최신글이 처음에 와야해서 정렬함
		
		String driverName = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/jsp_project";
		String username = "root";
		String password = "12345";
		
		Connection conn= null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<BoardDto> bDtos = new ArrayList<BoardDto>();
		
		try {
			Class.forName(driverName);
			conn = DriverManager.getConnection(url, username, password);
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {  // 저장된 글의 갯수만큼 반복
				
				int bnum = rs.getInt("bnum");
				String bname = rs.getString("bname");
				String btitle = rs.getString("btitle");
				String bcontent = rs.getString("bcontent");
				Timestamp bdate = rs.getTimestamp("bdate");
				int bhit = rs.getInt("bhit");
				
				BoardDto bDto = new BoardDto(bnum, bname, btitle, bcontent, bdate, bhit);
				bDtos.add(bDto);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return bDtos;  // board_list 메소드를 호출 시 최종적으로 반환되는 글들이 모여있는 리스트 값
	}

	public void board_write(String btitle, String bname, String bcontent) {  // 게시판에 글쓰기 메소드. 함수만들면 반환타입, 매개변수 고민. 입력이라 반환타입은 없음.
		
		String sql = "INSERT INTO mvc_board(btitle, bname, bcontent, bhit) VALUES (?,?,?,0)";  // 게시판 글쓰기
		
		String driverName = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/jsp_project";
		String username = "root";
		String password = "12345";
		
		Connection conn= null;
		PreparedStatement pstmt = null;
				
		try {
			Class.forName(driverName);
			conn = DriverManager.getConnection(url, username, password);
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1,  btitle);
			pstmt.setString(2,  bname);
			pstmt.setString(3,  bcontent);
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public BoardDto content_view(String cbnum, String modifyFlag) {  // 게시판 글 목록에서 클릭한 글 내용 보기
		
		if (modifyFlag.equals("0")) {
			up_hit(cbnum); //조회수 중가 메소드 호출
		}
		
		String sql = "SELECT * FROM mvc_board WHERE bnum = ?";  // 클릭한 글 번호로 글 내용 가져오기
		
		String driverName = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/jsp_project";
		String username = "root";
		String password = "12345";
		
		Connection conn= null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		BoardDto bDto = new BoardDto();
		
		try {
			Class.forName(driverName);
			conn = DriverManager.getConnection(url, username, password);
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, cbnum);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {  // 클릭한 번호의 글이 존재하면 true, 존재하지 않으면 false
				
				int bnum = rs.getInt("bnum");
				String bname = rs.getString("bname");
				String btitle = rs.getString("btitle");
				String bcontent = rs.getString("bcontent");
				Timestamp bdate = rs.getTimestamp("bdate");
				int bhit = rs.getInt("bhit");
				
				bDto.setBnum(bnum);
				bDto.setBname(bname);
				bDto.setBtitle(btitle);
				bDto.setBcontent(bcontent);
				bDto.setBdate(bdate);
				bDto.setBhit(bhit);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return bDto;  // 클릭한 글 번호를 인수로 넣어서 호출하면 그 글의 내용이 담긴 DTO를 반환
	}

	public void board_delete(String cbnum) {  // 게시판 글 삭제
		
		String sql = "DELETE FROM mvc_board WHERE bnum = ?";  // 글 번호로 삭제하기
		
		String driverName = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/jsp_project";
		String username = "root";
		String password = "12345";
		
		Connection conn= null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driverName);
			conn = DriverManager.getConnection(url, username, password);
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, cbnum);
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void board_modify(String bnum, String btitle, String bname, String bcontent) {  // 게시판 글 수정
		
		String sql = "UPDATE mvc_board SET btitle = ?, bname = ?, bcontent = ? WHERE bnum = ?";  // 글 번호로 삭제하기
		
		String driverName = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/jsp_project";
		String username = "root";
		String password = "12345";
		
		Connection conn= null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driverName);
			conn = DriverManager.getConnection(url, username, password);
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1,  btitle);
			pstmt.setString(2,  bname);
			pstmt.setString(3,  bcontent);
			pstmt.setString(4,  bnum);
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void up_hit(String bnum) {  // 조회수 증가
		
		String sql = "UPDATE mvc_board SET bhit = bhit + 1 WHERE bnum = ?";  // 글 번호로 삭제하기
		
		String driverName = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/jsp_project";
		String username = "root";
		String password = "12345";
		
		Connection conn= null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driverName);
			conn = DriverManager.getConnection(url, username, password);
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, bnum);
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}
