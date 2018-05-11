package com.petecat.interchan.core.task;

import java.util.concurrent.ScheduledFuture;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @ClassName:  SingleInterjob   
 * @Description:单机版本的任务实体 
 * @author: mhuang
 * @date:   2018年5月11日 上午10:36:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SingleInterjob {

	private ScheduledFuture<?> future ;
	
	private Runnable runnable;
	
	private String cron;
}
