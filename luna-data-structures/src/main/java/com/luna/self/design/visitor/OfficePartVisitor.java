package com.luna.self.design.visitor;

/**
 * @author luna@mac
 * @className OfficePartVisitor.java
 * @description TODO
 * @createTime 2021年03月11日 10:41:00
 */
public interface OfficePartVisitor {
    public void visit(Exhibition exhibition);

    public void visit(Committee committee);

    public void visit(Store store);

    public void visit(MeetingRoom meetingRoom);

    public void visit(Officer officer);
}

class OfficeDisplayVisitor implements OfficePartVisitor{

    @Override
    public void visit(Exhibition exhibition) {
        System.out.println("展示厅");
    }

    @Override
    public void visit(Committee committee) {
        System.out.println("会议室");
    }

    @Override
    public void visit(Store store) {
        System.out.println("小卖部");
    }

    @Override
    public void visit(MeetingRoom meetingRoom) {
        System.out.println("办公室");
    }

    @Override
    public void visit(Officer officer) {
        System.out.println("办公楼");
    }
}

class Exhibition implements OfficePart {

    @Override
    public void accept(OfficePartVisitor officePartVisitor) {
        officePartVisitor.visit(this);
    }
}

class Committee implements OfficePart {

    @Override
    public void accept(OfficePartVisitor officePartVisitor) {
        officePartVisitor.visit(this);
    }
}

class Store implements OfficePart {

    @Override
    public void accept(OfficePartVisitor officePartVisitor) {
        officePartVisitor.visit(this);
    }
}

class MeetingRoom implements OfficePart {

    @Override
    public void accept(OfficePartVisitor officePartVisitor) {
        officePartVisitor.visit(this);
    }
}

class Officer implements OfficePart {

    OfficePart[] parts;

    public Officer() {
        parts = new OfficePart[] {new Exhibition(), new Committee(), new Store(), new MeetingRoom()};
    }

    @Override
    public void accept(OfficePartVisitor officePartVisitor) {
        for (int i = 0; i < parts.length; i++) {
            parts[i].accept(officePartVisitor);
        }
        officePartVisitor.visit(this);
    }

    public static void main(String[] args) {
        OfficePart officePart = new Officer();
        officePart.accept(new OfficeDisplayVisitor());
    }
}

interface OfficePart {
    public void accept(OfficePartVisitor officePartVisitor);
}