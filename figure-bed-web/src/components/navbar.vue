<template>
  <el-menu
      class="el-menu-demo"
      mode="horizontal"
      :ellipsis="false"

  >
    <el-menu-item index="0"><img id="tu-logo" src="../assets/tuLogo.png" alt=""></el-menu-item>

    <div class="flex-grow" style="padding-right: 100px">

      <el-button @click="addPicDialog.addDialogVisible=true" size="large" style="float:right;" type="primary">上传图片</el-button>
      <add-com/>
    </div>


    <a style="display: block;padding-top: 12px" href="https://github.com/klontrf/figure_bed.git" target="_blank"> <img id="githubLogo" src="../assets/github.svg" alt=""> </a>
    <el-sub-menu index="1">
      <template #title><img id="userLogo" src="../assets/user.svg" alt="">&nbsp;&nbsp;{{loginData.account}}</template>
      <el-menu-item index="1-3" @click="addPicDialog.changePasswordVisible=true"><img id="changeLogo" src="../assets/changePasswd.svg" alt="">&nbsp;&nbsp;更改密码</el-menu-item>
      <el-menu-item index="1-2" @click="addPicDialog.exitLogin"><img id="exit-logo" src="../assets/exit.svg" alt="">&nbsp;&nbsp;退出登录</el-menu-item>

    </el-sub-menu>
    <change-password/>
  </el-menu>

</template>

<script  setup>
//导入添加文件组件状态管理
import {addPic} from "@/stores/addPicStatus"
//导入子组件，添加文件
import addCom from "@/components/addPic.vue"
//添加修改密码的组件
import changePassword from "@/components/chagePassword.vue"
import {useLoginStore} from "@/stores/login";

const addPicDialog=addPic();
const loginData=useLoginStore();


</script>

<style scoped>
.flex-grow {
  flex-grow: 1;
}
.el-menu-demo{
  position: absolute;
  border-top: 1px;
}
#exit-logo,#changeLogo,#userLogo{
  height: 20px;
}
#githubLogo{
  height: 30px;
  border-top: 30px;
}
#tu-logo{
  height: 60px;
}
</style>
