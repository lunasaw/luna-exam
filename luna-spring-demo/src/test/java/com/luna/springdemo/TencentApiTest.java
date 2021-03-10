package com.luna.springdemo;

import com.luna.media.javacv.CheckFace;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URL;


/**
 * @author Luna@win10
 * @date 2020/5/6 20:58
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TencentApiTest {

	@Test
	public void aTest() throws Exception {
        URL flandMarkModel = CheckFace.class.getClassLoader().getResource("static/faceData/flandmark_model.dat");
        System.out.println(flandMarkModel.getPath());
        CheckFace.chackFaceAndShow("C:\\Users\\improve\\Pictures\\Saved Pictures\\friends.jpg");
	}

}
