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
	create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
		COMMENT '创建日期',
	update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
		COMMENT '更新日期',
	status INT(1) NOT NULL DEFAULT 1
		COMMENT '状态值，VISIBLE(1) - 可见 | INVISIBLE(0) - 不可见 | DELETED(-1) - 删除'
)ENGINE=INNODB DEFAULT CHARSET=UTF8
	COMMENT '新闻表';

DROP VIEW IF EXISTS v_news;
CREATE VIEW v_news AS SELECT * FROM t_news WHERE status <> -1;

DROP TABLE IF EXISTS t_news_content;
CREATE TABLE IF NOT EXISTS t_news_content(
	news_id INT(11) NOT NULL PRIMARY KEY
		COMMENT 't_news的主键',
	header VARCHAR(255)
	    COMMENT '头部信息',
	simple VARCHAR(255)
	    COMMENT '简短部分，预览用',
	content BLOB
		COMMENT '正文内容，因为长度较长设置为BLOB',
	timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
	    COMMENT '更新时间戳'
) ENGINE=INNODB DEFAULT CHARSET=UTF8
    COMMENT '新闻正文表';

DROP TABLE IF EXISTS t_image;
CREATE TABLE IF NOT EXISTS t_image(
    image_id INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT
        COMMENT '图片唯一主键',
    md5 VARCHAR(255) NOT NULL
        COMMENT 'MD5值',
    site VARCHAR(255) NOT NULL
        COMMENT '图片位置',
    suffix VARCHAR(10) NOT NULL
        COMMENT '后缀',
    status INT(1) NOT NULL DEFAULT 1
        COMMENT '状态值，VISIBLE(1) - 可见 | INVISIBLE(0) - 不可见 | DELETED(-1) - 删除',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
        COMMENT '创建时间',
    UNIQUE KEY (md5, status)
) ENGINE=INNODB DEFAULT CHARSET=UTF8
    COMMENT '图片表';
DROP VIEW IF EXISTS v_image;
CREATE VIEW v_image AS SELECT * FROM t_image WHERE status <> -1;

DROP TABLE IF EXISTS t_folder;
CREATE TABLE IF NOT EXISTS t_folder(
    folder_id INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT
        COMMENT '文件夹ID',
    folder_name VARCHAR(40) NOT NULL
        COMMENT '文件夹名称',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
        COMMENT '创建日期',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
        COMMENT '更新时间',
    user_id VARCHAR(40)
        COMMENT '所属用户ID',
    parent_id INT(11)
        COMMENT '父级ID',
    status INT(1) NOT NULL DEFAULT 1
        COMMENT '状态值，VISIBLE(1) - 可见 | INVISIBLE(0) - 不可见 | DELETED(-1) - 删除',
    UNIQUE KEY ( folder_name, status)
) ENGINE=INNODB DEFAULT CHARSET=UTF8
    COMMENT '文件夹表';
DROP VIEW IF EXISTS v_folder;
CREATE VIEW v_folder AS SELECT * FROM t_folder WHERE status <> -1;

DROP TABLE IF EXISTS t_folder_tree;
CREATE TABLE IF NOT EXISTS t_folder_tree(
	folder_id INT(11) NOT NULL PRIMARY KEY
	    COMMENT '文件夹ID',
    path VARCHAR(255) NOT NULL
        COMMENT '文件夹路径',
    UNIQUE KEY (path)
)ENGINE=INNODB DEFAULT CHARSET=UTF8
    COMMENT '文件夹树形路径';


DROP TABLE IF EXISTS t_folder_images;
CREATE TABLE IF NOT EXISTS t_folder_images(
    folder_id INT(11) NOT NULL
        COMMENT '文件夹ID',
    image_id INT(11) NOT NULL
        COMMENT '图像ID',
    image_name VARCHAR(40) NOT NULL
        COMMENT '图像名称',
    UNIQUE KEY (folder_id, image_name)
)ENGINE=INNODB DEFAULT CHARSET=UTF8
    COMMENT '文件夹图像关联表';

DROP VIEW IF EXISTS v_image_relate;
CREATE VIEW v_image_relate AS
SELECT image.*, relate.image_name ,folder.folder_id, folder.folder_name
FROM(SELECT * FROM t_folder folder WHERE status <> -1) folder
INNER JOIN  t_folder_images relate on relate.folder_id = folder.folder_id AND folder.status <> -1
INNER JOIN t_image image ON relate.image_id = image.image_id AND image.status <> -1;