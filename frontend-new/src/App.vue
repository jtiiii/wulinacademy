<template>
  <div id="app">
    <v-layout-header class="v-layout-header" />
    <router-view></router-view>
    <v-layout-footer />
  </div>
</template>

<script>
  import Header from './components/Header.vue';
  import Footer from './components/Footer.vue';
  import CheckService from "./scripts/api/CheckService";

  export default {
    name: 'app',
    components: {
      'v-layout-footer':Footer,
      'v-layout-header':Header,
    },
    mounted() {
      CheckService.available();
    }
  }
</script>

<style>
  @import url("~f-vue-components/src/vue-components.css");
  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    color: #555;
  }
  button{
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
  }
  .v-layout-header{
    z-index : 3;
  }
  #app > div{
    position: relative;
  }

</style>
