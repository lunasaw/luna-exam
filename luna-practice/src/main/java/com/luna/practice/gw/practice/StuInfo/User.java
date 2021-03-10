package com.luna.practice.gw.practice.StuInfo;

/**
 * @author Luna@win10
 * @date 2020/4/14 10:16
 */
public class User {

	private Integer id;
	private String name;
	private Integer deptId;
	private String duty;
	private Double salary;
	private int education;

	public User(Integer id, String name) {
		this.id = id;
		this.name = name;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public int getEducation() {
		return education;
	}

	public void setEducation(int education) {
		this.education = education;
	}

	public User(Integer id, String name, Integer deptId, String duty, Double salary, int education) {
		this.id = id;
		this.name = name;
		this.deptId = deptId;
		this.duty = duty;
		this.salary = salary;
		this.education = education;
	}

	public User() {
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", name='" + name + '\'' +
				", deptId=" + deptId +
				", duty='" + duty + '\'' +
				", salary=" + salary +
				", education=" + education +
				'}';
	}
}
