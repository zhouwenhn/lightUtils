package com.chowen.cn.library.db.ORM.utils;


import com.chowen.cn.library.db.ORM.extra.TableColumn;

/**
 * @author zhouwen
 * @version 0.1
 * @since 2016/07/26
 */
public class FieldUtils {

    public static final String OWNER = "com_m_common_owner";

    public static final String KEY = "com_m_common_key";

    public static final String CREATEAT = "com_m_common_createat";

    public static TableColumn getOwnerColumn() {
        TableColumn column = new TableColumn();
        column.setColumn(OWNER);
        return column;
    }

    public static TableColumn getKeyColumn() {
        TableColumn column = new TableColumn();
        column.setColumn(KEY);
        return column;
    }

    public static TableColumn getCreateAtColumn() {
        TableColumn column = new TableColumn();
        column.setColumn(CREATEAT);
        return column;
    }

}
