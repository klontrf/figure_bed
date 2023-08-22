<script lang="ts" setup>
import {addPic} from "@/stores/addPicStatus";

import { ElMessageBox } from 'element-plus'
const addPict=addPic()


const handleClose = (done: () => void) => {
  ElMessageBox.confirm('关闭该窗口吗?')
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
      v-model="addPict.changePasswordVisible"
      title="修改密码"
      width="30%"
      :before-close="handleClose"
  >
    <div>
      输入新密码：
      <el-input maxlength="16" show-password v-model="addPict.newPassword" onkeyup="this.value=this.value.replace(/\s+/g, '');"/>
      确认新密码：
      <el-input maxlength="16" show-password v-model="addPict.verifyPassword" onkeyup="this.value=this.value.replace(/\s+/g, '');"/>
    </div>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="addPict.changePasswordVisible = false">取消修改</el-button>
        <el-button type="primary" @click="addPict.change">
          提交修改
        </el-button>
      </span>
    </template>
  </el-dialog>
</template>

<style scoped>
.dialog-footer button:first-child {
  margin-right: 10px;
}
</style>