package com.mycompany.cron.parser.domain;

import java.time.DateTimeException;
import java.time.temporal.ChronoField;
import java.time.temporal.ValueRange;
import lombok.Getter;

@Getter
public class CronField {

    private FieldType type;
    private String value;

    public CronField(final FieldType type, final String value) {
        this.type = type;
        this.value = value;
    }

    @Getter
    public enum FieldType {
        MINUTE(ChronoField.MINUTE_OF_HOUR, "minute        "),
        HOUR(ChronoField.HOUR_OF_DAY, "hour          "),
        DAY_OF_MONTH(ChronoField.DAY_OF_MONTH, "day of month  "),
        MONTH(ChronoField.MONTH_OF_YEAR, "month         "),
        DAY_OF_WEEK(ChronoField.DAY_OF_WEEK, "day of week   "),
        COMMAND(null, "command       ");

        private ChronoField field;
        private String value;

        FieldType(
            final ChronoField field,
            final String value
        ) {
            this.field = field;
            this.value = value;
        }

        public ValueRange getRange() {
            return this.getField().range();
        }

        public int getValidValue(int value) {
            try {
                return this.getField().checkValidIntValue(value);
            } catch (DateTimeException dateTimeException) {
                System.err.println("Wrong value : {" + value + "} provided for type {"
                    + this.name() + "}. Valid values are {" + this.field.range().getMinimum()
                    + " - " + this.field.range().getMaximum() + "}."
                );
                throw new IllegalArgumentException(dateTimeException);
            }
        }


    }

    public CronResult parse() {
        String[] entries = value.split(",");
        CronResult cronResult = new CronResult();
        for (String entry : entries) {
            int slash = entry.indexOf("/");
            CronResult toMerge;
            if (slash == -1) {
                ValueRange range = parseRange(entry, type);
                toMerge = new CronResult(range);
            } else {
                String rangeStr = entry.substring(0, slash);
                String deltaStr = entry.substring(slash + 1);
                ValueRange range = parseRange(rangeStr, type);
                if (rangeStr.indexOf('-') == -1) {
                    range = ValueRange.of(range.getMinimum(), type.getField().range().getMaximum());
                }
                int delta = Integer.parseInt(deltaStr);
                if (delta <= 0) {
                    System.err.println("Delta: " + delta + " in " + entry + " must be greater than 0");
                    throw new IllegalArgumentException("Delta must be greater than 0");
                }
                toMerge = new CronResult(range, delta);
            }
            cronResult.merge(toMerge);
        }
        return cronResult;
     }

     private ValueRange parseRange(String value, FieldType type) {
        if (value.equals("*")) {
            return type.getRange();
        } else {
            int hyphen = value.indexOf("-");
            if (hyphen == -1) {
                int result = type.getValidValue(Integer.parseInt(value));
                return ValueRange.of(result, result);
            } else {
                int min = Integer.parseInt(value, 0, hyphen, 10);
                int max = Integer.parseInt(value, hyphen + 1, value.length(), 10);
                min = type.getValidValue(min);
                max = type.getValidValue(max);
                return ValueRange.of(min, max);
            }
        }
    }
}
