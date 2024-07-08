package com.overmighties.pubber.app;

import com.overmighties.pubber.R;

import java.util.regex.Pattern;

public class Constants {


    public static final Pattern LOWERCASE_PATTERN = Pattern.compile(
            "\\p{Ll}");
    public static final Pattern UPPERCASE_PATTERN = Pattern.compile(
            "\\p{Lu}");
    public static final Pattern DIGIT_PATTERN = Pattern.compile(
            "\\p{Nd}");

    public static final Pattern SPECIAL_CHAR_PATTERN = Pattern.compile(
            "[!\"#$%&'()*+,-./:;<=>?@[\\\\]^_`{|}~]");

    public static final String EXTRA_CITY_CONSTRAINT="com.pubber.EXTRA_CITY";
    public static final String [] STRING_RES_NAMES = new String[]{
            "app_name",
            "nazwa_aplikacji",
            "AleBrowar",
            "Amber",
            "Artezan",
            "FunkyFluids",
            "Grimbergen",
            "Grodziskie",
            "Grolsch",
            "Harnaś",
            "Kasztelan",
            "Kingpin",
            "Komoran",
            "Kozel",
            "Książęce",
            "Lech",
            "Łomża",
            "Miłosław",
            "Moczybroda",
            "NaJurze",
            "Namysłów",
            "Nepomucen",
            "Okocim",
            "Paulaner",
            "Perła",
            "PilsenrUrquell",
            "Pinta",
            "Primator",
            "Racibórz",
            "Raduga",
            "Recraft",
            "Staropramen",
            "Somersby",
            "TrzechKumpli",
            "Tyskie",
            "Witnica",
            "Wrężel",
            "Zatecky",
            "Zamiastem",
            "Żubr",
            "Żywiec",
            "wiecej",
            "Brokreacja",
            "Budweiser",
            "Cieszyn",
            "Fortuna",
            "Komes",
            "BlackRussian",
            "B52",
            "Cosmopolitan",
            "CubaLibre",
            "Daiquiri",
            "Kamikaze",
            "KrwawaMary",
            "LongIsland",
            "Margarita",
            "Martini",
            "Mojito",
            "PinaColada",
            "Sexonthebeach",
            "TequilaSunrise"

    };
    public static final String [] BREWERIES_VIEW_ID_LIST= new String[]{
            "AleBrowar",
            "Amber",
            "Artezan",
            "Brokreacja",
            "Budweiser",
            "Cieszyn",
            "Fortuna",
            "FunkyFluids",
            "Grimbergen",
            "Kingpin",
            "Komes",
            "Komoran",
            "Miłosław",
            "Moczybroda",
            "NaJurze",
            "Nepomucen",
            "Primator",
            "Raduga",
            "Recraft",
            "Zatecky",
            "Zamiastem"
    };
    public static final String [] DRINKS_VIEW_ID_LIST= new String[]{
            "BlackRussian",
            "B52",
            "Cosmopolitan",
            "CubaLibre",
            "Daiquiri",
            "Kamikaze",
            "KrwawaMary",
            "LongIsland",
            "Margarita",
            "Martini",
            "Mojito",
            "PinaColada",
            "Sexonthebeach",
            "TequilaSunrise"
    };
    public static final String [] PRICE_VIEW_ID_LIST= new String[]{
            "malo",
            "srednio",
            "duzo",
    };
    public static final int[] POP_UP_DAYS_IDS=new int[]{
                    R.id.cl_mon,
                    R.id.cl_tue,
                    R.id.cl_wen,
                    R.id.cl_thu,
                    R.id.cl_fri,
                    R.id.cl_sat,
                    R.id.cl_sun
    };
    public static final Integer[] DAY_OF_WEEK =new Integer[]
            {
                    1,2,3,4,5,6,7
            };



    public static final int[] POP_UP_DAYS_TEXT_IDS =new int[]
            {
                    R.id.tv_mon,
                    R.id.tv_tue,
                    R.id.tv_wen,
                    R.id.tv_thu,
                    R.id.tv_fri,
                    R.id.tv_sat,
                    R.id.tv_sun
            };


    public static final int[] SORT_POP_UP_IDS=new int[]
            {
                    R.id.radio_butt_relevance,
                    R.id.radio_butt_alphabetical,
                    R.id.radio_butt_rating,
                    R.id.radio_butt_distance

            };
    public static final int[] POP_UP_TIME_IDS=new int[]
            {
                    R.id.g0000,
                    R.id.g0030,
                    R.id.g0100,
                    R.id.g0130,
                    R.id.g0200,
                    R.id.g0230,
                    R.id.g0300,
                    R.id.g0330,
                    R.id.g0400,
                    R.id.g0430,
                    R.id.g0500,
                    R.id.g0530,
                    R.id.g0600,
                    R.id.g0630,
                    R.id.g0700,
                    R.id.g0730,
                    R.id.g0800,
                    R.id.g0830,
                    R.id.g0900,
                    R.id.g0930,
                    R.id.g1000,
                    R.id.g1030,
                    R.id.g1100,
                    R.id.g1130,
                    R.id.g1200,
                    R.id.g1230,
                    R.id.g1300,
                    R.id.g1330,
                    R.id.g1400,
                    R.id.g1430,
                    R.id.g1500,
                    R.id.g1530,
                    R.id.g1600,
                    R.id.g1630,
                    R.id.g1700,
                    R.id.g1730,
                    R.id.g1800,
                    R.id.g1830,
                    R.id.g1900,
                    R.id.g1930,
                    R.id.g2000,
                    R.id.g2030,
                    R.id.g2100,
                    R.id.g2130,
                    R.id.g2200,
                    R.id.g2230,
                    R.id.g2300,
                    R.id.g2330,
                    R.id.g2400,
                    R.id.tg0030,
                    R.id.tg0100,
                    R.id.tg0130,
                    R.id.tg0200,
                    R.id.tg0230,
                    R.id.tg0300,
                    R.id.gdowolne
            };
    public static final int[] POP_UP_TIME_TEXT_IDS =new int[]
            {
                    R.id.textg0000,
                    R.id.textg0030,
                    R.id.textg0100,
                    R.id.textg0130,
                    R.id.textg0200,
                    R.id.textg0230,
                    R.id.textg0300,
                    R.id.textg0330,
                    R.id.textg0400,
                    R.id.textg0430,
                    R.id.textg0500,
                    R.id.textg0530,
                    R.id.textg0600,
                    R.id.textg0630,
                    R.id.textg0700,
                    R.id.textg0730,
                    R.id.textg0800,
                    R.id.textg0830,
                    R.id.textg0900,
                    R.id.textg0930,
                    R.id.textg1000,
                    R.id.textg1030,
                    R.id.textg1100,
                    R.id.textg1130,
                    R.id.textg1200,
                    R.id.textg1230,
                    R.id.textg1300,
                    R.id.textg1330,
                    R.id.textg1400,
                    R.id.textg1430,
                    R.id.textg1500,
                    R.id.textg1530,
                    R.id.textg1600,
                    R.id.textg1630,
                    R.id.textg1700,
                    R.id.textg1730,
                    R.id.textg1800,
                    R.id.textg1830,
                    R.id.textg1900,
                    R.id.textg1930,
                    R.id.textg2000,
                    R.id.textg2030,
                    R.id.textg2100,
                    R.id.textg2130,
                    R.id.textg2200,
                    R.id.textg2230,
                    R.id.textg2300,
                    R.id.textg2330,
                    R.id.textg2400,
                    R.id.texttg0030,
                    R.id.texttg0100,
                    R.id.texttg0130,
                    R.id.texttg0200,
                    R.id.texttg0230,
                    R.id.texttg0300,
                    R.id.textgdowolne
            };
    public static final int[] POP_UP_TIME_TEXT_Od_IDS =new int[]
            {
                    R.id.Odg0000,
                    R.id.Odg0030,
                    R.id.Odg0100,
                    R.id.Odg0130,
                    R.id.Odg0200,
                    R.id.Odg0230,
                    R.id.Odg0300,
                    R.id.Odg0330,
                    R.id.Odg0400,
                    R.id.Odg0430,
                    R.id.Odg0500,
                    R.id.Odg0530,
                    R.id.Odg0600,
                    R.id.Odg0630,
                    R.id.Odg0700,
                    R.id.Odg0730,
                    R.id.Odg0800,
                    R.id.Odg0830,
                    R.id.Odg0900,
                    R.id.Odg0930,
                    R.id.Odg1000,
                    R.id.Odg1030,
                    R.id.Odg1100,
                    R.id.Odg1130,
                    R.id.Odg1200,
                    R.id.Odg1230,
                    R.id.Odg1300,
                    R.id.Odg1330,
                    R.id.Odg1400,
                    R.id.Odg1430,
                    R.id.Odg1500,
                    R.id.Odg1530,
                    R.id.Odg1600,
                    R.id.Odg1630,
                    R.id.Odg1700,
                    R.id.Odg1730,
                    R.id.Odg1800,
                    R.id.Odg1830,
                    R.id.Odg1900,
                    R.id.Odg1930,
                    R.id.Odg2000,
                    R.id.Odg2030,
                    R.id.Odg2100,
                    R.id.Odg2130,
                    R.id.Odg2200,
                    R.id.Odg2230,
                    R.id.Odg2300,
                    R.id.Odg2330,
                    R.id.Odg2400,
                    R.id.Odtg0030,
                    R.id.Odtg0100,
                    R.id.Odtg0130,
                    R.id.Odtg0200,
                    R.id.Odtg0230,
                    R.id.Odtg0300
            };
    public static final int[] POP_UP_TIME_TEXT_NEXT_DAYS=new int[]
            {
                    R.id.TVt0030,
                    R.id.TVt0100,
                    R.id.TVt0130,
                    R.id.TVt0200,
                    R.id.TVt0230,
                    R.id.TVt0300
            };

    public static final int[] TAB_OVERVIEW_TEXTVIEW_DAY_IDS=new int[]
            {
                    R.id.OvTvDay1,
                    R.id.OvTvDay2,
                    R.id.OvTvDay3,
                    R.id.OvTvDay4,
                    R.id.OvTvDay5,
                    R.id.OvTvDay6,
                    R.id.OvTvDay7
            };
    public static final int[] TAB_OVERVIEW_TEXTVIEW_DAYTIME_IDS=new int[]
            {
                    R.id.OvTvTime1,
                    R.id.OvTvTime2,
                    R.id.OvTvTime3,
                    R.id.OvTvTime4,
                    R.id.OvTvTime5,
                    R.id.OvTvTime6,
                    R.id.OvTvTime7
            };

    public static final int[] SIGN_IN_TEXTFIELDS_IDS=new int[]{
            R.id.edit_field_email_sing_in,
            R.id.edit_field_password_sing_in,
    };

    public static final int[] SIGN_UP_TEXTFIELDS_IDS=new int[]{
                    R.id.edit_field_email_sing_up,
                    R.id.edit_filed_password_sing_up,
                    R.id.edit_field_confirm_password_sign_up
            };
    public static final int[] SIGN_UP_INPUTLAYOUTS_IDS=new int[]{
            R.id.textInputLayoutEmail,
            R.id.textInputLayoutPassword,
            R.id.textInputLayoutConfirmPassword
    };
    /*
    public static final int[] TAB_OVERVIEW_TEXTVIEW_OUR_RATING_IDS = new int[]
            {
                    R.id.OvTVOurRGeneral,
                    R.id.OvTVOurRPrice,
                    R.id.OvTVOurRService,
                    R.id.OvTVOurRVibe,
                    R.id.OvTVGeneral,
                    R.id.OvTVVibe,
                    R.id.OvTVPrice,
                    R.id.OvTVService,
                    R.id.OvClOurRGeneral,
                    //R.id.OvClOurRPrice,
                    //R.id.OvClOurRVibe,
                    //R.id.OvClOurRService,
                    R.id.OvTVOurReview,
                    R.id.OvClOurRating

    };

*/
}
