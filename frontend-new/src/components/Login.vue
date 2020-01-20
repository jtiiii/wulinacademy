<template>
    <div class="root">
        <div v-if="isLogin" class="form" >
            登陆成功！
            <v-button :emotion="'warning'" @click="logout"> 登出 </v-button>
        </div>
        <div v-else class="form">
            <v-input-text :label="$t('login.username')" v-model="username"/>
            <v-input-text :label="$t('login.password')" :type="'password'" v-model="password"/>
            <v-button :emotion="'info'" @click="login">{{ $t('login.login') }}</v-button>
        </div>
    </div>
</template>
<script type="text/javascript">
    import SecurityService from '../scripts/api/SecurityService';
    import FComponents from 'f-vue-components';

    export default {
        components:{
            'v-input-text': FComponents.Input.Text,
            'v-button': FComponents.Button
        },
        data(){
            return {
                onLogin: false,
                username: "",
                password: "",
            };
        },
        computed: {
            isLogin(){
                return this.$store.state.isLogin;
            }
        },
        methods:{
            login(){
                SecurityService.login(this.username,this.password).then( () => {
                    this.setLoginStatus(true);
                    this.$emit('loginSuccessful');
                });
            },
            logout(){
                SecurityService.logout()
                    .then(() => {
                        this.setLoginStatus( false );
                    });
            },
            setLoginStatus( isLogin ){
                if(this.isLogin !== isLogin){
                    this.$store.commit(isLogin? 'login' : 'logout');
                }
            }
        },
        mounted() {
            this.setLoginStatus( SecurityService.hasToken() );
        }
    }
</script>
<style>
   @import url('../assets/styles/components/login.css');
</style>