package com.petecat.interchan.core.task;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

/**
 * 
 * @ClassName:  SingleDymanicTask   
 * @Description:  单机版动态任务
 * @author: mhuang
 * @date:   2018年5月11日 上午10:33:45
 */
@Component
public class SingleDymanicTask {

	@Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;
 
    private Map<String, SingleInterjob> interJobMap = new ConcurrentHashMap<>();
    
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    /**
     * 启动定时任务
     * @Title: startJob   
     * @param jobName
     * @param run
     * @param conn
     * @return
     * @return Boolean
     */
    public Boolean startJob(String jobName,Runnable run,String conn) {
    	logger.debug("正在启动定时任务:{}，表达式时间是:{}",jobName,conn);
    	Boolean result =  false;
    	if(!interJobMap.containsKey(jobName)){
    		logger.debug("执行定时任务:{}，表达式时间是:{}",jobName,conn);
    		
    		ScheduledFuture<?> future = threadPoolTaskScheduler.schedule(run, (triggerContext)->{
    			SingleInterjob interJob = interJobMap.get(jobName);
    			String nextCron = conn;
    			if(interJob != null){
    				nextCron = interJob.getCron();
    			}
    			return new CronTrigger(nextCron).nextExecutionTime(triggerContext);
    		});
    		
    		interJobMap.put(jobName, new SingleInterjob(future,run,conn));
        	result = true;
    	}
    	logger.debug("启动定时任务:{}，表达式时间是:{}完成",jobName,conn);
    	return result;
    }
 
    public Boolean stopJob(String jobName) {
    	logger.debug("正在停止定时任务:{}",jobName);
    	Boolean result =  false;
    	if(interJobMap.containsKey(jobName)){
    		logger.debug("正在执行停止定时任务:{}",jobName);
    		SingleInterjob job = interJobMap.remove(jobName);
    		job.getFuture().cancel(true);
    		interJobMap.remove(jobName);
    		result =  true;
    	}
    	logger.debug("停止定时任务:{}完成",jobName);
       return result;
    }
 
    /**
     * 
     * @Title: updateCronLazy   
     * @Description: 非立刻生效
     * @param jobName
     * @param cron
     * @return
     * @return Boolean
     */
    public Boolean updateCronLazy(String jobName,String cron){
    	logger.debug("正在执行修改任务时间,定时任务:{}，表达式时间是:{}",jobName,cron);
    	Boolean result =  false;
    	if(interJobMap.containsKey(jobName)){
    		interJobMap.get(jobName);
    		logger.debug("找到任务：{}，执行时间覆盖覆盖，旧的时间为：{}",jobName,interJobMap.get(jobName).getCron());
    		interJobMap.get(jobName).setCron(cron);
    		result = true;
    	}
    	logger.debug("执行修改任务时间,定时任务:{}，表达式时间是:{}成功",jobName,cron);
    	return result;
    }
    
    public Boolean updateJob(String jobName,String cron){
    	Boolean result = false;
    	if(interJobMap.containsKey(jobName)){
    		result = updateJob(jobName,interJobMap.get(jobName).getRunnable(),cron);
    	}
    	return result;
    }
    
    public Boolean updateJob(String jobName,Runnable run){
    	Boolean result = false;
    	if(interJobMap.containsKey(jobName)){
    		result = updateJob(jobName, run,interJobMap.get(jobName).getCron());
    	}
    	return result;
    }
    /**
     * 修改任务
     * @Title: updateJob   
     * @param jobName
     * 			任务名称
     * @param run
     * 			修改的处理
     * @param conn
     * 			修改的时间
     * @return
     * @return Boolean
     */
    public Boolean updateJob(String jobName,Runnable run,String conn) {
       logger.debug("正在执行修改定时任务:{}，表达式时间是:{}",jobName,conn);
       Boolean result = stopJob(jobName);// 先停止，在开启.
       if(result){
    	   result = startJob(jobName, run, conn);
       }
       logger.debug("执行修改定时任务:{}，表达式时间是:{}完成",jobName,conn);
       return result;
    }
}
