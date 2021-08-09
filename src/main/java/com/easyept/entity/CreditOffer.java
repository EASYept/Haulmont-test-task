package com.easyept.entity;

import com.easyept.util.CreditPaymentUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class CreditOffer {
    @Id
    @GeneratedValue
    private UUID uuid;

    @ManyToOne
    private Client client;

    @ManyToOne
    private Credit credit;

    private float amount;

    private int months;

    private LocalDate startingDate;

    /**
     * Do not saving to data base
     * (i think it's waste of space, because for every offer this list would be about 10-120 objects long.
     * And you can generate this pretty fast)
     */
    @Transient
    private List<Payment> paymentSchedule;

    public CreditOffer(Client client, Credit credit, float amount, int months, LocalDate startingDate) {
        this.client = client;
        this.credit = credit;
        setAmount(amount);
        this.months = months;
        this.startingDate = startingDate;
        this.paymentSchedule = generateSchedule(startingDate, months, amount, credit.getCreditRate());
    }

    public void setAmount(float amount) {
        if (credit == null){
            this.amount = amount;
        } else if (amount <= credit.getCreditLimit()) {
            this.amount = amount;
        } else {
            this.amount = credit.getCreditLimit();
        }
    }

    public List<Payment> getPaymentSchedule() {
        if (startingDate == null ||  client == null || credit == null || months == 0 || amount == 0 ) {
            return null;
        }
        this.paymentSchedule = generateSchedule(startingDate, months, amount, credit.getCreditRate());
        return paymentSchedule;
    }

    public List<Payment> generateSchedule(LocalDate startingDate, int months, float amount, float rate) {
        List<Payment> listToReturn = new ArrayList<>(months);

        BigDecimal bodyRemainder = BigDecimal.valueOf(amount);
        BigDecimal yearRate = BigDecimal.valueOf(rate);
        BigDecimal perMonth = CreditPaymentUtil.perMonth(bodyRemainder, yearRate, months);
        LocalDate nextDate = startingDate;

        for (int i=0; i < months; i++){
            BigDecimal bodyRepay;
            BigDecimal percentRepay;

            nextDate = nextDate.plusMonths(1);
            percentRepay = (bodyRemainder
                            .multiply(CreditPaymentUtil.getMonthRate(yearRate)))
                            .setScale(2, CreditPaymentUtil.ROUNDING_MODE);
            bodyRepay = perMonth.subtract(percentRepay); //perMonth - percentRepay
            bodyRemainder = bodyRemainder.subtract(bodyRepay); // bodyRemainder - bodyRepay
            listToReturn.add(
                    new Payment(
                        nextDate,
                        bodyRepay
                                .add(percentRepay)
//                                .setScale(2, CreditPaymentUtil.ROUNDING_MODE)
                                .floatValue(),
                        bodyRepay
//                                .setScale(2, CreditPaymentUtil.ROUNDING_MODE)
                                .floatValue(),
                        percentRepay
//                                .setScale(2, CreditPaymentUtil.ROUNDING_MODE)
                                .floatValue()));
        }
        return listToReturn;
    }


    @Data
    @NoArgsConstructor
    public static class Payment {

        private LocalDate date;
        private float paymentAmount;
        private float bodyPayment;
        private float percentPayment;

        public Payment(LocalDate date, float paymentAmount, float bodyPayment, float percentPayment) {
            this.date = date;
            this.paymentAmount = paymentAmount;
            this.bodyPayment = bodyPayment;
            this.percentPayment = percentPayment;
        }

    }

}
