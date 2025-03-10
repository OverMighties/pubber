package com.overmighties.pubber.feature.search.data;

public class FilterConstants {
    public static final String CHEAP_PRICE ="$";
    public static final String AVERAGE_PRICE ="$$";
    public static final String EXPENSIVE_PRICE ="$$$";
    public static final String NONE_PRICE ="-$";
    public static final float BASE_BOTTOM_RATING=0.0f;
    public static final float BASE_UPPER_RATING=5.0f;
    public static final float BASE_DISTANCE=-1.0f;
    //Blocking default constructor
    private FilterConstants(){
        throw new AssertionError();
    }
}
