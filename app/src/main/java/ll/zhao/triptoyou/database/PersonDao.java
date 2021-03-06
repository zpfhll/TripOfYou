package ll.zhao.triptoyou.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "Person".
*/
public class PersonDao extends AbstractDao<Person, Long> {

    public static final String TABLENAME = "Person";

    /**
     * Properties of entity Person.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Tel = new Property(1, String.class, "tel", false, "TEL");
        public final static Property Name = new Property(2, String.class, "name", false, "NAME");
        public final static Property Icon = new Property(3, byte[].class, "icon", false, "ICON");
        public final static Property Type = new Property(4, String.class, "type", false, "TYPE");
    }


    public PersonDao(DaoConfig config) {
        super(config);
    }
    
    public PersonDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"Person\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"TEL\" TEXT NOT NULL ," + // 1: tel
                "\"NAME\" TEXT NOT NULL ," + // 2: name
                "\"ICON\" BLOB," + // 3: icon
                "\"TYPE\" TEXT NOT NULL );"); // 4: type
        // Add Indexes
        db.execSQL("CREATE UNIQUE INDEX " + constraint + "IDX_Person_TEL_DESC ON \"Person\"" +
                " (\"TEL\" DESC);");
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"Person\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Person entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getTel());
        stmt.bindString(3, entity.getName());
 
        byte[] icon = entity.getIcon();
        if (icon != null) {
            stmt.bindBlob(4, icon);
        }
        stmt.bindString(5, entity.getType());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Person entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getTel());
        stmt.bindString(3, entity.getName());
 
        byte[] icon = entity.getIcon();
        if (icon != null) {
            stmt.bindBlob(4, icon);
        }
        stmt.bindString(5, entity.getType());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Person readEntity(Cursor cursor, int offset) {
        Person entity = new Person( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // tel
            cursor.getString(offset + 2), // name
            cursor.isNull(offset + 3) ? null : cursor.getBlob(offset + 3), // icon
            cursor.getString(offset + 4) // type
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Person entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setTel(cursor.getString(offset + 1));
        entity.setName(cursor.getString(offset + 2));
        entity.setIcon(cursor.isNull(offset + 3) ? null : cursor.getBlob(offset + 3));
        entity.setType(cursor.getString(offset + 4));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Person entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Person entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Person entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
