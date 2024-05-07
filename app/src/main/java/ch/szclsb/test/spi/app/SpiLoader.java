package ch.szclsb.test.spi.app;

import ch.szclsb.test.spi.api.MyServiceProvider;

import java.io.Closeable;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.ProviderNotFoundException;
import java.util.*;
import java.util.function.BinaryOperator;

public class SpiLoader implements Closeable {
    private final URLClassLoader classLoader;

    public SpiLoader() {
        try (var is = SpiExecutor.class.getClassLoader().getResourceAsStream("plugin.properties")) {
            var properties = new Properties();
            properties.load(is);
            var pluginDir = (String) properties.get("plugin-dir");
            var dir = System.getProperty("user.dir");
            try (var stream = Files.list(Paths.get(dir, pluginDir))) {
                var urls = stream
                        .filter(file -> !Files.isDirectory(file) && file.toString().endsWith(".jar"))
                        .map(FunctionUtils.safe(path -> {
                            var url = path.toUri().toURL();
                            System.out.println("Loading " + url);
                            return url;
                        }))
                        .toArray(URL[]::new);
                this.classLoader = new URLClassLoader(urls);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<MyServiceProvider> providers() {
        var result = new ArrayList<MyServiceProvider>();
        var loader = ServiceLoader.load(MyServiceProvider.class, classLoader);
        for (var provider : loader) {
            result.add(provider);
        }
        if (result.isEmpty()) {
            throw new ProviderNotFoundException(MyServiceProvider.class.getName());
        }
        return result;
    }

    @Override
    public void close() throws IOException {
        classLoader.close();
    }
}
