package com.data.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.data.greendao.Activity;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table ACTIVITY.
*/
public class ActivityDao extends AbstractDao<Activity, Long> {

    public static final String TABLENAME = "ACTIVITY";

    /**
     * Properties of entity Activity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Channel_id = new Property(1, Integer.class, "channel_id", false, "CHANNEL_ID");
        public final static Property Task_id = new Property(2, Integer.class, "task_id", false, "TASK_ID");
        public final static Property User_id = new Property(3, Integer.class, "user_id", false, "USER_ID");
        public final static Property Target_user_id = new Property(4, Integer.class, "target_user_id", false, "TARGET_USER_ID");
        public final static Property Target_id = new Property(5, Integer.class, "target_id", false, "TARGET_ID");
        public final static Property Target_type = new Property(6, String.class, "target_type", false, "TARGET_TYPE");
        public final static Property Action = new Property(7, String.class, "action", false, "ACTION");
        public final static Property Read_at = new Property(8, String.class, "read_at", false, "READ_AT");
        public final static Property Created_at = new Property(9, String.class, "created_at", false, "CREATED_AT");
    };


    public ActivityDao(DaoConfig config) {
        super(config);
    }
    
    public ActivityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'ACTIVITY' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'CHANNEL_ID' INTEGER," + // 1: channel_id
                "'TASK_ID' INTEGER," + // 2: task_id
                "'USER_ID' INTEGER," + // 3: user_id
                "'TARGET_USER_ID' INTEGER," + // 4: target_user_id
                "'TARGET_ID' INTEGER," + // 5: target_id
                "'TARGET_TYPE' TEXT," + // 6: target_type
                "'ACTION' TEXT," + // 7: action
                "'READ_AT' TEXT," + // 8: read_at
                "'CREATED_AT' TEXT);"); // 9: created_at
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'ACTIVITY'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Activity entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Integer channel_id = entity.getChannel_id();
        if (channel_id != null) {
            stmt.bindLong(2, channel_id);
        }
 
        Integer task_id = entity.getTask_id();
        if (task_id != null) {
            stmt.bindLong(3, task_id);
        }
 
        Integer user_id = entity.getUser_id();
        if (user_id != null) {
            stmt.bindLong(4, user_id);
        }
 
        Integer target_user_id = entity.getTarget_user_id();
        if (target_user_id != null) {
            stmt.bindLong(5, target_user_id);
        }
 
        Integer target_id = entity.getTarget_id();
        if (target_id != null) {
            stmt.bindLong(6, target_id);
        }
 
        String target_type = entity.getTarget_type();
        if (target_type != null) {
            stmt.bindString(7, target_type);
        }
 
        String action = entity.getAction();
        if (action != null) {
            stmt.bindString(8, action);
        }
 
        String read_at = entity.getRead_at();
        if (read_at != null) {
            stmt.bindString(9, read_at);
        }
 
        String created_at = entity.getCreated_at();
        if (created_at != null) {
            stmt.bindString(10, created_at);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Activity readEntity(Cursor cursor, int offset) {
        Activity entity = new Activity( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1), // channel_id
            cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2), // task_id
            cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3), // user_id
            cursor.isNull(offset + 4) ? null : cursor.getInt(offset + 4), // target_user_id
            cursor.isNull(offset + 5) ? null : cursor.getInt(offset + 5), // target_id
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // target_type
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // action
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // read_at
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9) // created_at
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Activity entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setChannel_id(cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1));
        entity.setTask_id(cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2));
        entity.setUser_id(cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3));
        entity.setTarget_user_id(cursor.isNull(offset + 4) ? null : cursor.getInt(offset + 4));
        entity.setTarget_id(cursor.isNull(offset + 5) ? null : cursor.getInt(offset + 5));
        entity.setTarget_type(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setAction(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setRead_at(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setCreated_at(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Activity entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Activity entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
