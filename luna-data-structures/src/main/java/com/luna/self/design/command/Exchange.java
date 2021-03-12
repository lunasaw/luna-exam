package com.luna.self.design.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author luna@mac
 * @className Scalpers.java
 * @description TODO
 * @createTime021年03月12日 10:01:00
 */
public interface Exchange {

    void execute();
}

class Buyers implements Exchange {

    private Ticket ticket;

    public Buyers(Ticket ticket) {
        this.ticket = ticket;
    }

    @Override
    public void execute() {
        ticket.buy();
    }
}

class TicketRoom implements Exchange {

    private Ticket tickets;

    public TicketRoom(Ticket tickets) {
        this.tickets = tickets;
    }

    @Override
    public void execute() {
        tickets.sell();
    }
}

class Scalpers {
    private List<Exchange> exchanges = new ArrayList<Exchange>();

    private int            count;

    public void takeExchange(Exchange exchange) {
        exchanges.add(exchange);
    }

    public void sellExchange() {
        for (Exchange exchange : exchanges) {
            if (exchange.getClass().isAssignableFrom(Buyers.class)) {
                exchange.execute();
                count--;
            }
        }
    }

    public void buyExchange() {
        for (Exchange exchange : exchanges) {
            if (exchange.getClass().isAssignableFrom(TicketRoom.class)) {
                exchange.execute();
                count++;
            }
        }
    }

    public static void main(String[] args) {
        Ticket ticket = new Ticket("周杰伦", 100);

        Buyers buyers = new Buyers(ticket);
        TicketRoom ticketRoom = new TicketRoom(ticket);

        Scalpers scalpers = new Scalpers();
        scalpers.takeExchange(ticketRoom);
        scalpers.takeExchange(buyers);
        Random random = new Random();
        System.out.println("初始票数" + scalpers.count);

        for (int i = 0; i < 10; i++) {
            if (random.nextInt(10) > 5) {
                ticket.setPrice(300);
            } else {
                ticket.setPrice(100);
            }
            if (ticket.getPrice() < 200) {
                scalpers.buyExchange();
            } else {
                scalpers.sellExchange();
            }
        }
        System.out.println("余票" + scalpers.count);
    }
}

class Ticket {
    private String name;

    private int    price;

    public String getName() {
        return name;
    }

    public Ticket setName(String name) {
        this.name = name;
        return this;
    }

    public int getPrice() {
        return price;
    }

    public Ticket setPrice(int price) {
        this.price = price;
        return this;
    }

    public Ticket(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public void buy() {
        System.out.println("Ticket [ Name: " + name + ", price: " + price + " ] bought");
    }

    public void sell() {
        System.out.println("Ticket [ Name: " + name + ", price: " + price + " ] sold");
    }
}