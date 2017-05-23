package com.cqh.greendao.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.go.cqh.goodprojects.entry.DBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "DBEAN".
*/
public class DBeanDao extends AbstractDao<DBean, Long> {

    public static final String TABLENAME = "DBEAN";

    /**
     * Properties of entity DBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property ItemId = new Property(1, String.class, "itemId", false, "ITEM_ID");
        public final static Property Json = new Property(2, String.class, "json", false, "JSON");
        public final static Property IsCollect = new Property(3, boolean.class, "isCollect", false, "IS_COLLECT");
    }


    public DBeanDao(DaoConfig config) {
        super(config);
    }
    
    public DBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"DBEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"ITEM_ID\" TEXT," + // 1: itemId
                "\"JSON\" TEXT," + // 2: json
                "\"IS_COLLECT\" INTEGER NOT NULL );"); // 3: isCollect
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"DBEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, DBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String itemId = entity.getItemId();
        if (itemId != null) {
            stmt.bindString(2, itemId);
        }
 
        String json = entity.getJson();
        if (json != null) {
            stmt.bindString(3, json);
        }
        stmt.bindLong(4, entity.getIsCollect() ? 1L: 0L);
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, DBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String itemId = entity.getItemId();
        if (itemId != null) {
            stmt.bindString(2, itemId);
        }
 
        String json = entity.getJson();
        if (json != null) {
            stmt.bindString(3, json);
        }
        stmt.bindLong(4, entity.getIsCollect() ? 1L: 0L);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public DBean readEntity(Cursor cursor, int offset) {
        DBean entity = new DBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // itemId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // json
            cursor.getShort(offset + 3) != 0 // isCollect
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, DBean entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setItemId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setJson(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setIsCollect(cursor.getShort(offset + 3) != 0);
     }
    
    @Override
    protected final Long updateKeyAfterInsert(DBean entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(DBean entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(DBean entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
