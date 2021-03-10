package com.luna.practice.ls.daily;

import java.util.HashMap;

/**
 * @author czy@win10
 * @date 2019/10/31 8:13
 */
public class Test_03_2 {


    public static void main(String[] args) {
        Bank bank=new Bank();
        User user=new User("����",123.2);
        user.showbalance();
        HashMap<String,Double> map=new HashMap<>();
        bank.setMap(map);
        bank.saveMoney(798.1,"����",map);

        Double Money1 = bank.drawMoney("����", 567.1,map);

        if (Money1!=null ){
            System.out.println("success,��Ĵ������:"+Money1);
        }else {
            System.out.println("�Բ���,��������");
        }
    }
}
class Bank{
    private HashMap<String,Double> map;

    public HashMap<String, Double> getMap() {
        return map;
    }

    public void setMap(HashMap<String, Double> map) {
        this.map = map;
    }

    public Bank() {
    }

    public Double drawMoney(String name, Double money,HashMap<String,Double> map){
        if (map.containsKey(name)){
            if (map.get(name)<money){
                return null;
            }else {
                Double money1=map.get(name)-money ;
                map.remove(name);
                map.put(name,money1);
                return money1;
            }
        }else {
            return null;
        }
    }

    public void saveMoney(Double money, String name,HashMap<String,Double> map){
        if (map.containsKey(name)){
            Double money1=map.get(name)+money;
            map.remove(name);
            map.put(name,money1);
        }else {
            map.put(name,money);
        }
    }
}

class User{
    private String  name;
    private Double balance;

    public User(String name, Double balance) {
        this.name = name;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", balance=" + balance +
                '}';
    }

    public void showbalance(){
        System.out.println(toString());
    }


}