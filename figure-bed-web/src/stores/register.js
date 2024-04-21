import {defineStore} from "pinia";
import {ref} from "vue";
import {messageTip} from "@/stores/message";
import axios from "axios";

export const registerStatus = defineStore('register', () => {
    const messagePrompt = messageTip()
    const counter=ref(180)
    const confirmMail = (mail) => {
        let reg = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;
        return reg.test(mail);
    }


    const email = ref('');
    const yzm = ref('');
    const account = ref('');
    const password = ref('');
    const confirmPassword = ref('');
    const getYzmButton = ref(false);
    const buttonMessage = ref('获取验证码')
    const sendEmail = () => {
        if (email.value == null || email.value === '') {
            messagePrompt.errorMessage('请填写邮箱！');
            return
        }
        if (!confirmMail(email.value)) {
            messagePrompt.errorMessage('请检查邮箱格式');
            return
        }
        axios({
            method: "post",
            url: "http://8.130.90.140:8080/user",
            params: {
                "mail": email.value
            }
        }).then(res => {
            if (res.data.code === 10011) messagePrompt.errorMessage(res.data.message);
            else if (res.data.code === 10010) {
                messagePrompt.successMessage(res.data.message)
                getYzmButton.value = true
                const aa=setInterval(() => {
                    buttonMessage.value=counter.value+'s后重新获取';
                    counter.value--;
                    if (counter.value === 0) {
                        buttonMessage.value='获取验证码'
                        getYzmButton.value=false;
                        counter.value=180
                        clearInterval(aa);
                    }

                }, 1000)
            }
        }).catch(err => {
            console.log(err)
        })
    }
    const registerNow=()=>{
        if(account.value===''||password.value===''||confirmPassword.value===''||yzm.value===''||email.value===''){
            messagePrompt.errorMessage("请将信息填写完整")
            return
        }
        if(password.value!==confirmPassword.value){
            messagePrompt.errorMessage('两次输入密码不匹配！');
            return;
        }
        if(!confirmMail(email.value)){
            messagePrompt.errorMessage('请检查邮箱格式');
            return;
        }
        axios({
            method:"post",
            url:"http://8.130.90.140:8080/user/signup",
            data:{
                "account":account.value,
                "password":confirmPassword.value,
                "mail":email.value,
                "yzm":yzm.value
            }
        }).then(res=>{
            if(res.data.code===10010)messagePrompt.successMessage(res.data.message);
            else if(res.data.code===10011)messagePrompt.errorMessage(res.data.message)
        }).catch(err=>{
            console.log(err)
        })

    }

    return {registerNow,email, yzm, account, password, confirmPassword, buttonMessage, sendEmail, getYzmButton}
})