# 武林书画院主页项目-源码（前端 ）

该前端框架采用 `webpack` + `vue` 的作为开发框架，项目为 `spa` 单页面应用项目

## 目录说明

项目文件夹列表如下：

```shell
.
├── README.md
├── package-lock.json
├── package.json #npm 项目配置文件
├── src #源码
│   ├── main #主要内容
│   │   ├── i18n #国际化
│   │   ├── icons #图标
│   │   ├── images #图片
│   │   ├── index.html #主页
│   │   ├── index.js #打包执行
│   │   ├── pages #其他页面
│   │   ├── scripts #脚本文件
│   │   │   └── components #vue组件
│   │   └── styles #通用/统一样式
│   └── test
├── webpack.config.js #webpack 打包配置文件
└── webpack.test-config.js #webpack 测试打包配置
```



## 所需环境

安装开发环境：

+ `node`
+ `npm`

在该目录内执行语句：

```shell
$npm install #安装所需依赖
```



## 打包

在目录内执行语句

```shell
$npm run build
```

此时会在当前目录中生成 `/build` 目录。

