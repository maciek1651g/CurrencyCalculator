import java.util.Currency;
import java.util.Optional;

public class CurrencyCalculator {

    private final CurrencyExchangeRate currencyExchangeRate;

    public CurrencyCalculator(CurrencyExchangeRate currencyExchangeRate) {
        this.currencyExchangeRate = currencyExchangeRate;
    }

    Money convertCurrency(Money source, Currency targetCurrency) throws CurrencyNotFoundException {
        final Optional<ExchangeRate> exchangeRate = currencyExchangeRate.findCurrencyRatio(targetCurrency);
        if(exchangeRate.isPresent()) {
            return processConverting(exchangeRate.get(), source);
        } else {
            throw new CurrencyNotFoundException();
        }
    }

    private Money processConverting(ExchangeRate exchangeRate, Money source) {
        final Money instantExchangeRate = new Money(exchangeRate.rate(), exchangeRate.currency());
        return instantExchangeRate.multiply(source.value());
    }

}
