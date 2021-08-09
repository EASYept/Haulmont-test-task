package com.easyept.util;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class CreditPaymentUtilTest {

    @Test
    void TestPaymentPerMonth() {
        double expected = 664.29;
        BigDecimal get = CreditPaymentUtil.perMonth(BigDecimal.valueOf( 20000), BigDecimal.valueOf(12), 36);
        //if 0 -> expected == get
        assert (0 == get.compareTo(BigDecimal.valueOf(expected)));
    }

}