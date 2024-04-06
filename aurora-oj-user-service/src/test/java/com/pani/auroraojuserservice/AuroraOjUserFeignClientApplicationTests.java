package com.pani.auroraojuserservice;

import cn.hutool.core.io.FileUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AuroraOjUserFeignClientApplicationTests {
    public static void main(String[] args) {
        String s = "wafegegdavsa.jpg";
        String prefix = FileUtil.getPrefix(s);
        System.out.println(prefix);
    }

    @Test
    void contextLoads() {
    }

}
