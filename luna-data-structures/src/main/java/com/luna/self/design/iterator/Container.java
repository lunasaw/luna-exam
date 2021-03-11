package com.luna.self.design.iterator;

/**
 * @author luna@mac
 * @className Container.java
 * @description TODO
 * @createTime 2021年03月11日 10:29:00
 */
public interface Container {

    Iterator getIterator();
}

class NameRepository implements Container {

    public String[] names = {"Robert", "John", "Julie", "Lora"};

    @Override
    public Iterator getIterator() {
        return new NameIterator();
    }

    /**
     * 内部类
     */
    class NameIterator implements Iterator {

        int index;

        @Override
        public boolean hasNext() {
            if (index < names.length) {
                return true;
            }
            return false;
        }

        @Override
        public Object next() {
            if (this.hasNext()) {
                return names[index++];
            }
            return null;
        }
    }

    public static void main(String[] args) {
        NameRepository namesRepository = new NameRepository();

        for (Iterator iter = namesRepository.getIterator(); iter.hasNext();) {
            String name = (String)iter.next();
            System.out.println("Name : " + name);
        }
    }
}

interface Iterator {
    boolean hasNext();

    Object next();
}
