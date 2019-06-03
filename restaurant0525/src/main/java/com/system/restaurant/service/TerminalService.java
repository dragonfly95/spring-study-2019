package com.system.restaurant.service;

import com.system.restaurant.controller.OrderRequestVO;
import com.system.restaurant.domain.Order;
import com.system.restaurant.domain.OrderDetail;
import com.system.restaurant.domain.Terminal;
import com.system.restaurant.repository.OrderDetailRepository;
import com.system.restaurant.repository.OrderRepository;
import com.system.restaurant.repository.TerminalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;


@Service // Service 역할을 하는 bean 임을 선언한다.
@Transactional
public class TerminalService implements TerminalServiceIF {

    @Autowired
    TerminalRepository terminalRepository;


    @Autowired
    OrderRepository orderRepository;


    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Override
    public ArrayList<Terminal> terminalList() {
        return terminalRepository.terminalList();
    }

  @Override
    public Terminal findById(int id) {
        return terminalRepository.findById(id);
    }

    /**
     * https://taetaetae.github.io/2017/04/04/mybatis-useGeneratedKeys/
     *
     *
     * @param orderRequestVO
     * @return
     */
    @Override
    @Transactional(readOnly=false, rollbackFor = { Exception.class } )
    public int post(OrderRequestVO orderRequestVO) throws RuntimeException {

      Order order = new Order();
            order.setUser_id(orderRequestVO.getUser_id());
            order.setTotal_cost(orderRequestVO.getTotal_cost());
            order.setCooking_status("Not started");
            orderRepository.post(order);
            int order_id = order.getOrder_id();

            ArrayList<OrderDetail> list = (ArrayList<OrderDetail>) orderRequestVO.getOrder_detail();
            Iterator<OrderDetail> iterator = list.iterator();
            while(iterator.hasNext()) {
                OrderDetail orderDetail = (OrderDetail) iterator.next();
                orderDetail.setOrder_id(order_id);
                orderDetailRepository.post(orderDetail);
            }


        return 1;
//        return terminalRepository.post(terminalData);
    }

  @Override
    public int put(Terminal terminalData) {
        return terminalRepository.put(terminalData);
    }

  @Override
    public int delete(int order_id) {
        return terminalRepository.delete(order_id);
    }
}
