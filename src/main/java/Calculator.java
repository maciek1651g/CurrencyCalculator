import java.math.BigDecimal;
import java.util.Currency;
import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;

public class Calculator {

    private final static String DEFAULT_CURRENCY = "EUR";
    private final static String FILEPATH = "eurofxref-daily.xml";

    public static void main(String[] args) {

        final ExchangeRateXMLParser exchangeRateXMLParser = new ExchangeRateXMLParser(FILEPATH);
        final Optional<CurrencyExchangeRate> currencyExchangeRate = exchangeRateXMLParser.parseCurrencyExchangeRates();

        if(!currencyExchangeRate.isPresent()) {
            System.out.println("Could Not Read Data From File!");
        }
        final CurrencyCalculator currencyCalculator = new CurrencyCalculator(currencyExchangeRate.get());

        try {

            final Scanner scanner = new Scanner(System.in);

            System.out.print("Provide value to convert("+DEFAULT_CURRENCY+"): ");
            final double source = scanner.nextDouble();

            System.out.print("Provide target currency: ");
            final String targetCurrency = scanner.next();

            final Currency currency = Currency.getInstance(targetCurrency.toUpperCase());
            final Money converted = currencyCalculator.convertCurrency(new Money(BigDecimal.valueOf(source), Currency.getInstance(DEFAULT_CURRENCY)), currency);

            System.out.println("Calculated value: " + converted.value() + " " + converted.currency().getCurrencyCode());

        } catch(CurrencyNotFoundException e) {
            System.out.println("Currency Not Found!");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid Currency Provided!");
        } catch (InputMismatchException e) {
            System.out.println("Invalid Input Data Format!");
        }
    }

}
