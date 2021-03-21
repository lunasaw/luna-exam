package com.luna.spring.user.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author Luna@win10
 * @date 2020/3/21 14:42
 */
public class UserDO {

    private String       name;
    private int          id;
    private int          gender;
    private int          age;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date         birth;
    private DepartmentDO department;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public DepartmentDO getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentDO department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "UserDO{" +
            "name='" + name + '\'' +
            ", id=" + id +
            ", gender=" + gender +
            ", age=" + age +
            ", birth=" + birth +
            ", department=" + department +
            '}';
    }
}
