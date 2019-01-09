package com.funeral.wulinconsole;

import com.funeral.wulinapi.model.Department;
import com.funeral.wulinapi.model.Employee;
import com.funeral.wulincore.dao.departMent.DepartmentMapper;
import com.funeral.wulincore.dao.employee.EmployeeMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WulinConsoleApplicationTests {
	@Autowired
	private DepartmentMapper departmentMapper;

	@Autowired
	private EmployeeMapper employeeMapper;

	/**
	 * 测试MyBatis 注解版开发模式
	 */
	@Test
	public void contextLoads() {
		Department departMentById = this.departmentMapper.getDepartMentById(1);
		System.out.println(departMentById);
	}

	/**
	 * Mybatis 配置文件开发模式
	 */
	@Test
	public void testMybatisConfig () {
		Employee empById = this.employeeMapper.getEmpById(1);
		System.out.println(empById);
	}

}

