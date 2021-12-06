package com.acme;

import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.util.List;

public class LambdaAsAnonymousInnerClassUsage {

    private JdbcTemplate jdbcTemplate;


    // tag::lambdaSample[]
public List<DataRecord> getRecords(String jobId) {
  return jdbcTemplate.query("select * from DATARECORD ",
     (ResultSet rs, int rowNum) -> {
        return new DataRecord(rs.getString("job_id"), rs.getString("analysis_name"));
     });
}
    // end::lambdaSample[]

    // tag::lambda[]
public List<DataRecord> getRecordsByJobId(String jobId) {
    return jdbcTemplate.query("select * from DATARECORD where job_id=?",
            ps -> ps.setInt(0, Integer.parseInt(jobId)),
            (rs, rowNum) -> new DataRecord(rs.getString("job_id"), rs.getString("analysis_name")));
}
    // end::lambda[]

}
