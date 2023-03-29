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

public class FilesTest {

    //данный метод по асболютному пути ищет конкретно на моём компьютере, что неудобно, если допустим работаю на проекте
    @Test
    @DisplayName("Загрузка файла по абсолютному пути (не рекомендуется)")
    void filenameShouldDisplayedAfterUploadActionAbsolutePathTest() {
        open("https://the-internet.herokuapp.com/upload");
        File exampleFile = new File("C:\\Users\\Rustam\\IdeaProjects\\qa_guru_9_8_files\\src\\test\\resources\\example.txt");
        $("input[type='file']").uploadFile(exampleFile);
        $("#file-submit").click();
        $("#uploaded-files").shouldHave(text("example.txt"));
        //Пример, когда данный метод может загружать несколько файлов - это exampleFile и exampleFile0
        //File exampleFile = new File("C:\\Users\\Rustam\\IdeaProjects\\qa_guru_9_8_files\\src\\test\\resources\\example.txt");
        //File exampleFile0 = new File("C:\\Users\\Rustam\\IdeaProjects\\qa_guru_9_8_files\\src\\test\\resources\\example.txt");
        //$("input[type='file']").uploadFile(exampleFile, exampleFile0);
    }

    @Test
    @DisplayName("Загрузка файла по относительному пути (рекомендуется!)")
    void filenameShouldDisplayedAfterUploadActionFromClasspathTest() {
        open("https://the-internet.herokuapp.com/upload");
        $("input[type='file']").uploadFromClasspath("example.txt");
        //ищет корень класса (метод uploadFromClasspath) в ресурсах
        $("#file-submit").click();
        $("#uploaded-files").shouldHave(text("example.txt"));
    }

    @Disabled
    //скачать файл можно, только если в элементе содержится href, в ином случае задейсвтуется прокси
    @Test
    @DisplayName("скачивание текстового файла и проверка его содержимого")
    void downloadSimpleTextFileTest() throws IOException {
        open("https://github.com/junit-team/junit5/blob/main/README.md");
        File download = $("#raw-url").download();
        //создание объекта через Стринг для дальнейшей проверки
        String fileContent = IOUtils.toString(new FileReader(download));
        //Assert - проверка на действительность скачиваемого файла по тексту в файле
        assertTrue(fileContent.contains("This repository is the home of the next generation of JUnit, _JUnit 5_."));
    }

    @Disabled
    @Test
    @DisplayName("скачивание PDF файла")
    void pdfFileDownloadTest() throws IOException {
        open("https://junit.org/junit5/docs/current/user-guide/");
        //поиск селектора по тексту и перемещение его в переменную pdf для дальнейшего использования и проверки
        File pdf = $(byText("PDF download")).download();
        //метод PDF взят с билдгрэдла и пермещение его в переменную для проверки скачиваемого PDF файла
        PDF parsedPdf = new PDF(pdf);
        Assertions.assertEquals(180, parsedPdf.numberOfPages);
        //данная переменная parsedPdf несёт в себе несколько методов, например проверка autor
    }

    @Disabled
    @Test
    @DisplayName("скачивание XLS файла")
    void xlsFileDownloadTest() throws IOException {
        open("http://romashka2008.ru/price");
        File xls = $$("a[href*=prajs]")
                .find(text("Скачать Прайс-лист Excel"))
                .download();
        XLS parsedXls = new XLS(xls);
        boolean checkPassed = parsedXls.excel
                .getSheetAt(0)
                .getRow(11)
                .getCell(1)
                .getStringCellValue()
                .contains("693010, Сахалинская обл, Южно-Сахалинск г, им Анкудинова Федора Степановича б-р, дом № 15, корпус А");

        assertTrue(checkPassed);
    }

    @Disabled
    @Test
    @DisplayName("Парсинг CSV файлов")
    void parseCSVFileTest() throws IOException, CsvException {
        //вызов класса лоадер для скачивания файла csv
        ClassLoader classLoader = this.getClass().getClassLoader();
        //обращаемся к InputStream для чтения файла
        try (InputStream is = classLoader.getResourceAsStream("file.csv");
        Reader reader = new InputStreamReader(is)){
            CSVReader csvReader = new CSVReader(reader);//с этим методом можем взаимодействовать с файлом
            // strings - это строчки в csv файле, поэтому переменная создается с листом List массивов
            List<String[]> strings = csvReader.readAll();
            assertEquals(3, strings.size());
        }

    }

    @Disabled
    @Test
    @DisplayName("Парсинг ZIP файлов")
    void parseZipFileTest() throws IOException{
        ClassLoader classLoader = this.getClass().getClassLoader();
        try (InputStream is = classLoader.getResourceAsStream("zipRar.zip");
             ZipInputStream zis = new ZipInputStream(is)){
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null){
                System.out.println(entry.getName());
            }
        }

    }


















}


