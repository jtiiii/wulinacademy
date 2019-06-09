const config = require('./src/main/config/webpack');

config.mode = 'development';
config.devtool = 'source-map';
config.devServer={
    port: 8081,
    host: '192.168.1.101'
};
module.exports = config;