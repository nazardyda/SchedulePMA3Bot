package io.project.SchedulePMABot.utils;

import io.project.SchedulePMABot.service.TelegramBot;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TextUtils {
    private static final Calendar CALENDAR = Calendar.getInstance();
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");

    private static final String DAY_TODAY = "Сьогодні - " + TelegramBot.dayOfWeek() + "\n\n";
    private static final String WEEK_PARITY = "Тиждень - " + TelegramBot.parity() +"\n\n";
    public static final String GREETING_DAY_IN_SCHEDULE =
             DAY_TODAY +
             SIMPLE_DATE_FORMAT.format(CALENDAR.getTime()) + "\n\n" +
             WEEK_PARITY +
             "Розклад:\n\n";
    private static final String GREETING_WEEK_IN_SCHEDULE =
            SIMPLE_DATE_FORMAT.format(CALENDAR.getTime()) + "\n\n" +
            WEEK_PARITY;

    private static final String GREETING_EXAM =
            DAY_TODAY+
            SIMPLE_DATE_FORMAT.format(CALENDAR.getTime())+"\n\n";

    public static final String MONDAY_ODD =
            "Пара 3 - \n\n" +
            "Пара 4 - \n\n" +
            "Пара 5 - \n\n";
    public static final String MONDAY_PAIR =
            "Пара 3 - \n\n" +
            "Пара 4 - \n\n" +
            "Пара 5 - \n\n";

    public static final String TUESDAY_ODD =
            "Пара 2 - \n\n" +
            "Пара 3 - \n\n" +
            "Пара 4 - \n\n" +
            "Пара 5 - \n\n";
    public static final String TUESDAY_PAIR =
            "Пара 2 - \n\n" +
            "Пара 3 - \n\n" +
            "Пара 4 - \n\n" +
            "Пара 5 - \n\n";

    public static final String WEDNESDAY_ODD =
            "Пара 1 - \n\n" +
            "Пара 2 - \n\n" +
            "Пара 3 - \n\n" +
            "Пара 4 - \n\n";
    public static final String WEDNESDAY_PAIR =
            "Пара 1 - \n\n" +
            "Пара 2 - \n\n" +
            "Пара 3 - \n\n" +
            "Пара 4 - \n\n";

    public static final String THURSDAY_ODD =
            "Пара 1 - \n\n" +
            "Пара 2 - \n\n" +
            "Пара 3 - \n\n";
    public static final String THURSDAY_PAIR =
            "Пара 1 - \n\n" +
            "Пара 2 - \n\n" +
            "Пара 3 - \n\n";

    public static final String FRIDAY_ODD =
            "Пара 1 - \n\n" +
            "Пара 2 - \n\n" +
            "Пара 3 - \n\n";
    public static final String FRIDAY_PAIR =
            "Пара 2 - \n\n" +
            "Пара 3 - \n\n";

    public static final String HOLIDAY = "Сьогодні вихідний";

    public static final String WEEK_ODD =  GREETING_WEEK_IN_SCHEDULE +
            "Понеділок - - - - - - - - \n\n"+
            MONDAY_ODD+
            "Вівторок - - - - - - - - - \n\n"+
            TUESDAY_ODD+
            "Середа - - - - - - - - - - \n\n"+
            WEDNESDAY_ODD+
            "Четвер - - - - - - - - - - \n\n"+
            THURSDAY_ODD+
            "П'ятниця - - - - - - - - - \n\n"+
            FRIDAY_ODD;
    public static final String WEEK_PAIR = GREETING_WEEK_IN_SCHEDULE+
            "Понеділок - - - - - - - - \n\n"+
            MONDAY_PAIR+
            "Вівторок - - - - - - - - - \n\n"+
            TUESDAY_PAIR+
            "Середа - - - - - - - - - - \n\n"+
            WEDNESDAY_PAIR+
            "Четвер - - - - - - - - - - \n\n"+
            THURSDAY_PAIR+
            "П'ятниця - - - - - - - - - \n\n"+
            FRIDAY_PAIR;

    private static final String FIRST_EXAM =
            "- - - - - - - - - - - - - - - "+"\n\n"+
            "2-12-2022"+"\n\n"+
            "Екзамен з Дифрівняння"+"\n\n"+
            "11:00"+"\n\n"+
            "ауд. 366"+"\n\n"+
            "Викладач: Бугрій Олег Миколайович"+"\n\n";
    private static final String SECOND_EXAM =
            "- - - - - - - - - - - - - - - "+"\n\n"+
            "5-12-2022"+"\n\n"+
            "Екзамен з Теорії Імовірностей"+"\n\n"+
            "10:00"+"\n\n"+
            "ауд. 367"+"\n\n"+
            "Викладач: Сеньо Петро Степанович"+"\n\n";
    private static final String THIRD_EXAM =
            "- - - - - - - - - - - - - - - "+"\n\n"+
            "9-12-2022"+"\n\n"+
            "Екзамен з Архітектура комп'ютерних систем"+"\n\n"+
            "11:00"+"\n\n"+
            "ауд. 270"+"\n\n"+
            "Викладач: Заблоцький Тарас Миронович"+"\n\n";
    private static final String FOURTH_EXAM =
            "- - - - - - - - - - - - - - - "+"\n\n"+
            "12-12-2022"+"\n\n"+
            "Екзамен з Програмування"+"\n\n"+
            "9:00"+"\n\n"+
            "ауд. 272/3"+"\n\n"+
            "Викладач: Гошко Богдан Мирославович"+"\n\n"+
            "- - - - - - - - - - - - - - - "+"\n\n";
//
//    public static SimpleDateFormat getSimpleDateFormat() {
//        return SIMPLE_DATE_FORMAT;
//    }
    public static final String EXAM_SCHEDULE=
        GREETING_EXAM+
                FIRST_EXAM+
                SECOND_EXAM+
                THIRD_EXAM+
                FOURTH_EXAM;
}
