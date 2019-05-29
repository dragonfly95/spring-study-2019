package com.system.restaurant.domain;

public class Waiter {
    private int user_id;
    private String user_name;
    private String user_type;
    

    public Waiter(int user_id, String user_name, String user_type) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_type = user_type;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }
}
