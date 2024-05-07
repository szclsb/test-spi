package ch.szclsb.test.spi.app;

import ch.szclsb.test.spi.api.MyServiceProvider;

import java.nio.file.ProviderNotFoundException;
import java.util.ServiceLoader;

public class SpiExecutor {
    public static void main(String[] args) {
        var name = defaultProvider().getService().getName();
        System.out.println(name);
    }

    private static final String DEFAULT_PROVIDER_NAME = "ch.szclsb.test.spi.impl.MyServiceProviderImpl1";

    public static MyServiceProvider defaultProvider() {
        return provider(DEFAULT_PROVIDER_NAME);
    }

    public static MyServiceProvider provider(String providerName) {
        var loader = ServiceLoader.load(MyServiceProvider.class);
        for (var provider : loader) {
            if (provider.getClass().getName().equals(providerName)) {
                return provider;
            }
        }
        throw new ProviderNotFoundException(providerName);
    }
}
