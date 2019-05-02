const config = require('./src/main/config/webpack');

config.mode = 'development';
config.devtool = 'source-map';
module.exports = config;