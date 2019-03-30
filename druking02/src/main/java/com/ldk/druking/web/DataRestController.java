package com.ldk.druking.web;

import com.ldk.druking.exception.ErrorMessage;
import com.ldk.druking.model.*;
import com.ldk.druking.service.DBService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

@RestController
public class DataRestController {

	private static final Logger logger = LoggerFactory.getLogger(DataRestController.class);
	
	@Autowired
	DBService dbService;


	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	@RequestMapping(value = "getTable/{campus}/{paper}", method = RequestMethod.GET)
	public ResponseEntity<List<Team>> getTable(@PathVariable Integer campus, 
			@PathVariable String paper) {
		List<Team> teams = null;
		if (campus == -1) {
			teams = dbService.getAllTeams();
		}
		else {
			teams = dbService.getTeamsByCampus(campus);
		}
		
		ResponseEntity<List<Team>> entity = new ResponseEntity<List<Team>>(teams, HttpStatus.OK);
		return entity;
	}


	/**
	 *  캠퍼스목록 불러오기
	 * @return
	 */
	@RequestMapping(value = "/getCampusList", method = RequestMethod.GET)
	public ResponseEntity<CommonCode> getCampusList() {
		List<Campus> campusList = dbService.getCampusList();
		List<Department> departmentList = dbService.getDepartmentList();
		
		CommonCode commonCode = new CommonCode();
		commonCode.setCampusList(campusList);
		commonCode.setDepartmentList(departmentList);
		
		ResponseEntity<CommonCode> entity = new ResponseEntity<CommonCode>(commonCode, HttpStatus.OK);
		return entity;
	}
	
	@RequestMapping(value = "/save_excutors", method = RequestMethod.POST)
	public ResponseEntity<String> save_excutors(@RequestParam Map<String, String> data, Principal principal) {
		Team team = this.mapToTeam(data);
		team.setUploader(principal.getName());
		dbService.saveExcutors(team);
		ResponseEntity<String> entity = new ResponseEntity<String>("OK", HttpStatus.OK);
		return entity;
	}
	
	@RequestMapping(value = "/get_items_filter", method = RequestMethod.GET)
	public ResponseEntity<List<Team>> get_items_filter(@RequestParam Map<String, String> data, Principal principal) {
		Team team = this.mapToTeam(data);
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String loggedInUserName = auth.getName();
		List<GrantedAuthority> as = (List<GrantedAuthority>) auth.getAuthorities();
		GrantedAuthority grantedAuthority = as.get(0);
		
		if (grantedAuthority.getAuthority().equals("ROLE_ADMIN") || grantedAuthority.getAuthority().equals("ROLE_INPUT")) {
			if (data.get("keyword") != null && !data.get("keyword").isEmpty()) {
				String keyword = data.get("keyword");
				List<Team> teams = dbService.getTeamsByFilterKeyword(team, keyword);
				
				ResponseEntity<List<Team>> entity = new ResponseEntity<List<Team>>(teams, HttpStatus.OK);
				return entity;
			}
			else {
				List<Team> teams = dbService.getTeamsByFilter(team);
				
				ResponseEntity<List<Team>> entity = new ResponseEntity<List<Team>>(teams, HttpStatus.OK);
				return entity;	
			}
		}
		else {
			
			if (data.get("keyword") != null && !data.get("keyword").isEmpty()) {
				String keyword = data.get("keyword");
				List<Team> teams = dbService.getTeamsByFilterKeyword(team, keyword);
				
				List<Team> uploaderFiltered = new ArrayList<Team>();
				
				for (Team t : teams) {
					if (t.getUploader().equals(loggedInUserName)) {
						uploaderFiltered.add(t);
					}
				}
				
				ResponseEntity<List<Team>> entity = new ResponseEntity<List<Team>>(uploaderFiltered, HttpStatus.OK);
				return entity;
			}
			else {
				List<Team> teams = dbService.getTeamsByFilter(team);
				List<Team> uploaderFiltered = new ArrayList<Team>();
				
				for (Team t : teams) {
					if (t.getUploader().equals(loggedInUserName)) {
						uploaderFiltered.add(t);
					}
				}
				ResponseEntity<List<Team>> entity = new ResponseEntity<List<Team>>(uploaderFiltered, HttpStatus.OK);
				return entity;	
			}
		}
	}


	////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	/**
	 * 조직에 편성된 인원 조회
	 * @param principal
	 * @return
	 */
	@RequestMapping(value = "/get_items", method = RequestMethod.GET)
	public ResponseEntity<List<Team>> get_items(Principal principal) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String loggedInUserName = auth.getName();
		List<GrantedAuthority> as = (List<GrantedAuthority>) auth.getAuthorities();
		GrantedAuthority grantedAuthority = as.get(0);
		
		List<Team> teams = null;
		if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
			teams = dbService.getAllTeams();
		}
		else {
			teams = dbService.getTeamsByUploader(loggedInUserName);
		}
		
		ResponseEntity<List<Team>> entity = new ResponseEntity<List<Team>>(teams, HttpStatus.OK);
		return entity;
	}


	/**
	 * 조직/인원 수정
	 */
	@RequestMapping(value = "/update_item", method = RequestMethod.POST)
	public ResponseEntity<Team> update_item(@RequestParam Map<String, String> data, Principal principal) {
		Team team = this.mapToTeamNotNull(data);
		team.setUploader(principal.getName());
		dbService.updateTeam(team);
		ResponseEntity<Team> entity = new ResponseEntity<Team>(team, HttpStatus.OK);
		return entity;
	}


	/**
	 * 조직/인원 등록
	 */
	@RequestMapping(value = "/insert_item", method = RequestMethod.POST)
	public ResponseEntity<Team> insert_item(@RequestParam Map<String, String> data, Principal principal) {
		Team team = this.mapToTeamNotNull(data);
		team.setUploader(principal.getName());

		boolean result = dbService.insertTeam(team);
		ResponseEntity<Team> entity = new ResponseEntity<Team>(team, HttpStatus.OK);
		return entity;
	}

	/**
	 * 조직/인원 삭제
	 */
	@RequestMapping(value = "/delete_item", method = RequestMethod.POST)
	public ResponseEntity<Team> delete_item(@RequestParam Map<String, String> data, Principal principal) {
		Team team = this.mapToTeam(data);
		team.setUploader(principal.getName());
		boolean result = dbService.deleteTeam(team);
		ResponseEntity<Team> entity = new ResponseEntity<Team>(team, HttpStatus.OK);
		return entity;
	}


	/**
	 * 조직/인원  삭제 - 분류작업
	 */
	@RequestMapping(value = "/teamperson/{teamid}", method = RequestMethod.GET)
	public ResponseEntity<List<ChurchPerson>> getPersonList(@PathVariable Integer teamid) {
		Team targetTeam = dbService.getTeamById(teamid);
		
		List<ChurchPerson> result = new ArrayList<ChurchPerson>();
		
		processPersonStr(targetTeam.getPastor(), result, "목회자");
		processPersonStr(targetTeam.getElder(), result, "장로");
		processPersonStr(targetTeam.getCheif(), result, "팀장");
		processPersonStr(targetTeam.getManager(), result, "총무");
		processPersonStr(targetTeam.getExcutors(), result, "실행위원");
		
		ResponseEntity<List<ChurchPerson>> entity = new ResponseEntity<List<ChurchPerson>>(result, HttpStatus.OK);
		return entity;
	}
	
	private void processPersonStr(String str, List<ChurchPerson> result, String personRole) {
		StringTokenizer stk = new StringTokenizer(str, ",");	
		
		while (stk.hasMoreTokens()) {
			String tok = stk.nextToken();
			ChurchPerson team = new ChurchPerson();
			team.setName(tok);
			team.setKk(personRole);
			result.add(team);
		}
	}
	
	@RequestMapping(value = "/excutors/{teamid}", method = RequestMethod.GET)
	public ResponseEntity<List<ChurchPerson>> getExcutors(@PathVariable Integer teamid) throws UnsupportedEncodingException {
		String excutors = dbService.getExcutors(teamid);
		List<ChurchPerson> list = new ArrayList<ChurchPerson>();
		StringTokenizer stk = new StringTokenizer(excutors, ",");	
		
		while (stk.hasMoreTokens()) {
			String tok = stk.nextToken();
			ChurchPerson team = new ChurchPerson();
			team.setName(tok);
			list.add(team);
		}
		
		ResponseEntity<List<ChurchPerson>> entity = new ResponseEntity<List<ChurchPerson>>(list, HttpStatus.OK);
		return entity;
	}


	////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	private Team mapToTeamNotNull(Map<String, String> data) {
		Team team = new Team();

		if (data.get("id") != null) {
			team.setId(Integer.parseInt(data.get("id")));
		}
		if (data.get("campus") != null && !data.get("campus").isEmpty()) {
			team.setCampus(Integer.parseInt(data.get("campus")));
		}
		if (data.get("department") != null && !data.get("department").isEmpty()) {
			team.setDepartment(Integer.parseInt(data.get("department")));
		}

		team.setTeam1(data.get("team1") == null ? "" : data.get("team1"));
		team.setTeam2(data.get("team2") == null ? "" : data.get("team2"));
		team.setPastor(data.get("pastor")== null ? "" : data.get("pastor"));
		team.setElder(data.get("elder")== null ? "" : data.get("elder"));
		team.setCheif(data.get("cheif")== null ? "" : data.get("cheif"));
		team.setManager(data.get("manager")== null ? "" : data.get("manager"));
		team.setExcutors(data.get("excutors")== null ? "" : data.get("excutors"));

		return team;
	}

	private Team mapToTeam(Map<String, String> data) {
		Team team = new Team();

		if (data.get("id") != null) {
			team.setId(Integer.parseInt(data.get("id")));
		}
		if (data.get("campus") != null && !data.get("campus").isEmpty()) {
			team.setCampus(Integer.parseInt(data.get("campus")));
		}
		if (data.get("department") != null && !data.get("department").isEmpty()) {
			team.setDepartment(Integer.parseInt(data.get("department")));
		}
		if (data.get("team1") != null && !data.get("team1").isEmpty()) {
			team.setTeam1(data.get("team1"));
		}
		if (data.get("team2") != null && !data.get("team2").isEmpty()) {
			team.setTeam2(data.get("team2"));
		}
		if (data.get("pastor") != null && !data.get("pastor").isEmpty()) {
			team.setPastor(data.get("pastor"));
		}
		if (data.get("elder") != null && !data.get("elder").isEmpty()) {
			team.setElder(data.get("elder"));
		}
		if (data.get("cheif") != null && !data.get("cheif").isEmpty()) {
			team.setCheif(data.get("cheif"));
		}
		if (data.get("manager") != null && !data.get("manager").isEmpty()) {
			team.setManager(data.get("manager"));
		}
		team.setExcutors(data.get("excutors"));

		return team;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorMessage> exceptionHandle(Exception e) {
		ErrorMessage em = new ErrorMessage();
		
		em.setErrorCode(404);
		em.setErrorMessage(e.getMessage());
		
		return new ResponseEntity<ErrorMessage>(em, HttpStatus.OK);
	}

}
