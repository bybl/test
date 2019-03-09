package net.huadong.service;

import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;

/**
 * Created by wuph on 2016-9-30.
 * Description
 */
//@Service

public abstract class BaseService<T> {
    @Autowired
    protected Mapper<T> mapper;

    public T selectByPrimaryKey(Object key) {
        return mapper.selectByPrimaryKey(key);
    }

    public int insert(T entity) {
        return mapper.insertSelective(entity);
    }

    public int update(T entity) {
        return mapper.updateByPrimaryKeySelective(entity);
    }

    public int deleteByPrimaryKey(Object id) {
        return mapper.deleteByPrimaryKey(id);
    }

}
