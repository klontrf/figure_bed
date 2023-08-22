package com.su;

import com.su.dao.UserDao;
import com.su.domain.User;
import com.su.service.IUserService;
import com.su.service.impl.UserService;
import com.su.tools.ParseUrl;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;


import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@SpringBootTest
//@ComponentScan("com.su.service")
class FigureBedApplicationTests {
    @Autowired
    JavaMailSender javaMailSender;
@Autowired
UserDao userDao;
    @Value("${spring.mail.username}") //动态获取配置文件中 发送邮件的账户
    private String sendemail;
    @Autowired
    IUserService userService;
    @Autowired
    StringRedisTemplate redisTemplate;
    @Test
    //
    void contextLoads() throws MessagingException, UnsupportedEncodingException {
//随机生成一个四位数的验证码
        int yzm = new Random().nextInt(9999) % (9999 - 1000 + 1) + 1000;
        //创建简单邮件消息
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper=new MimeMessageHelper(message,true);
        //发送邮件账户
        helper.setFrom(sendemail,"邮件小助手");
        //谁要接收
        helper.setTo("su2650438058@yeah.net");
        //邮件标题
        helper.setSubject("服务器邮件");
        //邮件内容
        helper.setText("提醒：您的验证码是"+yzm);
        javaMailSender.send(message);
//        System.out.println(yzm);
    }
    @Autowired
    private ParseUrl tool;
    @Test
    void s(){
        System.out.println(tool.getUserAccount("http://localhost:8080/pic/test/202308.jpg"));
    }
    @Test
    void daoTest(){
        List<String> userSource = userDao.getUserSource(2);
        System.out.println(userSource);
    }
    //redis侧式
    @Test
    void redisdata(){
        HashOperations<String,String,String> hashOps=redisTemplate.opsForHash();
        hashOps.put("user", "name", "John");
        hashOps.put("user", "age", "25");
        hashOps.put("user", "email", "john@example.com");

redisTemplate.expire("user",1, TimeUnit.MINUTES);
        String s = hashOps.get("user", "name");

        System.out.println(s==null?"null":s);
    }
    @Value("${app.users.path}")
    String path;
    @Test
    void asd(){
        File file=new File(path,"test-dir");
        if (file.mkdirs()) System.out.println("success");
        else System.out.println("failed");
    }


}
