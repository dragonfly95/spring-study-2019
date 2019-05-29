package com.system.restaurant.domain;

public class Terminal {

    private int order_id;
    private int order_detail_id;
    private String user_name;
    private String menu_name;
    private String message;
    private String cooking_status;

    public Terminal() {}

    public Terminal(int order_id, int order_detail_id, String user_name, String menu_name, String message, String cooking_status) {
        this.order_id = order_id;
        this.order_detail_id = order_detail_id;
        this.user_name = user_name;
        this.menu_name = menu_name;
        this.message = message;
        this.cooking_status = cooking_status;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getOrder_detail_id() {
        return order_detail_id;
    }

    public void setOrder_detail_id(int order_detail_id) {
        this.order_detail_id = order_detail_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCooking_status() {
        return cooking_status;
    }

    public void setCooking_status(String cooking_status) {
        this.cooking_status = cooking_status;
    }

    @Override
    public String toString() {
        return "Terminal{" +
                "order_id=" + order_id +
                ", order_detail_id=" + order_detail_id +
                ", user_name='" + user_name + '\'' +
                ", menu_name='" + menu_name + '\'' +
                ", message='" + message + '\'' +
                ", cooking_status='" + cooking_status + '\'' +
                '}';
    }
}

