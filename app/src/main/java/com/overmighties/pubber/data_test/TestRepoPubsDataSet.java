package com.overmighties.pubber.data_test;

import com.overmighties.pubber.R;
import com.overmighties.pubber.core.network.model.DrinkDto;
import com.overmighties.pubber.core.network.model.OpeningHoursDto;
import com.overmighties.pubber.core.network.model.PhotoDto;
import com.overmighties.pubber.core.network.model.PubDto;
import com.overmighties.pubber.core.network.model.RatingsDto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

public class TestRepoPubsDataSet {
    @Getter
    private List<PubDto> dataSet;

    private final static TestRepoPubsDataSet INSTANCE=new TestRepoPubsDataSet();

    private static ArrayList<DrinkDto> drinksDataSet1;
    private static ArrayList<DrinkDto> drinksDataSet2;
    private static ArrayList<DrinkDto> drinksDataSet3;
    private static ArrayList<DrinkDto> drinksDataSet4;
    private static ArrayList<DrinkDto> drinksDataSet5;
    private static ArrayList<DrinkDto> drinksDataSet6;


    private static ArrayList<OpeningHoursDto> openingHoursDataSet1;
    private static ArrayList<OpeningHoursDto> openingHoursDataSet2;
    private static ArrayList<OpeningHoursDto> openingHoursDataSet3;
    private static ArrayList<OpeningHoursDto>  openingHoursDataSet4;
    private static ArrayList<OpeningHoursDto>openingHoursDataSet5;
    private static ArrayList<OpeningHoursDto> openingHoursDataSet6;

    private static ArrayList<PhotoDto> photosDataSet1;
    private static ArrayList<PhotoDto> photosDataSet2;
    private static ArrayList<PhotoDto> photosDataSet3;
    private static ArrayList<PhotoDto> photosDataSet4;
    private static ArrayList<PhotoDto> photosDataSet5;
    private static ArrayList<PhotoDto> photosDataSet6;


    private static final String NOTHING="";
    private static final String LOREM_IPSUM_20="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur tempor, lacus vel commodo euismod, lectus purus convallis leo, in dictum.";
    private static final String LOREM_IPSUM_50="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris lorem erat, malesuada volutpat semper vel, feugiat non nisl. Fusce ac tortor vel enim pellentesque tristique eget eleifend nibh. Sed accumsan elit eget laoreet semper. Phasellus in lectus lorem. Sed malesuada in turpis ut pretium. Nullam enim sem, semper et dolor.";
    private static final String LOREM_IPSUM_100="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis vitae ante nec diam volutpat maximus sed quis ex. Donec nec velit tincidunt, aliquam nunc nec, commodo mauris. Sed sit amet consequat nisl. Morbi non magna eu lorem posuere auctor. Vestibulum dictum euismod scelerisque. Nunc posuere tempor nisl vel rutrum. Vestibulum egestas,"
            +"\n"+
            " augue in viverra elementum, tellus mi semper nunc, at accumsan est nisi ac enim. Nullam dictum molestie quam sodales mattis. Donec ultrices dapibus arcu vel gravida. Etiam sit amet ultrices sem, ac tincidunt felis. In feugiat ultricies molestie. Morbi sollicitudin at eros sed vulputate. Duis sed lectus quis est. ";
    private static final String LOREM_IPSUM_200= "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin luctus libero sed felis interdum dignissim. Aenean posuere, nibh ac ullamcorper pretium, ipsum purus suscipit est, quis dictum nisi leo eu dolor. Morbi felis enim, sagittis sed dolor in, facilisis fringilla nisl. Nullam eget ultricies nisl. Fusce posuere sapien leo, at aliquam nisl fermentum quis. Nullam congue ligula in suscipit convallis. Curabitur enim elit, viverra et augue non, feugiat dignissim nulla. Aenean consectetur purus id dictum semper. Mauris suscipit id libero quis aliquam. Vestibulum dignissim mattis sem a ullamcorper.\n" +
            "\n" +
            "Duis ornare mi non quam egestas, et finibus libero tempus. Cras vitae fermentum mi. Suspendisse id vehicula magna, a maximus mi. Morbi ullamcorper interdum est at lobortis. In condimentum, mauris in commodo condimentum, lorem libero finibus sem, et gravida nulla nulla porta eros. Morbi et suscipit mi. Nunc aliquam urna at neque ultricies condimentum. Curabitur purus urna, elementum ultricies posuere a, posuere at lorem. In sed lectus ligula. Vivamus bibendum lectus ullamcorper libero vulputate, et tempor metus finibus. Proin pulvinar interdum congue.\n" +
            "\n" +
            "Duis quis magna a urna imperdiet maximus id et dui. Quisque eleifend tortor vel ex lobortis, tincidunt aliquet erat facilisis. Proin consequat sit amet mauris sit amet pharetra. In sed.";

    public TestRepoPubsDataSet()
    {
        init();
    }
    public static TestRepoPubsDataSet getInstance()
    {
        return INSTANCE;
    }

    private void init()
    {
        initDataSet1();
        initDataSet2();
        initDataSet3();
        initDataSet4();
        initDataSet5();
        initDataSet6();


        dataSet=new ArrayList<>();

        dataSet.add(new PubDto(1L,	"Pewex Pub","Rynek 18, 35-064 Rzeszów, Poland","ChIJS3sKMwL7PEcRurd9BXet-fw","Rzeszów","17 871 80 77",	"https://www.facebook.com/PewexPub/","",NOTHING,null,null,
                new RatingsDto(4.0f,588,null,null,null,null,null,null,null,null,2),
              openingHoursDataSet1,drinksDataSet1,photosDataSet1,null));
        dataSet.add(new PubDto(2L,	"Rambla Cocktail&Music Pub","Jana Matejki 16, 35-064 Rzeszów, Poland","ChIJF-VPDAL7PEcRE5pnPMUZVAw","Rzeszów","535 041 290",	"https://www.facebook.com/PubRambla","",LOREM_IPSUM_20,true,true,
                new RatingsDto(4.2f,514,null,null,null,null,null,null,null,null,3),
                openingHoursDataSet2,drinksDataSet2,photosDataSet2,null));
        dataSet.add(new PubDto(3L,	"Pub K20","Mikołaja Kopernika 4, 35-002 Rzeszów, Poland","ChIJ79jBNQL7PEcRAJ0TSKs0Bic","Rzeszów","17 225 31 06",	"http://www.fb.com/K20PUB","",LOREM_IPSUM_50,true,true,
                new RatingsDto(4.6f,956,4.7f,156,4.3f,343,4.24f,343,4.0f,5.0f,4),
                openingHoursDataSet3,drinksDataSet3,photosDataSet3,null));
        dataSet.add(new PubDto(4L,	"Corner Pub Mała Graciarnia Rzeszów","Przesmyk 2, 35-065 Rzeszów, Poland","ChIJGcTVBwL7PEcRTflMy_gvdhI","Rzeszów","519 159 156",	"https://www.facebook.com/PewexPub/","",LOREM_IPSUM_20,true,false,
                new RatingsDto(4.0f,588,null,null,null,null,null,null,3.3f,4.1f,2),
                openingHoursDataSet4,drinksDataSet4,photosDataSet4,null));
        dataSet.add(new PubDto(5L,		"Jameson Pub","Stanisława Moniuszki 4, 35-015 Rzeszów, Poland","ChIJlxM_5QP7PEcRuKMUGawHRgs","Rzeszów","17 871 80 77",	"https://www.facebook.com/PewexPub/","",LOREM_IPSUM_100,null,true,
                new RatingsDto(4.0f,588,null,null,null,null,null,null,2.3f,4.0f,4),
                openingHoursDataSet5,drinksDataSet5,photosDataSet5,null));
        dataSet.add(new PubDto(6L,	 "Pub Underground","Jana Matejki 10, 35-001 Rzeszów, Poland","ChIJS3sKMwL7PEcRurd9BXet-fw",null,null,	"https://www.facebook.com/PewexPub/","",LOREM_IPSUM_200,true,null,
                new RatingsDto(4.0f,588,null,null,null,null,null,null,null,null,null),
                openingHoursDataSet6,drinksDataSet6,photosDataSet6,null));
    }
    private void initDataSet1()
    {
        drinksDataSet1=new ArrayList<>();
        openingHoursDataSet1=new ArrayList<>();
        photosDataSet1=new ArrayList<>();

        drinksDataSet1.add(new DrinkDto("AleBrowar","Beer"));
        drinksDataSet1.add(new DrinkDto("Amber","Beer"));
        drinksDataSet1.add(new DrinkDto("Sexonthebeach","Cocktail"));

        openingHoursDataSet1.add(new OpeningHoursDto("MONDAY","00:00","02:00"));
        openingHoursDataSet1.add(new OpeningHoursDto("TUESDAY","00:00","02:00"));
        openingHoursDataSet1.add(new OpeningHoursDto("WEDNESDAY","16:00","02:00"));
        openingHoursDataSet1.add(new OpeningHoursDto("THURSDAY","16:00","02:00"));
        openingHoursDataSet1.add(new OpeningHoursDto("FRIDAY","16:00","03:00"));
        openingHoursDataSet1.add(new OpeningHoursDto("SATURDAY","16:00","00:00"));
        openingHoursDataSet1.add(new OpeningHoursDto("SUNDAY","00:00","02:00"));



    }
    private void initDataSet2()
    {
        drinksDataSet2=new ArrayList<>();
        openingHoursDataSet2=new ArrayList<>();
        photosDataSet2=new ArrayList<>();

        drinksDataSet1.add(new DrinkDto("AleBrowar","Beer"));
        drinksDataSet1.add(new DrinkDto("Amber","Beer"));
        drinksDataSet1.add(new DrinkDto("Artezanr","Beer"));
        drinksDataSet1.add(new DrinkDto("Sexonthebeach","Cocktail"));

        openingHoursDataSet2.add(new OpeningHoursDto("MONDAY","00:00","00:00"));
        openingHoursDataSet2.add(new OpeningHoursDto("WEDNESDAY","00:00","00:00"));
        openingHoursDataSet2.add(new OpeningHoursDto("THURSDAY","00:00","00:00"));
        openingHoursDataSet2.add(new OpeningHoursDto("FRIDAY","00:00","00:00"));
        openingHoursDataSet2.add(new OpeningHoursDto("SATURDAY","00:00","00:00"));
        openingHoursDataSet2.add(new OpeningHoursDto("SUNDAY","00:00","00:00"));
        openingHoursDataSet2.add(new OpeningHoursDto("TUESDAY","00:00","00:00"));
    }
    private void initDataSet3()
    {
        drinksDataSet3=new ArrayList<>();
        openingHoursDataSet3=new ArrayList<>();
        photosDataSet3=new ArrayList<>();

        drinksDataSet1.add(new DrinkDto("AleBrowar","Beer"));
        drinksDataSet1.add(new DrinkDto("FunkyFluids","Cocktail"));
        drinksDataSet1.add(new DrinkDto("Sexonthebeach","Cocktail"));

        openingHoursDataSet3.add(new OpeningHoursDto("MONDAY","18:00","01:00"));
        openingHoursDataSet3.add(new OpeningHoursDto("TUESDAY","18:00","01:00"));
        openingHoursDataSet3.add(new OpeningHoursDto("WEDNESDAY","18:00","01:00"));
        openingHoursDataSet3.add(new OpeningHoursDto("THURSDAY","18:00","01:00"));
        openingHoursDataSet3.add(new OpeningHoursDto("FRIDAY","18:00","04:00"));
        openingHoursDataSet3.add(new OpeningHoursDto("SATURDAY","18:00","04:00"));
        openingHoursDataSet3.add(new OpeningHoursDto("SUNDAY","15:00","01:00"));
    }
    private void initDataSet4()
    {
        drinksDataSet4=new ArrayList<>();
        openingHoursDataSet4=new ArrayList<>();
        photosDataSet4=new ArrayList<>();

        drinksDataSet1.add(new DrinkDto("Komes","Beer"));
        drinksDataSet1.add(new DrinkDto("Amber","Beer"));
        drinksDataSet1.add(new DrinkDto("Martini","Cocktail"));

        openingHoursDataSet4.add(new OpeningHoursDto("MONDAY","18:00","01:00"));
        openingHoursDataSet4.add(new OpeningHoursDto("TUESDAY","00:00","02:00"));
        openingHoursDataSet4.add(new OpeningHoursDto("WEDNESDAY","16:00","02:00"));
        openingHoursDataSet4.add(new OpeningHoursDto("THURSDAY","16:00","02:00"));
        openingHoursDataSet4.add(new OpeningHoursDto("FRIDAY","16:00","03:00"));
        openingHoursDataSet4.add(new OpeningHoursDto("SATURDAY","16:00","00:00"));
        openingHoursDataSet4.add(new OpeningHoursDto("SUNDAY","15:00","01:00"));
    }
    private void initDataSet5()
    {
        drinksDataSet5=new ArrayList<>();
        openingHoursDataSet5=new ArrayList<>();
        photosDataSet5=new ArrayList<>();

        drinksDataSet1.add(new DrinkDto("Recraft","Beer"));
        drinksDataSet1.add(new DrinkDto("Amber","Beer"));
        drinksDataSet1.add(new DrinkDto("Martini","Cocktail"));

        openingHoursDataSet5.add(new OpeningHoursDto("MONDAY","18:00","01:00"));
        openingHoursDataSet5.add(new OpeningHoursDto("TUESDAY","00:00","02:00"));
        openingHoursDataSet5.add(new OpeningHoursDto("WEDNESDAY","16:00","02:00"));
        openingHoursDataSet5.add(new OpeningHoursDto("THURSDAY","16:00","02:00"));
        openingHoursDataSet5.add(new OpeningHoursDto("FRIDAY","16:00","03:00"));
        openingHoursDataSet5.add(new OpeningHoursDto("SATURDAY","16:00","00:00"));
        openingHoursDataSet5.add(new OpeningHoursDto("SUNDAY","15:00","01:00"));
    }
    private void initDataSet6()
    {
        drinksDataSet6=null;
        openingHoursDataSet6=null;
        photosDataSet6=null;
    }
}
