package com.acme;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MapUsage {

    public Map<String, List<DataRecord>> legacy(List<DataRecord> dataRecordList, String key){
        Map<String, List<DataRecord>> sortedDataRecord = new HashMap<>();
        for (DataRecord record : dataRecordList) {
            String keyword = ((key.equals("a_name")) ? record.getAnalysisName() : record.getJobId());
            if (!sortedDataRecord.containsKey(keyword)) {
                sortedDataRecord.put(keyword, new ArrayList<>());
            }
            sortedDataRecord.get(keyword).add(record);
        }
        return sortedDataRecord;
    }


    public Map<String, List<DataRecord>> mapWithAnonymousInnerClass(List<DataRecord> dataRecordList, String key){
        Map<String, List<DataRecord>> sortedDataRecord = new HashMap<>();
        for (DataRecord record : dataRecordList) {
            String keyword = ((key.equals("a_name")) ? record.getAnalysisName() : record.getJobId());
            sortedDataRecord.computeIfAbsent(keyword, new Function<String,List<DataRecord>>(){

                @Override
                public List<DataRecord> apply(String key) {
                    return new ArrayList<>();
                }  
            });
            sortedDataRecord.get(keyword).add(record);
        }
        return sortedDataRecord;
    }

    public Map<String, List<DataRecord>> mapWithLambda(List<DataRecord> dataRecordList, String value){
        Map<String, List<DataRecord>> sortedDataRecord = new HashMap<>();
        for (DataRecord record : dataRecordList) {
            String keyword = ((value.equals("a_name")) ? record.getAnalysisName() : record.getJobId());
            sortedDataRecord.computeIfAbsent(keyword, key -> {
                return new ArrayList<>();
                }  
            );
            sortedDataRecord.get(keyword).add(record);
        }
        return sortedDataRecord;
    }

    public Map<String, List<DataRecord>> mapWithLambdaShort(List<DataRecord> dataRecordList, String value){
        Map<String, List<DataRecord>> sortedDataRecord = new HashMap<>();
        for (DataRecord record : dataRecordList) {
            String keyword = ((value.equals("a_name")) ? record.getAnalysisName() : record.getJobId());
            sortedDataRecord.computeIfAbsent(keyword, key -> new ArrayList<>());
            sortedDataRecord.get(keyword).add(record);
        }
        return sortedDataRecord;
    }

    public Map<String, List<DataRecord>> streamWithLambda(List<DataRecord> dataRecordList, String value){
        return dataRecordList.stream().collect(Collectors.groupingBy(record -> {
            return value.equals("a_name") ? record.getAnalysisName() : record.getJobId();
        }));
    }

    public Map<String, List<DataRecord>> streamBetter(List<DataRecord> dataRecordList, String key){
        Function<DataRecord, String> classifier = key.equals("a_name") ? DataRecord::getAnalysisName : DataRecord::getJobId;
        return dataRecordList.stream().collect(Collectors.groupingBy(classifier));
    }


}