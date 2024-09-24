package com.sfbest.finance;

/**
 * 将数据data对象 转换为相应的字符串
 * @param <T>
 */
public interface CheckDataConvert<T> {

    String serialization(T data);
    T parse(String data);
    CheckDataModel convert(String data);

    String formatter(CheckDataModel dataModel);
}
