package com.gus.kzis.data;

import com.gus.kzis.model.Profession;
import com.gus.kzis.service.ProfessionService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class DataCollector extends DataCollectorSetting {

    @Autowired
    ProfessionService professionService;


    public void collectStructure() throws Exception {
        createPaginationList();
        for (int i = 0; i < paginationList.size(); i++) {
//            Thread.sleep(3000 + (long) Math.random() * 3000);
            Document paginationPage = null;
            try {
                paginationPage = Jsoup.connect(paginationList.get(i))
                        .proxy("10.51.55.34", 8080)
                        .userAgent(USER_AGENT)
                        .referrer(REFERRER)
                        .timeout(12000)
                        .ignoreHttpErrors(true)
                        .followRedirects(true)
                        .get();
            } catch (IOException e) {
                e.printStackTrace();
            }

            assert paginationPage != null;
            Elements table = paginationPage.select("table.job-classification_search-results.results-grid");
            Elements singleProfession = table.select("tr");
            for (Element element : singleProfession) {
                String classificationId = element.select("td.first").text();
                String link = element.select("td.last >a.viewMore").attr("abs:href");
                String name = element.select("td.last >a.viewMore").text();
                jobOffersList.add(link);
//                jobOffersList.add(
//                        "http://psz.praca.gov.pl/rynek-pracy/bazy-danych/klasyfikacja-zawodow-i-specjalnosci/wyszukiwarka-opisow-zawodow//-/klasyfikacja_zawodow/zawod/"+classificationId+"?_jobclassificationportlet_WAR_nnkportlet_backUrl=http%3A%2F%2Fpsz.praca.gov.pl%2Frynek-pracy%2Fbazy-danych%2Fklasyfikacja-zawodow-i-specjalnosci%2Fwyszukiwarka-opisow-zawodow%2F%2F-%2Fklasyfikacja_zawodow%2Flitera%2F"+Character.toString(name.charAt(0)));
                professionLinksMap.put(name, link);
                System.out.println(name+ ": "+link);
            }
        }
        jobOffersList.removeAll(Arrays.asList("", null));
    }


    public void collectData() throws Exception {
        System.out.println(jobOffersList.size());
        for (int i = 0; i < jobOffersList.size(); i++) {
//            Thread.sleep(3000 + (long) Math.random() * 3000);
            Document document = Jsoup.connect(
                            jobOffersList.get(i))
                            .proxy("10.51.55.34", 8080)
                            .userAgent(USER_AGENT)
                            .referrer(REFERRER)
                            .timeout(12000)
                            .ignoreHttpErrors(true)
                            .followRedirects(true)
                            .get();

            Elements description = document.select("table.job-classification_search-results.viewDetail");
            for (Element element : description) {
                Profession profession = new Profession();
                profession.setSynthesis(searchForSynthesis(element));
                profession.setName(searchForName(element));
                profession.setCode(searchFodCode(element));
                profession.setTasks(searchForTasks(element));
                profession.setAdditionalTasks(searchForAdditionalTasks(element));
                profession.setUrl(jobOffersList.get(i));
                professionService.save(profession);
            }
        }
    }

    private List<String> createPaginationList() {
        for (char letter = 'A'; letter <= 'Z'; letter++) {
            paginationList.add("http://psz.praca.gov.pl/rynek-pracy/bazy-danych/klasyfikacja-zawodow-i-specjalnosci/wyszukiwarka-opisow-zawodow//-/klasyfikacja_zawodow/litera/" + letter);
        }
        paginationList.add("http://psz.praca.gov.pl/rynek-pracy/bazy-danych/klasyfikacja-zawodow-i-specjalnosci/wyszukiwarka-opisow-zawodow//-/klasyfikacja_zawodow/litera/%C5%81");
        paginationList.add("http://psz.praca.gov.pl/rynek-pracy/bazy-danych/klasyfikacja-zawodow-i-specjalnosci/wyszukiwarka-opisow-zawodow//-/klasyfikacja_zawodow/litera/%C5%9A");
        paginationList.add("http://psz.praca.gov.pl/rynek-pracy/bazy-danych/klasyfikacja-zawodow-i-specjalnosci/wyszukiwarka-opisow-zawodow//-/klasyfikacja_zawodow/litera/%C5%BB");
        return paginationList;
    }

    private String searchForAdditionalTasks(Element element) {
        String additionalTasks = element.getElementsContainingOwnText
                ("Dodatkowe zadania zawodowe:").next().text();
        return additionalTasks;
    }

    private String searchForTasks(Element element) {
        String tasks = element.getElementsContainingOwnText("Zadania zawodowe:").next().text();
        return tasks;
    }

    private String searchFodCode(Element element) {
        String code = element.getElementsContainingOwnText("Kod:").next().text();
        return code;
    }

    private String searchForSynthesis(Element element) {
        String synthesis = element.getElementsContainingOwnText("Synteza:").next().text();
        return synthesis;
    }

    private String searchForName(Element element) {
        String name = element.getElementsContainingOwnText("Nazwa:").next().text();
        return name;
    }
}




