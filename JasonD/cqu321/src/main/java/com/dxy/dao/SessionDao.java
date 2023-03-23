package com.dxy.dao;

import com.dxy.pojo.Session;
import com.dxy.utils.SqliteUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author: JasonD
 * @date: 2023/3/16 17:59
 * @Description:
 */
public class SessionDao {
    PreparedStatement statement;

    //增
    public int addSession(Session session) {
        try {
            statement = SqliteUtils.getConnection().prepareStatement("insert into session values (?, ?, ?)");
            Object[] params = {null, session.getYear(), session.getIs_autumn()};
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            int add = statement.executeUpdate();
            return add;
        } catch (Exception e) {
            return 0;
        }
    }

    //删
    public int deleteSessionById(Integer id) {
        try {
            statement = SqliteUtils.getConnection().prepareStatement("delete from session where id = ?");
            statement.setObject(1, id);
            int delete = statement.executeUpdate();
            return delete;
        } catch (Exception e) {
            return 0;
        }
    }

    //改
    public int updateSession(Session session) {
        try {
            statement = SqliteUtils.getConnection().prepareStatement("update session set year = ?, is_autumn = ? where id = ?");
            Object[] params = {session.getYear(), session.getIs_autumn(), session.getId()};
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            int update = statement.executeUpdate();
            return update;
        } catch (Exception e) {
            return 0;
        }
    }

    //查单个session
    public Session querySession(Integer id) throws Exception {
        statement = SqliteUtils.getConnection().prepareStatement("select * from session where id = ?");
        statement.setObject(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet != null) {
            Integer year = resultSet.getInt(2);
            Boolean is_autumn = resultSet.getBoolean(3);
            Session session = new Session(id, year, is_autumn);
            return session;
        }
        return null;
    }

    public Integer querySessionId(Integer year, Boolean is_autumn) throws Exception {
        statement = SqliteUtils.getConnection().prepareStatement("select * from session where year = ? and is_autumn = ?");
        statement.setObject(1, year);
        statement.setObject(2, is_autumn);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet != null) {
            Integer id = resultSet.getInt(1);
            return id;
        }
        return null;
    }

    public void close() throws Exception {
        statement.close();
    }
}
