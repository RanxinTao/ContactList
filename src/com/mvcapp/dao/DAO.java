package com.mvcapp.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.mvcapp.db.JDBCUtils;

public class DAO<T> {
	
	private QueryRunner queryRunner = new QueryRunner();
	private Class<T> clazz;
	
	public DAO() {
		Type superClass = getClass().getGenericSuperclass();
		
		if(superClass instanceof ParameterizedType) {
			ParameterizedType parameterizedType = (ParameterizedType)superClass;
			Type[] typeArgs = parameterizedType.getActualTypeArguments();
			if(typeArgs != null && typeArgs.length > 0) {
				if(typeArgs[0] instanceof Class) {
					clazz = (Class<T>) typeArgs[0];
				}
			}
		}
	}
	
	/**
	 * @param sql
	 * @param args
	 * @return a field of a T instance or aggregate data, e.g., total count of records
	 */
	public <E> E getValue(String sql, Object ... args) {
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnetion();
			return (E) queryRunner.query(conn, sql, new ScalarHandler(), args);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.closeConnection(conn);
		}
		return null;
	}
	
	/**
	 * @param sql
	 * @param args
	 * @return a list of T instances
	 */
	public List<T> getList(String sql, Object ... args) {
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnetion();
			return queryRunner.query(conn, sql, new BeanListHandler<>(clazz), args);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.closeConnection(conn);
		}
		return null;
	}
	
	/**
	 * @param sql
	 * @param args
	 * @return an instance of T
	 */
	public T get(String sql, Object ... args) {
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnetion();
			return queryRunner.query(conn, sql, new BeanHandler<>(clazz), args);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.closeConnection(conn);
		}
		return null;
	}
	
	/** 
	 * implements INSERT, DELETE, UPDATE operations in a relational database
	 * @param sql
	 * @param args
	 */
	public void update(String sql, Object ... args) {
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnetion();
			queryRunner.update(conn, sql, args);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.closeConnection(conn);
		}
	}
	
}
