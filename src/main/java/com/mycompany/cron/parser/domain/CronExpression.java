package com.mycompany.cron.parser.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CronExpression {
    private CronField minutes;
    private CronField hour;
    private CronField dayOfMonth;
    private CronField month;
    private CronField dayOfWeek;
    private CronField command;
}
