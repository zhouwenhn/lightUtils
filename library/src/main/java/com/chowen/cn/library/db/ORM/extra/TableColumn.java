package com.chowen.cn.library.db.ORM.extra;

/**
 * @author zhouwen
 * @version 0.1
 * @since 2016/07/26
 */
import java.lang.reflect.Field;

public class TableColumn {

    private String dataType;

    private Field field;

    private String column;

    private String columnType;

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }
}
