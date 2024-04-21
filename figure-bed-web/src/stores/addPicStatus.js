import {defineStore} from "pinia";
import {ref} from "vue";
import axios from "axios";
import {ElMessage} from "element-plus";
import {pageStatus} from "@/stores/page";


export const addPic=defineStore('2',()=>{
    const addDialogVisible=ref(false);
    const page=pageStatus()
    const newPassword=ref('');
    const verifyPassword=ref('');
    const changePasswordVisible=ref(false);
    const exitLogin=()=>{
        axios({
            method:"post",
            url:"http://8.130.90.140:8080/user/exit",
            data:null
        }).then(()=>{
                page.isBarVisible=false;
                page.isHome=false;
                page.isLogin=true;
        }).catch(err=>{
            console.log(err)
        })
    }

    const change=()=>{
        if(newPassword.value===''||verifyPassword.value===''){
            ElMessage.error("请补全信息！");
            return;
        }
        if(newPassword.value!==verifyPassword.value){
            ElMessage.error("两次密码输入不一致！");
            return;
        }

        axios({
            method:"PUT",
            url:"http://8.130.90.140:8080/user/changePassword",
            data:{
                "password": verifyPassword.value,
            },
            withCredentials: true,

        }).then(res=>{
            console.log(res)
            if(res.data.code===10011){ElMessage.error(res.data.message)}
            else if(res.data.code===10010){
                ElMessage.success(res.data.message);
                page.isBarVisible=false;
                page.isHome=false;
                page.isLogin=true;
            }
        }).catch(err=>{
            console.log(err)
        })

    }
    return {addDialogVisible,exitLogin,change,changePasswordVisible,newPassword,verifyPassword}
})