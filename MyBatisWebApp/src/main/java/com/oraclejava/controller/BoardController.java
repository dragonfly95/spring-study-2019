 package com.oraclejava.controller; 


import com.oraclejava.mapper.BoardMapper;
import com.oraclejava.model.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@Controller
public class BoardController {

    @Autowired
    BoardMapper boardMapper;

    // 한줄주석
    /*
    여러줄 주석
     */

    /**
     * document -   한건 조회
     * @param model
     * @return
     */
    @RequestMapping(value = "/board/{no}", method = RequestMethod.GET)
    public ResponseEntity<Board> select1(@PathVariable("no") int no, ModelMap model) {

        System.out.println("no::" + no);
        Board board = boardMapper.select1(no);
        return new ResponseEntity<Board>(board, HttpStatus.OK);

    }


    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(HttpServletRequest request) {
      return "test";
    }


    /**
     * 목록조회
     * @return
     */
    @RequestMapping(value = "/board")
    public ResponseEntity<HashMap> selectList(HttpServletRequest request, ModelMap model) {

        String no = request.getParameter("keyword");

        System.out.println("no::" + no);

        List boardList = boardMapper.selectAll();

        HashMap map = new HashMap();
        map.put("list", boardList);

        return new ResponseEntity<HashMap>(map, HttpStatus.OK);
    }


    /**
     * 등록
     * @param model
     * @return
     */
    @RequestMapping(value = "/board", method = RequestMethod.POST)
    public ResponseEntity<Board> create1(@RequestBody Board board, ModelMap model) {

//        System.out.print(board);

//
//        String title = request.getParameter("title");
//        String content = request.getParameter("content");
//
//        Board board = new Board();
//        board.setTitle(title);
//        board.setContent(content);

        int id = boardMapper.create1(board);
        board.setId(id);
        return new ResponseEntity<Board>(board, HttpStatus.OK);
    }


    /**
     * 수정
     * @param model
     * @return
     */
    @RequestMapping(value = "/board/{no}", method = RequestMethod.PUT)
    public ResponseEntity<HashMap> update1(@RequestBody Board board, ModelMap model) {

        boardMapper.update1(board);

        HashMap map = new HashMap();
        map.put("message", "ok");
        return new ResponseEntity<HashMap>(map, HttpStatus.OK);

    }


    /**
     * 삭제
     * @param model
     * @return
     */
    @RequestMapping(value = "/board/{no}", method = RequestMethod.DELETE)
    public ResponseEntity<HashMap> delete1(@PathVariable("no") int no, ModelMap model) {

        boardMapper.delete1(no);

        HashMap map = new HashMap();
        map.put("message", "ok");
        return new ResponseEntity<HashMap>(map, HttpStatus.OK);

    }




}
