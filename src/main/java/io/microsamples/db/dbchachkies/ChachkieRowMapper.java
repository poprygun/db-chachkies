package io.microsamples.db.dbchachkies;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ChachkieRowMapper implements RowMapper<Chachkie> {
    @Override
    public Chachkie mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Chachkie(
                resultSet.getInt("ID")
                , resultSet.getString("CNAME")
                , resultSet.getDate("UPD_DATE")
        );
    }
}
