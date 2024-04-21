# 图床项目功能规划

## 1、登录与注册

- 登录
  - 根据账号绑定每个用户的资源，将资源与对应的账号关联起来，需要结合mysql数据库实现。
  - 根据用户名密码登录，登录失败返回相应异常信息
- 注册
  - 注册信息包括账号，二次确认密码，邮箱信息
  - 尽量验证邮箱的可用性，向邮箱发送邮件验证码什么的
  - 忘记密码发送账号邮件更改密码

## 2、资源的管理

- 将相应的图片资源与用户id或账号绑定
- 用户可以上传图片，删除已经上传的图片，浏览图片
- 退出登录
- 需要看到用户自己的图片链接



# 预估用到的相关技术

- **文件**上传相关
- <font color='blue'>需要调用一些第三方api丰富供能</font>



# api使用说明

- [x] 测试通过

- 账号的注册

1、发送post请求至 **/user** 携带注册邮箱参数 **mail**

2、发送post请求至 **/user/signup** 携带user请求体，请求体中只需包含账号(account)、密码(password)、邮箱(mail)、验证码(yzm)

- [x] 测试通过

- 登录账号

1、发送post请求至 **/user/login** 携带user请求体，包含账号、密码属性即可



- [x] 测试通过

- 获取登录用户资源列表

1、登陆后发送get请求至 **/user/home** 



- [x] 测试通过

- 退出登录

1、登录的情况下发送post请求至 **user/exit** 



- [x] 测试通过

- 登陆后更改密码

1、发送put请求至 **/user/changePassword** 携带新密码参数 **password**



- [x] 测试通过

- 忘记密码

1、发送put请求至 **/user** 携带账号参数 **account**获取验证码

2、发送put请求至 **/user/forgetPassword** 请求体：账号、新密码、验证码参数 **account、newPassword、yzm**



上传文件请求通过

删除文件请求通过

校验文件是否存在接口废弃

……



可能出现的问题：

- 如果前端资源与后端不在同一项目中可能会出现axios跨域请求错误

# 开发日志

1. 将虚拟路径``/xvni/**``映射到了本地的D盘下的``test-dir``目录,后期需要更改
2. 上传文件大小需要在1mb以内，支持上传的文件后缀：``svg,jpg,png,jpeg,gif,bmp,webp``
3. 涉及到请求的组件以及store：
   - addPic.vue
   - 除了message.js和page.js的所有store

……
### 开发环境
- mysql8.0
- jdk17或以上
- vue3.3.4
- node-v20.5.0
- springboot3
- element-plus
- redis
- mybatis-plus
- ……详细见依赖等配置文件
