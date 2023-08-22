<script lang="ts" setup>

import { ElMessageBox } from 'element-plus'
import {forgetStatus} from "@/stores/foundPasswordStatus";

const dialogVisible = forgetStatus()
const handleClose = (done: () => void) => {
  ElMessageBox.confirm('关闭该窗口吗？你输入的信息会被暂时保留。')
      .then(() => {
        done()
      })
      .catch(() => {
        // catch error
      })
}

</script>

<template>
  <el-dialog
      v-model="dialogVisible.dialogVisible"
      title="找回密码"
      width="35%"
      :before-close="handleClose"
      style="border-radius: 4px"
  >
    <span>输入要找回密码的用户名：</span><br>
    <span><el-input maxlength="12" v-model="dialogVisible.account" type="text" placeholder="Username…" onkeyup="this.value=this.value.replace(/[^\w_]/g,'');"/></span><br>
    <div style="height: 30px">校验原始注册邮箱(填写验证码)：
      <el-button :disabled="dialogVisible.getYzmBtn" @click="dialogVisible.getCap" id="bb" type="primary" >{{dialogVisible.btnMsg}}</el-button>
    </div><br>

      <span>
        <el-input maxlength="10" v-model="dialogVisible.yzm" placeholder="Captcha…" />
    </span>
    <span>新密码：</span><br>
    <span><el-input max="16" v-model="dialogVisible.password" onkeyup="this.value=this.value.replace(/\s+/g, '');" type="password" show-password placeholder="NewPassword…"/> </span>

    <br>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible.dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="dialogVisible.change">
          提交
        </el-button>
      </span>
    </template>
  </el-dialog>
</template>

<style scoped>
.dialog-footer button:first-child {
  margin-right: 10px;
}
#bb{
  float: right;
  width:130px ;
  border-bottom: 30px;
}
</style>