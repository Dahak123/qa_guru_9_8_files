package guru.qa.homepack;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;


import java.util.stream.Stream;

import static com.codeborne.selenide.Selenide.*;

public class ParamHHruTests {


    @Test
    @DisplayName("Smoke Test сайта hh.ru")
    @Tag("blocker")
    void selenideSearchTest() {
        Configuration.browserSize = "1920x1800";
        open("https://orenburg.hh.ru/");
        $("input[data-qa='search-input']").setValue("Тестировщик").pressEnter();
        $$("div.serp-item")
                .find(Condition.text("Тестировщик"))
                .shouldBe(Condition.visible);
    }

    @Tag("blocker")
    @ParameterizedTest(name = "Поиска в hh.ru слова {0}")
    @ValueSource(strings = {"Тестировщик", "QA engineer"})
    void valuehruSearchTest(String searchQuary) {
        Configuration.browserSize = "1920x1800";
        open("https://orenburg.hh.ru/");
        $("input[data-qa='search-input']").setValue(searchQuary).pressEnter();
        $$("div.serp-item")
                .find(Condition.text(searchQuary))
                .shouldBe(Condition.visible);
    }

    @Tag("blocker")
    @ParameterizedTest(name = "Поиска в hh.ru слова {0} и проверка в нём текста {1}")
    @CsvSource(value = {"Тестировщик|Тестировщик ПО / QA Engineer",
            "QA engineer|QA Team Lead / Тимлид команды тестирования"
    }, delimiter = '|')
    void sourcehruSearchTest(String searchQuary, String expectedResult) {
        Configuration.browserSize = "1920x1800";
        open("https://orenburg.hh.ru/");
        $("input[data-qa='search-input']").setValue(searchQuary).pressEnter();
        $$("div.serp-item")
                .find(Condition.text(expectedResult))
                .shouldBe(Condition.visible);
    }

    static Stream<Arguments> methodhruSearchTest() {
        return Stream.of(
                Arguments.of("Тестировщик", "Тестировщик ПО / QA Engineer"),
                Arguments.of("QA engineer", "QA Team Lead / Тимлид команды тестирования")
        );
    }

    @Tag("blocker")
    @ParameterizedTest(name = "Поиска в hh.ru слова {0} и проверка в нём текста {1}")
    @MethodSource
    void methodhruSearchTest(String searchQuary, String expectedResult) {
        Configuration.browserSize = "1920x1800";
        open("https://orenburg.hh.ru/");
        $("input[data-qa='search-input']").setValue(searchQuary).pressEnter();
        $$("div.serp-item")
                .find(Condition.text(expectedResult))
                .shouldBe(Condition.visible);

    }
}

