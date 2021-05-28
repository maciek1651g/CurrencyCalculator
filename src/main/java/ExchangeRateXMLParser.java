import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class ExchangeRateXMLParser {

    private final String filepath;

    public ExchangeRateXMLParser(String filepath) {
        this.filepath = filepath;
    }

    public Optional<CurrencyExchangeRate> parseCurrencyExchangeRates() {

        try {
            final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setAttribute(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new File(filepath));
            NodeList list = document.getElementsByTagName("Cube");
            final Set<ExchangeRate> exchangeRates = new HashSet<>();


            for(int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);
                if(node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String currencyCode = element.getAttribute("currency");
                    String stringRate = element.getAttribute("rate");
                    if(correctExchangeRate(currencyCode, stringRate)) {
                        final Currency currency = Currency.getInstance(currencyCode);
                        final BigDecimal rate = BigDecimal.valueOf(Double.parseDouble(stringRate));
                        exchangeRates.add(new ExchangeRate(currency, rate));
                    }
                }
            }

            return Optional.of(new CurrencyExchangeRate(exchangeRates));

        } catch(Exception e) {
            return Optional.empty();
        }
    }

    private boolean correctExchangeRate(String currency, String rate) {
        return currency != null && !currency.equals("") && rate != null && !rate.equals("");
    }
}
