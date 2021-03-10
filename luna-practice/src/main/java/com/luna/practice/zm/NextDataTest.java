package com.luna.practice.zm;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class NextDataTest {

	@Test
	public void atestGetNextDate() throws Exception {
		assertEquals(NextDate.formatDate(NextDate.getNextDate("1950-6-15")), "1950年6月16日");
	}

	@Test
	public void btestGetNextDate() throws Exception {
		assertEquals(NextDate.formatDate(NextDate.getNextDate("1800-1-1")), "1800年1月2日");
	}

	@Test
	public void ctestGetNextDate() throws Exception {
		assertEquals(NextDate.formatDate(NextDate.getNextDate("2050-12-31")), "2051年1月1日");
	}

	@Test
	public void dtestGetNextDate() throws Exception {
		assertEquals(NextDate.formatDate(NextDate.getNextDate("2012-5-10")), "2012年5月11日");
	}

	@Test
	public void etestGetNextDate() throws Exception {
		assertEquals(NextDate.formatDate(NextDate.getNextDate("1700-6-15")), "1700年6月16日");
	}

	@Test
	public void ftestGetNextDate() throws Exception {
		assertEquals(NextDate.formatDate(NextDate.getNextDate("1799-1-1")), "1799年1月2日");
	}

	@Test
	public void gtestGetNextDate() throws Exception {
		assertEquals(NextDate.formatDate(NextDate.getNextDate("2051-1-1")), "2051年1月2日");
	}

	@Test
	public void htestGetNextDate() throws Exception {
		assertEquals(NextDate.formatDate(NextDate.getNextDate("2012-0-16")), "2012年0月17日");
	}

	@Test
	public void itestGetNextDate() throws Exception {
		assertEquals(NextDate.formatDate(NextDate.getNextDate("2012-13-20")), "2012年13月21日");
	}

	@Test
	public void jtestGetNextDate() throws Exception {
		assertEquals(NextDate.formatDate(NextDate.getNextDate("2012-6-31")), "2012年7月1日");
	}


	@Before
	public void testBefore() {
		System.out.println("start test:");
	}

}
