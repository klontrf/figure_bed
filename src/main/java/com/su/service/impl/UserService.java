package com.su.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.su.dao.SourceDao;
import com.su.dao.UserDao;
import com.su.domain.User;
import com.su.service.IUserService;
import com.su.tools.ParseUrl;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@ComponentScan("com.su.dao")
public class UserService extends ServiceImpl<UserDao, User> implements IUserService {
    //mysql数据mapper
    @Autowired
    private UserDao userDao;
    @Autowired
    private SourceDao sourceDao;
    //邮件
    @Autowired
    JavaMailSender javaMailSender;
    @Value("${spring.mail.username}") //动态获取配置文件中 发送邮件的账户
    private String sendemail;
    //redis
    @Autowired
    StringRedisTemplate redisTemplate;

    @Override
    public List<String> getData(int id) {
        return userDao.getUserSource(id);
    }

    @Override
    public boolean login(User user) {
        String account = user.getAccount();
        User user1 = userDao.selectById(account);
        return user1.getPassword().equals(user.getPassword());
    }

    @Override
    public boolean captcha(String mail) {

        int yzm = new Random().nextInt(9999) % (9999 - 1000 + 1) + 1000;
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper=new MimeMessageHelper(message,true);
            //发送邮件账户
            helper.setFrom(sendemail,"邮件小助手");
            //谁要接收
            helper.setTo(mail);
            //邮件标题
            helper.setSubject("验证码");
            //邮件内容
            helper.setText("图床：您的验证码是"+yzm+" ,3分钟内有效,请勿泄露给其他人。");
            HashOperations<String, String, String> hOps = redisTemplate.opsForHash();
            hOps.put(mail,"yzm",String.valueOf(yzm));
            //验证码3分钟有效
            redisTemplate.expire(mail,3, TimeUnit.MINUTES);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        javaMailSender.send(message);

        return true;
    }

    @Override
    public boolean cmpYzm(String mail,String yzmt) {
        HashOperations<String, String, String> hOps = redisTemplate.opsForHash();
        String s = hOps.get(mail, "yzm");
        if(s==null)return false;
        return s.equals(yzmt);
    }

    @Override
    public boolean removeYzm(String mail) {
        return redisTemplate.delete(mail) != null;
    }

    @Value("${app.users.path}")
    private String rootDir;
    //创建用户目录
    @Override
    public boolean createDir(String account) {
        File file=new File(rootDir,account);
        return file.mkdir();
    }

    @Override
    public String getAccountById(Integer id) {
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",id);
        User user = userDao.selectOne(queryWrapper);
        return user.getAccount();
    }
    @Value("${app.ip-config.prefix}")
    private String ipPrefix;

    //保存文件
    @Override
    public boolean saveFile(MultipartFile file,String account,Integer id) {
        String filePath=rootDir+account+"/"+file.getOriginalFilename();
        File tmp=new File(filePath);
        if(tmp.exists())return false;
        try {
            file.transferTo(tmp);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    sourceDao.savePic(id,ipPrefix+account+"/"+file.getOriginalFilename());
        return true;
    }
    //删除文件
    @Autowired
    private ParseUrl urlTool;

    @Override
    public boolean delFile(String picUrl) {
        String filePath = urlTool.getFilePath(picUrl);
        File pic=new File(filePath);
        if (pic.delete()){
            sourceDao.delPic(picUrl);
            return true;
        }
        return false;
    }

    @Override
    public boolean verifyUser(String url, String account) {
        String userAccount = urlTool.getUserAccount(url);
        if(userAccount==null)return false;
        return userAccount.equals(account);
    }

}
