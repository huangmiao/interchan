package com.petecat.interchan.logger.common.dao;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.mhuang.elk.common.source.crud.EsFactoryImpl;
import com.mhuang.elk.common.source.crud.IEsFactory;
import com.petecat.interchan.logger.common.model.EsOperatorLogger;

import lombok.Data;

/**
 * 
 * @ClassName:  EsLoggerDao   
 * @Description:Spring-data 自带实现
 * @author: mhuang
 * @date:   2017年8月3日 下午2:02:36
 */
@Repository
public class EsLoggerDao extends EsFactoryImpl<EsOperatorLogger, String> implements InitializingBean, IEsFactory<EsOperatorLogger, String>{

	 private   int  DEFAULT_QUEUE_SIZE = 512;
	 
	 private  final Logger logger = LoggerFactory.getLogger(this.getClass());
	 
	 @Autowired
	 private Environment environment;
	 
	 private boolean neverBlock = true;
	 
	 private BlockingQueue<EsAsysLogModel> blockingQueue =  new ArrayBlockingQueue<>(DEFAULT_QUEUE_SIZE);
	
	/** 
	 * 异步写入
	 * @Title: insertAsync
	 * @param eslogger
	 * @param index
	 * @param type
	 * @return String     
	 */
	public void insertAsync(EsOperatorLogger eslogger, String index, String type) {
		if(eslogger!=null) {
			EsAsysLogModel logModel =  new  EsAsysLogModel();
			logModel.setEsLogger(eslogger);
			logModel.setIndex(index);
			logModel.setOpType(EsAsysLogOpType.INSERT);
			logModel.setType(type);
			this.putLog(logModel);
		}
	}

	/** 
	 * 异步更新
	 * @Title: updateAsync
	 * @param esLogger
	 * @param application
	 * @param application2
	 * @param id
	 * @return void     
	 */
	public void updateAsync(EsOperatorLogger esLogger, String index, String type) {
		if(esLogger!=null) {
			EsAsysLogModel logModel =  new  EsAsysLogModel();
			logModel.setEsLogger(esLogger);
			logModel.setIndex(index);
			if(StringUtils.isNotBlank(esLogger.getId())) {
				logModel.setOpType(EsAsysLogOpType.MOD);
			}else{
				logModel.setOpType(EsAsysLogOpType.INSERT);
			}
			logModel.setType(type);
			this.putLog(logModel);
		}
	}

	public void putLog(EsAsysLogModel model) {
		if (neverBlock) {
             blockingQueue.offer(model);
        } else {
            try {
                blockingQueue.put(model);
            } catch (Exception e) {
            	
            }
        }
	}
	
	class Worker extends Thread {
		
		    private int workNum;
            
			/** 
			 *   
			 * @Title:  Worker   
			 * @param workNum 
			 */
			public Worker(int workNum) {
				super();
				this.workNum = workNum;
			}

			public void run() {
	        	EsLoggerDao parent = EsLoggerDao.this;
	        	Thread.currentThread().setName("es异步写入线程"+workNum);
	            while (true) {
	            	EsAsysLogModel logModel =  null;
	                try {
	                    logModel = blockingQueue.take();
	                	if(logModel.getOpType()==EsAsysLogOpType.INSERT) {
	                		parent.insert(logModel.getEsLogger(), logModel.getIndex(), logModel.getType());
	                	}else if(logModel.getOpType()==EsAsysLogOpType.MOD) {
	                		parent.update(logModel.getEsLogger(), logModel.getIndex(), logModel.getType(),
	                				logModel.getEsLogger().getId());
	                	}
	                } catch (Exception ie) {
	                	if(logModel!=null) {
	                	  logger.error("写入es日志失败,日志信息：{}",logModel.toString(),ie);
	                	}else {
	                		logger.error("写入es日志失败",ie);
	                	}
	                }
	            }
	    }
	}
	
  @Data
  class EsAsysLogModel{
	  private EsOperatorLogger esLogger;
	  private String index;
	  private String type;
	  private EsAsysLogOpType opType;
	/**   
	 * <p>Title: toString</p>   
	 * <p>Description: </p>   
	 * @return   
	 * @see java.lang.Object#toString()   
	 */  
	@Override
	public String toString() {
		try {
			return JSON.toJSONString(this);
		} catch (Exception e) {
		}
		return null;
	}
	  
	  
  }
	  
  enum EsAsysLogOpType{
	  MOD,INSERT;
  }
  

/**   
 * <p>Title: afterPropertiesSet</p>   
 * <p>Description: </p>   
 * @throws Exception   
 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()   
 */  
@Override
public void afterPropertiesSet() throws Exception {
	 String neverBlock = environment.getProperty("elk.logger.neverBlock");
	 try {
		 if(StringUtils.isNotBlank(neverBlock)) {
		    this.neverBlock=Boolean.valueOf(neverBlock);
		 }
	 }catch(Exception e) {
		 
	 }
	 String cacheSize = environment.getProperty("elk.logger.cacheSize");
	 try {
		 if(StringUtils.isNotBlank(cacheSize)) {
		    this.DEFAULT_QUEUE_SIZE=Integer.parseInt(cacheSize);
		 }
	 }catch(Exception e) {
		 
	 }
	 String threadSizeStr = environment.getProperty("elk.logger.threadSize");
	 int threadSize = 4;
	 try {
		 threadSize=Integer.parseInt(threadSizeStr);
		 threadSize=threadSize<=0?4:threadSize;
	 }catch(Exception e) {
		 
	 }
	 logger.debug("开始启动es异步发送日志线程");
	 //创建消费线程池
	 ExecutorService service = Executors.newFixedThreadPool(threadSize);
	 for(int i=1;i<=threadSize;i++) {
		 Worker woker = new Worker(i);
		 service.submit(woker);
	 }
	 logger.debug("启动es异步发送日志线程完成");
}

}
