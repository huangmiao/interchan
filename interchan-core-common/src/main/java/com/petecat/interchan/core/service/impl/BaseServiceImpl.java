package com.petecat.interchan.core.service.impl;

import com.petecat.interchan.core.mapper.BaseMapper;
import com.petecat.interchan.core.service.BaseService;
import com.petecat.interchan.protocol.InsertInto;
import com.petecat.interchan.protocol.data.Page;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

/**
 * @param <T>
 * @param <Id>
 * @ClassName: BaseServiceImpl
 * @Description: 通用
 * @author: mhuang
 * @date: 2017年7月13日 下午3:38:05
 */
public abstract class BaseServiceImpl<T extends Serializable, Id> implements BaseService<T, Id> {

    @Autowired
    private BaseMapper<T, Id> baseMapper;

    public void setBaseMapper(BaseMapper<T, Id> baseMapper) {
        this.baseMapper = baseMapper;
    }

    /**
     * @param t 查询的实例对象
     * @return T
     * @Title: get
     * @Description: 获取单个实例
     */
    @Override
    public T get(T t) {
        return baseMapper.get(t);
    }

    /**
     * @param id 查询的主键id
     * @return T
     * @Title: getById
     * @Description: 根据Id获取单个实例
     */
    @Override
    public T getById(Id id) {
        return baseMapper.getById(id);
    }

    /**
     * @param t 实例对象
     * @return int
     * 新增个数
     * @Title: save
     * @Description: 新增单个实例
     */
    @Override
    public int save(T t) {
        return baseMapper.save(t);
    }

    /**
     * @param t 实例对象
     * @return int
     * 修改个数
     * @Title: update
     * @Description: 修改单个实例
     */
    @Override
    public int update(T t) {
        return baseMapper.update(t);
    }

    @Override
    public int updateAll(T t) {
        return baseMapper.updateAll(t);
    }

    /**
     * @param t 实例对象
     * @return int
     * 删除的个数
     * @Title: remove
     * @Description: 删除单个实例
     */
    @Override
    public int remove(T t) {
        return baseMapper.remove(t);
    }

    /**
     * @param id 删除实例的id
     * @return int
     * 删除的个数
     * @Title: remove
     * @Description: 删除单个实例
     */
    @Override
    public int remove(Id id) {
        return baseMapper.remove(id);
    }

    /**
     * @param t 查询实例
     * @return int
     * 查询的个数
     * @Title: remove
     * @Description: 拆线呢总条数
     */
    @Override
    public int count(T t) {
        return baseMapper.count(t);
    }

    @Override
    public int insert(T t) {
        return baseMapper.insert(t);
    }

    @Override
    public List<T> page(Page<T> page) {
        return baseMapper.page(page);
    }

    @Override
    public int pageCount(Page<T> page) {
        return baseMapper.pageCount(page);
    }

    @Override
    public List<T> queryAll() {
        return baseMapper.queryAll();
    }

    @Override
    public List<T> query(T t) {
        return baseMapper.query(t);
    }

    @Override
    public int delete(Id id) {
        return baseMapper.delete(id);
    }

    @Override
    public int insertInto(InsertInto<Id> into) {
        return baseMapper.insertInto(into);
    }
}
