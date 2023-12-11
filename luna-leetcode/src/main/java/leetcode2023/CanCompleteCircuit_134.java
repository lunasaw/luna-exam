package leetcode2023;

/**
 * @author weidian
 * @version 1.0
 * @date 2023/12/11
 * @description:
 */
public class CanCompleteCircuit_134 {

    /**
     * 加油站
     * 在一条环路上有 n 个加油站，其中第 i 个加油站有汽油 gas[i] 升。
     *
     * 你有一辆油箱容量无限的的汽车，从第 i 个加油站开往第 i+1 个加油站需要消耗汽油 cost[i] 升。你从其中的一个加油站出发，开始时油箱为空。
     *
     * 给定两个整数数组 gas 和 cost ，如果你可以按顺序绕环路行驶一周，则返回出发时加油站的编号，否则返回 -1 。如果存在解，则 保证 它是 唯一 的。
     * @param gas 加油站汽油数
     * @param cost 到加油站消耗汽油数
     * @return
     */
    public static int canCompleteCircuit(int[] gas, int[] cost) {
        int cuttentGas = 0;
        int index = 0;
        int count = 0;
        for (int i = 0; i < gas.length * 2; i++) {
            cuttentGas += gas[i % gas.length];
            cuttentGas -= cost[i % gas.length];
            count ++;
            if (cuttentGas < 0){
                // 当前已经断路了，重置起点
                cuttentGas = 0;
                index = i % gas.length + 1;
                count = 0;
            }
            if (count == gas.length){
                // 已经跑完全程
                return index;
            }
        }
        if (count != gas.length){
            return -1;
        }
        return index;
    }

    public static void main(String[] args) {
        int[] gas = new int[]{5,1,2,3,4};
        int[] cost = new int[]{4,4,1,5,1};
        System.out.println(canCompleteCircuit(gas, cost));
    }
}
