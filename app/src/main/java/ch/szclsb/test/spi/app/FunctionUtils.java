package ch.szclsb.test.spi.app;

import java.util.function.Function;

public final class FunctionUtils {
    public interface SafeFunction<T, R> {
        R apply(T t) throws Exception;
    }

     public static <T, R> Function<T, R> safe(SafeFunction<T, R> function) {
        return t -> {
            try {
                return function.apply(t);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
}
