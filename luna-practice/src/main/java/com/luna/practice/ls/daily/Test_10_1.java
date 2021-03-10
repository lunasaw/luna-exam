package com.luna.practice.ls.daily;

/**
 * @author czy@win10
 * @date 2019/10/31 8:39
 */
public class Test_10_1 {
}
class Patient{
    String name;
    char sex;
    Integer age;
    float weight;
    Boolean allergies;

    @Override
    public String toString() {
        return "Patient{" +
                "name='" + name + '\'' +
                ", sex=" + sex +
                ", age=" + age +
                ", weight=" + weight +
                ", allergies=" + allergies +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public Boolean getAllergies() {
        return allergies;
    }

    public void setAllergies(Boolean allergies) {
        this.allergies = allergies;
    }

    public static void main(String[] args) {
        Patient patient1=new Patient();
        Patient patient2=new Patient();
        patient1.setName("zhangli");
        patient1.setSex('f');
        patient1.setAge(45);
        patient1.setWeight(154.72f);
        patient1.setAllergies(true);
        System.out.println("Name:     "+patient1.getName());
        System.out.println("Sex:     "+patient1.getSex());
        System.out.println("Age:     "+patient1.getAge());
        System.out.println("Weight:     "+patient1.getWeight());
        System.out.println("Allergies:     "+patient1.getAllergies());

        System.out.println(patient1.toString());
    }
}

