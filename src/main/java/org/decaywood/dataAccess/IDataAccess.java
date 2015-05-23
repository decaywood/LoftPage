package org.decaywood.dataAccess;

import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

/**
 * Created by decaywood on 2015/5/23.
 */
public interface IDataAccess<R, L, K, V, P> {


    R selectOne(String statement);

    R selectOne(String statement, P parameter);

    List<L> selectList(String statement);

    List<L> selectList(String statement, P parameter);

    List<L> selectList(String statement, P parameter, RowBounds rowBounds);

    Map<K, V> selectMap(String statement, String parameter);

    Map<K, V> selectMap(String statement, P parameter, String mapKey);

    Map<K, V> selectMap(String statement, P parameter, String mapKey, RowBounds rowBounds);

    void select(String statement, P parameter, ResultHandler handler);

    void select(String statement, ResultHandler handler);

    void select(String statement, P parameter, RowBounds rowBounds, ResultHandler handler);

    int insert(String statement);

    int insert(String statement, P parameter);

    int update(String statement);

    int update(String statement, P parameter);

    int delete(String statement);

    int delete(String statement, P parameter);


}
