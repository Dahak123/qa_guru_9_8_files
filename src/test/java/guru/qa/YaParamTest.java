package guru.qa;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static com.codeborne.selenide.Selenide.*;

public class YaParamTest {

    @DisplayName("Поиск в ya.ru слова Selenide")
    @Tag("blocker")
    @Test
    void selenideSearchTest(){
        open("https://ya.ru/");
        $("#text").setValue("Selenide");
        $("button[type='submit']").click();
        $$("li.serp-item")
                .find(Condition.text("лаконичные и стабильные UI тесты на Java"))
                .shouldBe(Condition.visible);
    }

    //на запуск 2х тестов предоставляется 2 аргумента в CvsSource ({1,2}, {1,2})
    //данный тест пишется на случай проверки лишь единичных параметров
    @CsvSource(value = {"Selenide| лаконичные и стабильные UI тесты на Java",
    "Allure| Beauty Tips, Trends & Product Reviews"
    },
    delimiter = '|')
    @Tag("blocker")
    //0 - 1ый параметр войда, 1 - второй параметр войда
    @ParameterizedTest(name = "Поиск в ya.ru слова {0} и проверка отображения текста {1}")
    void commonYaSearchTest(String searchQuery, String expectedResult) {
        open("https://ya.ru/");
        $("#text").setValue(searchQuery);
        $("button[type='submit']").click();
        $$("li.serp-item")
                .find(Condition.text(expectedResult))
                .shouldBe(Condition.visible);
    }
















}
