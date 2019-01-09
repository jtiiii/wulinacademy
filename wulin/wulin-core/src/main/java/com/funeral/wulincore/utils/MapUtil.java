package com.funeral.wulincore.utils;

/**
 * Map相关处理类
 */
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MapUtil {

	private MapUtil() {
		throw new Error ("不要实例化我");
	}

	/**
	 * map转换，将（request.getParameterMap()）得到的特殊Map转为普通Map
	 * 
	 * @param
	 * @return Map
	 */
	public static Map<String, String> mapTransfer(Map properties) {
		Map<String, String> returnMap = new HashMap<String, String> ();
		if (null != properties) {
			Iterator entries = properties.entrySet().iterator();
			Map.Entry entry;
			String name = "";
			String value = "";
			while (entries.hasNext()) {
				entry = (Map.Entry) entries.next();
				name = (String) entry.getKey();
				Object valueObj = entry.getValue();
				if (null == valueObj) {
					value = "";
				} else if (valueObj instanceof String[]) {
					String[] values = (String[]) valueObj;
					for (int i = 0; i < values.length; i++) {
						value = values[i] + ",";
					}
					value = value.substring(0, value.length() - 1);
				} else {
					value = valueObj.toString();
				}
				returnMap.put(name, value);
			}
		}
		return returnMap;
	}

	/**
	 * 将一个 JavaBean 对象转化为一个 Map
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map bean2Map(Object bean) throws IntrospectionException, IllegalAccessException, InvocationTargetException {
		Class type = bean.getClass();
		Map returnMap = new HashMap ();
		BeanInfo beanInfo = Introspector.getBeanInfo(type);
		PropertyDescriptor[] propertyDescriptors = beanInfo
				.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			if (!propertyName.equals("class")) {
				Method readMethod = descriptor.getReadMethod();
				Object result = readMethod.invoke(bean, new Object[0]);
				if (result != null) {
					returnMap.put(propertyName, result);
				} else {
					returnMap.put(propertyName, "");
				}
			}
		}
		return returnMap;
	}

	/**
	 * 将一个 Map 对象转化为一个 JavaBean
	 * 
	 */
	@SuppressWarnings("rawtypes")
	public static Object map2Bean(Class beanClass, Map map) throws Exception {
		Method[] methods = beanClass.getMethods();
		Object bean = beanClass.newInstance();
		for (Method method : methods) {
			if (method.getName().startsWith("set")) {
				String field = method.getName();
				field = field.substring(field.indexOf("set") + 3);
				String getterName = "get" + field;
				field = field.toLowerCase().charAt(0) + field.substring(1);
				if (map.containsKey(field)) {
					String value = (String) map.get(field);
					if (value != null && !"".equals(value)) {
						Method getter = beanClass.getMethod(getterName);
						String methodType = getter.getReturnType().getName();
						methodType = methodType.substring(methodType
								.lastIndexOf(".") + 1);
						Object[] cache = new Object[1];
						try {
							if (methodType.equalsIgnoreCase("long")) {
								cache[0] = new Long (value);
							} else if (methodType.equalsIgnoreCase("int")
									|| methodType.equalsIgnoreCase("integer")) {
								cache[0] = new Integer (value);
							} else if (methodType.equalsIgnoreCase("short")) {
								cache[0] = new Short (value);
							} else if (methodType.equalsIgnoreCase("float")) {
								cache[0] = new Float (value);
							} else if (methodType.equalsIgnoreCase("double")) {
								cache[0] = new Double (value);
							} else if (methodType.equalsIgnoreCase("boolean")) {
								cache[0] = new Boolean (value);
							} else if (methodType
									.equalsIgnoreCase("string")) {
								cache[0] = value;
							} else if (methodType
									.equalsIgnoreCase("inputStream")) {

							} else if (methodType.equalsIgnoreCase("char")) {
								cache[0] = (Character.valueOf(value.charAt(0)));
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						method.invoke(bean, cache);
					}
				}
			}

		}
		return bean;
	}

}