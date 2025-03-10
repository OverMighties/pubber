package com.overmighties.pubber.core.data_test;

import com.overmighties.pubber.core.network.model.BeerDto;
import com.overmighties.pubber.core.network.model.DrinkDto;
import com.overmighties.pubber.core.network.model.OpeningHoursDto;
import com.overmighties.pubber.core.network.model.PhotoDto;
import com.overmighties.pubber.core.network.model.PubDto;
import com.overmighties.pubber.core.network.model.RatingsDto;
import com.overmighties.pubber.core.network.model.DrinkStyleDto;
import com.overmighties.pubber.core.network.model.TagDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import lombok.Getter;

@Getter
public class TestRepoPubsDataSet {
    private List<PubDto> dataSet;

    private final static TestRepoPubsDataSet INSTANCE=new TestRepoPubsDataSet();

    private ArrayList<DrinkDto> drinksDataSet1;
    private ArrayList<DrinkDto> drinksDataSet2;
    private ArrayList<DrinkDto> drinksDataSet3;
    private ArrayList<DrinkDto> drinksDataSet4;
    private ArrayList<DrinkDto> drinksDataSet5;
    private ArrayList<DrinkDto> drinksDataSet6;


    private ArrayList<OpeningHoursDto> openingHoursDataSet1;
    private ArrayList<OpeningHoursDto> openingHoursDataSet2;
    private ArrayList<OpeningHoursDto> openingHoursDataSet3;
    private ArrayList<OpeningHoursDto> openingHoursDataSet4;
    private ArrayList<OpeningHoursDto> openingHoursDataSet5;
    private ArrayList<OpeningHoursDto> openingHoursDataSet6;

    private ArrayList<PhotoDto> photosDataSet1;
    private ArrayList<PhotoDto> photosDataSet2;
    private ArrayList<PhotoDto> photosDataSet3;
    private ArrayList<PhotoDto> photosDataSet4;
    private ArrayList<PhotoDto> photosDataSet5;
    private ArrayList<PhotoDto> photosDataSet6;

    private ArrayList<TagDto> tagsDataSet1;
    private ArrayList<TagDto> tagsDataSet2;
    private ArrayList<TagDto> tagsDataSet3;
    private ArrayList<TagDto> tagsDataSet4;
    private ArrayList<TagDto> tagsDataSet5;
    private ArrayList<TagDto> tagsDataSet6;


    public static final String NOTHING="";
    public static final String LOREM_IPSUM_20="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur tempor, lacus vel commodo euismod, lectus purus convallis leo, in dictum.";
    public static final String LOREM_IPSUM_50="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris lorem erat, malesuada volutpat semper vel, feugiat non nisl. Fusce ac tortor vel enim pellentesque tristique eget eleifend nibh. Sed accumsan elit eget laoreet semper. Phasellus in lectus lorem. Sed malesuada in turpis ut pretium. Nullam enim sem, semper et dolor.";
    public static final String LOREM_IPSUM_100="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis vitae ante nec diam volutpat maximus sed quis ex. Donec nec velit tincidunt, aliquam nunc nec, commodo mauris. Sed sit amet consequat nisl. Morbi non magna eu lorem posuere auctor. Vestibulum dictum euismod scelerisque. Nunc posuere tempor nisl vel rutrum. Vestibulum egestas,"
            +"\n"+
            " augue in viverra elementum, tellus mi semper nunc, at accumsan est nisi ac enim. Nullam dictum molestie quam sodales mattis. Donec ultrices dapibus arcu vel gravida. Etiam sit amet ultrices sem, ac tincidunt felis. In feugiat ultricies molestie. Morbi sollicitudin at eros sed vulputate. Duis sed lectus quis est. ";
    public static final String LOREM_IPSUM_200= "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin luctus libero sed felis interdum dignissim. Aenean posuere, nibh ac ullamcorper pretium, ipsum purus suscipit est, quis dictum nisi leo eu dolor. Morbi felis enim, sagittis sed dolor in, facilisis fringilla nisl. Nullam eget ultricies nisl. Fusce posuere sapien leo, at aliquam nisl fermentum quis. Nullam congue ligula in suscipit convallis. Curabitur enim elit, viverra et augue non, feugiat dignissim nulla. Aenean consectetur purus id dictum semper. Mauris suscipit id libero quis aliquam. Vestibulum dignissim mattis sem a ullamcorper.\n" +
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


        dataSet = new ArrayList<>();

        dataSet.add(PubDto.builder()
                .pubId(1L)
                .name("Pewex Pub")
                .address("Rynek 18, 35-064 Rzeszów, Poland")
                .placeId("ChIJS3sKMwL7PEcRurd9BXet-fw")
                .city("Rzeszów")
                .phoneNumber("17 871 80 77")
                .websiteUrl("https://www.facebook.com/PewexPub/")
                .longitude(22.0053014)
                .latitude(50.0377769)
                .ratings(RatingsDto.builder()
                        .google(2.6f)
                        .googleCount(588)
                        .ourCost(2)
                        .build())
                .openingHours(openingHoursDataSet1)
                .drinks(drinksDataSet1)
                .photos(photosDataSet1)
                .tags(tagsDataSet1)
                .build());

        dataSet.add(PubDto.builder()
                .pubId(2L)
                .name("Rambla Cocktail&Music Pub")
                .address("Jana Matejki 16, 35-064 Rzeszów, Poland")
                .placeId("ChIJF-VPDAL7PEcRE5pnPMUZVAw")
                .city("Rzeszów")
                .phoneNumber("535 041 290")
                .websiteUrl("https://www.facebook.com/PubRambla")
                .longitude(22.0023941)
                .latitude(50.0380331)
                .description(LOREM_IPSUM_20)
                .reservable(true)
                .takeout(true)
                .ratings(RatingsDto.builder()
                        .google(4.2f)
                        .googleCount(514)
                        .ourCost(3)
                        .build())
                .openingHours(openingHoursDataSet2)
                .drinks(drinksDataSet2)
                .photos(photosDataSet2)
                .tags(tagsDataSet2)
                .build());

        dataSet.add(PubDto.builder()
                .pubId(3L)
                .name("Pub K20")
                .address("Mikołaja Kopernika 4, 35-002 Rzeszów, Poland")
                .placeId("ChIJ79jBNQL7PEcRAJ0TSKs0Bic")
                .city("Rzeszów")
                .phoneNumber("17 225 31 06")
                .websiteUrl("http://www.fb.com/K20PUB")
                .longitude(22.0031631)
                .latitude(50.0382432)
                .description(LOREM_IPSUM_50)
                .reservable(true)
                .takeout(true)
                .ratings(RatingsDto.builder()
                        .google(4.6f)
                        .googleCount(956)
                        .facebook(4.7f)
                        .facebookCount(156)
                        .tripadvisor(4.3f)
                        .tripadvisorCount(343)
                        .untapped(4.24f)
                        .untappedCount(343)
                        .ourDrinksQuality(4.0f)
                        .ourServiceQuality(5.0f)
                        .ourCost(4)
                        .build())
                .openingHours(openingHoursDataSet3)
                .drinks(drinksDataSet3)
                .photos(photosDataSet3)
                .tags(tagsDataSet3)
                .build());

        dataSet.add(PubDto.builder()
                .pubId(4L)
                .name("Corner Pub Mała Graciarnia Rzeszów")
                .address("Przesmyk 2, 35-065 Rzeszów, Poland")
                .placeId("ChIJGcTVBwL7PEcRTflMy_gvdhI")
                .city("Rzeszów")
                .phoneNumber("519 159 156")
                .websiteUrl("https://www.facebook.com/PewexPub/")
                .longitude(22.0056159)
                .latitude(50.0371217)
                .description(LOREM_IPSUM_20)
                .reservable(true)
                .takeout(false)
                .ratings(RatingsDto.builder()
                        .google(4.0f)
                        .googleCount(588)
                        .ourDrinksQuality(3.3f)
                        .ourServiceQuality(4.1f)
                        .ourCost(2)
                        .build())
                .openingHours(openingHoursDataSet4)
                .drinks(drinksDataSet4)
                .photos(photosDataSet4)
                .tags(tagsDataSet4)
                .build());

        dataSet.add(PubDto.builder()
                .pubId(5L)
                .name("Jameson Pub")
                .address("Stanisława Moniuszki 4, 35-015 Rzeszów, Poland")
                .placeId("ChIJlxM_5QP7PEcRuKMUGawHRgs")
                .city("Rzeszów")
                .phoneNumber("17 871 80 77")
                .websiteUrl("https://www.facebook.com/PewexPub/")
                .longitude(21.9996011)
                .latitude(50.0382023)
                .description(LOREM_IPSUM_100)
                .takeout(true)
                .ratings(RatingsDto.builder()
                        .google(4.0f)
                        .googleCount(588)
                        .ourDrinksQuality(2.3f)
                        .ourServiceQuality(4.0f)
                        .ourCost(4)
                        .build())
                .openingHours(openingHoursDataSet5)
                .drinks(drinksDataSet5)
                .photos(photosDataSet5)
                .tags(tagsDataSet5)
                .build());

        dataSet.add(PubDto.builder()
                .pubId(6L)
                .name("Pub Underground")
                .address("Jana Matejki 10, 35-001 Rzeszów, Poland")
                .placeId("ChIJS3sKMwL7PEcRurd9BXet-fw")
                .websiteUrl("https://www.facebook.com/PewexPub/")
                .description(LOREM_IPSUM_200)
                .reservable(true)
                .ratings(RatingsDto.builder()
                        .google(4.0f)
                        .googleCount(588)
                        .build())
                .openingHours(openingHoursDataSet6)
                .drinks(drinksDataSet6)
                .photos(photosDataSet6)
                .tags(tagsDataSet6)
                .build());
        dataSet.add(PubDto.builder()
                .pubId(7L)
                .name("Pub Underground")
                .address("Jana Matejki 10, 35-001 Rzeszów, Poland")
                .placeId("ChIJS3sKMwL7PEcRurd9BXet-fw")
                .websiteUrl("https://www.facebook.com/PewexPub/")
                .description(LOREM_IPSUM_200)
                .reservable(true)
                .ratings(RatingsDto.builder()
                        .google(4.0f)
                        .googleCount(588)
                        .build())
                .openingHours(openingHoursDataSet6)
                .drinks(drinksDataSet6)
                .photos(photosDataSet6)
                .tags(tagsDataSet6)
                .build());
        dataSet.add(PubDto.builder()
                .pubId(8L)
                .name("Pub Underground")
                .address("Jana Matejki 10, 35-001 Rzeszów, Poland")
                .placeId("ChIJS3sKMwL7PEcRurd9BXet-fw")
                .websiteUrl("https://www.facebook.com/PewexPub/")
                .description(LOREM_IPSUM_200)
                .reservable(true)
                .ratings(RatingsDto.builder()
                        .google(4.0f)
                        .googleCount(588)
                        .build())
                .openingHours(openingHoursDataSet6)
                .drinks(drinksDataSet6)
                .photos(photosDataSet6)
                .tags(tagsDataSet6)
                .build());

    }
    private void initDataSet1()
    {
        drinksDataSet1 = new ArrayList<>();
        openingHoursDataSet1 = new ArrayList<>();
        photosDataSet1 = new ArrayList<>();
        tagsDataSet1 = new ArrayList<>();
        drinksDataSet1.add(DrinkDto.builder()
                .name("Lager Delight")
                .type("Beer")
                .beer(BeerDto.builder()
                        .beerId(1L)
                        .longDescription("A light and crisp lager with a smooth finish.")
                        .shortDescription("Crisp lager")
                        .photoUrl("/images/lager.jpg")
                        .maltiness("Low")
                        .blg("12")
                        .alcoholContent("5%")
                        .build())
                .build());

        drinksDataSet1.add(DrinkDto.builder()
                .name("Stout Supreme")
                .type("Beer")
                .drinkStyles(Collections.singletonList(
                        DrinkStyleDto.builder()
                                .styleName("Pilsner")
                                .build()))
                .beer(BeerDto.builder()
                        .beerId(2L)
                        .longDescription("A rich and creamy stout with deep chocolate undertones.")
                        .shortDescription("Creamy stout")
                        .photoUrl("/images/stout.jpg")
                        .maltiness("High")
                        .blg("20")
                        .alcoholContent("7%")
                        .build())
                .build());

        drinksDataSet1.add(DrinkDto.builder()
                .name("Sex on the beach")
                .type("Cocktail")
                .build());

        openingHoursDataSet1.add(OpeningHoursDto.builder()
                .weekday("MONDAY")
                .timeOpen("00:00")
                .timeClose("02:00")
                .build());

        openingHoursDataSet1.add(OpeningHoursDto.builder()
                .weekday("TUESDAY")
                .timeOpen("00:00")
                .timeClose("02:00")
                .build());

        openingHoursDataSet1.add(OpeningHoursDto.builder()
                .weekday("WEDNESDAY")
                .timeOpen("16:00")
                .timeClose("02:00")
                .build());

        openingHoursDataSet1.add(OpeningHoursDto.builder()
                .weekday("THURSDAY")
                .timeOpen("16:00")
                .timeClose("02:00")
                .build());

        openingHoursDataSet1.add(OpeningHoursDto.builder()
                .weekday("FRIDAY")
                .timeOpen("16:00")
                .timeClose("03:00")
                .build());

        openingHoursDataSet1.add(OpeningHoursDto.builder()
                .weekday("SATURDAY")
                .timeOpen("16:00")
                .timeClose("24:00")  // Note: This might be a typo; "00:00" typically means midnight, suggesting closing time is exactly at the start of the day.
                .build());

        openingHoursDataSet1.add(OpeningHoursDto.builder()
                .weekday("SUNDAY")
                .timeOpen("00:00")
                .timeClose("02:00")
                .build());

        tagsDataSet1.add(new TagDto("TagNr1"));
        tagsDataSet1.add(new TagDto("TagNr2"));

    }
    private void initDataSet2()
    {
        drinksDataSet2 = new ArrayList<>();
        openingHoursDataSet2 = new ArrayList<>();
        photosDataSet2 = new ArrayList<>();
        tagsDataSet2 = new ArrayList<>();

        drinksDataSet2.add(DrinkDto.builder()
                .name("AleBrowar")
                .type("Beer")
                .drinkStyles(Collections.singletonList(
                        DrinkStyleDto.builder()
                                .styleName("Lager")
                                .build()))
                .build());

        drinksDataSet2.add(DrinkDto.builder()
                .name("Amber")
                .type("Beer")
                .drinkStyles(Collections.singletonList(
                        DrinkStyleDto.builder()
                                .styleName("IPA")
                                .build()))
                .build());

        drinksDataSet2.add(DrinkDto.builder()
                .name("Artezanr")
                .type("Beer")
                .drinkStyles(Collections.singletonList(
                        DrinkStyleDto.builder()
                                .styleName("APA")
                                .build()))
                .build());

        drinksDataSet2.add(DrinkDto.builder()
                .name("Sex on the beach")
                .type("Cocktail")
                .build());

        openingHoursDataSet2.add(OpeningHoursDto.builder()
                .weekday("MONDAY")
                .timeOpen("00:00")
                .timeClose("00:00")
                .build());

        openingHoursDataSet2.add(OpeningHoursDto.builder()
                .weekday("TUESDAY")
                .timeOpen("00:00")
                .timeClose("00:00")
                .build());

        openingHoursDataSet2.add(OpeningHoursDto.builder()
                .weekday("WEDNESDAY")
                .timeOpen("00:00")
                .timeClose("00:00")
                .build());

        openingHoursDataSet2.add(OpeningHoursDto.builder()
                .weekday("THURSDAY")
                .timeOpen("00:00")
                .timeClose("00:00")
                .build());

        openingHoursDataSet2.add(OpeningHoursDto.builder()
                .weekday("FRIDAY")
                .timeOpen("00:00")
                .timeClose("00:00")
                .build());

        openingHoursDataSet2.add(OpeningHoursDto.builder()
                .weekday("SATURDAY")
                .timeOpen("00:00")
                .timeClose("00:00")
                .build());

        openingHoursDataSet2.add(OpeningHoursDto.builder()
                .weekday("SUNDAY")
                .timeOpen("00:00")
                .timeClose("00:00")
                .build());
        tagsDataSet2.add(new TagDto("TagNr1"));
        tagsDataSet2.add(new TagDto("TagNr3"));
    }
    private void initDataSet3()
    {
        drinksDataSet3 = new ArrayList<>();
        openingHoursDataSet3 = new ArrayList<>();
        photosDataSet3 = new ArrayList<>();
        tagsDataSet3 = new ArrayList<>();

        drinksDataSet3.add(DrinkDto.builder()
                .name("Żywiec")
                .type("Beer")
                .drinkStyles(Arrays.asList(
                        DrinkStyleDto.builder().styleName("Lager").build(),
                        DrinkStyleDto.builder().styleName("Jasne").build()))
                .build());

        drinksDataSet3.add(DrinkDto.builder()
                .name("FunkyFluids")
                .type("Beer")
                .drinkStyles(List.of(
                        DrinkStyleDto.builder().styleName("Witbier").build()))
                .build());

        drinksDataSet3.add(DrinkDto.builder()
                .name("Sex on the beach")
                .type("Cocktail")
                .build());

        openingHoursDataSet3.add(OpeningHoursDto.builder()
                .weekday("MONDAY")
                .timeOpen("18:00")
                .timeClose("01:00")
                .build());

        openingHoursDataSet3.add(OpeningHoursDto.builder()
                .weekday("TUESDAY")
                .timeOpen("18:00")
                .timeClose("01:00")
                .build());

        openingHoursDataSet3.add(OpeningHoursDto.builder()
                .weekday("WEDNESDAY")
                .timeOpen("18:00")
                .timeClose("01:00")
                .build());

        openingHoursDataSet3.add(OpeningHoursDto.builder()
                .weekday("THURSDAY")
                .timeOpen("18:00")
                .timeClose("01:00")
                .build());

        openingHoursDataSet3.add(OpeningHoursDto.builder()
                .weekday("FRIDAY")
                .timeOpen("18:00")
                .timeClose("04:00")
                .build());

        openingHoursDataSet3.add(OpeningHoursDto.builder()
                .weekday("SATURDAY")
                .timeOpen("18:00")
                .timeClose("04:00")
                .build());

        openingHoursDataSet3.add(OpeningHoursDto.builder()
                .weekday("SUNDAY")
                .timeOpen("15:00")
                .timeClose("01:00")
                .build());
        tagsDataSet3.add(new TagDto("TagNr3"));
        tagsDataSet3.add(new TagDto("TagNr2"));

    }
    private void initDataSet4()
    {
        drinksDataSet4 = new ArrayList<>();
        openingHoursDataSet4 = new ArrayList<>();
        photosDataSet4 = new ArrayList<>();
        tagsDataSet4 = new ArrayList<>();

        drinksDataSet4.add(DrinkDto.builder()
                .name("Komes")
                .type("Beer")
                .drinkStyles(Arrays.asList(
                        DrinkStyleDto.builder().styleName("Porter").build(),
                        DrinkStyleDto.builder().styleName("Ciemne").build()))
                .build());

        drinksDataSet4.add(DrinkDto.builder()
                .name("Amber")
                .type("Beer")
                .build());

        drinksDataSet4.add(DrinkDto.builder()
                .name("Martini")
                .type("Cocktail")
                .build());

        openingHoursDataSet4.add(OpeningHoursDto.builder()
                .weekday("MONDAY")
                .timeOpen("18:00")
                .timeClose("01:00")
                .build());

        openingHoursDataSet4.add(OpeningHoursDto.builder()
                .weekday("TUESDAY")
                .timeOpen("00:00")
                .timeClose("02:00")
                .build());

        openingHoursDataSet4.add(OpeningHoursDto.builder()
                .weekday("WEDNESDAY")
                .timeOpen("16:00")
                .timeClose("02:00")
                .build());

        openingHoursDataSet4.add(OpeningHoursDto.builder()
                .weekday("THURSDAY")
                .timeOpen("16:00")
                .timeClose("02:00")
                .build());

        openingHoursDataSet4.add(OpeningHoursDto.builder()
                .weekday("FRIDAY")
                .timeOpen("16:00")
                .timeClose("03:00")
                .build());

        openingHoursDataSet4.add(OpeningHoursDto.builder()
                .weekday("SATURDAY")
                .timeOpen("16:00")
                .timeClose("00:00")  // Note: "00:00" typically means midnight, suggesting closing time is exactly at the start of the day.
                .build());

        openingHoursDataSet4.add(OpeningHoursDto.builder()
                .weekday("SUNDAY")
                .timeOpen("15:00")
                .timeClose("01:00")
                .build());
        tagsDataSet4.add(new TagDto("tagel"));
        tagsDataSet4.add(new TagDto("meh"));

    }
    private void initDataSet5()
    {
        drinksDataSet5 = new ArrayList<>();
        openingHoursDataSet5 = new ArrayList<>();
        photosDataSet5 = new ArrayList<>();
        tagsDataSet5 = new ArrayList<>();

        drinksDataSet5.add(DrinkDto.builder()
                .name("Recraft")
                .type("Beer")
                .drinkStyles(List.of(
                        DrinkStyleDto.builder().styleName("Ale").build()))
                .build());

        drinksDataSet5.add(DrinkDto.builder()
                .name("Recraft")
                .type("Beer")
                .drinkStyles(List.of(
                        DrinkStyleDto.builder().styleName("Lager").build()))
                .build());

        drinksDataSet5.add(DrinkDto.builder()
                .name("Recraft")
                .type("Beer")
                .drinkStyles(List.of(
                        DrinkStyleDto.builder().styleName("Pszeniczne").build()))
                .build());

        drinksDataSet5.add(DrinkDto.builder()
                .name("Martini")
                .type("Cocktail")
                .build());

        openingHoursDataSet5.add(OpeningHoursDto.builder()
                .weekday("MONDAY")
                .timeOpen("18:00")
                .timeClose("01:00")
                .build());

        openingHoursDataSet5.add(OpeningHoursDto.builder()
                .weekday("TUESDAY")
                .timeOpen("00:00")
                .timeClose("02:00")
                .build());

        openingHoursDataSet5.add(OpeningHoursDto.builder()
                .weekday("WEDNESDAY")
                .timeOpen("16:00")
                .timeClose("02:00")
                .build());

        openingHoursDataSet5.add(OpeningHoursDto.builder()
                .weekday("THURSDAY")
                .timeOpen("16:00")
                .timeClose("02:00")
                .build());

        openingHoursDataSet5.add(OpeningHoursDto.builder()
                .weekday("FRIDAY")
                .timeOpen("16:00")
                .timeClose("03:00")
                .build());

        openingHoursDataSet5.add(OpeningHoursDto.builder()
                .weekday("SATURDAY")
                .timeOpen("16:00")
                .timeClose("00:00")  // Note: "00:00" typically means midnight, suggesting closing time is exactly at the start of the day.
                .build());

        openingHoursDataSet5.add(OpeningHoursDto.builder()
                .weekday("SUNDAY")
                .timeOpen("15:00")
                .timeClose("01:00")
                .build());
        tagsDataSet5.add(new TagDto("Uf"));
        tagsDataSet5.add(new TagDto("Ah"));

    }
    private void initDataSet6()
    {
        drinksDataSet6=null;
        openingHoursDataSet6=null;
        photosDataSet6=null;
        tagsDataSet6 = null;
    }
}
