package com.morningglory.mvc.canal;

/**
 * @Author: qianniu
 * @Date: 2019-12-01 12:57
 * @Desc:
 */
public interface CanalHandler<T> {

    /**
     * 获取表名
     * @return
     */
    String getTable();

    /**
     * 新增数据
     * @param obj
     */
    void doInsert( T obj);

    /**
     * 删除数据
     * @param obj
     */
    void doDelete(T obj);

    /**
     * 修改数据
     * @param obj
     */
    void doUpdate(T obj);

    /**
     * 初始化
     */
    default void init(){}

    /**
     * 销毁
     */
    default void destroy(){}

    /**
     * 创建结构
     * @param Obj
     */
    default void doCreate(T Obj){}

    /**
     * 修改结构
     * @param Obj
     */
    default void doAlter(T Obj){}

}
