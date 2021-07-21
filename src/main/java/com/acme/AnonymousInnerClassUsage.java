package com.acme;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

public class AnonymousInnerClassUsage {

    private JdbcTemplate jdbcTemplate;

    public List<DataRecord> getDataRecordsByJobId(String jobId){
        return jdbcTemplate.query("select * from DATARECORD where job_id=?", new PreparedStatementSetter(){

            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setInt(0, Integer.parseInt(jobId));
            }
            
        }, new RowMapper<DataRecord>(){

            @Override
            public DataRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new DataRecord(rs.getString("job_id"), rs.getString("analysis_name"));
            }
            
        });
    }
    
}
