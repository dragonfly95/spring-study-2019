package com.system.restaurant.controller;

import com.system.restaurant.domain.Menu;
import com.system.restaurant.domain.Order;
import com.system.restaurant.service.MenuService;
import com.system.restaurant.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;

@Controller//아래의 Class 를 controller 로 모듈화 시키는 Tag
public class OrderController {

    @Resource
    OrderService orderService;



    /**
     * 메뉴 목록 jsp
     * @param model
     * @return
     */
    @RequestMapping(value = "/order.do", method = RequestMethod.GET)
    // '/Order' 라는 요청이 들어왔을 때 'OrderList' method 를 실행한다
    public String orderlist(ModelMap model) {
        //JSP에서 객체를 쓰기 위해 model로 orderlist를 넘겨준다
        /*'ModelMap model' -> OrderList() method 실행 후
         View 에서 활용되는 데이터 담고 있는 object*/

        /* 개발자는 Model object 에 데이터를 담아서 DispatcherServlet 에 전달한다.
            DispatcherServlet 에 전달된 Model 데이터는 View 에서 가공되어 client 에게
            응답처리된다.
         */
        ArrayList<Order> orderlist = orderService.orderlist();
        model.addAttribute("orderlist", orderlist);

        /*addAttribute 속성을 추가해주는 method.
          OrderService class 의 OrderList 객체의 속성을 주입한다.
         */

        return "orderlist";
        /* mvc-config.xml 의 코드에서 '.jsp' 와 return 값 "OrderList" 가 합쳐져 응답해주는
        view 페이지 주소를 나타낸다. 'OrderList.jsp'
        <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/view/"/>
        <property name="suffix" value=".jsp"/>
        </bean>
         */
    }

    /**
     * 메뉴 목록 api
     * @return
     */
    @RequestMapping(value = "/orders")
    public ResponseEntity<ArrayList<Order>> orderlist2() {
        //JSON으로 출력하기 위한 작업
        ArrayList<Order> Orderlist = orderService.orderlist();
        return new ResponseEntity<ArrayList<Order>>(Orderlist, HttpStatus.ACCEPTED);
    }

    // 메뉴 1건 조회 시
    //Orders?id=1
    //Orders/1
    @RequestMapping(value = "/orders/{order_id}")
    public ResponseEntity<Order> OrderDetail(@PathVariable("order_id") int order_id) {
        Order Order = orderService.findById(order_id);
        return new ResponseEntity<>(Order, HttpStatus.ACCEPTED);
    }


    // 메뉴 등록
    // formdata submit => @ModelAttribute Order Orderdata
    // json => @RequestBody Order Orderdata 테이터의 흐름
    @RequestMapping(value = "/orders", method = RequestMethod.POST)
    public ResponseEntity<Order> OrderPost(@RequestBody OrderRequestVO orderRequestVO) {

//        int affected = orderService.post(orderdata);
        return new ResponseEntity<>(new Order(), HttpStatus.ACCEPTED);
    }


    // 수정
    @RequestMapping(value = "/orders/{order_id}", method = RequestMethod.PUT)
    public ResponseEntity<Order> OrderPut(@RequestBody Order orderdata) {
        int affected = orderService.put(orderdata);
        return new ResponseEntity<>(new Order(), HttpStatus.ACCEPTED);
    }

    // 삭제
    @RequestMapping(value = "/orders/{order_id}", method = RequestMethod.DELETE)
    public ResponseEntity<Order> OrderDelete(@PathVariable("order_id") int order_id) {
        int affected = orderService.delete(order_id);
        return new ResponseEntity<>(new Order(), HttpStatus.ACCEPTED);
    }
}
