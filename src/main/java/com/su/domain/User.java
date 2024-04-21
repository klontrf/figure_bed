package com.su.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("user")
public class User {
//    @TableField(value = "idcard")
    private Integer id;

    private String password;
    private String mail;
    @TableId(value = "account")
    private String account;
@TableField(exist = false)
    private String yzm;
}
