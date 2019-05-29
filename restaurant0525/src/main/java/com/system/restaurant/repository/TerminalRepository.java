package com.system.restaurant.repository;

import com.system.restaurant.domain.Terminal;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

public interface TerminalRepository {
    ArrayList<Terminal> terminalList();
    /*OrderDetail Class 의 Object 화

        private int order_id;
        private int order_detail_id;
        private int menu_id;
        private int menu_price;
    */
    Terminal findById(int id);
    int post(Terminal terminalData);
    int put(Terminal terminalData);
    int delete(int order_id);

}
