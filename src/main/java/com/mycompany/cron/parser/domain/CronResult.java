package com.mycompany.cron.parser.domain;

import java.time.temporal.ValueRange;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class CronResult {
    private List<String> result;

    public CronResult() {
        this.result = new ArrayList<>();
    }

    public CronResult(ValueRange range) {
        this.result = new ArrayList<>();
        for(long l = range.getMinimum(); l <= range.getMaximum(); l++) {
            result.add(Long.toString(l));
        }
    }

    public CronResult(ValueRange range, long delta) {
        this.result = new ArrayList<>();
        for(long l = range.getMinimum(); l <= range.getMaximum(); l += delta) {
            result.add(Long.toString(l));
        }
    }

    public void merge(CronResult cronResult) {
        this.result.addAll(cronResult.getResult());
    }

    public String toString() {
        StringBuilder output = new StringBuilder();
        result.forEach(value -> output.append(value).append(" "));
        return output.toString();
    }
}
