package com.acme;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

public class NamedFunctionAsAnonymousInnerClassUsage {

    private JdbcTemplate jdbcTemplate;

     // tag::named-functions[]
     private RowMapper<DataRecord> mapper = (rs, rowNum) -> new DataRecord(rs.getString("job_id"), rs.getString("analysis_name"));

     public List<DataRecord> getRecordsByJobId(String jobId){
         return jdbcTemplate.query("select * from DATARECORD where job_id=?", getJobIDSetter(jobId), mapper);
     }
 
     private PreparedStatementSetter getJobIDSetter(String jobId) {
         return ps -> ps.setInt(0, Integer.parseInt(jobId));
     }
     // end::named-functions[]
    
}
