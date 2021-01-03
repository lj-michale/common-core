package common.core.common.util;

import java.text.ParseException;
import java.util.Date;

import org.junit.Test;

import common.core.common.json.JsonDateFormat;

public class JsonDateFormatTest {
    @Test
    public void parse() throws ParseException {
        JsonDateFormat jsonDateFormat = new JsonDateFormat();
        System.out.println(jsonDateFormat.parse("1463842974911"));
        System.out.println(jsonDateFormat.parse("0"));
        System.out.println(jsonDateFormat.parse("2011-01-01"));
    }

    public static void main(String[] args) {
        while (true) {
            new Thread(new Runnable() {
                @Override
                public void run() {
//                    System.out.println("thread name="+Thread.currentThread().getName()+"    "+DateUtils.formateStandDateTime(new Date()));
                    System.out.println(DateUtils.formateStandDate("2019-08-09 16:53:23"));
//                    System.out.println(DateUtils.formateStandDate(new Date()));
//                    System.out.println(DateUtils.formateStandDateTime("2019-8-10 10:41:11"));
//                    System.out.println(DateUtils.getMondayOfWeek(new Date()));
                }
            }).start();
        }
    }
}
