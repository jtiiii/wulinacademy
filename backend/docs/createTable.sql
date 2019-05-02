DROP TABLE IF EXISTS t_news;
CREATE TABLE IF NOT EXISTS t_news(
	news_id INT(11) PRIMARY KEY auto_increment
		COMMENT '主键ID',
	title VARCHAR(255) NOT NULL
		COMMENT '标题',
	event_date DATETIME NOT NULL
		COMMENT '事件日期',
	preview VARCHAR(255)
	    COMMENT '预览信息',
	thumbnail VARCHAR(255)
	    COMMENT '缩略图路径',
	create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
		COMMENT '创建日期',
	update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
		COMMENT '更新日期',
	status int(1) NOT NULL DEFAULT 1
		COMMENT '状态值，VISIBLE(1) - 可见 | INVISIBLE(0) - 不可见 | DELETED(-1) - 删除'
)ENGINE=INNODB DEFAULT charset=utf8
	COMMENT '新闻表';

DROP TABLE IF EXISTS t_news_content;
CREATE TABLE IF NOT EXISTS t_news_content(
	news_id INT(11) NOT NULL PRIMARY KEY
		COMMENT 't_news的主键',
	header varchar(255)
	    COMMENT '头部信息',
	simple varchar(255)
	    COMMENT '简短部分，预览用',
	content BLOB
		COMMENT '正文内容，因为长度较长设置为BLOB',
	timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
	    COMMENT '更新时间戳'
) ENGINE=INNODB DEFAULT charset=utf8;