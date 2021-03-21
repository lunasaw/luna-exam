package com.luna.spring;

import com.luna.media.javacv.CheckFace;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Package: com.luna.spring
 * @ClassName: MediaTest
 * @Author: luna
 * @CreateTime: 2020/8/24 14:50
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MediaTest {

    @Test
    public void aTest() throws Exception {
        CheckFace.chackFaceAndShow("C:\\Users\\improve\\Pictures\\Saved Pictures\\friends.jpg");
    }
}
