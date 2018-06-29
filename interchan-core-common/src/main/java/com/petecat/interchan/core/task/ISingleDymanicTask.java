package com.petecat.interchan.core.task;

/**
 * @author mhuang
 * @Title: ISingleDymanicTask
 * @Package com.petecat.interchan.core.task
 * @Description: 接口类
 * @date 2018/6/4 10:02
 */
public interface ISingleDymanicTask {

	/**
	 * 
	 * @Title: startJob   
	 * @Description: 启动任务
	 * @param jobName 任务名
	 * @param run 启动执行的线程
	 * @param conn cron表达式
	 * @return
	 * @return Boolean
	 */
    Boolean startJob(String jobName,Runnable run,String conn);

    /**
     * 
     * @Title: startJob   
     * @Description: 启动任务
     * @param jobName 任务名
     * @param run     启动执行的线程
     * @param secord  执行的毫秒间隔
     * @return
     * @return Boolean
     */
    Boolean startJob(String jobName,Runnable run,Long secord);

    /**
     * 
     * @Title: stopJob   
     * @Description: 停止删除任务   
     * @param jobName 任务名
     * @return
     * @return Boolean
     */
    Boolean stopJob(String jobName);

    /**
     * 
     * @Title: updateCronLazy   
     * @Description: 修改Cron任务，不会影响当前执行的任务。等执行完毕后会计算下次的时间戳
     * @param jobName 任务名
     * @param cron 修改后的cron表达式
     * @return
     * @return Boolean
     */
    Boolean updateCronLazy(String jobName,String cron);
    
    /**
     * 
     * @Title: updateCronLazy   
     * @Description: 修改时间间隔任务，不会影响当前执行的任务。等执行完毕后会计算下次的时间戳
     * @param jobName 任务名
     * @param secord 修改后的毫秒间隔
     * @return
     * @return Boolean
     */
    Boolean updateSecordsLazy(String jobName,Long secord);

    /**
     * 执行逻辑线删除在创建任务。创建后会根据时间立刻执行任务
     * @Title: updateJob   
     * @Description: 修改CRON任务 ，
     * @param jobName 任务名
     * @param cron 修改后的cron表达式
     * @return
     * @return Boolean
     */
    Boolean updateJob(String jobName,String cron);

    /**
     * 执行逻辑线删除在创建任务。创建后会根据时间立刻执行任务
     * @Title: updateJob   
     * @Description: 修改毫秒间隔任务 ，
     * @param jobName  任务名
     * @param secord 修改后的毫秒间隔
     * @return
     * @return Boolean
     */
    Boolean updateJob(String jobName,Long secord);

    /**
     * 执行逻辑线删除在创建任务。创建后会根据时间立刻执行任务
     * @Title: updateJob   
     * @Description: 修改任务的启动线程
     * @param jobName  任务名
     * @param run 修改后的任务启动线程
     * @return
     * @return Boolean
     */
    Boolean updateJob(String jobName,Runnable run);

    /**
     * 会先停止任务在创建任务，  创建后会立即执行任务
     * @Title: updateJob   
     * @Description: 修改CRON任务。
     * @param jobName  任务名
     * @param run 修改后的修改任务的启动线程
     * @return
     * @return Boolean
     */
    Boolean updateJob(String jobName,Runnable run,String conn);

    /**
     * 会先停止任务在创建任务，  创建后会立即执行任务
     * @Title: updateJob   
     * @Description: 修改毫秒间隔任务
     * @param jobName 任务名
     * @param run	修改后的启动的线程
     * @param secord 修改后的间隔的毫秒数
     * @return
     * @return Boolean
     */
    Boolean updateJob(String jobName,Runnable run,Long secord);
}
