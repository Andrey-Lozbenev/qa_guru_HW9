package dzr.hanom;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import dzr.hanom.data.Gadgets;
import dzr.hanom.data.GadgetsType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static dzr.hanom.data.Gadgets.TABLET;


public class TestJunit5Annotation {

    @BeforeEach
    void beforeAll() {

        Configuration.browserSize = "1920x1080";

    }

    @ParameterizedTest(name = "В поисковой выдаче новостей по запросу {0} отображается 20 новостей")
    @ValueSource(strings = {
            "Москва", "QA"
    })
    void searchResultsShouldHave20News(String testDataNews) {
        open("https://dzen.ru/news");
        $(".news-dzen-header-new-search").click();
        $(".news-dzen-header-new-search__input").setValue(testDataNews).pressEnter();
        $$("article.news-search-story").filter(visible).shouldHave(CollectionCondition.sizeGreaterThanOrEqual(20));
    }

    @ParameterizedTest(name = "При выборе города из списка {0} в виджете погоде отображается текст {1}")
    @CsvSource(value = {
            "Сочи,           Погода в Сочи",
            "Ростов-на-Дону, Погода в Ростове-на-Дону"
    })
    void cityInWhetherWidgetShouldBeChanged(String testDataCity, String testDataCityWhether) {
        open("https://dzen.ru/pogoda");
        $(".other-cities").$(byText(testDataCity)).click();
        $(".fact__title").shouldHave(Condition.text(testDataCityWhether));
    }

    static Stream<Arguments> siteShouldContainsAllOfGivenAppsName() {
        return Stream.of(
                Arguments.of(TABLET, GadgetsType.IPAD, List.of("Браузер", "Auto.ru", "Почта", "Навигатор", "Карты",
                        "Метро", "Переводчик", "Диск", "Кинопоиск", "Толока", "Заправки", "Букмейт")),
                Arguments.of(TABLET, GadgetsType.ANDROID, List.of("Браузер", "Auto.ru", "Почта", "Навигатор", "Карты",
                        "Переводчик", "Диск", "Виджет Яндекса", "Толока", "Заправки", "Букмейт"))
        );
    }

    @MethodSource()
    @ParameterizedTest(name = "При выборе устройства {0} и операционной системы {1} отображаются приложения {2}")
    void siteShouldContainsAllOfGivenAppsName(Gadgets gadgets, GadgetsType gadgetsType, List<String> apps) {
        open("https://mobile.yandex.ru/");
        $(".header__menu").$(byText(gadgets.getDesc())).click();
        $(".header").$(byText(gadgetsType.getDesc())).click();
        $$(".description-card__title").shouldHave(texts(apps));
    }

}
