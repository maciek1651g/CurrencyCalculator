import java.util.Currency;
import java.util.Optional;
import java.util.Set;

public class CurrencyExchangeRate {

    private final Set<ExchangeRate> currenciesExchangeRate;

    public CurrencyExchangeRate(Set<ExchangeRate> currenciesExchangeRate) {
        this.currenciesExchangeRate = currenciesExchangeRate;
    }

    public Optional<ExchangeRate> findCurrencyRatio(Currency target) {
        return currenciesExchangeRate.stream()
            .filter(exchangeRate -> exchangeRate.currency().equals(target))
            .findFirst();
    }

}
