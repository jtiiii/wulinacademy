package com.funeral.wulinconsole;
import com.funeral.wulinapi.model.user.AdminUserModel;
import com.funeral.wulincore.service.user.IAdminUserService;
import com.funeral.wulincore.utils.WuLinStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;

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
    /*    // 初始化一个默认用户
		AdminUserModel adminUserModel = new AdminUserModel();
		adminUserModel.setId(WuLinStringUtils.getRandomString(30));
		adminUserModel.setAccount("admin@wulin.com");
		adminUserModel.setUserName("admin");
		adminUserModel.setPassword(WuLinStringUtils.md5Base64("123456"));
		adminUserModel.setAddress("本地");
		adminUserModel.setAge(200);
		adminUserModel.setBirthday(new Date());
		adminUserModel.setCreateTime(new Date());
		adminUserModel.setIsDelete(0);
		adminUserModel.setForbiden(1);
		adminUserModel.setMale(2);
		this.adminUserService.insert(adminUserModel);*/
	}

}

