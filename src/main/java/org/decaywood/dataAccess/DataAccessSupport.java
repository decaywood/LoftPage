package org.decaywood.dataAccess;

import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by decaywood on 2015/5/23.
 */

@Repository("dataAccessSupport")
public class DataAccessSupport <R, L, K, V, P> implements IDataAccess <R, L, K, V, P> {

    @Resource(name = "sqlSessionTemplate")
    private SqlSessionTemplate sqlSessionTemplate;


    @Override
    public R selectOne(String statement) {
        return sqlSessionTemplate.selectOne(statement);
    }

    @Override
    public R selectOne(String statement, P parameter) {
        return sqlSessionTemplate.selectOne(statement, parameter);
    }

    @Override
    public List<L> selectList(String statement) {
        return sqlSessionTemplate.selectList(statement);
    }

    @Override
    public List<L> selectList(String statement, P parameter) {
        return sqlSessionTemplate.selectList(statement, parameter);
    }

    @Override
    public List<L> selectList(String statement, P parameter, RowBounds rowBounds) {
        return sqlSessionTemplate.selectList(statement, parameter, rowBounds);
    }

    @Override
    public Map<K, V> selectMap(String statement, String parameter) {
        return sqlSessionTemplate.selectMap(statement, parameter);
    }

    @Override
    public Map<K, V> selectMap(String statement, P parameter, String mapKey) {
        return sqlSessionTemplate.selectMap(statement, parameter, mapKey);
    }

    @Override
    public Map<K, V> selectMap(String statement, P parameter, String mapKey, RowBounds rowBounds) {
        return sqlSessionTemplate.selectMap(statement, parameter, mapKey, rowBounds);
    }

    @Override
    public void select(String statement, P parameter, ResultHandler handler) {
        sqlSessionTemplate.select(statement, parameter, handler);
    }

    @Override
    public void select(String statement, ResultHandler handler) {
        sqlSessionTemplate.select(statement, handler);
    }

    @Override
    public void select(String statement, P parameter, RowBounds rowBounds, ResultHandler handler) {
        sqlSessionTemplate.select(statement, parameter, rowBounds, handler);
    }

    @Override
    public int insert(String statement) {
        return sqlSessionTemplate.insert(statement);
    }

    @Override
    public int insert(String statement, P parameter) {
        return sqlSessionTemplate.insert(statement, parameter);
    }

    @Override
    public int update(String statement) {
        return sqlSessionTemplate.update(statement);
    }

    @Override
    public int update(String statement, P parameter) {
        return sqlSessionTemplate.update(statement, parameter);
    }

    @Override
    public int delete(String statement) {
        return sqlSessionTemplate.delete(statement);
    }

    @Override
    public int delete(String statement, P parameter) {
        return sqlSessionTemplate.delete(statement, parameter);
    }
}
