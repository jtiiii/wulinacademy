package com.funeral.wulinconsole;
import com.funeral.wulinapi.model.user.AdminUserModel;
import com.funeral.wulincore.service.user.IAdminUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WulinConsoleApplicationTests {

	@Resource
	private IAdminUserService adminUserService;

	/**
	 * 测试MyBatis 注解版开发模式
	 */
/*	@Test
	public void contextLoads() {
		Department departMentById = this.departmentMapper.getDepartMentById(1);
		System.out.println(departMentById);
	}*/

	/**
	 * Mybatis 配置文件开发模式
	 */
	@Test
	public void testMybatisConfig () {
		AdminUserModel adminUserById = this.adminUserService.getAdminUserById("1");
		System.out.println(adminUserById);
	}

}

