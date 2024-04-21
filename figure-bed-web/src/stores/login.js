import {defineStore} from "pinia";
import {ref} from "vue";
import axios from "axios";
import {messageTip} from "@/stores/message";
import {pageStatus} from "@/stores/page";

export const useLoginStore=defineStore('login',()=>{
    const account=ref(null);
    const password=ref(null);
    const userData=ref([]);
    const messagePrompt=messageTip()
    const page1=pageStatus();

    const loginRequest=()=>{
        if(account.value==null||password.value==null||account.value===''||password.value===''){
            messagePrompt.errorMessage('请填写登录信息！');
            return;
        }
        axios({
            method:"post",
            url:"http://8.130.90.140:8080/user/login",
            data:{
                "account":account.value,
                "password":password.value
            }
        }).then(res=>{
            // console.log(res)
            if (res.data.code===10011) messagePrompt.errorMessage(res.data.message)
            else if (res.data.code===10010){
                userData.value=res.data.data
                page1.isLogin=false;
                page1.isBarVisible=true;
                page1.isHome=true;
            }
        }).catch(err=>{
            console.log(err)
        })
    }
    const flushUserData=()=>{
        axios({
            method:"get",
            url:"http://8.130.90.140:8080/user/home",
            data:null
        }).then(res=>{
            userData.value=res.data.data
        }).catch(err=>{
            console.log(err)
        })
    }
    const delFile=(fileUrl)=>{
        axios({
            method:"delete",
            url:"http://8.130.90.140:8080/user/del-file",
            params:{
                "picUrl":fileUrl
            }
        }).then(res=>{
            if(res.data.code===10010){
                messagePrompt.successMessage(res.data.message);
                flushUserData();

            }else if(res.data.code===10011)messagePrompt.errorMessage(res.data.message)
        }).catch(err=>{
            console.log(err)})
    }
    return {account,password,loginRequest,userData,flushUserData,delFile}
})