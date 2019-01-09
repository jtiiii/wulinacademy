package com.funeral.wulinconsole;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.funeral.wulincore.dao")
@ComponentScan(basePackages={"com.funeral"})
public class WulinConsoleApplication {

	public static void main(String[] args) {
		SpringApplication.run(WulinConsoleApplication.class, args);
	}

}

