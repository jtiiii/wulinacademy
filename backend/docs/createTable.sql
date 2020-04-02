CREATE DATABASE IF NOT EXISTS wulin_prod COLLATE utf8_bin;
use wulin_prod;

DROP TABLE IF EXISTS t_folder_images;
DROP TABLE IF EXISTS t_folder_tree;
DROP TABLE IF EXISTS t_folder;
DROP TABLE IF EXISTS t_news;

CREATE TABLE IF NOT EXISTS t_news(
                                     news_id INT(11) PRIMARY KEY AUTO_INCREMENT
                                         COMMENT '主键ID',
                                     title VARCHAR(255) NOT NULL
                                         COMMENT '标题',
                                     event_date DATETIME NOT NULL
                                         COMMENT '事件日期',
                                     preview VARCHAR(255)
                                         COMMENT '预览信息',
                                     thumbnail VARCHAR(255)
                                         COMMENT '缩略图路径',
                                     uuid VARCHAR(255)
                                         COMMENT 'uuid文件',
                                     create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
                                         COMMENT '创建日期',
                                     update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
                                         COMMENT '更新日期',
                                     status INT(1) NOT NULL DEFAULT 1
                                         COMMENT '状态值，VISIBLE(1) - 可见 | INVISIBLE(0) - 不可见 | DELETED(-1) - 删除'
)ENGINE=INNODB DEFAULT CHARSET=UTF8MB4
    COMMENT '新闻表';

CREATE TABLE IF NOT EXISTS t_folder(
                                       folder_id INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT
                                           COMMENT '文件夹ID',
                                       folder_name VARCHAR(40) NOT NULL
                                           COMMENT '文件夹名称',
                                       create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
                                           COMMENT '创建日期',
                                       update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
                                           COMMENT '更新时间',
                                       parent_id INT(11)
                                           COMMENT '父级ID',
                                       UNIQUE KEY ( folder_name)
) ENGINE=INNODB DEFAULT CHARSET=UTF8MB4
    COMMENT '文件夹表';

CREATE TABLE IF NOT EXISTS t_folder_tree(
                                            folder_id INT(11) NOT NULL PRIMARY KEY
                                                COMMENT '文件夹ID',
                                            path VARCHAR(255) NOT NULL
                                                COMMENT '文件夹路径',
                                            UNIQUE KEY (path),
                                            FOREIGN KEY (folder_id) REFERENCES t_folder(folder_id)
)ENGINE=INNODB DEFAULT CHARSET=UTF8MB4
    COMMENT '文件夹树形路径';

CREATE TABLE IF NOT EXISTS t_folder_images(
                                              folder_id INT(11) NOT NULL
                                                  COMMENT '文件夹ID',
                                              sha1_md5 VARCHAR(74) NOT NULL
                                                  COMMENT '图像ID',
                                              image_name VARCHAR(40) NOT NULL
                                                  COMMENT '图像名称',
                                              suffix VARCHAR(10) NOT NULL
                                                  COMMENT '后缀名',
                                              UNIQUE KEY (folder_id, image_name),
                                              FOREIGN KEY (folder_id) REFERENCES t_folder(folder_id)
)ENGINE=INNODB DEFAULT CHARSET=UTF8MB4
    COMMENT '文件夹图像关联表';