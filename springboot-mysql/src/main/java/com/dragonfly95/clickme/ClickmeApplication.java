package com.dragonfly95.clickme;

import lombok.Data;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;

@SpringBootApplication
@MapperScan(value={"com.dragonfly95.clickme"})
public class ClickmeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClickmeApplication.class, args);
	}

	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception{
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);

		Resource[] res = new PathMatchingResourcePatternResolver().getResources("classpath:mappers/*Mapper.xml");
		sessionFactory.setMapperLocations(res);

		return sessionFactory.getObject();
	}
}


@Controller
@EnableAutoConfiguration
class SampleController {

	@Autowired
	private MemberMapper mapper;

	@RequestMapping(value="/")
	@ResponseBody
	public List<MemberVO> home() throws Exception{

		List<MemberVO> list = mapper.selectMemberList();

		for(int i=0; i<list.size(); i++){
			System.out.println("name : " + list.get(i).getName());
			System.out.println("team : " + list.get(i).getTeam());
		}

		return list;
	}

	@RequestMapping(value="/member", method = RequestMethod.POST)

	public @ResponseBody HashMap createMember(@RequestBody MemberVO memberVO) throws Exception{

		int saved = mapper.insertMember(memberVO);
		HashMap resultMap = new HashMap();
    resultMap.put("result", "ok");
		return resultMap;
	}

}


interface MemberMapper {

	public List<MemberVO> selectMemberList() throws Exception;

	public int insertMember(MemberVO memberVO);
}


@Data
class MemberVO {

	private String code = "";
	private String name = "";
	private String team = "";
}
