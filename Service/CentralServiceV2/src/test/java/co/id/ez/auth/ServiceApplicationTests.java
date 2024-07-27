package co.id.ez.auth;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ServiceApplicationTests {
    public static void main(String[] args) throws Exception {
        Document doc = Jsoup.connect("http://182.23.93.77:88/pl/price-list-h2h.html").get();
        Element tablebody = doc.select("tbody").get(0);
        
        Elements tableRow = tablebody.select("tr");
        int index = 0;
        for (Element element : tableRow) {
            if (index > 0) {
                System.out.println("==== Data "+index+" ====");
                for (int i = 0; i < 3; i++) {
                    System.out.println(element.select("td").get(i).text());
                }
            }
            index++;
        }
    }
}
