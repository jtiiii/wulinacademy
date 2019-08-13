<template>
    <div>
        <img style="width: 100%" :src="sorry">
    </div>
</template>
<script type="text/javascript">
    import SorryPic from '../images/sorry.png';

    export default {
        data: function(){
            return {
              sorry: SorryPic
            };
        }
    }
</script>
<style scoped>

</style>