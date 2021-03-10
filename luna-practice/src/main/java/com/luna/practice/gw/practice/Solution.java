package com.luna.practice.gw.practice;

import java.util.Stack;

public class Solution {
	public boolean IsPopOrder(int[] pushA, int[] popA) {
		Stack<Integer> stack = new Stack<Integer>();
		if (pushA.length <= 0){
			return false;
		}
		int n = pushA.length;
		int index1 = 0;
		int index2 = 0;
		while (index2 < n) {
			if (stack.isEmpty()) {
				stack.push(pushA[index1++]);
			} else {
				if (stack.peek() != popA[index2]) {
					if (index1 < n){
						stack.push(pushA[index1++]);
					}
					else{
						return false;
					}
				} else {
					index2++;
					stack.pop();
				}
			}
		}
		return true;

	}
}