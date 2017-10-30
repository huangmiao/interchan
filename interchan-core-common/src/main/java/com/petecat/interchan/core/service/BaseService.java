package com.petecat.interchan.core.service;

import java.io.Serializable;
import java.util.List;

import com.petecat.interchan.protocol.data.Page;

/**
 * 
 * @ClassName:  BaseService   
 * @Description:通用Service层   
 * @author: mhuang
 * @date:   2017年7月11日 下午6:51:54   
 * @param <T>
 * @param <Id>
 */
public interface BaseService <T extends Serializable,Id>{

	/**
	 * 
	 * @Title: get   
	 * @Description: 获取单个实例   
	 * @param t
	 * 		查询的实例对象
	 * @return
	 * @return T
	 */
	T get(T t);
	
	/**
	 * 
	 * @Title: getById   
	 * @Description: 根据Id获取单个实例   
	 * @param id
	 * 		查询的主键id
	 * @return
	 * @return T
	 */
	T getById(Id id);
	
	/**
	 * 
	 * @Title: save   
	 * @Description: 新增单个实例
	 * @param t
	 * 		实例对象
	 * @return
	 * @return int
	 * 		新增个数
	 */
	int save(T t);
	
	/**
	 * 
	 * @Title: update   
	 * @Description: 修改单个实例  
	 * @param t
	 * 		实例对象
	 * @return
	 * @return int
	 * 		修改个数
	 */
	int update(T t);
	
	/**
	 * @Title: remove   
	 * @Description: 删除单个实例   
	 * @param t
	 * 		实例对象
	 * @return
	 * @return int
	 * 		删除的个数
	 */
	int remove(T t);
	
	/**
	 * @Title: remove   
	 * @Description: 删除单个实例   
	 * @param id
	 * 		删除的Id
	 * @return
	 * @return int
	 * 		删除的个数
	 */
	int remove(Id id);
	
	/**
	 * 
	 * @Title: count   
	 * @Description: 查询条数
	 * @param t
	 * 		查询的实例
	 * @return
	 * @return int
	 * 		查询的个数
	 */
	int count(T t);
	
	public List<T> page(Page<T> page);
}
