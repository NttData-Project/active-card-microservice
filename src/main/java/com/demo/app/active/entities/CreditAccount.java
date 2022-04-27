package com.demo.app.active.entities;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

@JsonPropertyOrder({"id","dni","balance","currency","accountNumber","cvc","createdAt","updateAt"})
@Document(collection = "credit_account")
@Data
public class CreditAccount extends Audit{
    @Id
    private String id;

    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private TypeCurrency currency;

    @Field(name = "account_number")
    @Size(min = 16,max = 16)
    private String accountNumber;

    @Range(min = 100,max = 999)
    private Integer cvc;

    @NotEmpty
    @Size(min = 8,max = 11)
    private String identifier;

    @Field(name = "expiration_date")
    private Date expirationDate;

    @Field(name = "payment_date")
    private Date paymentDate;

    @Field(name = "cutoff_date")
    private Date cutoffDate;

    public CreditAccount(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        LocalDate expiration =LocalDate.of(year+5,month+2,day);
        LocalDate payment = LocalDate.of(year,month,20);
        LocalDate cutOff = LocalDate.of(year,month,28);
        expirationDate = Date.from(expiration.atStartOfDay(ZoneId.systemDefault()).toInstant());
        paymentDate = Date.from(payment.atStartOfDay(ZoneId.systemDefault()).toInstant());
        cutoffDate = Date.from(cutOff.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
