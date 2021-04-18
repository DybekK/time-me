package com.dybek.timeme.datasource.mapper;

import com.dybek.timeme.datasource.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int arg1) throws SQLException {
        User user = new User();
        user.setId(rs.getObject("id", UUID.class));
        user.setExternalId(rs.getObject("external_id", UUID.class));
        user.setUsername(rs.getString("username"));
        return user;
    }
}
