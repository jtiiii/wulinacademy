const VueLoaderPlugin = require('vue-loader/lib/plugin');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const CleanWebpackPlugin = require('clean-webpack-plugin');

module.exports = {
    mode: 'development',
    devtool: 'inline-source-map',
    entry: {
        test: './src/test/test.js'
    },
    output: {
        path: __dirname + '/build',
        filename: '[name].bundle.js'
    },
    module: {
        rules: [
            { test: /\.vue$/ , use: ['vue-loader']},
            { test: /\.css$/ , use: ['style-loader','css-loader']},
            { test: /\.(jpg|png)$/, use:[{
                loader: 'url-loader',
                options: {
                    limit: 81920,
                    outputPath: 'img'
                }}]
            }
        ]
    },
    plugins:[
        new VueLoaderPlugin(),
        new HtmlWebpackPlugin({
            filename: 'test.html',
            template: 'src/test/test.html',
            chunks: ['test']
        }),
        new CleanWebpackPlugin('build')
    ],
    resolve: {
        alias: {
            'vue$': 'vue/dist/vue.esm.js' // 用 webpack 1 时需用 'vue/dist/vue.common.js'
        }
    }
};