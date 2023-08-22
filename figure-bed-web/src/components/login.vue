<script setup>
import "../assets/login.css"
import {ref,onMounted} from "vue";
import {useLoginStore} from "@/stores/login";
import {registerStatus} from "@/stores/register";
import FoundPassword from "@/components/foundPassword.vue";
import {forgetStatus} from "@/stores/foundPasswordStatus";

const isActive=ref(null);

onMounted(() => {
  isActive.value=false;
  // console.log(isActive.value)
})
const addNewClass=()=>{
  isActive.value=!isActive.value;
  // console.log(isActive.value)
}

const loginData=useLoginStore()
const register=registerStatus()
const visible=ref(false)
const forgetPasswdStatus=forgetStatus()
</script>

<template>
  <div :class="['container',{'active':isActive}]" >
    <!-- register -->
    <div class="form-container sign-up-container">
      <div class="form">
        <h2>sign up</h2>
        <input maxlength="12" type="text" v-model="register.account" onkeyup="this.value=this.value.replace(/[^\w_]/g,'');" placeholder="Username...">
        <input maxlength="32" type="email" v-model="register.email" onkeyup="this.value=this.value.replace(/\s+/g, '');" placeholder="Email...">

        <div>
          <input maxlength="10" type="text" v-model="register.yzm" onkeyup="this.value=this.value.replace(/[^\w_]/g,'');" id="captcha" placeholder="Captcha...">
          &nbsp;&nbsp;&nbsp;
          <el-button id="yz" @click="register.sendEmail" :disabled="register.getYzmButton" type="primary">{{register.buttonMessage}}</el-button>
        </div>

        <input maxlength="16" type="password" v-model="register.password" onkeyup="this.value=this.value.replace(/\s+/g, '');" placeholder="Password...">
        <input maxlength="16" type="password" v-model="register.confirmPassword" onkeyup="this.value=this.value.replace(/\s+/g, '');" placeholder="ConfirmPassword...">
        <button class="signUp" @click="register.registerNow()">sign up</button>
      </div>
    </div>
    <!-- login -->
    <div class="form-container sign-in-container">
      <div class="form">
        <h2>sign in</h2>
        <input maxlength="12" v-model="loginData.account" onkeyup="this.value=this.value.replace(/[^\w_]/g,'');" type="text" placeholder="Username...">
        <input maxlength="16" v-model="loginData.password" onkeyup="this.value=this.value.replace(/\s+/g, '');" type="password" placeholder="Password...">
        <a @click="forgetPasswdStatus.isRender=true,forgetPasswdStatus.dialogVisible=true" class="forget-password">forget your password</a>
        <found-password v-if="forgetPasswdStatus.isRender" />
        <button class="signIn" @click="loginData.loginRequest">sign in</button>
      </div>
    </div>
    <!-- overlay container -->
    <div class="overlay_container">
      <div class="overlay">
        <!-- overlay left -->
        <div class="overlay_panel overlay_left_container">
          <h2>welcome back!</h2>
          <p>To keep connected with us please login with your personal info</p>
          <button id="sign-in"  @click="addNewClass()">sign in</button>
        </div>
        <!-- overlay right -->
        <div class="overlay_panel overlay_right_container">
          <h2>hello friend!</h2>
          <p>Enter your personal details and start journey with us</p>
          <button id="sign-up"  @click="addNewClass()">sign up</button>
        </div>
      </div>
    </div>
  </div>
  <div id="pic">
    <img id="photo" src="../assets/456.jpg" alt="样图">
  </div>
</template>

<style scoped>
#pic{
  position: relative;
  width: 480px;
  padding: 0 30px 0;
}
#photo{
  height: 480px;
  width: 100%;
  border-radius: 30px;
}
#captcha{
  width: 157px;
  float: left;
}
#yz{
  width: 110px;
}
</style>