package guru.qa;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FilesTestValuesNull {

    private String str = "JUnit instance lifecycle"; //данное поле принадлежит к объектам этого класса

    //данный метод по асболютному пути ищет конкретно на моём компьютере, что неудобно, если допустим работаю на проекте
    @Test
    @DisplayName("Загрузка файла по абсолютному пути (не рекомендуется)")
    void filenameShouldDisplayedAfterUploadActionAbsolutePathTest() {
        open("https://the-internet.herokuapp.com/upload");
        File exampleFile = new File("C:\\Users\\Rustam\\IdeaProjects\\qa_guru_9_8_files\\src\\test\\resources\\example.txt");
        $("input[type='file']").uploadFile(exampleFile);
        $("#file-submit").click();
        $("#uploaded-files").shouldHave(text("example.txt"));
        str = null;
    }

    //Тест не упадёт, так как они не равнозависимы, str в первом тесте = 0, во втором уже не будет
    //Если бы мы добавили static, то упал бы второй тест, так как полю вначале присваивается 0
    //=> значение str становится 0
    @Test
    @DisplayName("Загрузка файла по относительному пути (рекомендуется!)")
    void filenameShouldDisplayedAfterUploadActionFromClasspathTest() {
        Assertions.assertNotNull(str);
        open("https://the-internet.herokuapp.com/upload");
        $("input[type='file']").uploadFromClasspath("example.txt");
        //ищет корень класса (метод uploadFromClasspath) в ресурсах
        $("#file-submit").click();
        $("#uploaded-files").shouldHave(text("example.txt"));
    }
}





















