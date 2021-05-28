import java.math.BigDecimal;
import java.util.Currency;

public class Money {

    private final BigDecimal value;
    private final Currency currency;

    public Money(BigDecimal value, Currency currency) {
        this.value = value;
        this.currency = currency;
    }

    public Money multiply(BigDecimal source) {
        final BigDecimal ratio = value.multiply(source);
        return new Money(ratio, currency);
    }

    public BigDecimal value() {
        return value;
    }

    public Currency currency() {
        return currency;
    }
}
