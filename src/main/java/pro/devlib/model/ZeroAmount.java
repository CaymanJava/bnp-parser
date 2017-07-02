package pro.devlib.model;


import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class ZeroAmount extends Amount{
    private final double value = 0;
    private final String currency = "PLN";

    public ZeroAmount() {
        super();
    }

    public ZeroAmount(double value, String currency) {
        super(0, "PLN");
    }
}
