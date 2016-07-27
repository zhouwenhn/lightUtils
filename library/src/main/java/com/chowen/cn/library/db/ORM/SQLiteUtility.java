package com.chowen.cn.library.db.ORM;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.chowen.cn.library.db.ORM.extra.AutoIncrementTableColumn;
import com.chowen.cn.library.db.ORM.extra.Extra;
import com.chowen.cn.library.db.ORM.extra.TableColumn;
import com.chowen.cn.library.db.ORM.extra.TableInfo;
import com.chowen.cn.library.db.ORM.utils.SqlUtils;
import com.chowen.cn.library.db.ORM.utils.TableInfoUtils;
import com.chowen.cn.library.log.Logger;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

/**
 * @author zhouwen
 * @version 0.1
 * @since 2016/07/26
 */
public class SQLiteUtility {
    public static final String TAG = "SQLiteUtility";

    private static Hashtable<String, SQLiteUtility> dbCache = new Hashtable<String, SQLiteUtility>();

    private String dbName;
    private SQLiteDatabase db;

    SQLiteUtility(String dbName, SQLiteDatabase db) {
        this.db = db;
        this.dbName = dbName;

        dbCache.put(dbName, this);
    }

    public static SQLiteUtility getInstance() {
        return getInstance(SQLiteUtilityBuilder.DEFAULT_DB);
    }

    public static SQLiteUtility getInstance(String dbName) {
        return dbCache.get(dbName);
    }

    /*******************************************
     * 开始Select系列方法
     ****************************************************/

    public <T> T selectById(Class<T> clazz, Object id) {
        return selectById(getDefaultExtra(), clazz, id);
    }

    public <T> T selectById(Extra extra, Class<T> clazz, Object id) {
        try {
            TableInfo tableInfo = checkTable(clazz);

            String selection = String.format(" %s = ? ", tableInfo.getPrimaryKey().getColumn());
            String extraSelection = SqlUtils.appendExtraKeyLikeWhereClause(extra);
            if (!TextUtils.isEmpty(extraSelection))
                selection = String.format("%s and %s", selection, extraSelection);

            List<String> selectionArgList = new ArrayList<String>();
            selectionArgList.add(String.valueOf(id));
            String[] extraSelectionArgs = SqlUtils.appendExtraKeyLikeWhereArgs(extra);
            if (extraSelectionArgs != null && extraSelectionArgs.length > 0)
                selectionArgList.addAll(Arrays.asList(extraSelectionArgs));
            String[] selectionArgs = selectionArgList.toArray(new String[0]);

            List<T> list = select(clazz, selection, selectionArgs, null, null, null, null);
            if (list.size() > 0) {
                return list.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

//    public <T> List<T> selectGroupById(Class<T> clazz, Object id) {
//        return selectGroupById(getDefaultExtra(), clazz, id);
//    }

    /**
     * 根据ID 查出一组数据
     * @param extra
     * @param clazz
     * @param id
     * @param <T>
     * @return
     */
//    public <T> List<T> selectGroupById(Extra extra, Class<T> clazz, Object id) {
//        try {
//            TableInfo tableInfo = checkTable(clazz);
//
//            String selection = String.format(" %s = ? ", tableInfo.getPrimaryKey().getColumn());
//            String extraSelection = SqlUtils.appendExtraWhereClause(extra);
//            if (!TextUtils.isEmpty(extraSelection))
//                selection = String.format("%s and %s", selection, extraSelection);
//
//            List<String> selectionArgList = new ArrayList<String>();
//            selectionArgList.add(String.valueOf(id));
//            String[] extraSelectionArgs = SqlUtils.appendExtraWhereArgs(extra);
//            if (extraSelectionArgs != null && extraSelectionArgs.length > 0)
//                selectionArgList.addAll(Arrays.asList(extraSelectionArgs));
//            String[] selectionArgs = selectionArgList.toArray(new String[0]);
//            return select(clazz, selection, selectionArgs, null, null, null, null);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }

    public <T> List<T> select(Class<T> clazz) {
        return select(getDefaultExtra(), clazz);
    }

    public <T> List<T> select(Extra extra, Class<T> clazz) {
        String selection = SqlUtils.appendExtraWhereClause(extra);
        String[] selectionArgs = SqlUtils.appendExtraWhereArgs(extra);

        return select(clazz, selection, selectionArgs, null, null, null, null);
    }

    public <T> List<T> select(Class<T> clazz, String selection, String[] selectionArgs) {
        return select(clazz, selection, selectionArgs, null, null, null, null);
    }

    public <T> List<T> select(Class<T> clazz, String selection,
                              String[] selectionArgs, String groupBy, String having,
                              String orderBy, String limit) {
        TableInfo tableInfo = checkTable(clazz);

        ArrayList<T> list = new ArrayList<T>();

        List<String> columnList = new ArrayList<String>();
        columnList.add(tableInfo.getPrimaryKey().getColumn());
        for (TableColumn tableColumn : tableInfo.getColumns())
            columnList.add(tableColumn.getColumn());

        Cursor cursor = db.query(tableInfo.getTableName(), columnList.toArray(new String[0]),
                selection, selectionArgs, groupBy, having, orderBy, limit);
        try {
            if (cursor.moveToFirst()) {
                do {
                    try {
                        T entity = clazz.newInstance();

                        // 绑定主键
                        bindSelectValue(entity, cursor, tableInfo.getPrimaryKey());

                        // 绑定其他数据
                        for (TableColumn column : tableInfo.getColumns())
                            bindSelectValue(entity, cursor, column);

                        list.add(entity);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }
        Logger.d(TAG + " 查询到数据 %d 条", list.size());

        return list;
    }

    /*******************************************开始Insert系列方法****************************************************/

    /**
     * 如果主键实体已经存在，则忽略插库
     *
     * @param entities
     */
    public <T> void insert(T... entities) {
        insert(getDefaultExtra(), entities);
    }
    /**
     * 如果主键实体已经存在，则忽略插库
     *
     */
    public <T> void insert(Extra extra, T... entities) {
        try {
            if (entities != null && entities.length > 0)
                insert(extra, Arrays.asList(entities));
            else
                Logger.d(TAG+" method[insert(Extra extra, T... entities)], entities is null or empty");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 如果主键实体已经存在，使用新的对象存库
     */
//    public <T> void insertOrReplace(T... entities) {
//        insertOrReplace(getDefaultExtra(), entities);
//    }

    /**
     * 如果主键实体已经存在，使用新的对象存库
     *
     * @param extra
     * @param entities
     */
    public <T> void insertOrReplace(Extra extra, T... entities) {
        try {
            if (entities != null && entities.length > 0)
                insert(extra, Arrays.asList(entities), "INSERT OR REPLACE INTO ");
            else
                Logger.d(TAG+" method[insertOrReplace(Extra extra, T... entities)], entities is null or empty");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public <T> void insert(List<T> entityList) {
        insert(getDefaultExtra(), entityList);
    }

    public <T> void insert(Extra extra, List<T> entityList) {
        try {
            insert(extra, entityList, "INSERT OR IGNORE INTO ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public <T> void insertOrReplace(List<T> entityList) {
//        insertOrReplace(getDefaultExtra(), entityList);
//    }

    public <T> void insertOrReplace(Extra extra, List<T> entityList) {
        try {
            insert(extra, entityList, "INSERT OR REPLACE INTO ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private <T> void insert(Extra extra, List<T> entityList, String insertInto) {
        if (entityList == null || entityList.size() == 0) {
            Logger.d(TAG+ " method[insert(Extra extra, List<T> entityList)], entityList is null or empty");
            return;
        }

        TableInfo tableInfo = checkTable(entityList.get(0).getClass());
        db.beginTransaction();
        try {
            String sql = SqlUtils.createSqlInsert(insertInto, tableInfo);

            Logger.v(TAG + insertInto + " sql = %s", sql);

            SQLiteStatement insertStatement = db.compileStatement(sql);
            for (T entity : entityList) {
                bindInsertValues(extra, insertStatement, tableInfo, entity);
                insertStatement.execute();
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    /*******************************************
     * 开始Update系列方法
     ****************************************************/

    public <T> void update(T... entities) {
        update(getDefaultExtra(), entities);
    }

    public <T> void update(Extra extra, T... entities) {
        try {
            if (entities != null && entities.length > 0)
                insertOrReplace(extra, entities);
            else
                Logger.d(TAG+ " method[update(Extra extra, T... entities)], entities is null or empty");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public <T> void update(List<T> entityList) {
        update(getDefaultExtra(), entityList);
    }

    public <T> void update(Extra extra, List<T> entityList) {
        try {
            if (entityList != null && entityList.size() > 0)
                insertOrReplace(extra, entityList);
            else
                Logger.d(TAG, "method[update(Extra extra, T... entities)], entities is null or empty");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public <T> void myUpdateById(Extra extra, T entity, Object id) {
//        if (entity == null) {
//            L.d(TAG+ " method[myUpdateById(Extra extra, T entity)], entity is null or empty");
//            return;
//        }
//
//        try {
//            TableInfo tableInfo = checkTable(entity.getClass());
//
//            String whereClause = String.format(" %s = ? ", tableInfo.getPrimaryKey().getColumn());
//            String extraWhereClause = SqlUtils.appendExtraWhereClause(extra);
//            if (!TextUtils.isEmpty(extraWhereClause))
//                whereClause = String.format("%s and %s", whereClause, extraWhereClause);
//
//            List<String> whereArgList = new ArrayList<String>();
//            whereArgList.add(String.valueOf(id));
//            String[] extraWhereArgs = SqlUtils.appendExtraWhereArgs(extra);
//            if (extraWhereArgs != null && extraWhereArgs.length > 0)
//                whereArgList.addAll(Arrays.asList(extraWhereArgs));
//            String[] whereArgs = whereArgList.toArray(new String[0]);
//
//            doMyUpdate(entity, whereClause, whereArgs);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    public <T> void doMyUpdate(T entity, String whereClause, String[] whereArgs) {
//        if (entity == null) {
//            L.d("method[doMyUpdate(Extra extra, T entity, String whereClause, String[] whereArgs)], entity is null or empty");
//            return;
//        }
//
//        TableInfo tableInfo = checkTable(entity.getClass());
//        db.beginTransaction();
//        try {
//            String updateSql = SqlUtils.createSqlUpdate(tableInfo);
//            StringBuilder builder = new StringBuilder(updateSql);
//            builder.append(" WHERE").append(whereClause);
//            String sql = builder.toString();
//            L.v("update sql = %s", sql);
//
//            SQLiteStatement updateStatement = db.compileStatement(sql);
//            bindUpdateValues(updateStatement, tableInfo, entity, whereArgs);
//            updateStatement.execute();
//            db.setTransactionSuccessful();
//        } finally {
//            db.endTransaction();
//        }
//    }

    public int updateById(Class<?> clazz, ContentValues values, Extra extra, Object id) {
        if (values == null) {
            Logger.d(TAG+ " method[myUpdateById(Extra extra, T entity)], entity is null or empty");
            return 0;
        }

        try {
            TableInfo tableInfo = checkTable(clazz);

            String whereClause = String.format(" %s = ? ", tableInfo.getPrimaryKey().getColumn());
            String extraWhereClause = SqlUtils.appendExtraKeyLikeWhereClause(extra);
            if (!TextUtils.isEmpty(extraWhereClause))
                whereClause = String.format("%s and %s", whereClause, extraWhereClause);

            List<String> whereArgList = new ArrayList<String>();
            whereArgList.add(String.valueOf(id));
            String[] extraWhereArgs = SqlUtils.appendExtraKeyLikeWhereArgs(extra);
            if (extraWhereArgs != null && extraWhereArgs.length > 0)
                whereArgList.addAll(Arrays.asList(extraWhereArgs));
            String[] whereArgs = whereArgList.toArray(new String[0]);

            return update(clazz, values, whereClause, whereArgs);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public <T> int update(Class<?> clazz, ContentValues values, String whereClause, String[] whereArgs) {
        try {
            TableInfo tableInfo = checkTable(clazz);

            return db.update(tableInfo.getTableName(), values, whereClause, whereArgs);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    /*******************************************
     * 开始Delete系列方法
     ****************************************************/

//    public <T> void deleteAll(Class<T> clazz) {
//        deleteAll(getDefaultExtra(), clazz);
//    }

    public <T> void deleteAll(Extra extra, Class<T> clazz) {
        try {
            TableInfo tableInfo = checkTable(clazz);

            String where = SqlUtils.appendExtraWhereClauseSql(extra);
            if (!TextUtils.isEmpty(where))
                where = " where " + where;
            String sql = "DELETE FROM '" + tableInfo.getTableName() + "' " + where;
            db.execSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public <T> void deleteById(Class<T> clazz, Object id) {
//        deleteById(getDefaultExtra(), clazz, id);
//    }

    public <T> void deleteById(Extra extra, Class<T> clazz, Object id) {
        try {
            TableInfo tableInfo = checkTable(clazz);

            String whereClause = String.format(" %s = ? ", tableInfo.getPrimaryKey().getColumn());
            String extraWhereClause = SqlUtils.appendExtraWhereClause(extra);
            if (!TextUtils.isEmpty(extraWhereClause))
                whereClause = String.format("%s and %s", whereClause, extraWhereClause);

            List<String> whereArgList = new ArrayList<String>();
            whereArgList.add(String.valueOf(id));
            String[] extraWhereArgs = SqlUtils.appendExtraWhereArgs(extra);
            if (extraWhereArgs != null && extraWhereArgs.length > 0)
                whereArgList.addAll(Arrays.asList(extraWhereArgs));
            String[] whereArgs = whereArgList.toArray(new String[0]);
            db.delete(tableInfo.getTableName(), whereClause, whereArgs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public <T> void delete(Class<T> clazz, String whereClause, String[] whereArgs) {
        try {
            TableInfo tableInfo = checkTable(clazz);

            long start = System.currentTimeMillis();
            int rowCount = db.delete(tableInfo.getTableName(), whereClause, whereArgs);

            Logger.d(TAG + " 表 %s 删除数据 %d 条，耗时 %s ms", tableInfo.getTableName(), rowCount, String.valueOf(System.currentTimeMillis() - start));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*******************************************
     * 系列统计的方法
     ****************************************************/

//    public long sum(Class<?> clazz, String column, String whereClause, String[] whereArgs) {
//        TableInfo tableInfo = checkTable(clazz);
//
//        if (TextUtils.isEmpty(column))
//            return 0;
//
//        String sql;
//        if (TextUtils.isEmpty(whereClause)) {
//            whereArgs = null;
//            sql = String.format(" select sum(%s) as _sum_ from %s ", column, tableInfo.getTableName());
//        } else {
//            sql = String.format(" select sum(%s) as _sum_ from %s where %s ", column, tableInfo.getTableName(), whereClause);
//        }
//        try {
//            Cursor cursor = db.rawQuery(sql, whereArgs);
//            if (cursor.moveToFirst()) {
//                long sum = cursor.getLong(cursor.getColumnIndex("_sum_"));
//                cursor.close();
//                return sum;
//            }
//        } catch (Exception e) {
//            L.d(e.toString());
//        }
//        return 0;
//    }

    public long count(Class<?> clazz, String whereClause, String[] whereArgs) {
        TableInfo tableInfo = checkTable(clazz);

        String sql;
        if (TextUtils.isEmpty(whereClause)) {
            whereArgs = null;
            sql = String.format(" select count(*) as _count_ from %s ", tableInfo.getTableName());
        } else {
            sql = String.format(" select count(*) as _count_ from %s where %s ", tableInfo.getTableName(), whereClause);
        }
        try {
            Cursor cursor = db.rawQuery(sql, whereArgs);
            if (cursor.moveToFirst()) {
                long count = cursor.getLong(cursor.getColumnIndex("_count_"));
                cursor.close();
                return count;
            }
        } catch (Exception e) {
            Logger.d("count exception");
        }
        return 0;
    }

    /*******************************************
     * 系列绑定数据的方法
     ****************************************************/

    private <T> void bindInsertValues(Extra extra, SQLiteStatement insertStatement, TableInfo tableInfo, T entity) {
        int index = 1;

        // 如果是自增主键，不设置值
        if (tableInfo.getPrimaryKey() instanceof AutoIncrementTableColumn){
            Logger.d("auto increment key");
        } else {
            bindInsertValue(insertStatement, index++, tableInfo.getPrimaryKey(), entity);
        }

        for (int i = 0; i < tableInfo.getColumns().size(); i++) {
            TableColumn column = tableInfo.getColumns().get(i);
            bindInsertValue(insertStatement, index++, column, entity);
        }

        // owner
        String owner = extra == null || TextUtils.isEmpty(extra.getOwner()) ? "" : extra.getOwner();
        insertStatement.bindString(index++, owner);
        // key
        String key = extra == null || TextUtils.isEmpty(extra.getKey()) ? "" : extra.getKey();
        insertStatement.bindString(index++, key);
        // createAt
        long createAt = System.currentTimeMillis();
        insertStatement.bindLong(index, createAt);
    }

    private <T> void bindInsertValue(SQLiteStatement insertStatement, int index, TableColumn column, T entity) {
        // 通过反射绑定数据
        try {
            column.getField().setAccessible(true);
            Object value = column.getField().get(entity);
            if (value == null) {
                insertStatement.bindNull(index);
                return;
            }

            if ("object".equalsIgnoreCase(column.getDataType())) {
                insertStatement.bindString(index, JSON.toJSONString(value));
            } else if ("INTEGER".equalsIgnoreCase(column.getColumnType())) {
                insertStatement.bindLong(index, Long.parseLong(value.toString()));
            } else if ("REAL".equalsIgnoreCase(column.getColumnType())) {
                insertStatement.bindDouble(index, Double.parseDouble(value.toString()));
            } else if ("BLOB".equalsIgnoreCase(column.getColumnType())) {
                insertStatement.bindBlob(index, (byte[]) value);
            } else if ("TEXT".equalsIgnoreCase(column.getColumnType())) {
                insertStatement.bindString(index, value.toString());
            }
        } catch (Exception e) {
            Logger.w("属性 %s 绑定异常", column.getField().getName());
        }
    }

//    private <T> void bindUpdateValues(SQLiteStatement updateStatement, TableInfo tableInfo, T entity, String[] whereArgs) {
//        int index = 1;
//        int size = tableInfo.getColumns().size();
//        for (int i = 0; i < size; i++) {
//            TableColumn column = tableInfo.getColumns().get(i);
//            bindUpdateValue(updateStatement, index++, column, entity);
//        }
//
//        int whereSize = whereArgs != null ? whereArgs.length : 0;
//        for(int i=0; i<whereSize; i++) {
//            bindWhereValue(updateStatement, index++, whereArgs[i]);
//        }
//    }

//    private <T> void bindUpdateValue(SQLiteStatement updateStatement, int index, TableColumn column, T entity) {
//        // 通过反射绑定数据
//        try {
//            column.getField().setAccessible(true);
//            Object value = column.getField().get(entity);
//            if (value == null) {
//                updateStatement.bindNull(index);
//                return;
//            }
//
//            if ("object".equalsIgnoreCase(column.getDataType())) {
//                updateStatement.bindString(index, JSON.toJSONString(value));
//            } else if ("INTEGER".equalsIgnoreCase(column.getColumnType())) {
//                updateStatement.bindLong(index, Long.parseLong(value.toString()));
//            } else if ("REAL".equalsIgnoreCase(column.getColumnType())) {
//                updateStatement.bindDouble(index, Double.parseDouble(value.toString()));
//            } else if ("BLOB".equalsIgnoreCase(column.getColumnType())) {
//                updateStatement.bindBlob(index, (byte[]) value);
//            } else if ("TEXT".equalsIgnoreCase(column.getColumnType())) {
//                updateStatement.bindString(index, value.toString());
//            }
//        } catch (Exception e) {
//            L.w( "属性 %s 绑定异常", column.getField().getName());
//        }
//    }

    public static void bindContentValues(ContentValues cv, String key, Object value) {
        try {
            if (value == null) {
                cv.putNull(key);
            }

            if (value instanceof Boolean) {
                cv.put(key, Boolean.toString(((Boolean) value).booleanValue()));
            } else if (value instanceof Double || value instanceof Float) {
                cv.put(key, Double.parseDouble(String.valueOf(value)));
            } else if (value instanceof byte[]) {
                cv.put(key, (byte[])value);
            } else if(value instanceof Integer || value instanceof Long || value instanceof Byte) {
                cv.put(key, Long.parseLong(String.valueOf(value)));
            } else if(value instanceof Character || value instanceof String || value instanceof Short) {
                cv.put(key, String.valueOf(value));
            } else {
                cv.put(key, JSON.toJSONString(value));
            }
        } catch (Exception e) {
            Logger.w("属性 %s 绑定异常", key);
        }
    }

//    private void bindWhereValue(SQLiteStatement sqLiteStatement, int index, String value) {
//        if(value == null) {
//            sqLiteStatement.bindNull(index);
//        } else {
//            sqLiteStatement.bindString(index, value);
//        }
//    }

    private <T> void bindSelectValue(T entity, Cursor cursor, TableColumn column) {
        Field field = column.getField();
        field.setAccessible(true);

        try {
            if (field.getType().getName().equals("int") ||
                    field.getType().getName().equals("java.lang.Integer")) {
                field.set(entity, cursor.getInt(cursor.getColumnIndex(column.getColumn())));
            } else if (field.getType().getName().equals("long") ||
                    field.getType().getName().equals("java.lang.Long")) {
                field.set(entity, cursor.getLong(cursor.getColumnIndex(column.getColumn())));
            } else if (field.getType().getName().equals("float") ||
                    field.getType().getName().equals("java.lang.Float")) {
                field.set(entity, cursor.getFloat(cursor.getColumnIndex(column.getColumn())));
            } else if (field.getType().getName().equals("double") ||
                    field.getType().getName().equals("java.lang.Double")) {
                field.set(entity, cursor.getDouble(cursor.getColumnIndex(column.getColumn())));
            } else if (field.getType().getName().equals("boolean") ||
                    field.getType().getName().equals("java.lang.Boolean")) {
                field.set(entity, Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(column.getColumn()))));
            } else if (field.getType().getName().equals("char") ||
                    field.getType().getName().equals("java.lang.Character")) {
                field.set(entity, cursor.getString(cursor.getColumnIndex(column.getColumn())).toCharArray()[0]);
            } else if (field.getType().getName().equals("byte") ||
                    field.getType().getName().equals("java.lang.Byte")) {
                field.set(entity, (byte) cursor.getInt(cursor.getColumnIndex(column.getColumn())));
            } else if (field.getType().getName().equals("short") ||
                    field.getType().getName().equals("java.lang.Short")) {
                field.set(entity, cursor.getShort(cursor.getColumnIndex(column.getColumn())));
            } else if (field.getType().getName().equals("java.lang.String")) {
                field.set(entity, cursor.getString(cursor.getColumnIndex(column.getColumn())));
            } else if (field.getType().getName().equals("[B")) {
                field.set(entity, cursor.getBlob(cursor.getColumnIndex(column.getColumn())));
            } else {
                String text = cursor.getString(cursor.getColumnIndex(column.getColumn()));
                field.set(entity, JSON.parseObject(text, field.getGenericType()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 检查table是否已经存在<br/>
     * 不存在，就自动创建<br/>
     * 存在，检查Entity字段是否有增加，有则更新表<br/>
     */
    private <T> TableInfo checkTable(Class<T> clazz) {
        TableInfo tableInfo = TableInfoUtils.exist(dbName, clazz);
        if (tableInfo != null) {
            Logger.d(TAG+" 检查Entity字段是否有增加，有则更新表");
        } else {
            tableInfo = TableInfoUtils.newTable(dbName, db, clazz);
        }

        return tableInfo;
    }

    private Extra getDefaultExtra() {
        return new Extra(String.valueOf("userId"), null);
    }

    public static class ContentValueSetter {
        private ContentValues cv;

        public ContentValueSetter() {
            cv = new ContentValues();
        }

        public void set(String key, Object value) {
            SQLiteUtility.bindContentValues(cv, key, value);
        }

        public ContentValues values() {
            return cv;
        }
    }
}
