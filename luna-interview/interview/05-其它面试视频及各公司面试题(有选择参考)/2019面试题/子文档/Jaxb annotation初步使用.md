# Jaxb annotation初步使用



# 一.Jaxb处理java对象和xml之间转换常用的annotation有：

1. @XmlType
2. @XmlElement
3. @XmlRootElement
4. @XmlAttribute
5. @XmlAccessorType
6. @XmlAccessorOrder
7. @XmlTransient
8. @XmlJavaTypeAdapter

# 　二.常用annotation使用说明

 

1. ## @XmlType

　　@XmlType用在class类的注解，常与@XmlRootElement，@XmlAccessorType一起使用。它有三个属性：name、propOrder、namespace，经常使用的只有前两个属性。如：

```
@XmlType(name = "basicStruct", propOrder = {
    "intValue",
    "stringArray",
    "stringValue"
)
在使用@XmlType的propOrder 属性时，必须列出JavaBean对象中的所有属性，否则会报错。
```

## 　　2.@XmlElement

　　@XmlElement将java对象的属性映射为xml的节点，在使用@XmlElement时，可通过name属性改变java对象属性在xml中显示的名称。如：

　　@XmlElement(name="Address")　　

　　private String yourAddress;

## 　　3.@XmlRootElement

　　@XmlRootElement用于类级别的注解，对应xml的跟元素，常与 @XmlType 和 @XmlAccessorType`一起使用。如：`

　　@XmlType
　　@XmlAccessorType(XmlAccessType.FIELD)
　　@XmlRootElement
　　public class Address {}

## 　　4.@XmlAttribute

　　@XmlAttribute用于把java对象的属性映射为xml的属性,并可通过name属性为生成的xml属性指定别名。如：

　　@XmlAttribute(name="Country")
　　private String state;

## 　　5.@XmlAccessorType

　　@XmlAccessorType用于指定由java对象生成xml文件时对java对象属性的访问方式。常与@XmlRootElement、@XmlType一起使用。它的属性值是XmlAccessType的4个枚举值，分　　　别为：

　　XmlAccessType.FIELD:java对象中的所有成员变量

　　XmlAccessType.PROPERTY：java对象中所有通过getter/setter方式访问的成员变量

　　XmlAccessType.PUBLIC_MEMBER：java对象中所有的public访问权限的成员变量和通过getter/setter方式访问的成员变量

　　XmlAccessType.NONE:java对象的所有属性都不映射为xml的元素

　　注意：@XmlAccessorType的默认访问级别是XmlAccessType.PUBLIC_MEMBER，因此，如果java对象中的private成员变量设置了public权限的getter/setter方法，就不要在　　　private变量上使用@XmlElement和@XmlAttribute注解，否则在由java对象生成xml时会报同一个属性在java类里存在两次的错误。同理，如果@XmlAccessorType的访问权限　　　为XmlAccessType.NONE，如果在java的成员变量上使用了@XmlElement或@XmlAttribute注解，这些成员变量依然可以映射到xml文件。

## 　　6.@XmlAccessorOrder

　　@XmlAccessorOrder用于对java对象生成的xml元素进行排序。它有两个属性值：

　　AccessorOrder.ALPHABETICAL：对生成的xml元素按字母书序排序

　　XmlAccessOrder.UNDEFINED:不排序

## 　　7.@XmlTransient

　　@XmlTransient用于标示在由java对象映射xml时，忽略此属性。即，在生成的xml文件中不出现此元素。

## 　　8.@XmlJavaTypeAdapter

　　@XmlJavaTypeAdapter常用在转换比较复杂的对象时，如map类型或者格式化日期等。使用此注解时，需要自己写一个adapter类继承`XmlAdapter抽象类，并实现里面的方法。`

　　@XmlJavaTypeAdapter(value=xxx.class),value为自己定义的adapter类

　　XmlAdapter如下：

```
public abstract class XmlAdapter<ValueType,BoundType> {
    // Do-nothing constructor for the derived classes.
    protected XmlAdapter() {}
    // Convert a value type to a bound type.
    public abstract BoundType unmarshal(ValueType v);
    // Convert a bound type to a value type.
    public abstract ValueType marshal(BoundType v);
 }
```

# 　三.示例

　　1.Shop.java

```

package jaxb.shop;
 
import java.util.Set;
 
import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlAccessorOrder;
 
@XmlAccessorOrder(XmlAccessOrder.ALPHABETICAL)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "shop", propOrder = { "name", "number", "describer", "address","orders" })
@XmlRootElement(name = "CHMart")
public class Shop {
 
    @XmlAttribute
    private String name;
 
    // @XmlElement
    private String number;
 
    @XmlElement
    private String describer;
 
    @XmlElementWrapper(name = "orders")
    @XmlElement(name = "order")
    private Set<Order> orders;
 
    @XmlElement
    private Address address;
 
    public Shop() {
    }
 
    public Shop(String name, String number, String describer, Address address) {
        this.name = name;
        this.number = number;
        this.describer = describer;
        this.address = address;
    }
 
    getter/setter略
//同时使用了@XmlType（propOrder={}）和@XmlAccessorOrder(XmlAccessOrder.ALPHABETICAL)，但是生成的xml只按照propOrder定义的顺序生成元素
```

　　2.Order.java

```

package jaxb.shop;
 
import java.math.BigDecimal;
import java.util.Date;
 
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
 
@XmlType(name="order",propOrder={"shopName","orderNumber","price","amount","purDate","customer"})
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Order {
 
//  @XmlElement　　
    private String shopName;
 
    @XmlAttribute
    private String orderNumber;
 
//  @XmlElement
    @XmlJavaTypeAdapter(value=DateAdapter.class)
    private Date purDate;
 
//  @XmlElement
    private BigDecimal price;
 
//  @XmlElement
    private int amount;
 
//  @XmlElement
    private Customer customer;
 
    public Order() {
    }
 
    public Order(String shopName, String orderNumber, Date purDate,
            BigDecimal price, int amount) {
        this.shopName = shopName;
        this.orderNumber = orderNumber;
        this.purDate = purDate;
        this.price = price;
        this.amount = amount;
    }
getter/setter略
//@XmlAccessorType(XmlAccessType.FIELD)，所以此处注释掉了@XmlElement，xml中依然会生成这些元素
```

　　3.Customer.java

```
package jaxb.shop;
 
import java.util.Set;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
 
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Customer {
 
    @XmlAttribute
    private String name;
 
    private String gender;
 
    private String phoneNo;
 
    private Address address;
 
    private Set<Order> orders;
 
    public Customer() {
    }
 
    public Customer(String name, String gender, String phoneNo, Address address) {
        this.name = name;
        this.gender = gender;
        this.phoneNo = phoneNo;
        this.address = address;
    }
getter/setter略
```

　　4.Address.java

```

package jaxb.shop;
 
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
 
@XmlType(propOrder={"state","province","city","street","zip"})
@XmlAccessorOrder(XmlAccessOrder.ALPHABETICAL)
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement
public class Address {
 
    @XmlAttribute　
    private String state;
     
    @XmlElement
    private String province;
     
    @XmlElement
    private String city;
 
    @XmlElement
    private String street;
     
    @XmlElement
    private String zip;
 
    public Address() {
        super();
    }
 
    public Address(String state, String province, String city, String street,
            String zip) {
        super();
        this.state = state;
        this.province = province;
        this.city = city;
        this.street = street;
        this.zip = zip;
    }
getter/setter略
//注意：虽然@XmlAccessorType为XmlAccessType.NONE,但是在java类的私有属性上加了@XmlAttribute和@XmlElement注解后，这些私有成员会映射生成xml的元素
```

　　5.DateAdapter.java

```
package jaxb.shop;
 
import java.util.Date;
import java.text.SimpleDateFormat;
 
import javax.xml.bind.annotation.adapters.XmlAdapter;
 
public class DateAdapter extends XmlAdapter<String, Date> {
 
    private String pattern = "yyyy-MM-dd HH:mm:ss";
    SimpleDateFormat fmt = new SimpleDateFormat(pattern);
     
    @Override
    public Date unmarshal(String dateStr) throws Exception {
         
        return fmt.parse(dateStr);
    }
 
    @Override
    public String marshal(Date date) throws Exception {
         
        return fmt.format(date);
    }
 
}
//用于格式化日期在xml中的显示格式，并且由xml unmarshal为java对象时，将字符串解析为Date对象
```

　　6.ShopTest.java

```

package jaxb.shop;
 
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
 
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
 
public class ShopTest {
 
    public static void main(String[] args) throws JAXBException, IOException{
        Set<Order> orders = new HashSet<Order>();
         
        Address address1 = new Address("China", "ShangHai", "ShangHai", "Huang", "200000");
        Customer customer1 = new Customer("Jim", "male", "13699990000", address1);
        Order order1 = new Order("Mart", "LH59900", new Date(), new BigDecimal(60), 1);
        order1.setCustomer(customer1);
         
        Address address2 = new Address("China", "JiangSu", "NanJing", "ZhongYangLu", "210000");
        Customer customer2 = new Customer("David", "male", "13699991000", address2);
        Order order2 = new Order("Mart", "LH59800", new Date(), new BigDecimal(80), 1);
        order2.setCustomer(customer2);
         
        orders.add(order1);
        orders.add(order2);
         
        Address address3 = new Address("China", "ZheJiang", "HangZhou", "XiHuRoad", "310000");
        Shop shop = new Shop("CHMart", "100000", "EveryThing",address3);
        shop.setOrder(orders);
         
         
        FileWriter writer = null;
        JAXBContext context = JAXBContext.newInstance(Shop.class);
        try {
            Marshaller marshal = context.createMarshaller();
            marshal.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshal.marshal(shop, System.out);
             
            writer = new FileWriter("shop.xml");
            marshal.marshal(shop, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
         
        Unmarshaller unmarshal = context.createUnmarshaller();
        FileReader reader = new FileReader("shop.xml") ;
        Shop shop1 = (Shop)unmarshal.unmarshal(reader);
         
        Set<Order> orders1 = shop1.getOrder();
        for(Order order : orders1){
            System.out.println("***************************");
            System.out.println(order.getOrderNumber());
            System.out.println(order.getCustomer().getName());
            System.out.println("***************************");
        }
    }
}
```

　　7.生成的xml文件

```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<CHMart name="CHMart">
    <number>100000</number>
    <describer>EveryThing</describer>
    <address state="China">
        <province>ZheJiang</province>
        <city>HangZhou</city>
        <street>XiHuRoad</street>
        <zip>310000</zip>
    </address>
    <orders>
        <order orderNumber="LH59800">
            <shopName>Mart</shopName>
            <price>80</price>
            <amount>1</amount>
            <purDate>2012-03-25 12:57:23</purDate>
            <customer name="David">
                <gender>male</gender>
                <phoneNo>13699991000</phoneNo>
                <address state="China">
                    <province>JiangSu</province>
                    <city>NanJing</city>
                    <street>ZhongYangLu</street>
                    <zip>210000</zip>
                </address>
            </customer>
        </order>
        <order orderNumber="LH59900">
            <shopName>Mart</shopName>
            <price>60</price>
            <amount>1</amount>
            <purDate>2012-03-25 12:57:23</purDate>
            <customer name="Jim">
                <gender>male</gender>
                <phoneNo>13699990000</phoneNo>
                <address state="China">
                    <province>ShangHai</province>
                    <city>ShangHai</city>
                    <street>Huang</street>
                    <zip>200000</zip>
                </address>
            </customer>
        </order>
    </orders>
</CHMart>
```

　　以上是以一个简单的商店订单模型作为示例。

