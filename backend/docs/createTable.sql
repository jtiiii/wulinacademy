DROP TABLE IF EXISTS t_news;
CREATE TABLE IF NOT EXISTS t_news(
	news_id INT(11) PRIMARY KEY auto_increment
		COMMENT '主键ID',
	title VARCHAR(255) NOT NULL
		COMMENT '标题',
	event_date DATETIME NOT NULL
		COMMENT '事件日期',
	create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
		COMMENT '创建日期',
	update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
		COMMENT '更新日期',
	status ENUM('VISIBLE','INVISIBLE','DELETED') NOT NULL DEFAULT 'VISIBLE'
		COMMENT '状态值，VISIBLE - 可见 | INVISIBLE - 不可见 | DELETED - 删除'
)ENGINE=INNODB DEFAULT charset=utf8
	COMMENT '新闻表';

DROP TABLE IF EXISTS t_news_content;
CREATE TABLE IF NOT EXISTS t_news_content(
	news_id INT(11) NOT NULL
		comment 't_news的主键',
	content BLOB
		comment '正文内容，因为长度较长设置为BLOB'
) ENGINE=INNODB DEFAULT charset=utf8;