package ru.javaLearning.lesson7;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class CatDao {
    private final Statement stmt;

    public CatDao(Statement stmt) {
        this.stmt = stmt;
    }

    public void createTable() throws SQLException {
        var sql = generateCreateTableScript();

        System.out.println(sql);

        execute(sql);
    }

    public void save(Cat cat) throws IllegalAccessException, SQLException {
        var sql = generateInsertItemScript(cat);

        System.out.println(sql);

        execute(sql);
    }

    private String generateCreateTableScript() {
        var cl = getItemClass();

        if (!cl.isAnnotationPresent(AppTable.class)) {
            throw new IllegalArgumentException("Invalid class");
        }

        Map<Class, String> converter = new HashMap<>();

        converter.put(int.class, "integer");
        converter.put(String.class, "text");

        var sb = new StringBuilder("CREATE TABLE IF NOT EXISTS ");

        sb.append(getTableName());

        sb.append(" (");

        Field[] fields = cl.getDeclaredFields();

        for (Field f : fields) {
            if (f.isAnnotationPresent(AppColumn.class)) {
                var name = f.getName();

                sb
                        .append(f.getName())
                        .append(" ")
                        .append(converter.get(f.getType()))
                        .append(isIdColumn(name) ? " PRIMARY KEY AUTOINCREMENT" : "")
                        .append(", ");
            }
        }

        sb.setLength(sb.length() - 2);

        sb.append(");");

        return sb.toString();
    }

    private String generateInsertItemScript(Cat cat) throws IllegalAccessException {
        var cl = getItemClass();

        var sb = new StringBuilder("INSERT INTO ");

        sb.append(getTableName());

        sb.append(" (");

        Field[] fields = cl.getDeclaredFields();

        for (Field f : fields) {
            if (f.isAnnotationPresent(AppColumn.class)) {
                var name = f.getName();

                if (isIdColumn(name)) continue;

                sb
                        .append(f.getName())
                        .append(", ");
            }
        }

        sb.setLength(sb.length() - 2);

        sb.append(") VALUES (");

        Map<Class, Boolean> classHasQuotesMap = new HashMap<>();

        classHasQuotesMap.put(int.class, false);
        classHasQuotesMap.put(String.class, true);

        for (Field f : fields) {
            if (f.isAnnotationPresent(AppColumn.class)) {
                var name = f.getName();

                if (isIdColumn(name)) continue;

                sb
                        .append(classHasQuotesMap.get(f.getType()) ? "\"" : "")
                        .append(f.get(cat))
                        .append(classHasQuotesMap.get(f.getType()) ? "\"" : "")
                        .append(", ");
            }
        }

        sb.setLength(sb.length() - 2);

        sb.append(");");

        return sb.toString();
    }

    private String getTableName() {
        return ((AppTable) getItemClass().getAnnotation(AppTable.class)).title();
    }

    private boolean isIdColumn(String name) {
        return name.equals("id");
    }

    private Class getItemClass() {
        return Cat.class;
    }

    private void execute(String sql) throws SQLException {
        stmt.executeUpdate(sql);
    }
}
