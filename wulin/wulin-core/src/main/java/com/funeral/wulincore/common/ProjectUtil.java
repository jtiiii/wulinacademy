
/**     
 * @FileName: ProjectUtil.java   
 * @Package:com.ceacsz.pms.common   
 * @Description: 工程工具类  
 * @author: Jackson_J
 * @date:2019年1月4日
 */

package com.funeral.wulincore.common;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author: Jaskson_J
 * @date:2014年10月10日  
 * @Copyright:Copyright (c) 中国电子器材深圳有限公司 2014 ~ 2015 版权所有   
 */

public class ProjectUtil {

	private static Logger logger = LoggerFactory.getLogger (ProjectUtil.class);
	
	public static final String jsVerison=new SimpleDateFormat ("yyyyMMddHHmmss").format(new Date ());
	

	
	public static String getJsVersion(){
		return ProjectUtil.jsVerison;
	}
	
}
