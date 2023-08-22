<script setup>
import Home from "@/components/home.vue";
import Login from "@/components/login.vue";
import {pageStatus} from "@/stores/page";
import Navbar from "@/components/navbar.vue";
const pageSts=pageStatus()


</script>

<template>

<login v-if="pageSts.isLogin" />
<navbar id="bar" v-if="pageSts.isBarVisible"/>
  <home id="home" v-if="pageSts.isHome"/>



</template>

<style scoped>
#bar{
  width: 100%  ;
  top: 0;
  left: 0;
  position: fixed;
  z-index: 999;
}
#home{
  width: 80vw;
  top: 100px;
  bottom: 100px;
}
</style>
