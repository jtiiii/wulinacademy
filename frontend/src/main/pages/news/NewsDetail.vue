<template>
    <div class="panel">
        <div class="panel-heading">
            {{ title }}
            <span> {{ time }} </span>
        </div>
        <div class="panel-body">
            <slot></slot>
        </div>
    </div>
</template>
<script type="text/javascript">
    export default {
        props:{
            time: {
                type: String,
                required: true,
            },
            title: {
                type: String,
                required: true,
            }
        },
        data: function(){
            return {

            };
        }
    }
</script>
<style type="text/css" scoped>

</style>