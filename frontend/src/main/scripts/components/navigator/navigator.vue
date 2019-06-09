<script>
    import Option from './navigator';
    export default Option;
</script>
<template>
    <dropdown v-model="menu.show">
        <template #button>
            <img class="nav-button" :src="menu.icon"/>
        </template>
        <navigator class="nav" @a-click="aClick" :tabs="tabs"></navigator>
    </dropdown>
</template>
<style scoped>
    .nav{
        width: 9.375rem; /* 150px */
    }
    .nav-button{
        width: 1.25rem; /* 20px */
        height: 1.25rem; /* 20px */
    }
</style>
