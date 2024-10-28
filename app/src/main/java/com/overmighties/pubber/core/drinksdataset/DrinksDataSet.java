package com.overmighties.pubber.core.drinksdataset;

import com.overmighties.pubber.core.model.DrinkStyle;
import com.overmighties.pubber.core.network.model.BeerDto;
import com.overmighties.pubber.core.network.model.DrinkDto;
import com.overmighties.pubber.core.network.model.DrinkStyleDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Getter;

@Getter
public class DrinksDataSet {
    private List<DrinkDto> dataSet;

    private final static DrinksDataSet INSTANCE=new DrinksDataSet();
    public DrinksDataSet()
    {
        init();
    }
    public static DrinksDataSet getInstance()
    {
        return INSTANCE;
    }

    private void init()
    {
        dataSet = new ArrayList<>();
        addBers();
        addDrinks();
    }

    private void addBers() {
        dataSet.add(DrinkDto.builder()
                .name("Budweiser")
                .type("Beer")
                .beer(BeerDto.builder()

                        .longDescription("Budweiser to piwo wręcz legendarne, jedno z najbardziej znanych czeskich piw. Powstaje tylko i wyłącznie w Czeskich Budziejowicach, dzięki czemu ma każdym kontynencie smakuje dokładnie tak samo. Tradycja warzenia piwa Budweiser według niezmiennej od lat, oryginalnej receptury liczy 700 lat. Rewelacyjny smak, łagodny, a jednocześnie z wyraźnym posmakiem lekkiej goryczki. Głęboki kolor i idealne nasycenie to jego dodatkowe atuty.")
                        .shortDescription("Chmiel, woda ze studni artezyjskich o głębokości 300 m, morawski słód, kunszt piwowarski i długi okres leżakowania w piwnicach lagerowych. Lager, który tworzy dobre imię dla czeskiego piwa na całym świecie.")
                        .photoUrl("https://sklepimpuls.pl/wp-content/uploads/2020/11/BudvarOriginal-800x800.jpg")
                        //.hopiness("3")
                        .maltiness("3.4")
                        .blg("12")
                        .alcoholContent("5%")
                        .build())
                .drinkStyles(Arrays.asList(
                        DrinkStyleDto.builder().styleName("Lager").build(),
                        DrinkStyleDto.builder().styleName("Jasne").build()))
                .build());
        dataSet.add(DrinkDto.builder()
                .name("Grimbergen Blonde")
                .type("Beer")
                .beer(BeerDto.builder()
                        .beerId(2L)
                        .longDescription("Grimbergen Blonde to klasyczny belgijski blond o wyraźnych nutach słodowych, subtelnej słodyczy i delikatnej owocowości. Zawiera 6.7% alkoholu, oferując pełny smak z nutami miodu i przypraw, idealny dla wielbicieli wyważonych, złożonych piw.")
                        .shortDescription("Belgijski blond z nutą słodu, owoców i przypraw, o pełnym i bogatym smaku.")
                        .photoUrl("https://www.grimbergen.com/our-brews/blonde.jpg")
                        //.hopiness("3")
                        .maltiness("4.2")
                        .blg("14")
                        .alcoholContent("6.7%")
                        .build())
                .drinkStyles(Arrays.asList(
                        DrinkStyleDto.builder().styleName("Belgian Blonde Ale").build(),
                        DrinkStyleDto.builder().styleName("Light").build()))
                .build());

        dataSet.add(DrinkDto.builder()
                .name("Grimbergen Double Ambrée")
                .type("Beer")
                .beer(BeerDto.builder()
                        .beerId(3L)
                        .longDescription("Grimbergen Double Ambrée to bogate, słodowe piwo w stylu Dubbel, z nutami karmelu, suszonych śliwek i delikatnej goryczki. Dzięki zawartości 6.5% alkoholu, oferuje głęboki, pełen ciepła smak z nutami karmelizowanego słodu.")
                        .shortDescription("Słodowe, głębokie i delikatnie gorzkie Dubbel, z aromatami karmelu i dojrzałych owoców.")
                        .photoUrl("https://www.grimbergen.com/our-brews/double-ambree.jpg")
                        //.hopiness("2.5")
                        .maltiness("4.5")
                        .blg("15")
                        .alcoholContent("6.5%")
                        .build())
                .drinkStyles(Arrays.asList(
                        DrinkStyleDto.builder().styleName("Dubbel").build(),
                        DrinkStyleDto.builder().styleName("Amber").build()))
                .build());

        dataSet.add(DrinkDto.builder()
                .name("Grimbergen Blanche")
                .type("Beer")
                .beer(BeerDto.builder()
                        .beerId(4L)
                        .longDescription("Grimbergen Blanche to belgijski witbier o jasnej barwie i kremowej pianie, z nutami cytrusów, kolendry i bergamotki. Ma 6.0% alkoholu, oferując orzeźwiający, wyważony smak, idealny na cieplejsze dni.")
                        .shortDescription("Lekki, orzeźwiający witbier z nutami cytrusów i przypraw.")
                        .photoUrl("https://www.grimbergen.com/our-brews/blanche.jpg")
                        //.hopiness("2")
                        .maltiness("3.0")
                        .blg("12")
                        .alcoholContent("6.0%")
                        .build())
                .drinkStyles(Arrays.asList(
                        DrinkStyleDto.builder().styleName("Witbier").build(),
                        DrinkStyleDto.builder().styleName("Light").build()))
                .build());
        dataSet.add(DrinkDto.builder()
                .name("Grodziskie Original")
                .type("Beer")
                .beer(BeerDto.builder()

                        .longDescription("Grodziskie Original, znane także jako 'polski szampan', to tradycyjne piwo pszeniczne o wyjątkowym, lekko dymnym posmaku. Jest warzone z dodatkiem słodu pszenicznego wędzonego dymem dębowym oraz charakterystycznego chmielu Tomyskiego, który podkreśla jego autentyczny smak.")
                        .shortDescription("Lekkie, musujące piwo o charakterystycznym aromacie dymnym, wyróżniające się oryginalną recepturą i naturalnym, subtelnym nasyceniem.")
                        .photoUrl("https://browargrodzisk.com/images/original.jpg")
                        //.hopiness("2.5")
                        .maltiness("3.0")
                        .blg("7.7")
                        .alcoholContent("3.1%")
                        .build())
                .drinkStyles(Arrays.asList(
                        DrinkStyleDto.builder().styleName("Grodziskie").build(),
                        DrinkStyleDto.builder().styleName("Wędzone").build()))
                .build());

        dataSet.add(DrinkDto.builder()
                .name("Grodziska APA")
                .type("Beer")
                .beer(BeerDto.builder()
                        .beerId(2L)
                        .longDescription("Grodziska APA to unikalna interpretacja stylu American Pale Ale, która łączy polskie chmiele (m.in. Lubelski i Marynka) z wyważonym smakiem cytrusowym i delikatną nutą karmelową. Piwo jest lekkie, orzeźwiające i idealnie zbalansowane między goryczką a słodyczą.")
                        .shortDescription("APA o przyjemnym cytrusowym aromacie i łagodnej goryczce, które łączy polskie chmiele z klasycznym charakterem piw z Grodziska.")
                        .photoUrl("https://browargrodzisk.com/images/apa.jpg")
                        //.hopiness("4.5")
                        .maltiness("3.5")
                        .blg("12.5")
                        .alcoholContent("5.2%")
                        .build())
                .drinkStyles(Arrays.asList(
                        DrinkStyleDto.builder().styleName("APA").build(),
                        DrinkStyleDto.builder().styleName("Jasne").build()))
                .build());

        dataSet.add(DrinkDto.builder()
                .name("Grodziskie Bezalkoholowe Mango ALE")
                .type("Beer")
                .beer(BeerDto.builder()
                        .beerId(3L)
                        .longDescription("Bezalkoholowe Mango ALE z Grodziska to lekka, owocowa wariacja na temat klasycznego ale z dodatkiem mango, które nadaje mu świeży, tropikalny aromat. Idealne do orzeźwienia, zachowuje pełnię smaku mimo braku alkoholu.")
                        .shortDescription("Bezalkoholowe ale o intensywnym aromacie mango, zapewniające świeżość i pełnię smaku.")
                        .photoUrl("https://browargrodzisk.com/images/mango_ale.jpg")
                        //.hopiness("2.0")
                        .maltiness("2.5")
                        .blg("8.0")
                        .alcoholContent("0%")
                        .build())
                .drinkStyles(Arrays.asList(
                        DrinkStyleDto.builder().styleName("Bezalkoholowe").build(),
                        DrinkStyleDto.builder().styleName("Owocowe").build()))
                .build());
        dataSet.add(DrinkDto.builder()
                .name("Grolsch Premium Pilsner")
                .type("Beer")
                .beer(BeerDto.builder()
                        .beerId(11L)
                        .longDescription("Grolsch Premium Pilsner, a classic Dutch lager, is known for its unique double-fermentation process, delivering a crisp taste with a distinct bitterness and mild hoppy aroma. Brewed since 1615, it features a medium golden hue with a perfect blend of malted barley and two Hallertau hops, Emerald and Magnum.")
                        .shortDescription("A well-balanced, refreshing Euro pale lager with crisp bitterness and rich malt depth.")
                        .photoUrl("https://www.grolsch.com/image-url")
                       // .hopiness("3")
                        .maltiness("4")
                        .blg("12.5")
                        .alcoholContent("5%")
                        .build())
                .drinkStyles(Arrays.asList(
                        DrinkStyleDto.builder().styleName("Pilsner").build(),
                        DrinkStyleDto.builder().styleName("Lager").build()))
                .build());

        dataSet.add(DrinkDto.builder()
                .name("Grolsch Weizen")
                .type("Beer")
                .beer(BeerDto.builder()
                        .beerId(12L)
                        .longDescription("Grolsch Weizen is a traditional Hefeweizen, crafted with German yeast and Hallertau hops for a refreshing, full-bodied wheat flavor, accented by fruity and spicy notes. Its naturally cloudy, golden color and smooth, light body make it an excellent choice for summer.")
                        .shortDescription("A fruity, spicy wheat beer with a natural haze, inspired by German brewing traditions.")
                        .photoUrl("https://www.grolsch.com/weizen-image-url")
                        //.hopiness("2")
                        .maltiness("3")
                        .blg("11")
                        .alcoholContent("5.5%")
                        .build())
                .drinkStyles(Arrays.asList(
                        DrinkStyleDto.builder().styleName("Weizen").build(),
                        DrinkStyleDto.builder().styleName("Wheat").build()))
                .build());

        dataSet.add(DrinkDto.builder()
                .name("Grolsch Radler")
                .type("Beer")
                .beer(BeerDto.builder()
                        .beerId(13L)
                        .longDescription("Grolsch Radler is a refreshing blend of Grolsch beer and real lemon juice, resulting in a light, citrusy flavor perfect for warm days. It has a bright yellow hue and combines mild malt sweetness with lemon acidity.")
                        .shortDescription("A light, lemony Radler with a balance of malt sweetness and citrus tang.")
                        .photoUrl("https://www.grolsch.com/radler-image-url")
                        //.hopiness("1")
                        .maltiness("2")
                        .blg("8")
                        .alcoholContent("2%")
                        .build())
                .drinkStyles(Arrays.asList(
                        DrinkStyleDto.builder().styleName("Radler").build(),
                        DrinkStyleDto.builder().styleName("Fruit Beer").build()))
                .build());
        dataSet.add(DrinkDto.builder()
                .name("Kozel Premium")
                .type("Beer")
                .beer(BeerDto.builder()

                        .longDescription("Kozel Premium to jasny lager o zrównoważonym smaku, warzony z trzech różnych słodów i aromatycznych czeskich chmieli. Charakteryzuje się delikatnym smakiem z wyraźną nutą goryczki, a jego łagodność sprawia, że jest popularnym wyborem na całym świecie.")
                        .shortDescription("Lager o pełnym smaku, z łagodną nutą goryczy, idealny na każdą okazję.")
                        .photoUrl("https://upload.wikimedia.org/wikipedia/commons/thumb/6/66/Kozel_beer.jpg/800px-Kozel_beer.jpg")
                        //.hopiness("2.5")
                        .maltiness("3")
                        .blg("11.5")
                        .alcoholContent("4.6%")
                        .build())
                .drinkStyles(Arrays.asList(
                        DrinkStyleDto.builder().styleName("Lager").build(),
                        DrinkStyleDto.builder().styleName("Jasne").build()))
                .build());

        dataSet.add(DrinkDto.builder()
                .name("Kozel Dark")
                .type("Beer")
                .beer(BeerDto.builder()
                        .beerId(2L)
                        .longDescription("Kozel Dark, ciemny lager o niskiej zawartości alkoholu, warzony z czterech rodzajów słodów, oferuje bogaty smak z subtelnymi nutami karmelu i czekolady. Dzięki niskiej goryczce i słodyczy, Kozel Dark stał się jednym z najbardziej nagradzanych ciemnych piw na świecie.")
                        .shortDescription("Lekki ciemny lager o słodkim, karmelowym smaku i łagodnej goryczce.")
                        .photoUrl("https://example.com/kozel_dark.jpg")
                        //.hopiness("2")
                        .maltiness("4")
                        .blg("10.5")
                        .alcoholContent("3.8%")
                        .build())
                .drinkStyles(Arrays.asList(
                        DrinkStyleDto.builder().styleName("Lager").build(),
                        DrinkStyleDto.builder().styleName("Ciemne").build()))
                .build());
        dataSet.add(DrinkDto.builder()
                .name("Książęce Złote Pszeniczne")
                .type("Beer")
                .beer(BeerDto.builder()

                        .longDescription("Książęce Złote Pszeniczne to unikalny lager pszeniczny o lekko mętnym kolorze i złotej barwie. Charakteryzuje się delikatnym, pszenicznym smakiem z wyczuwalnymi nutami miodu lipowego oraz słodkich owoców, jak brzoskwinie. Dzięki niskiej goryczce i kwaskowemu zakończeniu jest to piwo doskonale orzeźwiające.")
                        .shortDescription("Orzeźwiający lager pszeniczny z nutami miodu i brzoskwini.")
                        .photoUrl("https://example.com/zlote_pszeniczne.jpg")
                        //.hopiness("2")
                        .maltiness("3")
                        .blg("12.5")
                        .alcoholContent("4.9%")
                        .build())
                .drinkStyles(Arrays.asList(
                        DrinkStyleDto.builder().styleName("Lager pszeniczny").build(),
                        DrinkStyleDto.builder().styleName("Pszeniczne").build()))
                .build());

        dataSet.add(DrinkDto.builder()
                .name("Książęce IPA")
                .type("Beer")
                .beer(BeerDto.builder()
                        .beerId(2L)
                        .longDescription("Książęce IPA to jasne, pełne piwo w stylu India Pale Ale, charakteryzujące się intensywnym aromatem cytrusów i owoców tropikalnych, które pochodzą od chmieli takich jak Citra, Mosaic oraz Cascade. W smaku wyczuwalna jest przyjemna goryczka, która doskonale balansuje słodowe tło.")
                        .shortDescription("Aromatyczna IPA o cytrusowym aromacie i wyrazistej goryczce.")
                        .photoUrl("https://example.com/ksiazece_ipa.jpg")
                        //.hopiness("4")
                        .maltiness("2.5")
                        .blg("14")
                        .alcoholContent("5.4%")
                        .build())
                .drinkStyles(Arrays.asList(
                        DrinkStyleDto.builder().styleName("IPA").build(),
                        DrinkStyleDto.builder().styleName("Jasne").build()))
                .build());

        dataSet.add(DrinkDto.builder()
                .name("Książęce Porter")
                .type("Beer")
                .beer(BeerDto.builder()
                        .beerId(3L)
                        .longDescription("Książęce Porter to ciemne, mocne piwo o intensywnym smaku i aromacie kawy, czekolady oraz lekko przypalonych słodów. Pełne i aksamitne w smaku, z wyraźnym profilem słodowym, które szczególnie sprawdzi się w chłodniejsze wieczory.")
                        .shortDescription("Porter o intensywnym smaku kawy i czekolady.")
                        .photoUrl("https://example.com/ksiazece_porter.jpg")
                        //.hopiness("3")
                        .maltiness("4.5")
                        .blg("18")
                        .alcoholContent("8%")
                        .build())
                .drinkStyles(Arrays.asList(
                        DrinkStyleDto.builder().styleName("Porter").build(),
                        DrinkStyleDto.builder().styleName("Ciemne").build()))
                .build());
        dataSet.add(DrinkDto.builder()
                .name("Miłosław Niefiltrowane")
                .type("Beer")
                .beer(BeerDto.builder()

                        .longDescription("Miłosław Niefiltrowane to piwo dolnej fermentacji o naturalnej mętności, które powstaje tradycyjnymi metodami, zanim wynaleziono filtrację. W procesie fermentacji drożdże osiadają na dnie, co nadaje mu wyrazisty, lekko owocowy smak i aksamitną teksturę.")
                        .shortDescription("Lekkie piwo z wyczuwalną goryczką Styrian Goldings oraz nutą słodowego posmaku.")
                        .photoUrl("https://browarfortuna.pl/wp-content/uploads/2020/11/MiloslawNiefiltrowane.jpg")
                        //.hopiness("3")
                        .maltiness("3.5")
                        .blg("12")
                        .alcoholContent("5.2%")
                        .build())
                .drinkStyles(Arrays.asList(
                        DrinkStyleDto.builder().styleName("Lager").build(),
                        DrinkStyleDto.builder().styleName("Niefiltrowane").build()))
                .build());

        dataSet.add(DrinkDto.builder()
                .name("Miłosław Pilzner")
                .type("Beer")
                .beer(BeerDto.builder()
                        .beerId(2L)
                        .longDescription("Miłosław Pilzner to klasyczny pilzner o wyrazistej, korzennej goryczce, aromatyzowany chmielem Styrian Goldings. Cechuje go złocista barwa oraz delikatnie kwiatowy, słodowy zapach.")
                        .shortDescription("Pilzner z mocnym akcentem goryczki i subtelną nutą kwiatową, o pięknej złotej barwie.")
                        .photoUrl("https://browarfortuna.pl/wp-content/uploads/2020/11/MiloslawPilzner.jpg")
                        //.hopiness("4")
                        .maltiness("3")
                        .blg("12")
                        .alcoholContent("5.5%")
                        .build())
                .drinkStyles(Arrays.asList(
                        DrinkStyleDto.builder().styleName("Pilzner").build()))
                .build());

        dataSet.add(DrinkDto.builder()
                .name("Miłosław Pszeniczne")
                .type("Beer")
                .beer(BeerDto.builder()
                        .beerId(3L)
                        .longDescription("Miłosław Pszeniczne to piwo pszeniczne, które wyróżnia się pełnym, lekko słodkawym smakiem oraz aromatem bananowo-goździkowym, typowym dla stylu. Idealne dla miłośników pełniejszych, mocniejszych doznań smakowych.")
                        .shortDescription("Ciemne piwo pszeniczne o aromacie bananów i goździków, delikatnie słodowe.")
                        .photoUrl("https://browarfortuna.pl/wp-content/uploads/2020/11/MiloslawPszeniczne.jpg")
                        //.hopiness("2")
                        .maltiness("4")
                        .blg("13")
                        .alcoholContent("5.3%")
                        .build())
                .drinkStyles(Arrays.asList(
                        DrinkStyleDto.builder().styleName("Weizen").build(),
                        DrinkStyleDto.builder().styleName("Pszeniczne").build()))
                .build());

        dataSet.add(DrinkDto.builder()
                .name("Miłosław Marcowe")
                .type("Beer")
                .beer(BeerDto.builder()
                        .beerId(4L)
                        .longDescription("Miłosław Marcowe to piwo o głębokiej miedzianej barwie i bogatym, słodowo-chmielowym charakterze, stworzone z myślą o wiosennych spotkaniach. Posiada wyczuwalne nuty karmelowe i chlebowe.")
                        .shortDescription("Pełne piwo marcowe z delikatną słodyczą karmelu i wyraźnym chlebowym posmakiem.")
                        .photoUrl("https://browarfortuna.pl/wp-content/uploads/2020/11/MiloslawMarcowe.jpg")
                        //.hopiness("3")
                        .maltiness("4")
                        .blg("14")
                        .alcoholContent("5.4%")
                        .build())
                .drinkStyles(Arrays.asList(
                        DrinkStyleDto.builder().styleName("Marcowe").build()))
                .build());
        dataSet.add(DrinkDto.builder()
                .name("Paulaner Hefe-Weißbier")
                .type("Beer")
                .beer(BeerDto.builder()

                        .longDescription("Paulaner Hefe-Weißbier to klasyczne niemieckie piwo pszeniczne, które zachwyca bogatym smakiem i aromatem. Dzięki naturalnej fermentacji i zastosowaniu drożdży, piwo to ma intensywne nuty banana i goździków, a jego złożony charakter podkreślają akcenty zbożowe i delikatna goryczka.")
                        .shortDescription("Jasne, mętne piwo pszeniczne z nutami owocowymi i przyprawowymi. Idealne na ciepłe dni.")
                        .photoUrl("https://upload.wikimedia.org/wikipedia/commons/thumb/2/22/Paulaner_Hefe-Weissbier.jpg/800px-Paulaner_Hefe-Weissbier.jpg")
                        //.hopiness("3")
                        .maltiness("4")
                        .blg("11.5")
                        .alcoholContent("5.5%")
                        .build())
                .drinkStyles(Arrays.asList(
                        DrinkStyleDto.builder().styleName("Weissbier").build(),
                        DrinkStyleDto.builder().styleName("Hefeweizen").build()))
                .build());

        dataSet.add(DrinkDto.builder()
                .name("Paulaner Münchner Hell")
                .type("Beer")
                .beer(BeerDto.builder()
                        .beerId(2L)
                        .longDescription("Paulaner Münchner Hell to klasyczny bawarski lager, który charakteryzuje się jasną, złocistą barwą i delikatnym, słodowym smakiem. To piwo łączy w sobie rześkość i gładkość, co czyni je idealnym wyborem na każdą okazję.")
                        .shortDescription("Jasne piwo dolnej fermentacji z wyczuwalnym słodem i subtelną goryczką.")
                        .photoUrl("https://upload.wikimedia.org/wikipedia/commons/thumb/b/b7/Paulaner_M%C3%BCnchner_Hell.jpg/800px-Paulaner_M%C3%BCnchner_Hell.jpg")
                        //.hopiness("2.5")
                        .maltiness("4.5")
                        .blg("11.5")
                        .alcoholContent("4.9%")
                        .build())
                .drinkStyles(Arrays.asList(
                        DrinkStyleDto.builder().styleName("Lager").build(),
                        DrinkStyleDto.builder().styleName("Helles").build()))
                .build());

        dataSet.add(DrinkDto.builder()
                .name("Paulaner Oktoberfest Bier")
                .type("Beer")
                .beer(BeerDto.builder()
                        .beerId(3L)
                        .longDescription("Paulaner Oktoberfest Bier to wyjątkowe piwo, warzone specjalnie na festiwal Oktoberfest. Charakteryzuje się bogatym, słodowym smakiem z nutami karmelu, co czyni je idealnym towarzyszem do tradycyjnych bawarskich potraw.")
                        .shortDescription("Sezonowe piwo o pełnym ciele, idealne do picia w czasie festiwali.")
                        .photoUrl("https://upload.wikimedia.org/wikipedia/commons/thumb/8/81/Paulaner_Oktoberfest.jpg/800px-Paulaner_Oktoberfest.jpg")
                        //.hopiness("3")
                        .maltiness("5")
                        .blg("13")
                        .alcoholContent("6%")
                        .build())
                .drinkStyles(Arrays.asList(
                        DrinkStyleDto.builder().styleName("Märzen").build(),
                        DrinkStyleDto.builder().styleName("Oktoberfest").build()))
                .build());
        dataSet.add(DrinkDto.builder()
                .name("Pilsner Urquell")
                .type("Beer")
                .beer(BeerDto.builder()

                        .longDescription("Pilsner Urquell to piwo, które zapoczątkowało erę pilsnerów ponad 160 lat temu. Jego szlachetna złocista barwa, wyrafinowany smak i doskonała jakość są wynikiem niezmiennej receptury, stosowanej od 1842 roku. Zawiera delikatne nuty chmielowe, a jego pełen, orzeźwiający charakter sprawia, że jest to jeden z najpopularniejszych i najbardziej cenionych lagerów na świecie.")
                        .shortDescription("Wyjątkowe piwo o złocistej barwie i harmonijnym smaku, które łączy w sobie świeżość z wyraźną goryczką chmielową. Klasyczny pilsner z Plzeň, idealny na każdą okazję.")
                        .photoUrl("https://www.gbeer.com/img/pilsnerurquell.jpg")
                        //.hopiness("4")
                        .maltiness("3.5")
                        .blg("11.8")
                        .alcoholContent("4.4%")
                        .build())
                .drinkStyles(Arrays.asList(
                        DrinkStyleDto.builder().styleName("Lager").build(),
                        DrinkStyleDto.builder().styleName("Pilsner").build()))
                .build());
        dataSet.add(DrinkDto.builder()
                .name("Tyskie")
                .type("Beer")
                .beer(BeerDto.builder()

                        .longDescription("Tyskie Gronie to popularne polskie piwo typu lager, które wyróżnia się wyważoną goryczką i słodową pełnią. Warzone jest z najwyższej jakości składników: słodu jęczmiennego, chmielu i krystalicznie czystej wody, co przekłada się na jego wyjątkowy smak, lekko jabłkowo-bananowy aromat oraz piękny, jasnozłoty kolor z gęstą pianą.")
                        .shortDescription("Charakteryzuje się klarownością, jasnozłotym kolorem oraz delikatną goryczką. Idealne na każdą okazję, zyskało uznanie zarówno w Polsce, jak i na międzynarodowych konkursach piwnych.")
                        .photoUrl("https://upload.wikimedia.org/wikipedia/en/thumb/4/48/Tyskie_Gronie.png/220px-Tyskie_Gronie.png")
                        //.hopiness("2.5")
                        .maltiness("3.2")
                        .blg("12")
                        .alcoholContent("5%")
                        .build())
                .drinkStyles(Arrays.asList(
                        DrinkStyleDto.builder().styleName("Lager").build(),
                        DrinkStyleDto.builder().styleName("Jasne").build()))
                .build());
        dataSet.add(DrinkDto.builder()
                .name("Żywiec")
                .type("Beer")
                .beer(BeerDto.builder()

                        .longDescription("Żywiec to ikona polskiego piwowarstwa, warzony nieprzerwanie od 1856 roku w Arcyksiążęcym Browarze w Żywcu. Charakteryzuje się wyjątkowym smakiem, który uzyskuje dzięki zastosowaniu trzech prostych składników: słodu jęczmiennego, chmielu oraz krystalicznie czystej górskiej wody. Jego orzeźwiający profil i delikatna goryczka sprawiają, że jest idealnym towarzyszem wielu okazji.")
                        .shortDescription("Piwo jasne, pasteryzowane o zawartości alkoholu 5,5%. Żywiec to piwo z bogatą tradycją, warzone w zgodzie z oryginalną recepturą.")
                        .photoUrl("https://upload.wikimedia.org/wikipedia/commons/thumb/d/d6/Zywiec-beer.jpg/640px-Zywiec-beer.jpg")
                        //.hopiness("3")
                        .maltiness("4")
                        .blg("12")
                        .alcoholContent("5.5%")
                        .build())
                .drinkStyles(Arrays.asList(
                        DrinkStyleDto.builder().styleName("Lager").build(),
                        DrinkStyleDto.builder().styleName("Jasne").build()))
                .build());
        dataSet.add(DrinkDto.builder()
                .name("Żywiec Białe")
                .type("Beer")
                .beer(BeerDto.builder()

                        .longDescription("Żywiec Białe to orzeźwiające piwo pszeniczne o niskiej goryczce, które zachwyca lekkością i aromatem. Posiada delikatny, owocowy posmak, wzbogacony nutami cytrusowymi, co czyni je idealnym wyborem na ciepłe dni.")
                        .shortDescription("Piwo pszeniczne o zawartości alkoholu 4,9% obj., z orzeźwiającym smakiem i lekką nutą cytrusów.")
                        .photoUrl("https://sklepimpuls.pl/wp-content/uploads/2020/11/ZywiecBiale-800x800.jpg")
                        //.hopiness("2")
                        .maltiness("4")
                        .blg("11")
                        .alcoholContent("4.9%")
                        .build())
                .drinkStyles(Arrays.asList(
                        DrinkStyleDto.builder().styleName("Pszeniczne").build(),
                        DrinkStyleDto.builder().styleName("Białe").build()))
                .build());










    }

    private void addDrinks() {
    }

}
