package com.petecat.interchan.logger.common.dao;

import org.springframework.stereotype.Repository;

import com.mhuang.elk.common.source.crud.EsFactoryImpl;
import com.mhuang.elk.common.source.crud.IEsFactory;
import com.petecat.interchan.logger.common.model.EsOperatorLogger;

/**
 * 
 * @ClassName:  EsLoggerDao   
 * @Description:Spring-data 自带实现
 * @author: mhuang
 * @date:   2017年8月3日 下午2:02:36
 */
@Repository
public class EsLoggerDao extends EsFactoryImpl<EsOperatorLogger, String> implements IEsFactory<EsOperatorLogger, String>{

}
