package com.drmmx.devmax.exchangerates.util;

import android.annotation.SuppressLint;
import android.util.ArrayMap;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Utils {

    public static String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dayMonthFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dayMonthFormat.format(calendar.getTime());
    }

    //Currency name converter
    public static ArrayMap<String, String> currencyName = new ArrayMap<>();

    public static ArrayMap<String, String> getCurrencyName() {
        currencyName.put("AUD", "Австралийский доллар");
        currencyName.put("CAD", "Канадский Доллар");
        currencyName.put("CNY", "Китайский Юань");
        currencyName.put("CZK", "Чешская Крона");
        currencyName.put("DKK", "Датская Крона");
        currencyName.put("HUF", "Венгерский Форинт");
        currencyName.put("ILS", "Израильский Шекель");
        currencyName.put("JPY", "Японская Иена");
        currencyName.put("KZT", "Казахстанский Тенге");
        currencyName.put("MDL", "Молдавский Лей");
        currencyName.put("NOK", "Норвежская Крона");
        currencyName.put("SGD", "Сингапурский Доллар");
        currencyName.put("SEK", "Шведская Крона");
        currencyName.put("CHF", "Швейцарский Франк");
        currencyName.put("RUB", "Российский Рубль");
        currencyName.put("GBP", "Британский Фунт");
        currencyName.put("USD", "Доллар США");
        currencyName.put("UZS", "Узбекский Сум");
        currencyName.put("BYN", "Беларуский Рубль");
        currencyName.put("TMT", "Туркменський Манат");
        currencyName.put("AZN", "Азербаиджанский Манат");
        currencyName.put("TRY", "Турецкая Лира");
        currencyName.put("EUR", "Евро");
        currencyName.put("UAH", "Украинская Гривна");
        currencyName.put("GEL", "Грузинский Лари");
        currencyName.put("PLZ", "Польский Злотый");
        currencyName.put("LVL", "Латвийский Лат");
        currencyName.put("LTL", "Литовский Лит");
        currencyName.put("SKK", "Словацкая Крона");
        currencyName.put("BYR", "Беларуский Рубль");
        return currencyName;
    }

    //Round double to 0.001
    @SuppressLint("DefaultLocale")
    public static String roundDouble(double number) {
        return String.valueOf(String.format("%.3f", number));
    }

}
