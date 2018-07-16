package com.petecat.interchan.core.task;

import java.util.Date;

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
     * @Ttitle:startJobOnly
     * @Description: 启动任务（只执行一次、执行后记得将任务删除）
     * @param jobName 启动任务
     * @param run     启动执行的线程
     * @param date    启动任务的时间
     * @return
     */
    Boolean startJobOnly(String jobName, Runnable run, Date date);

    /**
     * @Ttitle:startJobOnly
     * @Description: 启动任务（只执行一次、执行后记得将任务删除）
     * @param jobName 启动任务
     * @param run     启动执行的线程
     * @param cron    启动任务的时间表达式
     * @return
     */
    Boolean startJobOnly(String jobName,Runnable run,String cron);

    /**
     * @Ttitle:startJobOnly
     * @Description: 启动任务（只执行一次、执行后记得将任务删除）
     * @param jobName 启动任务
     * @param run     启动执行的线程
     * @param peroid    启动任务的毫秒数
     * @return
     */
    Boolean startJobOnly(String jobName,Runnable run,Long peroid);

    /**
     *
     * @Title: startJob
     * @Description: 启动任务（启动后会等待到下一次的时间进行执行）
     * @param jobName 任务名
     * @param run 启动执行的线程
     * @param conn cron表达式
     * @param real 为true时跟startJob一致、为false时代表开启任务后等待时间到达后执行、创建则不执行
     * @return
     * @return Boolean
     */
    Boolean startJob(String jobName,Runnable run,String conn,Boolean real);

    /**
     *
     * @Title: startJob
     * @Description: 启动任务（启动后会等待到下一次的时间进行执行）
     * @param jobName 任务名
     * @param run 启动执行的线程
     * @param period 执行的毫秒间隔
     * @param real 为true时跟startJob一致、为false时代表开启任务后等待时间到达后执行、创建则不执行
     * @return
     * @return Boolean
     */
    Boolean startJob(String jobName,Runnable run,Long period,Boolean real);
    /**
     * 
     * @Title: startJob   
     * @Description: 启动任务
     * @param jobName 任务名
     * @param run     启动执行的线程
     * @param period  执行的毫秒间隔
     * @return
     * @return Boolean
     */
    Boolean startJob(String jobName,Runnable run,Long period);

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
     * @param peroid 修改后的毫秒间隔
     * @return
     * @return Boolean
     */
    Boolean updateSecordsLazy(String jobName,Long peroid);

    /**
     * 执行逻辑线删除在创建任务
     * @Title: updateJob   
     * @Description: 修改CRON任务 ，
     * @param jobName 任务名
     * @param cron 修改后的cron表达式
     * @return
     * @return Boolean
     */
    Boolean updateJob(String jobName,String cron);

    /**
     * 执行逻辑线删除在创建任务。
     * @Title: updateJob   
     * @Description: 修改毫秒间隔任务 ，
     * @param jobName  任务名
     * @param peroid 修改后的毫秒间隔
     * @return
     * @return Boolean
     */
    Boolean updateJob(String jobName,Long peroid);

    /**
     * 执行逻辑线删除在创建任务。
     * @Title: updateJob   
     * @Description: 修改任务的启动线程
     * @param jobName  任务名
     * @param run 修改后的任务启动线程
     * @return
     * @return Boolean
     */
    Boolean updateJob(String jobName,Runnable run);

    /**
     * 会先停止任务在创建任务，
     * @Title: updateJob   
     * @Description: 修改CRON任务。
     * @param jobName  任务名
     * @param run 修改后的修改任务的启动线程
     * @return
     * @return Boolean
     */
    Boolean updateJob(String jobName,Runnable run,String conn);

    /**
     * 会先停止任务在创建任务，
     * @Title: updateJob   
     * @Description: 修改毫秒间隔任务
     * @param jobName 任务名
     * @param run	修改后的启动的线程
     * @param peroid 修改后的间隔的毫秒数
     * @return
     * @return Boolean
     */
    Boolean updateJob(String jobName,Runnable run,Long peroid);
}
