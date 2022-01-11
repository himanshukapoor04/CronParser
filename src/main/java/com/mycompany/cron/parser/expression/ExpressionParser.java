package com.mycompany.cron.parser.expression;

import com.mycompany.cron.parser.domain.CronExpression;
import com.mycompany.cron.parser.domain.CronField;

public class ExpressionParser {

    public CronExpression parseExpression(String expression) {
        String[] expressions = expression.split(" ");
        if (expressions.length != 6) {
            System.err.println("Please enter a correct format with five cron expression fields plus a command");
            throw new IllegalArgumentException();
        }
        var cronExpression = CronExpression.builder()
            .minutes(new CronField(CronField.FieldType.MINUTE, expressions[0]))
            .hour(new CronField(CronField.FieldType.HOUR, expressions[1]))
            .dayOfMonth(new CronField(CronField.FieldType.DAY_OF_MONTH, expressions[2]))
            .month(new CronField(CronField.FieldType.MONTH, expressions[3]))
            .dayOfWeek(new CronField(CronField.FieldType.DAY_OF_WEEK, expressions[4]))
            .command(new CronField(CronField.FieldType.COMMAND, expressions[5]))
            .build();
        return cronExpression;
    }

    public void printExpression(CronExpression cronExpression) {
        System.out.println(cronExpression.getMinutes().getType().getValue() + " " + cronExpression.getMinutes().parse().toString());
        System.out.println(cronExpression.getHour().getType().getValue() + " " + cronExpression.getHour().parse().toString());
        System.out.println(cronExpression.getDayOfMonth().getType().getValue() + " " + cronExpression.getDayOfMonth().parse().toString());
        System.out.println(cronExpression.getMonth().getType().getValue() + " " + cronExpression.getMonth().parse().toString());
        System.out.println(cronExpression.getDayOfWeek().getType().getValue() + " " + cronExpression.getDayOfWeek().parse().toString());
        System.out.println(cronExpression.getCommand().getType().getValue() + " " + cronExpression.getCommand().getValue());
    }
}
