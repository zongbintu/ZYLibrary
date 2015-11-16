package com.zongyou.library.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

/**
 * @author Altas
 * @email Altas.Tutu@gmail.com
 * @time 2015-3-20 上午11:13:05
 */
public class CursorUtil {
	private static final String TAG = "CursorUtil";

	private static class SingletonHolder {
		static CursorUtil instance = new CursorUtil();
	}

	public static CursorUtil getCursorUtil() {
		return SingletonHolder.instance;
	}

	private CursorUtil() {
		super();
	}

	/**
	 * 根据对象创建对应的sql语句
	 * 
	 * @param obj
	 *            封装后sql语句
	 * @return
	 */
	public <T> String getSQLCreate(String tableName, Class<T> t) {
		StringBuffer sb = new StringBuffer("CREATE TABLE IF NOT EXISTS ");
		try {
			T cl = t.newInstance();
			if (cl != null) {
				if (!StringUtil.hasText(tableName)) {
					String classname = cl.getClass().getName();
					sb.append(classname.substring(
							classname.lastIndexOf(".") + 1, classname.length()));
				} else {
					sb.append(tableName);
				}
				sb.append("(");
				Field[] fields = cl.getClass().getDeclaredFields();
				for (int i = 0; i < fields.length; i++) {
					Field field = fields[i];
					if (!isBasicType(field.getType()))
						continue;
					field.setAccessible(true);
					String fieldName = field.getName();
					if (fieldName.equals("serialVersionUID"))
						continue;
					String fieldtype = field.getGenericType().toString();
					sb.append(fieldName);
					// 如果类型是String
					if (fieldtype.equals("class java.lang.String")) {
						sb.append("  TEXT");
					}
					// 如果类型是Integer
					else if (fieldtype.equals("class java.lang.Integer")
							|| fieldtype.equals("int")) {
						sb.append("  INTEGER DEFAULT  0");
					}
					// 如果类型是long
					else if (fieldtype.equals("long")) {
						sb.append("  LONG DEFAULT 0");
					}// 如果类型是Integer
					else if (Boolean.TYPE.toString().equals(fieldtype)
							|| fieldtype.equals("int")) {
						sb.append("  INTEGER DEFAULT  0");
					}
					if (i < fields.length - 1) {
						sb.append(",");
					}
				}
				if(",".equals(sb.substring(sb.length()-1,sb.length())))
				sb.replace(sb.length()-1, sb.length(),"");
			}
			sb.append(");");
		} catch (Exception e) {
			Log.e(TAG, StringUtil.getMessage(e));
		}
		Log.w(TAG, sb.toString());
		return sb.toString();
	}

	/**
	 * 根据传入的对象封装
	 * 
	 * @param obj
	 *            封装后结果
	 * @return
	 */
	public ContentValues getContentValues(Object obj) {
		ContentValues values = new ContentValues();
		try {
			if (obj != null) {
				Field[] fields = obj.getClass().getDeclaredFields();
				for (Field field : fields) {// --for() begin
					if (!isBasicType(field.getType()))
						continue;
					field.setAccessible(true);
					String fieldName = field.getName();
					if (fieldName.equals("serialVersionUID"))
						continue;
					Object o = field.get(obj);

					if (o != null) {
						String value = o.toString();
						if (!value.equals("") && !value.equals("null")) {
							// 如果类型是String
							if (field.getGenericType().toString()
									.equals("class java.lang.String")) {
								values.put(fieldName, value);
							}
							// 如果类型是Integer
							else if (field.getGenericType().toString()
									.equals("class java.lang.Integer")) {
								int code = Integer.valueOf(value);
								if (code > 0) {
									values.put(fieldName, code);
								}
							}
							// 如果类型是int
							else if (field.getGenericType().toString()
									.equals("int")) {
								int code = Integer.valueOf(value);
								if (code > 0) {
									values.put(fieldName, code);
								}
							}
							// 如果类型是long
							else if (field.getGenericType().toString()
									.equals("long")) {
								values.put(fieldName, Long.valueOf(value));
							}else
							if (Boolean.TYPE.equals(field.getGenericType())) 
								values.put(fieldName, Boolean.valueOf(value)?1:0);
						}
					}
				}
			}
			return values;
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
			return values;
		}

	}

	/**
	 * 多条数据 Class<T> t
	 * 
	 * @param cursor
	 * @return
	 */
	public <T> List<T> converts(Class<T> t, Cursor cursor) {
		try {
			List<T> list = new ArrayList<T>();
			while (cursor.moveToNext()) {
				list.add(comment(t, cursor));
			}
			return list;
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
			return null;
		} finally {
			if (cursor != null)
				cursor.close();
		}
	}

	public <T> T convert(Class<T> t, Cursor cursor) {
		try {
			if (cursor.moveToNext()) {
				return comment(t, cursor);
			}
			return null;
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
			return null;
		} finally {
			if (cursor != null)
				cursor.close();
		}
	}

	public <T> T comment(Class<T> t, Cursor cursor)
			throws InstantiationException, IllegalAccessException {
		T cl = t.newInstance();
		// 不需要的自己去掉即可
		if (cl != null) {
			if (t.equals(String.class)) {
				cl = (T) cursor.getString(0);
				return cl;
			}
			// 获取实体类的所有属性，返回Field数组
			Field[] fields = cl.getClass().getDeclaredFields();
			for (Field field : fields) {
				if (!isBasicType(field.getType()))
					continue;
				field.setAccessible(true);
				String fieldName = field.getName();
				if (fieldName.equals("serialVersionUID"))
					continue;
				// 根据属性名获取坐标
				int columnIndex = cursor.getColumnIndex(fieldName);
				// 判断列明是否正确
				if (columnIndex == -1)
					continue;

				// 如果类型是String
				if (field.getGenericType().toString()
						.equals("class java.lang.String")) {
					field.set(cl, cursor.getString(columnIndex));
				}
				// 如果类型是Integer
				else if (field.getGenericType().toString()
						.equals("class java.lang.Integer")) {
					field.setInt(cl, cursor.getInt((columnIndex)));
				}// 如果类型是int
				else if (field.getGenericType().toString().equals("int")) {
					field.setInt(cl, cursor.getInt(columnIndex));
				}
				// 如果类型是long
				else if (field.getGenericType().toString().equals("long")) {
					field.setLong(cl, cursor.getLong(columnIndex));
				}
				// boolean
				else if (Boolean.TYPE.equals(field.getGenericType())) 
					field.setBoolean(cl, cursor.getInt(columnIndex)==1);
				
			}
		}
		return cl;
	}

	/**
	 * 判断类型是否存在
	 * 
	 * @param typeClass
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private boolean isBasicType(Class typeClass) {
		if (basicMap.get(typeClass) != null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 所有的类型
	 */
	@SuppressWarnings("rawtypes")
	private static Map<Class, Class> basicMap = new HashMap<Class, Class>();
	static {
		basicMap.put(int.class, int.class);
		basicMap.put(long.class, long.class);
		basicMap.put(Integer.class, Integer.class);
		basicMap.put(Long.class, Long.class);
		basicMap.put(String.class, String.class);
		basicMap.put(boolean.class, boolean.class);
		basicMap.put(Boolean.class, Boolean.class);
	}
}
