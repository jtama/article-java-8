package com.acme;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

public class LambdaAsAnonymousInnerClassUsage {

    private JdbcTemplate jdbcTemplate;

    // tag::lambda[]
    public List<DataRecord> getRecordsByJobId(String jobId) {
        return jdbcTemplate.query("select * from DATARECORD where job_id=?",
                ps -> ps.setInt(0, Integer.parseInt(jobId)),
                (rs, rowNum) -> new DataRecord(rs.getString("job_id"), rs.getString("analysis_name")));
    }
    // end::lambda[]

}
