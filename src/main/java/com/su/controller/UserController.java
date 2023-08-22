package com.su.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.su.domain.Code;
import com.su.domain.Result;
import com.su.domain.User;
import com.su.service.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
    @Autowired
    IUserService userService;
//前端请求该方法来判断账号是否可用
    @GetMapping
    public Result accountAvailable(@RequestParam String account){
        if(userService.getById(account)==null)return new Result(Code.SUCCESS,"用户名可用",null) ;
        return new Result(Code.FAILED,"用户名被占用",null);
    }
//如果有后台管理系统，则用来获取全部用户信息
//    @GetMapping("/all")
//    public List<User> getAllUser(){
//        return userService.list();
//    }

    //登录请求
    @PostMapping("/login")
    public Result login_i(@RequestBody User user, HttpSession session){
        if(userService.getById(user.getAccount())==null)return new Result(Code.FAILED,"用户不存在",null);
        if(userService.login(user)){
            User user1 = userService.getById(user.getAccount());
            session.setAttribute("online",user1.getId());
//            System.out.println(session.getAttribute("online"));
            return new Result(Code.SUCCESS,null,null);
        }
        return new Result(Code.FAILED,"密码错误",null);
    }
    //退出登录
    @PostMapping("/exit")
    public void exit(HttpSession session){
        session.removeAttribute("online");
    }
//获取用户资源
    @GetMapping("/home")
    public Result home(HttpServletRequest request){
        Integer id = (Integer) (request.getSession().getAttribute("online"));
        List<String> data = userService.getData(id);
        return new Result(Code.SUCCESS,null, data);
    }
    //注册账号
    @PostMapping("/signup")
    public Result signUp(@RequestBody User user){
        if(userService.getById(user.getAccount())!=null)return new Result(Code.FAILED,"用户已存在",null);
        if(user.getPassword()==null||user.getMail()==null||user.getAccount()==null){
            return new Result(Code.FAILED,"信息不完整！",null);
        }

        if(!userService.cmpYzm(user.getMail(),user.getYzm()))return new Result(Code.FAILED,"验证码错误",null);

        boolean save = userService.save(user);
        if(save){
            //移除验证码
            userService.removeYzm(user.getMail());
            //创建用户目录
            userService.createDir(user.getAccount());
            return new Result(Code.SUCCESS,"注册成功",null);
        }
        return new Result(Code.FAILED,"请检查信息！",null);
    }
    //发送注册验证码
    @PostMapping
    public Result sentYzm(@RequestParam String mail){
        //不再校验邮箱格式的合法性，前端完成
        if(userService.captcha(mail))return new Result(Code.SUCCESS,"发送成功，2分钟内有效，请注意查收",null);
        return new Result(Code.FAILED,"发送失败",null);
    }
    //更改密码（登陆后更改）

    @PutMapping("/changePassword")
    public Result updatePa(@RequestBody User user, HttpSession session){
        String password=user.getPassword();
        Integer id=(Integer)(session.getAttribute("online"));
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.eq("id",id).set("password",password);
        if (userService.update(null,wrapper)){
            session.removeAttribute("online");
            return new Result(Code.SUCCESS,"密码更新成功，请重新登录。",null);
        };
        return new Result(Code.FAILED,"更新失败",null);
    }
//忘记密码-发送验证邮件
    @PutMapping
    public Result forgetPassword(@RequestParam String account){
        User user = userService.getById(account);
        if(user==null)return new Result(Code.FAILED,"该用户不存在",null);
        userService.captcha(user.getMail());
        return new Result(Code.SUCCESS,"已发送验证码至初始注册邮箱，3分钟内有效，请注意查收",null);
    }
    //忘记密码-修改密码
    @PutMapping("/forgetPassword")
    public Result forgetPassword(@RequestBody User user1){
        String account = user1.getAccount();
        String yzm = user1.getYzm();
        String newPassword = user1.getPassword();
        User user = userService.getById(account);
        if(user==null)return new Result(Code.FAILED,"该用户不存在",null);
        if(!userService.cmpYzm(user.getMail(),yzm))return new Result(Code.FAILED,"验证码错误",null);
        UpdateWrapper<User> wrapper=new UpdateWrapper<>();
        wrapper.eq("account",account).set("password",newPassword);
        boolean update = userService.update(null, wrapper);
        if(update){
            User user2 = userService.getById(user1.getAccount());
            userService.removeYzm(user2.getMail());
            return new Result(Code.SUCCESS,"更改密码成功",null);
        }
        return new Result(Code.FAILED,"更改失败",null);
    }
    //接收文件
    @PostMapping("/upload")
    public Result uploadFile(@RequestPart MultipartFile file,HttpSession session){
        Integer id=(Integer)session.getAttribute("online");

        if(file.isEmpty())return new Result(Code.FAILED,"文件为空",null);
        if(file.getSize()> 1048576L)return new Result(Code.FAILED,"上传失败，文件超出1MB",null);
        boolean b = userService.saveFile(file, userService.getAccountById(id),id);
        if(!b)return new Result(Code.FAILED,"文件名 "+file.getOriginalFilename()+" 已存在！",null);

        return new Result(Code.SUCCESS,"上传成功！",null);
    }

    //删除文件
    @DeleteMapping("/del-file")
    public Result delFile(@RequestParam("picUrl") String picUrl,HttpSession session){
        String account = userService.getAccountById((Integer) session.getAttribute("online"));
        if(!userService.verifyUser(picUrl,account))return new Result(Code.FAILED,"无权限操作",null);
        if(!userService.delFile(picUrl))return new Result(Code.FAILED,"文件不存在",null);
        return new Result(Code.SUCCESS,"删除成功",null);
    }
}
