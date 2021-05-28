import java.math.BigDecimal;
import java.util.Currency;

public class ExchangeRate {

    private final Currency currency;
    private final BigDecimal rate;

    public ExchangeRate(Currency currency, BigDecimal rate) {
        this.currency = currency;
        this.rate = rate;
    }

    public Currency currency() {
        return currency;
    }

    public BigDecimal rate() {
        return rate;
    }
}
