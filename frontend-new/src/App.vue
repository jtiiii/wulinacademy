<template>
  <div id="app">
    <v-layout-header class="v-layout-header"></v-layout-header>
    <v-layout-container></v-layout-container>
    <v-layout-footer></v-layout-footer>
  </div>
</template>

<script>
  import Header from './components/Header.vue';
  import Footer from './components/Footer.vue';
  import Container from './components/Container.vue';
  export default {
    name: 'app',
    components: {
      'v-layout-footer':Footer,
      'v-layout-header':Header,
      'v-layout-container': Container,
    }
  }
</script>

<style>
  @import url("~f-vue-components/src/vue-components.css");
  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif, alibaba;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    color: #555;
  }
  .v-layout-header{
    z-index : 3;
  }
  #app > div{
    position: relative;
  }
  @font-face {
    font-family: 'alibaba';
    font-display: swap;
    src: url('//at.alicdn.com/t/webfont_vqswb7vrm5l.eot'); /* IE9*/
    src: url('//at.alicdn.com/t/webfont_vqswb7vrm5l.eot?#iefix') format('embedded-opentype'), /* IE6-IE8 */
    url('//at.alicdn.com/t/webfont_vqswb7vrm5l.woff2') format('woff2'),
    url('//at.alicdn.com/t/webfont_vqswb7vrm5l.woff') format('woff'), /* chrome、firefox */
    url('//at.alicdn.com/t/webfont_vqswb7vrm5l.ttf') format('truetype'), /* chrome、firefox、opera、Safari, Android, iOS 4.2+*/
    url('//at.alicdn.com/t/webfont_vqswb7vrm5l.svg#Alibaba-PuHuiTi-Regular') format('svg'); /* iOS 4.1- */
  }
</style>
