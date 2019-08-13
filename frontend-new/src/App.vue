<template>
  <div id="app">

  </div>
</template>

<script>

export default {
  name: 'app',
  components: {

  }
}
</script>

<style>
  @import url("~f-vue-components/src/vue-components.css");
  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif, alibaba;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
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
