package com.wangshiqi.pineappleb.model.db;

import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.assit.QueryBuilder;
import com.litesuits.orm.db.assit.WhereBuilder;
import com.wangshiqi.pineappleb.ui.app.PineAppleApp;
import java.util.List;

/**
 * Created by dllo on 16/10/28.
 * 数据库单例
 */
public class LiteOrmInstance {
    private static LiteOrmInstance instance;
    /**
     * 数据库名字
     */
    private static final String DB_NAME = "pineapple.db";
    /**
     * LiteOrm对象
     */
    private LiteOrm liteOrm;

    private LiteOrmInstance() {
        liteOrm = liteOrm.newSingleInstance(PineAppleApp.getContext(), DB_NAME);
    }

    public static LiteOrmInstance getInstance() {
        if (instance == null) {
            synchronized (LiteOrmInstance.class) {
                if (instance == null) {
                    instance = new LiteOrmInstance();
                }
            }
        }
        return instance;
    }

    /**********************************增删改查******************************/

    /**
     * 插入一条数据
     */
    public void insert(PineAppleBean bean) {
        liteOrm.insert(bean);
    }

    /**
     * 插入集合数据
     */
    public void insert(List<PineAppleBean> beanList) {
        liteOrm.insert(beanList);
    }

    /**
     * 查询所有数据
     */
    public List<PineAppleBean> queryAll() {
        return liteOrm.query(PineAppleBean.class);
    }

    /**
     * 多张表查询
     */
    public <T> List<T> queryAll(Class<T> tClass) {
        return liteOrm.query(tClass);
    }

    /**
     * 根据条件查询
     */
    public List<PineAppleBean> queryByTitle(String title) {
        QueryBuilder<PineAppleBean> qb = new QueryBuilder<>(PineAppleBean.class);
        qb.where("title = ?", new String[]{title});
        return liteOrm.query(qb);
    }

    /**
     * start end 限制条数 如果查询100条 限制使用多少条 从第几条到第几条
     */
    public List<PineAppleBean> queryByTitle(String title, int start, int end) {
        QueryBuilder<PineAppleBean> qb = new QueryBuilder<>(PineAppleBean.class);
        qb.where("title = ?", new String[]{title});
        qb.limit(start, end);
        return liteOrm.query(qb);
    }

    /**
     * 按条件删除
     */
    public void deleteByTitle(String title) {
        WhereBuilder wb = new WhereBuilder(PineAppleBean.class);
        wb.where("title = ?", new String[]{title});
        liteOrm.delete(wb);
    }

    /**
     * 删除所有数据
     */
    public void deleteAll() {
        liteOrm.deleteAll(PineAppleBean.class);
    }

/********************************************************************************************/

}
