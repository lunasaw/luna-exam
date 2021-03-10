package com.luna.practice.ls.daily;
import java.util.ArrayList;
import java.util.List;

/**
 * @author czy@win10
 * @date 2019/10/29 13:10
 */
public class Test_03_1 {
    public static void main(String[] args) {
        aviation aviation=new aviation();
        Test_03_1 test03 = new Test_03_1();
        user user=new user();
        ticket ticket=new ticket(32,789.1,"���ﺽ��9898");
        ticket ticket1=new ticket(43,741.1,"�Ϸ�����");
        List<ticket> list=new ArrayList<>();
        list.add(ticket);
        list.add(ticket1);
        aviation.setListticket(list);
        aviation.showticketprice();
        String s = aviation.findTicket("���ﺽ��9898");
        if (s!=null){
            System.out.println( "yes");
        }
        aviation.sell();
        aviation.refund();
    }
}

class aviation{
    private int id;
    private List<ticket> listticket;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<ticket> getListticket() {
        return listticket;
    }

    public void setListticket(List<ticket> listticket) {
        this.listticket = listticket;
    }

    public aviation() {
    }

    public void showticketprice(){
        for (int i = 0; i < listticket.size(); i++) {
            System.out.println(listticket.get(i).toString());
        }
    }

    //��Ʊ
    public void sell(){
        
    }

    //��Ʊ
    public void refund(){

    }

    //���һ�Ʊ
    public String findTicket(String ticket){
        for (int i = 0; i < listticket.size(); i++) {
            if (listticket.get(i).getTicket().equals(ticket)){
                return listticket.get(i).getTicket();
            }
        }
        return null;
    }
}

class ticket{
    private int number;
    private  double price;
    private String ticketname;

    public ticket() {
    }

    public ticket(int number, double price, String ticket) {
        this.number = number;
        this.price = price;
        this.ticketname = ticket;
    }

    @Override
    public String toString() {
        return "ticket{" +
                "number=" + number +
                ", price=" + price +
                ", ticket='" + ticketname + '\'' +
                '}';
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTicket() {
        return ticketname;
    }

    public void setTicket(String ticket) {
        this.ticketname = ticket;
    }
}

class user{
   private String name;
    private  int number;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return number;
    }

    public void setId(int id) {
        this.number = id;
    }
    //��Ʊ
    public void  buy(){

    }
    //��Ʊ
    public void refund(){

    }
}