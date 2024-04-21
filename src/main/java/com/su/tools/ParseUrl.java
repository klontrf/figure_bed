package com.su.tools;


import com.su.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ParseUrl {
    @Value("${app.users.path}")
    private String rootPath;
    public String getUserAccount(String url){
        String[] split = url.split("/");
        int l= split.length;
        return split[l-2];
    }
    public String getFileName(String url){
        String[] split = url.split("/");
        int l= split.length;
        return split[l-1];
    }
    public String getFilePath(String url){
        String[] split = url.split("/");
        int l= split.length;
        return rootPath+split[l-2]+"/"+split[l-1];
    }
}
