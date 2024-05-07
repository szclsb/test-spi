package ch.szclsb.test.spi.impl;

import ch.szclsb.test.spi.api.MyService;

public class MyServiceImpl1 implements MyService {
    private final static String name = "test service 1";

    @Override
    public String getName() {
        return name;
    }
}
