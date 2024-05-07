package ch.szclsb.test.spi.impl;

import ch.szclsb.test.spi.api.MyServiceProvider;

public class MyServiceProviderImpl1 implements MyServiceProvider {
    @Override
    public MyServiceImpl1 getService() {
        return new MyServiceImpl1();
    }
}
