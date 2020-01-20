
const ServerConfig = {
    env:{
        development:{
            host: "http://localhost",
            port: 9000,
            debug: true
        }
    },
    author:{
        email: 'funeral_objects@qq.com',
        github: 'https://github.com/jtiiii',
        gitee: 'https://gitee.com/FuneralObjects',
        asciiArt: '  ___                       _    ___  _     _        _      \n' +
            ' | __|  _ _ _  ___ _ _ __ _| |  / _ \\| |__ (_)___ __| |_ ___\n' +
            ' | _| || | \' \\/ -_) \'_/ _` | | | (_) | \'_ \\| / -_) _|  _(_-<\n' +
            ' |_| \\_,_|_||_\\___|_| \\__,_|_|  \\___/|_.__// \\___\\__|\\__/__/\n' +
            '                                         |__/               '
    },
    localStorage:{
        authToken:{
            __name__: 'x-auth-token',
            set(value){
                localStorage.setItem(this.__name__,value);
            },
            remove(){
                localStorage.removeItem(this.__name__);
            },
            get(){
                return localStorage.getItem(this.__name__);
            }
        }
    },
};
export const env = ServerConfig.env[process.env.NODE_ENV];
export default ServerConfig;