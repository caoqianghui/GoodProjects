package com.cqh.greendao.gen;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.go.cqh.goodprojects.entry.DBean;

import com.cqh.greendao.gen.DBeanDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig dBeanDaoConfig;

    private final DBeanDao dBeanDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        dBeanDaoConfig = daoConfigMap.get(DBeanDao.class).clone();
        dBeanDaoConfig.initIdentityScope(type);

        dBeanDao = new DBeanDao(dBeanDaoConfig, this);

        registerDao(DBean.class, dBeanDao);
    }
    
    public void clear() {
        dBeanDaoConfig.clearIdentityScope();
    }

    public DBeanDao getDBeanDao() {
        return dBeanDao;
    }

}