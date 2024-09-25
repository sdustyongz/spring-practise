package com.sfbest.finance;

/**
 * 持续读取数据，若达到数据的末尾 ，返回null，否则按照顺序返回一条数据
 * @param <T>
 */
public interface Reader<T> {
    T get();
    void close();
}
