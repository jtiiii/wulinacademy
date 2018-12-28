const VueLoaderPlugin = require('vue-loader/lib/plugin');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const CleanWebpackPlugin = require('clean-webpack-plugin');
const path = require('path');

module.exports = {
    mode: 'development',
    devtool: 'source-map',
    entry: './src/test/test.js',
    output: {
        path: path.join( __dirname ,'build'),
        filename: '[name].bundle.js',
    },
    module: {
        rules: [
            { test: /\.vue$/, use: ['vue-loader']},
            { test: /\.css$/, use: ['style-loader','css-loader']},
            { test: /\.(jpg|png|jpeg|ttf|ttc)$/, use:[{
                    loader: 'url-loader',
                    options: {
                        limit: 81920,
                        outputPath: 'assets'
                    }}]
            },
            // { test: /\.pdf$/, use: [{
            //         loader:'file-loader',
            //         options: {
            //             name: '[name].[ext]',
            //             outputPath: 'pdfs/'
            //         }
            //     }]}
        ]
    },
    plugins:[
        new VueLoaderPlugin(),
        new HtmlWebpackPlugin({
            filename: 'index.html',
            template: 'src/test/test.html',
            favicon: 'src/main/icons/favicon.ico',
        }),
        new CleanWebpackPlugin('build')
    ],
    resolve: {
        alias: {
            'vue$': 'vue/dist/vue.esm.js' // 用 webpack 1 时需用 'vue/dist/vue.common.js'
        }
    }
};