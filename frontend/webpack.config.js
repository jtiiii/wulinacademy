const config = require('./src/main/config/webpack');
const path = require('path');
config.output.path  = path.join( __dirname ,'build');
module.exports = config;