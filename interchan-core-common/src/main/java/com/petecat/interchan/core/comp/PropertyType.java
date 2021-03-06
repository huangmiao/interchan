/**  
 * All rights Reserved, Designed By http://www.pete-cat.com/
 * @Title:  TransComp.java   
 * @Package com.petecat.interchan.core.comp   
 * @Description:TODO(用一句话描述该文件做什么)   
 * @author: 成都皮特猫科技     
 * @date:2017年7月28日 下午10:42:21   
 * @version V1.0 
 * @Copyright: 2017 www.pete-cat.com Inc. All rights reserved. 
 * 注意：本内容仅限于成都皮特猫信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */  
package com.petecat.interchan.core.comp;

import java.util.List;

/**   
 * @ClassName:  PropertyType
 * @Description: 属性交换值
 * @author: admin
 * @date:   2017年7月28日 下午10:42:21   
 */
public class PropertyType {

	private String propertyName;
	
	private Class<?> cls;
	
	private List<PropertyType> types;
	
	

	/**   
	 * @Title:  PropertyType   
	 * @Description:TODO(这里用一句话描述这个方法的作用)    
	 */
	public PropertyType() {
		super();
	}

	/**   
	 * @Title:  PropertyType   
	 * @Description:TODO(这里用一句话描述这个方法的作用)   
	 * @param propertyName
	 * @param cls 
	 */
	public PropertyType(String propertyName, Class<?> cls) {
		super();
		this.propertyName = propertyName;
		this.cls = cls;
	}

	/**  
	 * @Title:  getTypes
	 * @Description:获取types的值
	 * @return: List<PropertyType>
	 */
	public List<PropertyType> getTypes() {
		return types;
	}

	/**  
	 * @Title:  setTypes
	 * @Description:设置types的值
	 * @return: List<PropertyType>
	 */
	public void setTypes(List<PropertyType> types) {
		this.types = types;
	}

	/**  
	 * @Title:  getPropertyName
	 * @Description:获取propertyName的值
	 * @return: String
	 */
	public String getPropertyName() {
		return propertyName;
	}

	/**  
	 * @Title:  setPropertyName
	 * @Description:设置propertyName的值
	 * @return: String
	 */
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	/**  
	 * @Title:  getCls
	 * @Description:获取cls的值
	 * @return: Class<?>
	 */
	public Class<?> getCls() {
		return cls;
	}

	/**  
	 * @Title:  setCls
	 * @Description:设置cls的值
	 * @return: Class<?>
	 */
	public void setCls(Class<?> cls) {
		this.cls = cls;
	}
	
	
	
}
