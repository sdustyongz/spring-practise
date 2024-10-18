package com.sfbest.finance;

import java.math.BigDecimal;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        long refundMoney = 1000;
        long totalMoney = 101584;
        BigDecimal retundPercent = new BigDecimal(refundMoney).divide(new BigDecimal(totalMoney),2,BigDecimal.ROUND_DOWN);
        System.out.println(retundPercent);
        System.out.println( "Hello World!" );
    }
}
