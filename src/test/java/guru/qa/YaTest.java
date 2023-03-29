package guru.qa;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Selenide.*;

public class YaTest {

    @DisplayName("Поиск в ya.ru слова Selenide")
    @Tag("blocker")
    @Test
    void selenideSearchTest(){
        open("https://ya.ru/");
        $("#text").setValue("Selenide");
        $("button[type='submit']").click();
        $$("li.serp-item")
                .find(Condition.text("Selenide"))
                .shouldBe(Condition.visible);
    }

    @ValueSource(strings = {"Selenide", "Allure"}) //здесь junit-у мы сказали запустить тест 2 раза, так как 2 аргумента
    @Tag("blocker")
    @ParameterizedTest(name = "Поиск в ya.ru слова {0}")
    void commonYaSearchTest(String searchQuery) {
        open("https://ya.ru/");
        $("#text").setValue(searchQuery);
        $("button[type='submit']").click();
        $$("li.serp-item")
                .find(Condition.text(searchQuery))
                .shouldBe(Condition.visible);
    }
}
