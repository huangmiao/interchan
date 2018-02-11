package com.petecat.interchan.core.mapper;

import java.io.Serializable;
import java.util.List;

import com.petecat.interchan.protocol.InsertInto;
import com.petecat.interchan.protocol.data.Page;

/**
 * @ClassName:  BaseMapper   
 * @Description: 公共Mapper层  
 * @author: mhuang
 * @date:   2017年7月11日 下午5:43:02   
 * @param <T>
 */
public interface BaseMapper<T extends Serializable,Id>{

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
	 * @Title: delete   
	 * @Description: 删除
	 * @param t
	 * @return
	 * @return int
	 */
	int delete(T t);
	
	/**
	 * 
	 * @Title: delete   
	 * @Description: 删除
	 * @param id
	 * @return
	 * @return int
	 */
	int delete(Id id);
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
	
	/**
	 * 
	 * @Title: query   
	 * @Description: 查询 
	 * @param t
	 * @return
	 * @return List<T>
	 */
	List<T> query(T t);
	
	/**
	 * 
	 * @Title: queryAll   
	 * @Description: 查询
	 * @return
	 * @return List<T>
	 */
	List<T> queryAll();
	
	/**
	 * 
	 * @Title: page   
	 * @Description: 分页
	 * @param page
	 * @return
	 * @return List<T>
	 */
	List<T> page(Page<T> page);
	
	int pageCount(Page<T> page);
	/**
	 * 
	 * @Title: insertInto   
	 * @Description: 历史
	 * @param insertInto
	 * @return
	 * @return int
	 */
	int insertInto(InsertInto<Id> insertInto);
}
