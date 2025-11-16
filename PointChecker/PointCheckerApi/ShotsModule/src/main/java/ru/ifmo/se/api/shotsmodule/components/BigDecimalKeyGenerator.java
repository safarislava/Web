package ru.ifmo.se.api.shotsmodule.components;


import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class BigDecimalKeyGenerator implements KeyGenerator {
    @Override
    @NonNull
    public Object generate(@NonNull Object target, Method method, Object... params) {
        StringBuilder key = new StringBuilder();

        key.append(method.getName());
        for (Object param : params) {
            if (param instanceof BigDecimal) {
                key.append("_").append(((BigDecimal) param).setScale(1, RoundingMode.HALF_EVEN).toPlainString());
            } else {
                key.append("_").append(param.toString());
            }
        }
        return key.toString();
    }
}
