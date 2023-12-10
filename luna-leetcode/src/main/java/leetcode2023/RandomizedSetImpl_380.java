package leetcode2023;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class RandomizedSetImpl_380 {

    private List<Integer> list = new ArrayList<>();

    /**
     * Your RandomizedSet object will be instantiated and called as such:
     * RandomizedSet obj = new RandomizedSet();
     * boolean param_1 = obj.insert(val);
     * boolean param_2 = obj.remove(val);
     * int param_3 = obj.getRandom();
     */
    public static void main(String[] args) {
        RandomizedSetImpl_380 randomizedSetImpl380 = new RandomizedSetImpl_380();
        randomizedSetImpl380.insert(1);
        randomizedSetImpl380.remove(2);
        randomizedSetImpl380.insert(2);
        randomizedSetImpl380.getRandom();
        randomizedSetImpl380.remove(1);
        randomizedSetImpl380.insert(2);
        randomizedSetImpl380.getRandom();
    }

    public boolean insert(int val) {
        if (list.contains(val)) {
            return false;
        }
        return list.add(val);
    }

    public boolean remove(int val) {
        if (!list.contains(val)) {
            return false;
        }
        return list.remove((Integer) val);
    }

    public int getRandom() {
        Random random = new Random();
        Integer i = list.get(random.nextInt(list.size()));
        return i;
    }

    public static class RandomizedSet {

        private List<Integer> list;
        private HashMap<Integer, Integer> map;

        public RandomizedSet() {
            this.list = new ArrayList<>();
            this.map = new HashMap<>();
        }

        public boolean insert(int val) {
            if (map.containsKey(val)) {
                return false;
            }
            boolean add = list.add(val);
            int size = list.size();
            map.put(val, size - 1);
            return add;
        }

        public boolean remove(int val) {
            if (!map.containsKey(val)) {
                return false;
            }
            Integer i = map.get(val);
            Integer last = list.get(list.size() - 1);
            list.set(i, last);
            list.remove(list.size() - 1);
            map.put(last, i);
            map.remove(val);
            return true;
        }

        public int getRandom() {
            Random random = new Random();
            Integer i = list.get(random.nextInt(list.size()));
            return i;
        }
    }
}
