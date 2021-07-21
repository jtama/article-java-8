package com.acme;

public class DataRecord {

    private String jobId;

    private String analysisName;

    public DataRecord(String jobId, String analysisName) {
        this.jobId = jobId;
        this.analysisName = analysisName;
    }

    public String getJobId() {
        return jobId;
    }

    public String getAnalysisName() {
        return analysisName;
    }
    
}
