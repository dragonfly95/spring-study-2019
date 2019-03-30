package com.oraclejava.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.oraclejava.model.Board;
import org.apache.ibatis.annotations.Update;




public interface BoardMapper {

	/**
	 * 목록조회
	 * @return
	 */
	@Select ("select id, title, content, regdate, readcount from TBL_BOARD ")
	public List<Board> selectAll();

	/**
	 * 입력
	 * @param board
	 */
	@Insert("insert into TBL_BOARD (title,content,regdate) values (#{title},#{content},now())")
	public int create1(Board board);
 

	/**
	 * 한건 조회
	 */
	@Select("select id, title, content, regdate, readcount from TBL_BOARD where id = #{id} ")
	public Board select1(int id);


	/**
	 * 한건 수정
	 * @param board
	 */
	@Update("update TBL_BOARD set title = #{title}, content = #{content} where id = #{id} ")
	public void update1(Board board);

	/**
	 * 한건 삭제
	 */
	@Delete("delete from TBL_BOARD where id = #{id} ")
	public void delete1(int id);
}
