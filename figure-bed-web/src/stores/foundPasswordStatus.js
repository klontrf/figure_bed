import {defineStore} from "pinia";
import {ref} from "vue";
import axios from "axios";
import {messageTip} from "@/stores/message";

export const forgetStatus=defineStore('1',()=>{
    const counter=ref(180)
    const msg=messageTip()
    const getYzmBtn=ref(false)
    const dialogVisible=ref(false);
    const isRender=ref(false);

    const yzm = ref('');
    const account = ref('');
    const password = ref('');
    const btnMsg=ref('获取验证码')

    const getCap=()=>{
        if(account.value===''){
            msg.errorMessage('请填写账号!')
            return
        }
        axios({
            method: "put",
            url: "http://8.130.90.140:8080/user",
            params: {
                "account": account.value
            }
        }).then(res => {
            if (res.data.code === 10011) msg.errorMessage(res.data.message);
            else if (res.data.code === 10010) {
                msg.successMessage(res.data.message)
                getYzmBtn.value = true
                const myId=setInterval(() => {
                    btnMsg.value=counter.value+'s后重新获取';
                    counter.value--;
                    if (counter.value === 0) {
                        btnMsg.value='获取验证码'
                        getYzmBtn.value=false;
                        counter.value=180
                        clearInterval(myId);
                    }

                }, 1000)
            }
        }).catch(err => {
            console.log(err)
        })
    }
    const change=()=>{
        if(account.value===''||password.value===''||yzm.value===''){
            msg.errorMessage('请将信息填写完整!')
            return
        }
        axios({
            method:"put",
            url:"http://8.130.90.140:8080/user/forgetPassword",
            data:{
                "account":account.value,
                "password":password.value,
                "yzm":yzm.value
            }
        }).then(res=>{
            if(res.data.code===10010)msg.successMessage(res.data.message)
            else if (res.data.code===10011)msg.errorMessage(res.data.message)
        }).catch(err=>{
            console.log(err)
        })
    }

    return {dialogVisible,isRender,yzm,account,password,btnMsg,getYzmBtn,getCap,change}
})

