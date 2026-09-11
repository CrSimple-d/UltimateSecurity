package net.crsimple.usecurity.api.reflection;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.HashMap;
import java.util.Map;

public final class ReflectionTable {
    private final Map<String, MethodHandle> methods;

    private ReflectionTable(Builder builder) {
        this.methods = builder.methods;
    }

    public MethodHandle get(String name) {
        if (!methods.containsKey(name)) {
            throw new RuntimeException("The method is not registered");
        }
        return methods.get(name);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final MethodHandles.Lookup lookup;
        private final Map<String, MethodHandle> methods;

        public Builder() {
            this.lookup = MethodHandles.lookup();
            this.methods = new HashMap<>();
        }

        public Builder register(Class<?> clazz,String method,Class<?> returnType,Class<?>... argsTypes) {
            try {
                methods.put(clazz.getName() + method, lookup.findVirtual(clazz,method, MethodType.methodType(returnType,argsTypes)));
            } catch (NoSuchMethodException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            return this;
        }
        public Builder register(Class<?> clazz,String method,Class<?> returnType) {
            try {
                methods.put(clazz.getName() + method, lookup.findVirtual(clazz,method, MethodType.methodType(returnType)));
            } catch (NoSuchMethodException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            return this;
        }

        public ReflectionTable build() {
            return new ReflectionTable(this);
        }
    }
}
