package com.ldk.druking.persistence.impl;

import com.ldk.druking.model.Team;
import com.ldk.druking.persistence.SimpleTeamDTO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.inject.Inject;
import java.util.List;

@Repository
public class SimpleTeamDTOImpl implements SimpleTeamDTO {

	@Inject
	@Resource(name="sqlSession")
	private SqlSession sqlSession;
	private static final String namespace = "com.ldk.druking.mapper.MyMapper";

	@Override
	public List<Team> getTeamsByUplaoder(String uploader) {
		return sqlSession.selectList(namespace+".getTeamsByUplaoder", uploader);
	}

	@Override
	public boolean updateTeam(Team team) {
		return sqlSession.update(namespace+".updateTeam", team) == 1;
	}

	@Override
	public boolean insertTeam(Team team) {
		sqlSession.insert(namespace+".insertTeam",  team);
		return true;
	}

	@Override
	public boolean deleteTeam(Team team) {
		sqlSession.delete(namespace+".deleteTeam", team);
		return false;
	}

	@Override
	public String getExcutors(Integer id) {
		String excutors = sqlSession.selectOne(namespace+".getExcutors", id);
		return excutors;
	}

	@Override
	public void saveExcutors(Team team) {
		sqlSession.update(namespace+".saveExcutors", team);
		
	}

	@Override
	public List<Team> getTeamsByFilter(Team team) {
		return sqlSession.selectList(namespace+".getTeamsByFilter", team);
	}

	@Override
	public List<Team> getTeamsByFilterKeyword(Team team, String keyword) {
		return sqlSession.selectList(namespace+".getTeamsByFilterKeyword", keyword);
	}

	@Override
	public Team getTeamById(Integer id) {
		return sqlSession.selectOne(namespace+".getTeamById", id);
	}

	@Override
	public List<Team> getAllTeams() {
		return sqlSession.selectList(namespace+".getAllTeams");
	}

	@Override
	public List<Team> getTeamsByCampus(Integer campus) {
		return sqlSession.selectList(namespace+".getTeamsByCampus", campus);
	}
}
