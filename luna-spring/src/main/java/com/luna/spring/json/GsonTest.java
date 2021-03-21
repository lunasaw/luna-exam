package com.luna.spring.json;

import java.util.*;


import com.alibaba.fastjson.JSONArray;
import com.google.gson.*;


import com.google.gson.reflect.TypeToken;
import org.junit.Test;

public class GsonTest {

    @Test
    public void test1() throws Exception {
        Gson gson = new Gson();
        User user = new User(1, 20, "AA", new Date());

        System.out.println("Bean->转换为JSON字符串:");
        String jsonStr = gson.toJson(user);
		System.out.println(jsonStr);
		System.out.println();
	}
 
	@Test
	public void test2() throws Exception {
		Gson gson = new Gson();
		String jsonStr = "{\"id\":1,\"age\":20,\"userName\":\"AA\",\"birthday\":\"Nov 14, 2016 4:52:38 PM\"}";
		System.out.println("字符串->转换成Bean对象");
		User user = gson.fromJson(jsonStr, User.class);
		System.out.println(user);
		System.out.println();
	}
 
	@Test
	public void test3() throws Exception {
		Gson gson = new Gson();
		System.out.println("json转换复杂的bean，比如List，Set,Map:");
		String json = "[{\"id\":\"1\",\"name\":\"Json技术\"},{\"id\":\"2\",\"name\":\"java技术\"}]";
		List list = gson.fromJson(json, new TypeToken<List>() {
		}.getType());
		Set set = gson.fromJson(json, new TypeToken<Set>() {
		}.getType());
		System.out.println(list);
		System.out.println(set);
	}
 
	@Test
	public void test4() throws Exception {
		Gson gson = new Gson();
		String json = "[{\"id\":\"1\",\"name\":\"Json技术\"},{\"id\":\"2\",\"name\":\"java技术\"}]";
		System.out.println("格式化JSON:");
		gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(json);
		json = gson.toJson(je);
		System.out.println(json);
		System.out.println();
 
	}
 
	@Test
	public void test5() throws Exception {
		System.out.println("判断字符串是否是json,通过捕捉的异常来判断是否是json");
		String json = "[{\"id\":\"1\",\"name\":\"Json技术\"},{\"id\":\"2\",\"name\":\"java技术\"}]";
		boolean jsonFlag;
		try {
			new JsonParser().parse(json).getAsJsonObject();
			jsonFlag = true;
		} catch (Exception e) {
			jsonFlag = false;
		}
		System.out.println(jsonFlag + ":" + jsonFlag);
		System.out.println();
	}
 
	@Test
	public void test6() throws Exception {
		System.out.println("从json串中获取属性");
		String json = "{\"id\":\"1\",\"name\":\"Json技术\"}";
		String propertyName = "name";
		String propertyValue = "";
		try {
			JsonParser jsonParser = new JsonParser();
			JsonElement element = jsonParser.parse(json);
			JsonObject jsonObj = element.getAsJsonObject();
			propertyValue = jsonObj.get(propertyName).toString();
			System.out.println("propertyValue:" + propertyValue);
		} catch (Exception e) {
			propertyValue = null;
		}
		System.out.println();
	}
 
	@Test
	public void test7() throws Exception {
		System.out.println("除去json中的某个属性");
		String json = "{\"id\":\"1\",\"name\":\"Json技术\"}";
		String propertyName = "id";
		JsonParser jsonParser = new JsonParser();
		JsonElement element = jsonParser.parse(json);
		JsonObject jsonObj = element.getAsJsonObject();
		jsonObj.remove(propertyName);
		json = jsonObj.toString();
		System.out.println("json：" + json);
		System.out.println();
	}
 
	@Test
	public void test8() throws Exception {
		System.out.println("向json中添加属性");
		String json = "{\"id\":\"1\",\"name\":\"Json技术\"}";
		String propertyName = "desc";
		Object propertyValue = "json各种技术的调研";
		JsonParser jsonParser = new JsonParser();
		JsonElement element = jsonParser.parse(json);
		JsonObject jsonObj = element.getAsJsonObject();
		jsonObj.addProperty(propertyName, new Gson().toJson(propertyValue));
		json = jsonObj.toString();
		System.out.println("json:" + json);
		System.out.println();
	}
 
	@Test
	public void test9() throws Exception {
		System.out.println("修改json中的属性");
		String json = "{\"id\":\"1\",\"name\":\"Json技术\"}";
		String propertyName = "name";
		Object propertyValue = "json各种技术的调研";
		JsonParser jsonParser = new JsonParser();
		JsonElement element = jsonParser.parse(json);
		JsonObject jsonObj = element.getAsJsonObject();
		jsonObj.remove(propertyName);
		jsonObj.addProperty(propertyName, new Gson().toJson(propertyValue));
		json = jsonObj.toString();
		System.out.println("json:" + json);
		System.out.println();
	}
 
	@Test
	public void test10() throws Exception {
		System.out.println("判断json中是否有属性");
		String json = "{\"id\":\"1\",\"name\":\"Json技术\"}";
		String propertyName = "name";
		boolean isContains = false;
		JsonParser jsonParser = new JsonParser();
		JsonElement element = jsonParser.parse(json);
		JsonObject jsonObj = element.getAsJsonObject();
		isContains = jsonObj.has(propertyName);
		System.out.println("isContains:" + isContains);
		System.out.println();
	}
 
	@Test
	public void test11() throws Exception {
		System.out.println("json中日期格式的处理");
		GsonBuilder builder = new GsonBuilder();
		builder.setDateFormat("yyyy-MM-dd");
		Gson gson = builder.create();
		User user = new User();
		user.setBirthday(new Date());
		String json = gson.toJson(user);
		System.out.println("json:" + json);
		System.out.println();
	}
 
	@Test
	public void test12() throws Exception {
		System.out.println("json中对于Html的转义");
		GsonBuilder builder = new GsonBuilder();
		builder.disableHtmlEscaping();
		Gson gson = builder.create();
		System.out.println();
	}

	@Test
	public  void  mapToJson() {
		Map<String, User> map =new HashMap<>();
		User luna = new User(1, 21, "luna", new Date());
		map.put("map",luna);
		Gson gson = new Gson();
		String jsonStr = gson.toJson(map);
		System.out.println(jsonStr);

		map = gson.fromJson(jsonStr,Map.class);
		System.out.println(map.getClass());

		String s = JSONArray.toJSONString(map);
		System.out.println(s);
	}

	@Test
	public void aTest() {

		String str = "{ "+
				"\"client\":\"127.0.0.1\"," +
				"\"servers\":[" +
				"    \"8.8.8.8\"," +
				"    \"8.8.4.4\"," +
				"    \"156.154.70.1\"," +
				"    \"156.154.71.1\" " +
				"    ]" +
				"}";

		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject)jsonParser.parse(str);
		JsonArray jsonArr = jo.getAsJsonArray("servers");
		//jsonArr.
		Gson googleJson = new Gson();
		ArrayList jsonObjList = googleJson.fromJson(jsonArr, ArrayList.class);
		System.out.println("List size is : "+jsonObjList.size());
		System.out.println("List Elements are  : "+jsonObjList.toString());
	}
}