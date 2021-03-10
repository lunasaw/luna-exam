package com.luna.practice.gw.practice;

/**
 * @author Luna@win10
 * @date 2020/4/22 15:13
 */
public class Test_4_22 {

	public static void main(String[] args) {
		Dog dog = new Dog();
		dog.sound();
		Cat cat = new Cat();
		cat.sound();
	}

}

class Animal{

	public void sound(){
		System.out.println("发声");
	}
}
class Dog extends Animal{
	@Override
	public void sound() {
		System.out.println("汪汪汪...");
	}
}
class Cat extends Animal{
	@Override
	public void sound() {
		System.out.println("喵喵喵...");
	}
}

class Bus{
	private Integer speed;
	private Integer number;

	public Bus() {
	}

	public Bus(Integer speed, Integer number) {
		this.speed = speed;
		this.number = number;
	}

	public Integer getSpeed() {
		return speed;
	}

	public void setSpeed(Integer speed) {
		this.speed = speed;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}
}

class Bus2 extends Bus{
	public Bus2() {
		super();
	}

	public Bus2(Integer speed, Integer number) {
		super(speed, number);
	}

	@Override
	public Integer getSpeed() {
		return super.getSpeed();
	}

	@Override
	public void setSpeed(Integer speed) {
		super.setSpeed(speed);
	}

	@Override
	public Integer getNumber() {
		return super.getNumber();
	}

	@Override
	public void setNumber(Integer number) {
		super.setNumber(number);
	}
}
