package com.answer.biz;

import java.util.List;

import com.answer.dao.AnswerDao;
import com.answer.dto.AnswerDto;

public class AnswerBiz {
	AnswerDao dao = new AnswerDao();
	
	public List<AnswerDto> selectList(){
		return dao.selectList();
	}
	
	public AnswerDto selectOne(int boardno) {
		return dao.selectOne(boardno);
	}
	
	public int insert(AnswerDto dto) {
		return dao.insert(dto);
	}
	public int update(AnswerDto dto) {
		return dao.update(dto);
	}
	public int delete(int boardno) {
		return dao.delete(boardno);
	}
	public int reply (AnswerDto dto) {
		int update = dao.replyUpdate(dto.getBoardno());
		int insert = dao.replyInsert(dto);
		
		return update + insert;
	}

}
