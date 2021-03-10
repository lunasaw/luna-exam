package com.luna.practice.lyx.design.prototype;

import java.util.Hashtable;

/**
 * @author luna@mac
 * @className CellCache.java
 * @description TODO
 * @createTime 2021年03月09日 11:49:00
 */
public class CellCache {

    private static Hashtable<String, CellMother> cellMotherHashtable = new Hashtable<>();

    public CellMother getCell(String id) {
        CellMother cellMother = cellMotherHashtable.get(id);
        return (CellMother)cellMother.clone();
    }

    public void loacCache() {
        CellA cellA = new CellA();
        cellA.setId("1");
        cellA.setName("cellA");
        cellMotherHashtable.put(cellA.getId(), cellA);
        CellB cellB = new CellB();
        cellB.setId("2");
        cellB.setName("cellB");
        cellMotherHashtable.put(cellB.getId(), cellB);
        CellC cellC = new CellC();
        cellC.setId("3");
        cellC.setName("cellC");
        cellMotherHashtable.put(cellC.getId(), cellC);
    }

    public static void main(String[] args) {
        CellCache cellCache = new CellCache();
        cellCache.loacCache();
        System.out.println(cellCache.getCell("1"));
        System.out.println(cellCache.getCell("2"));
        System.out.println(cellCache.getCell("3"));
    }
}
