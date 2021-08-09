package com.easyept.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Utility class for reusability of code and static values
 */
public final class CreditPaymentUtil {

    /* количество знаков после запятой, учитываемых при расчетах */
    public static int CALC_SCALE = 16;

    /* метод округления, используемый при расчетах */
    public static RoundingMode ROUNDING_MODE = RoundingMode.CEILING;

    /* количество знаков после запятой, выводимых в результат расчета для сумм */
    public static int OUTPUT_AMOUNT_SCALE = 2;

    /* количество знаков после запятой, выводимых в результат процента */
    public static int OUTPUT_PERCENT_SCALE = 4;

    /**
     * Calculating monthly rate of credit
     * @param creditRate yearly rate of credit
     * @return monthly rate of credit
     */
    public static BigDecimal getMonthRate(BigDecimal creditRate) {
        return creditRate
                        .divide(BigDecimal.valueOf(100*12), CALC_SCALE, ROUNDING_MODE)
                        .setScale(OUTPUT_PERCENT_SCALE, ROUNDING_MODE);
    }

    /**
     * Calculating payment per month for credit
     * @param loanBalance credit amount
     * @param creditRate yearly rate of credit
     * @param months length of credit in months
     * @return months payment as BigDecimal
     */
    public static BigDecimal perMonth(BigDecimal loanBalance, BigDecimal creditRate, int months) {
        //perMonth = loanBalance * s
        //s = monthRate / (1-(1+monthRate)^(-n))

        //monthRate = creditRate/(100*12);
        BigDecimal monthRate = creditRate.divide(BigDecimal.valueOf(100*12), CALC_SCALE, ROUNDING_MODE);

        // k1 =  (1 + monthRate)^(-n) = Math.pow((1+monthRate), -months)
        BigDecimal k1 = (BigDecimal.valueOf(1).add(monthRate)).pow(-months, new MathContext(CALC_SCALE)) ;

        //s = monthRate /(1 - k1);
        BigDecimal s = monthRate.divide(BigDecimal.valueOf(1).subtract(k1), CALC_SCALE, ROUNDING_MODE);

        return loanBalance.multiply(s).setScale(OUTPUT_AMOUNT_SCALE, ROUNDING_MODE);
    }

}
