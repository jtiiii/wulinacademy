<script>
    import Option from './setting';
    export default Option;
</script>
<template>
    <dropdown v-model="menu.show" :position="'under-right'">
        <template #button>
            <img class="setting-button" :src="dropdown.icon"/>
        </template>
        <div class="settings">
            <div class="setting-group" v-for="group in menu.groups">
                <label><hr/><span>{{ $t(group.i18Key) }}</span></label>
                <navigator class="nav language-setting" @a-click="click" :tabs="group.tabs"></navigator>
            </div>
        </div>
    </dropdown>
</template>
<style scoped>
    .setting-group{
        position: relative;
    }
    .settings{
        width: 9.375rem; /* 150px */
        display: flex;
        flex-flow: column nowrap;
    }
    .setting-group > label > span{
        padding: 0 0.375rem; /* 6px */
        background: #ffffff;
        position: relative;
    }
    .setting-group > label > hr{
        width: 100%;
        position: absolute;
    }
    .nav{
        flex:1;
        /*width: 9.375rem; !* 150px *!*/
    }
    .setting-button{
        width: 1.25rem; /* 20px */
        height: 1.25rem; /* 20px */
    }
</style>