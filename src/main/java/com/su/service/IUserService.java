package com.su.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.su.domain.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IUserService extends IService<User> {
    List<String> getData(int id);
    boolean login(User user);
    boolean captcha(String mail);
    boolean cmpYzm(String mail,String yzmt);
    boolean removeYzm(String mail);

    boolean createDir(String account);

    boolean saveFile(MultipartFile file,String account,Integer id);
    String getAccountById(Integer id);
    boolean delFile(String picUrl);
    boolean verifyUser(String url,String account);

}
