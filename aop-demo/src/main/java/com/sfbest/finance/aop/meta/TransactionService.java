package com.sfbest.finance.aop.meta;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Fact
public @interface TransactionService {
}
