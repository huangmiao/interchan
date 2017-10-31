package com.petecat.interchan.wechat.common.execute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 反射调用抽象
 * @author mHuang
 *
 */
public abstract class InvokeExecute {

	private final static Map<Class<?>, Class<?>> CONST_TYPES = new LinkedHashMap<Class<?>, Class<?>>(){
		private static final long serialVersionUID = 1L;
	{
		put(HashMap.class, Map.class);
		put(LinkedHashMap.class,Map.class);
		put(TreeMap.class,Map.class);
		put(Hashtable.class,Map.class);
		put(ArrayList.class,List.class);
		put(LinkedList.class,List.class);
	}};
	
	static Class<?> checkType(Object obj){
		if(CONST_TYPES.containsKey(obj.getClass())){
			return CONST_TYPES.get(obj.getClass());
		}else{
			return obj.getClass();
		}
	}
}
