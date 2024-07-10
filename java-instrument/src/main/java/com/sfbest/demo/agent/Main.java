package com.sfbest.demo.agent;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        BusinessService bs = new BusinessService();
        bs.process();
    }
}
