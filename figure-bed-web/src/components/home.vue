
<template >

  <el-row >
    <el-col
        v-for="o in userData.userData"
        :key="o"
        :span="6"
        :offset="0"
    >
      <el-card :body-style="{ padding: '15px' }">

        <el-image
            class="pic"
            :src="o"
            fit="scale-down"
        />

        <div style="padding: 14px">
          <span>URL: <input :value="o" disabled></span>
          <div class="bottom">
            <el-button @click="showDialog=true,preDel=o" type="danger" :icon="Delete" circle />
          </div>
        </div>
      </el-card>
    </el-col>
<el-col>
  <el-dialog v-if="showDialog" v-model="showDialog" title="注意" width="30%" center>
        <span>
          确认删除&nbsp;<el-text type="primary">{{preDel}}</el-text>&nbsp;吗？
        </span>
    <template #footer>
          <span class="dialog-footer">
            <el-button @click="showDialog = false">手滑了</el-button>
            <el-button type="primary" @click="userData.delFile(preDel),showDialog=false,userData.flushUserData()">
              确认删除
            </el-button>
          </span>
    </template>
  </el-dialog>
</el-col>
    <el-col v-if="userData.userData===null||userData.userData.length===0">
        <el-empty description="这里空空如也，快上传图片吧……" />
    </el-col>
  </el-row>


</template>

<script lang="ts" setup>
import {useLoginStore} from "@/stores/login"
import {Delete} from "@element-plus/icons";
import {ref,onMounted} from "vue";
const userData=useLoginStore()
onMounted(()=>{
  userData.flushUserData()
})


const showDialog=ref(false)
const preDel=ref('')
</script>

<style scoped>
.bottom {
  margin-top: 13px;
  line-height: 12px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.pic{
  height: 200px;
}

.dialog-footer button:first-child {
  margin-right: 10px;
}
</style>
