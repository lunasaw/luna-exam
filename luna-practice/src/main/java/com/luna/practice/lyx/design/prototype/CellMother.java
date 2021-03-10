package com.luna.practice.lyx.design.prototype;

/**
 * @author luna@mac
 * @className CellMother.java
 * @description TODO
 * @createTime 2021年03月09日 11:45:00
 */
public abstract class CellMother implements Cloneable {

    private String   id;

    protected String name;

    abstract void display();

    public String getId() {
        return id;
    }

    public CellMother setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CellMother setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    protected Object clone() {
        Object clone = null;
        try {
            clone = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clone;
    }

    @Override
    public String toString() {
        return "CellMother{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            '}';
    }
}

class CellA extends CellMother {

    @Override
    void display() {
        System.out.println(this);
    }
}

class CellB extends CellMother {

    @Override
    void display() {
        System.out.println(this);
    }
}

class CellC extends CellMother {

    @Override
    void display() {
        System.out.println(this);
    }
}