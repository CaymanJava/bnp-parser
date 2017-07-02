package pro.devlib.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class Transaction {
    @JsonSerialize(using = ToStringSerializer.class)
    private LocalDate date;
    private String description;
    private Amount amount;
    private Amount balanceAfterOperation;
}
