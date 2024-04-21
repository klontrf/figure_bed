import {defineStore} from "pinia";
import {ref} from "vue";

export const pageStatus=defineStore('page',()=>{
    const isLogin=ref(true);
    const isHome=ref(false);
    const isBarVisible=ref(false);

    return {isHome,isLogin,isBarVisible}
})