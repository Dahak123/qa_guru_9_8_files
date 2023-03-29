package guru.qa;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;


import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Selenide.*;

public class YaParamMassiveTest {


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


    @MethodSource("commonYaSearchTest")
    @Tag("blocker")
    @ParameterizedTest(name = "Поиск в ya.ru слова {0} и проверка отображения текста {1}")
    void commonYaSearchTest(String searchQuery, List<String> expectedResult) {
        open("https://ya.ru/");
        $("#text").setValue(searchQuery);
        $("button[type='submit']").click();
        $$("li.serp-item")
                .find(Condition.text(expectedResult.get(0)))
                .shouldBe(Condition.visible);
    }

    //Arguments.of("Allure", "Beauty Tips, Trends & Product Reviews")
    static Stream<String> commonYaSearchTest(){
        return Stream.of(String.format("Selenide","Allure"));
    }














}
