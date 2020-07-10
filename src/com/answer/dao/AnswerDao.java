package com.answer.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.answer.dto.AnswerDto;
import static com.answer.db.JDBCTemplate.*;
public class AnswerDao {
	
	public List<AnswerDto> selectList(){
		Connection con = getConnection();
		Statement stmt = null;
		String sql = "SELECT BOARDNO, GROUPNO, GROUPSEQ, TITLETAB, TITLE, CONTENT, WRITER, REGDATE " + 
				" FROM ANSWERBOARD" + 
				" ORDER BY GROUPNO DESC, GROUPSEQ";
		ResultSet rs = null;
		List<AnswerDto> list = new ArrayList<AnswerDto>();
		
		try {
			stmt = con.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				AnswerDto dto = new AnswerDto();
				
				dto.setBoardno(rs.getInt(1));
				dto.setGroupno(rs.getInt(2));
				dto.setGroupseq(rs.getInt(3));
				dto.setTitletab(rs.getInt(4));
				dto.setTitle(rs.getString(5));
				dto.setContent(rs.getString(6));
				dto.setWriter(rs.getString(7));
				dto.setRegdate(rs.getDate(8));
				
				list.add(dto);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
			close(con);
		}
		
		return list;
	}
	
	public AnswerDto selectOne(int boardno) {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		String sql =  "SELECT BOARDNO, GROUPNO, GROUPSEQ, TITLETAB, TITLE, CONTENT, WRITER, REGDATE " + 
				" FROM ANSWERBOARD WHERE BOARDNO = ?" + 
				" ORDER BY GROUPNO DESC, GROUPSEQ";
		ResultSet rs = null;
		AnswerDto dto = new AnswerDto();
		
		try {
			pstm = con.prepareStatement(sql);
			pstm.setInt(1,boardno);
			
			rs = pstm.executeQuery();
			
			while(rs.next()) {
				
				dto.setBoardno(rs.getInt(1));
				dto.setGroupno(rs.getInt(2));
				dto.setGroupseq(rs.getInt(3));
				dto.setTitletab(rs.getInt(4));
				dto.setTitle(rs.getString(5));
				dto.setContent(rs.getString(6));
				dto.setWriter(rs.getString(7));
				dto.setRegdate(rs.getDate(8));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstm);
			close(con);
		}
		return dto;
	}
	
	public int insert(AnswerDto dto) {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		String sql = "INSERT INTO ANSWERBOARD " + 
				"VALUES(BOARDNOSEQ.NEXTVAL, GROUPNOSEQ.NEXTVAL, 1, 0, ?, ?, ?,SYSDATE)";
		int res = 0;
		
		try {
			pstm= con.prepareStatement(sql);
			pstm.setString(1,dto.getTitle());
			pstm.setString(2, dto.getContent());
			pstm.setString(3, dto.getWriter());
			
			res = pstm.executeUpdate();
			
			if(res>0) {
				commit(con);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstm);
			close(con);
		}
		
		return res;
	}
	public int update(AnswerDto dto) {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		String sql = "UPDATE ANSWERBOARD "
				+ " SET TITLE = ? , CONTENT = ?, WRITER = ? "
				+ "WHERE BOARDNO = ?";
		int res = 0;
		
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, dto.getTitle());
			pstm.setString(2, dto.getContent());
			pstm.setString(3, dto.getWriter());
			pstm.setInt(4, dto.getBoardno());
			
			res = pstm.executeUpdate();
			
			if(res >0) {
				commit(con);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstm);
			close(con);
		}
		
		return res;
	}
	public int delete(int boardno) {
		Connection con =getConnection();
		PreparedStatement pstm = null;
		String sql = "DELETE ANSWERBOARD WHERE BOARDNO = ?";
		int res = 0;
		
		try {
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, boardno);
			
			res = pstm.executeUpdate();
			if(res > 0) {
				commit(con);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstm);
			close(con);
		}
		
		return res;
	}
	public int replyUpdate(int boardno) {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		int res = 0;
		String update = "UPDATE ANSWERBOARD "
				+ " SET GROUPSEQ = GROUPSEQ+1 "
				+ " WHERE GROUPNO = (SELECT GROUPNO FROM ANSWERBOARD WHERE BOARDNO = ?) "
				+ " AND GROUPSEQ > (SELECT GROUPSEQ FROM ANSWERBOARD WHERE BOARDNO = ?)";
		
		try {
			pstm = con.prepareStatement(update);
			pstm.setInt(1,boardno);
			pstm.setInt(2, boardno);
			
			res = pstm.executeUpdate();
			
			if(res > 0) {
				commit(con);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	public int replyInsert (AnswerDto dto) {
		Connection con = getConnection();

		PreparedStatement pstm = null;
		int res =0;
		String insert = "INSERT INTO ANSWERBOARD "
				+ " VALUES ("
				+ " BOARDNOSEQ.NEXTVAL,"
				+ " (SELECT GROUPNO FROM ANSWERBOARD WHERE BOARDNO = ?),"
				+ " (SELECT GROUPSEQ FROM ANSWERBOARD WHERE BOARDNO = ?) +1 ,"
				+ " (SELECT TITLETAB FROM ANSWERBOARD WHERE BOARDNO = ?) +1 ,"
				+ " ? , ?, ?, SYSDATE"
				+ ")";
		try {
		
			pstm = con.prepareStatement(insert);
			pstm.setInt(1, dto.getBoardno());
			pstm.setInt(2, dto.getBoardno());
			pstm.setInt(3, dto.getBoardno());
			pstm.setString(4, dto.getTitle());
			pstm.setString(5, dto.getContent());
			pstm.setString(6, dto.getWriter());
			
			res = pstm.executeUpdate();
			if(res>0) {
				commit(con);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstm);
			close(pstm);
			close(con);
		}		
		return res;
	}
}
