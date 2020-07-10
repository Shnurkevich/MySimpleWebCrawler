package com.application;

import com.opencsv.CSVWriter;
import java.io.*;


public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        WebCrawl webCrawler = new WebCrawl();
        Sorting sorting = new Sorting();
        System.out.println("Enter path and name for csv file to create (Введите путь и имя для создания csv файла) :");
        String filePath = reader.readLine();
        File csvFile = new File(filePath);
        System.out.println("Enter amount of pages to web crawl (Введите количество страниц для сканирования) :");

        try {
        webCrawler.setHowManyPagesToParse(Integer.parseInt(reader.readLine())); }
        catch (NumberFormatException e){
            System.out.println("The number is not entered. Please try again.");
            System.exit(1);
        }

        System.out.println("File created: " + filePath + ". Starting web crawling.\n");

        CSVWriter fileWriter = new CSVWriter(new FileWriter(csvFile, true),';',
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END);

        String[] header = { "URL", "Tesla", "Musk", "Gigafactory", "Elon Mask", "Total count"};

        fileWriter.writeNext(header);
        webCrawler.crawlPages("https://en.wikipedia.org/wiki/Elon_Musk", 0, 2);
        fileWriter.writeAll(webCrawler.getDataLines());
        fileWriter.close();
        System.out.println("Crawling finished. Showing top results " +
                "(Сканирования закончено. Показываю лучшие результаты)\n");

        sorting.viewTopTenResults(webCrawler.getToSort());


    }
}
