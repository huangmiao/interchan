package com.petecat.interchan.logger.common.model;

import java.util.Date;

//import org.springframework.data.annotation.Id;
//import org.springframework.data.elasticsearch.annotations.Document;

import com.alibaba.fastjson.annotation.JSONField;
import com.mhuang.elk.common.source.crud.annoation.EsTable;
import com.petecat.interchan.logger.common.Global;

import lombok.Data;

/**
 * 
 * @ClassName:  OperatorLogger   
 * @Description:操作日志  
 * @author: mhuang
 * @date:   2017年8月3日 上午10:58:13
 */
@Data
@EsTable(index = "user_operator_logger",type = "user_operator_logger")
public class EsOperatorLogger {

//	@Id
	@JSONField(serialize = false)
	private String id;
	
	private String url;//操作url
	
	private String type;//操作类型
	
	private String clazz;//请求得类名
	
	private String method; //请求得方法名
	
	private String userId;//操作用户id

	private String ip;//操作ip
	
	private String headerData;//操作头

	private String queryData;//查询对象
	
	private String sendData;//发送得数据
	
	private String restData; //返回得参数
	
	private Date startDate;//开始操作时间
	
	private Date endDate;//结束操作时间

	private String remark;//备注
	
	private String eMsg;//异常信息
	
	private String eDetailMsg;//异常详细信息
	
	private String systemType;//系统类型
	
	private Integer status = 0;//状态(0 代表执行中，1代表成功，2代表异常）
	
	public EsOperatorLogger(){
		this.setSystemType(Global.getSystemType());
	}
}
