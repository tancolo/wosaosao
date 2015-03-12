package com.ckt.shrimp.utils;

/**
 * Created by ckt on 09/03/15.
 */
public class Staff {

    private String name = null;
    private String id = null;
    private String department = null;
    private String email = null;

    //add by colo start
    public Staff () {
        name = "";
        id = "";
        department = "";
        email = "";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    // add by colo end

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
