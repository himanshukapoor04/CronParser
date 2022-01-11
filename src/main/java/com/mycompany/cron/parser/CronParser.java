package com.mycompany.cron.parser;

import com.mycompany.cron.parser.domain.CronExpression;
import com.mycompany.cron.parser.expression.ExpressionParser;

public class CronParser {

    public static void main(String[] st) {
        try {
            if (st.length > 1) {
                System.err.println("Wrong input please provide one argument only");
                throw new IllegalArgumentException("Wrong input please provide one input sting only");
            }
            ExpressionParser expressionParser = new ExpressionParser();
            CronExpression cron = expressionParser.parseExpression(st[0]);
            expressionParser.printExpression(cron);
        } catch (Exception ex) {
            System.err.println("Script didn't ran successfully! Please check previous error statements to correct it");
        }

    }
}
