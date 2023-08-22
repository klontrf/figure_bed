<script lang="ts" setup>
import {addPic} from "@/stores/addPicStatus";
import {UploadFilled} from "@element-plus/icons";
import {ElMessage} from "element-plus";

import {useLoginStore} from "@/stores/login"
const userData=useLoginStore()
const addPicDialog = addPic()

const handleClose = (done: () => void) => {
  userData.flushUserData()
  done()
}


const verifyFile=async (file:File)=>{
  const allowedTypes = ['image/svg+xml', 'image/jpeg', 'image/png', 'image/gif', 'image/bmp', 'image/webp'];
  const isAllowedType = allowedTypes.indexOf(file.type)!=-1;

  if (!isAllowedType) {
    ElMessage.warning('只能上传 SVG、JPEG、PNG、GIF、BMP 和 WebP 文件');
    return false;
  }

  const allow=file.size/1024/1024<1;
  if(!allow){
    ElMessage.warning("文件大小超出1MB，请重新上传");
    return false;
  }
  return true
}
const success=(res)=>{
  console.log(res)
  if(res.code===10011)ElMessage.warning(res.message)
}
</script>

<template>
  <el-dialog
      v-model="addPicDialog.addDialogVisible"
      title="上传图片"
      width="70%"
      center
      :before-close="handleClose"

  >

    <el-upload
        class="upload-demo"
        drag
        method="post"
        accept=".svg,.png,.jpg,.jpeg,.gif,.bmp,.webp"
        :before-upload="verifyFile"
        action="http://8.130.90.140:8080/user/upload"
        :on-success="success"
        multiple
    >
      <el-icon class="el-icon--upload"><upload-filled /></el-icon>
      <div class="el-upload__text">
        拖拽文件到此处或 <em>点击上传</em>
      </div>
      <template #tip>
        <div class="el-upload__tip">
          只能上传&nbsp;<font style="color: #409eff">svg,jpg,png,jpeg,gif,bmp,webp</font>&nbsp;格式文件,且文件大小不能超过&nbsp;<font style="color: #409eff">1MB</font>
        </div>
      </template>
    </el-upload>

  </el-dialog>
</template>

<style scoped>
.dialog-footer button:first-child {
  margin-right: 10px;
}
</style>