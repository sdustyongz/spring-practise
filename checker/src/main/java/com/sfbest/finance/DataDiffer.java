package com.sfbest.finance;

import lombok.Data;

@Data
public class DataDiffer {
    KvDataModel one;
    KvDataModel two;

    public DataDiffer(KvDataModel dataOne, KvDataModel dataTwo) {
        this.one = dataOne;
        this.two = dataTwo;
    }
}
