package com.go.cqh.goodprojects.utils;

import android.content.Context;

import com.cqh.greendao.gen.DBeanDao;
import com.cqh.greendao.gen.DaoMaster;
import com.cqh.greendao.gen.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * Created by caoqianghui on 2016/12/16.
 */

public class DbUtil {

    public static DaoSession daoSession;
    public static DBeanDao dBeanDao;

    public static void init(Context context, String dbName) {
        /*初始化数据库*/
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, dbName, null);
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
        dBeanDao = daoSession.getDBeanDao();
    }
}
