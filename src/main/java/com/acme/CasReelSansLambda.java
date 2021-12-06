package com.acme;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CasReelSansLambda {

    // tag::casReelAAmeliorerAvant[]
public Map<String, List<Record>> getMap(List<Record> records, boolean sortByAnalysis) {
    Map<String, List<Record>> groupedRecords = new HashMap<>();//<1>
    for (Record record : records) { //<2>
        String keyword = sortByAnalysis ? record.getAnalysisName() : record.getJobId(); //<3>
        if (!groupedRecords.containsKey(keyword)) { //<4>
            groupedRecords.put(keyword, new ArrayList<>()); //<5>
        }
        groupedRecords.get(keyword).add(record); //<6>
    }
    return groupedRecords;
}
    // end::casReelAAmeliorerAvant[]
}
