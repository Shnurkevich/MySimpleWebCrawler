package com.application;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.*;


public class WebCrawl {

    //Constructor
        public WebCrawl() {
        links = new PriorityQueue<>();
    }

    //Class fields
        private PriorityQueue<String> links;                            //Keeping all crawled links
        private List<String[]> dataLines = new ArrayList<>();
        private Map<Integer, String[]> toSort = new HashMap<>();        //For sorting by the total count of keywords
        private int incrementPages = 0;                                 //Counting how many pages crawled
        private int howManyPagesToParse;

    //Getter
        public List<String[]> getDataLines() { return dataLines; }
        public Map<Integer, String[]> getToSort() { return toSort; }

    //Setter
        public void setHowManyPagesToParse(int howManyPagesToParse) {
        this.howManyPagesToParse = howManyPagesToParse;
    }

    //Method checking is URL valid
        private boolean urlIsValid(String URL){

            if(URL == null)                    { return false; }
            if(URL.startsWith("javascript:"))  { return false; }
            if(URL.contains("mailto:"))        { return false; }
            if(URL.startsWith("#"))            { return false; }
            if(URL.contains("#"))              { return false; }
            if(URL.endsWith(".swf"))           { return false; }
            if(URL.endsWith(".pdf"))           { return false; }
            if(URL.endsWith(".png"))           { return false; }
            if(URL.endsWith(".gif"))           { return false; }
            if(URL.endsWith(".jpg"))           { return false; }
            if(URL.endsWith(".jpeg"))          { return false; }

        return true;
    }


    // Method that parse websites and saves results
    public void crawlPages(String URL, int depthOfCrawl,  int maxDepthOfCrawl) {
        if (incrementPages < howManyPagesToParse) {
            if ((!links.contains(URL) && (depthOfCrawl < maxDepthOfCrawl)) && urlIsValid(URL)) {
                try {
                    int teslaCount = 0;
                    int muskCount = 0;
                    int gigafactoryCount = 0;
                    int elonmaskCount = 0;
                    int countAllKeywords;

                    links.add(URL);
                    Document document = Jsoup.connect(URL).maxBodySize(0).timeout(0).ignoreContentType(true).get();
                    String parsedPage = document.text().toLowerCase();
                    String wordsSplit[] = parsedPage.split("\\s+");

                    for (int i = 0; i < wordsSplit.length; i++) {
                        wordsSplit[i] = wordsSplit[i].replaceAll("[^A-Za-zА-Яа-я0-9]", "");
                    }

                    for (int i = 0; i < wordsSplit.length; i++) {
                        if (wordsSplit[i].contains("tesla"))
                            teslaCount++;
                        else if (wordsSplit[i].contains("musk"))
                            muskCount++;
                        else if (wordsSplit[i].contains("gigafactory"))
                            gigafactoryCount++;
                        else if (wordsSplit[i].contains("elon") && wordsSplit[i + 1].contains("mask"))
                            elonmaskCount++;
                    }

                    countAllKeywords = teslaCount + muskCount + gigafactoryCount + elonmaskCount;

                    String res = " [" + URL + "]" + teslaCount + " " + muskCount + " " +
                            gigafactoryCount + " " + elonmaskCount + " " + countAllKeywords;

                    dataLines.add(new String[] {URL, Integer.toString(teslaCount), Integer.toString(muskCount) ,
                            Integer.toString(gigafactoryCount), Integer.toString(elonmaskCount),
                            Integer.toString(countAllKeywords)});

                    toSort.put(incrementPages, new String[] {URL, Integer.toString(teslaCount),
                            Integer.toString(muskCount) , Integer.toString(gigafactoryCount),
                            Integer.toString(elonmaskCount), Integer.toString(countAllKeywords)});

                    Elements linksOnPage = document.select("a[href]");

                    depthOfCrawl++;
                    incrementPages++;
                    System.out.println("Page # " + incrementPages + " parsing.");

                    for (Element page : linksOnPage) {
                        crawlPages(page.attr("abs:href"), depthOfCrawl, maxDepthOfCrawl);
                    }

                } catch (IOException e) {
                    System.err.println("Cannot get page. Continue parsing.");
                }
            }
        }

    }

}

