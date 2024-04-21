import {defineStore} from "pinia";
import {ElMessage} from "element-plus";

export const messageTip=defineStore('message',()=>{
    const errorMessage = (message) => {
        ElMessage.error(message)
    }
    const successMessage=(message)=>{
        ElMessage.success(message)
    }
    return {errorMessage,successMessage}
})