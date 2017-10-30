package com.petecat.interchan.logger.common.dao;

import org.springframework.stereotype.Repository;

import com.mhuang.elk.common.source.crud.EsFactoryImpl;
import com.petecat.interchan.logger.common.model.EsOperatorLogger;

//import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
//
//import com.petecat.elk.commons.logger.model.EsOperatorLogger;

/**
 * 
 * @ClassName:  EsLoggerDao   
 * @Description:Spring-data 自带实现
 * @author: mhuang
 * @date:   2017年8月3日 下午2:02:36
 */
//public interface EsLoggerDao extends ElasticsearchRepository<EsOperatorLogger, String>{
//
//}
@Repository
public class EsLoggerDao extends EsFactoryImpl<EsOperatorLogger, String>{

}
