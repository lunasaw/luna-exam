package com.luna.spring.util;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Luna@win10
 * @date 2020/4/5 17:51
 */
public class InterviewList {

	@Test
	public void aTest() {
		String s = null;
//		System.out.println(s.equals("x"));
		System.out.println("x".equals(s));
		System.out.println(Objects.equals(s, "x"));
		System.out.println(Objects.hash("x", "X"));

		String[] myArray = {"Apple", "Banana", "Orange"};
		List<String> myList1 = Arrays.asList(myArray);
		System.out.println(myList1);
		//上面两个语句等价于下面一条语句
		List<String> myList2 = Arrays.asList("Apple", "Banana", "Orange");
		System.out.println(myList2);

		int[] myArray1 = {1, 2, 3};
		List myList = Arrays.asList(myArray1);
		System.out.println(myList.size());
		//1
		Object o = myList.get(0);
		System.out.println(myList.get(0));
		//数组地址值
//		System.out.println(myList.get(1)); 报错：ArrayIndexOutOfBoundsException
		int[] array = (int[]) myList.get(0);
		System.out.println(array[0]);
		//1


		/**
		 * TODO 转换反例
		 * 	myList3.add(4);
		 * 	//运行时报错：UnsupportedOperationException
		 * 	myList3.remove(1);
		 * 	//运行时报错：UnsupportedOperationException
		 * 	myList3.clear();
		 * 	//运行时报错：UnsupportedOperationException
         */


        //反例
        List myList3 = Arrays.asList(1, 2, 3);
        System.out.println(myList3.get(1));
        System.out.println(myList3.getClass());
        Integer[] myArray4 = {1, 2, 3};
        //教学
        System.out.println(arrayToList(myArray4).getClass());
        //class java.books.util.ArrayList
        //推荐
        List list = new ArrayList<>(Arrays.asList("a", "b", "c"));
        System.out.println(list.getClass());
        //利用Java8 新特性 stream
        Integer[] myArray5 = {1, 2, 3};
        List myList6 = Arrays.stream(myArray5).collect(Collectors.toList());
        System.out.println(myList6.getClass());
        //基本类型也可以实现转换（依赖boxed的装箱操作）
        int[] myArray2 = {1, 2, 3};
        List myList7 = Arrays.stream(myArray2).boxed().collect(Collectors.toList());
		System.out.println(myList7.getClass());


	}

	@Test
	public void bTest() {

		//TODO Guava 特性 对于不可变集合，你可以使用ImmutableList类及其of()与copyOf()工厂方法：（参数不能为空）
		List<String> list = ImmutableList.of("string", "elements");
		System.out.println(list.getClass());
		// from varargs
		List<String> list1 = ImmutableList.copyOf(new String[]{"1", "2", "3"});
		// from array
		System.out.println(list1.getClass());

		//TODO 对于可变集合，你可以使用Lists类及其newArrayList()工厂方法：
		List<String> l1 = Lists.newArrayList(new ArrayList<>());
		// from collection
		List<String> l2 = Lists.newArrayList(new String[]{"1", "2", "3"});
		// from array
		List<String> l3 = Lists.newArrayList("or", "string", "elements");
		// from varargs

		//TODO 使用 Apache Commons Collections
		List<String> list2 = new ArrayList<String>();
		CollectionUtils.addAll(list2, "x");
		System.out.println(list2);
	}

	@Test
	public void cTest() {

		String[] s = new String[]{
				"dog", "lazy", "a", "over", "jumps", "fox", "brown", "quick", "A"
		};
		List<String> list = Arrays.asList(s);
		Collections.reverse(list);
		//反转数组
		s = list.toArray(new String[0]);
		System.out.println(s);
		//new String[0] 用于指定类型 没有指定类型的话会报错
	}

	//JDK1.5+
	static <T> List<T> arrayToList(final T[] array) {
		final List<T> l = new ArrayList<T>(array.length);

		for (final T s : array) {
			l.add(s);
		}
		return (l);
	}

	@Test
	public void dTest() {
		//TODO 不要再foreach 中进行元素的 remove 和add 应使用迭代器
		List<String> list = new ArrayList<>();
		list.add("1");
		list.add("2");
//		Iterator<String> iterator = list.iterator();
//		System.out.println(iterator);
//		while (iterator.hasNext()) {
//			String string = iterator.next();
//			System.out.println(string);
//			iterator.remove();
//		}
		System.out.println(list);

		//TODO 反例
		for (String s : list) {
			if (s.equals("2")){
				list.remove(s);
				// TODO 这里第二次循环删除"2" 之后 size==1 又进入循环导致报错
			}
		}
		System.out.println(list);
	}
}
