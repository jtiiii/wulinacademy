<template>
    <div class="root filling">

        <div v-if="isLogin" >
            登陆成功！
            <button type="button" class="loginBtn btn" @click="logout"> 登出 </button>
        </div>
        <div v-else="isLogin">
            <label>{{ $t('login.username') }}</label>
            <input class="loginInput" type="text" v-model="username">
            <br/>
            <label>{{ $t('login.password') }}</label>
            <input class="loginInput" type="password" v-model="password">
            <br/>
            <button class="loginBtn btn btn-ok" type="button" @click="login">
                {{ $t('login.login') }}
            </button>
        </div>
    </div>
</template>
<script type="text/javascript">
    import i18n from '../i18n';
    import {mapState} from 'vuex';
    import SecurityService from '../api/SecurityService';

    export default {
        components:{

        },
        i18n,
        data(){
            return {
                onLogin: false,
                username: "",
                password: "",
                // isLogin: SecurityService.isLogin
            };
        },
        computed: mapState({
            isLogin: state => state.isLogin
        }),
        methods:{
            login(){
                let _vue = this;
                SecurityService.login(this.username,this.password).then( loginResult => {
                    if(loginResult){
                        _vue.loginSucc();
                    }
                });
            },
            logout(){
                SecurityService.logout();
            },
            loginSucc(){
                this.$emit('loginSuccessful');
            }
        }
    }
</script>
<style scoped>
    @import url('../../styles/box.css');
    .root{
        padding: 10px;
    }
    input {
        outline: none;
        margin: 5px;
    }
    label{
        display: inline-block;
        width: 60px;
    }
    .loginInput {
        border: 1px solid #a9a9a9;
        border-radius: 4px;
        height: 20px;
        width: 130px;
        font-size: 16px;
    }
    .loginBtn{
        position: relative;
        top: 20px;
        width: calc(100% - 20px);
        height: 40px;
    }
</style>