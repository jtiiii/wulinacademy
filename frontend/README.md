# 武林书画院主页项目-源码（前端 ）

该前端框架采用 `webpack` + `vue` 的作为开发框架，项目为 `spa` 单页面应用项目

## 目录说明

项目文件夹列表如下：

```shell
.
├── README.md
├── package-lock.json
├── package.json #npm项目配置文件
├── src
│   ├── main
│   │   ├── config #webpack的通用配置
│   │   ├── entries #构建入口
│   │   ├── fonts #外部字体
│   │   ├── i18n #国际化
│   │   ├── icons #图标
│   │   ├── images #图片
│   │   ├── pages #vue页面
│   │   ├── pdfs #pdf文件
│   │   ├── public #一些静态资源
│   │   ├── scripts #脚本文件
│   │   └── styles #css样式文件
│   └── test #测试文件夹
├── webpack.config.dev.js #开发模式的配置
└── webpack.config.js #生产环境的配置
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

## 开发构建
```shell
$npm run dev
```

此时会在开发状态下构建项目，并且启动简易web容器。并自动打开浏览器。