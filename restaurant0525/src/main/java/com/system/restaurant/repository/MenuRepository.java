package com.system.restaurant.repository;

import com.system.restaurant.domain.Menu;

import java.util.ArrayList;

public interface MenuRepository {
    ArrayList<Menu> menulist();

    Menu findById(int id);

    int post(Menu menudata);

    int put(Menu menudata);

    int delete(int menu_id);
}
