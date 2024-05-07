package ch.szclsb.test.spi.app;

public class SpiExecutor {
    public static void main(String[] args) throws Exception {
        try (var loader = new SpiLoader()) {
            for (var provider : loader.providers()) {
                var service = provider.getService();
                System.out.println(service.getClass().getName() + ".getName() = '" + service.getName() + "'");
            }
        }
    }
}
