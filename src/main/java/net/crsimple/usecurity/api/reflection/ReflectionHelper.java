package net.crsimple.usecurity.api.reflection;

public interface ReflectionHelper {
    ReflectionTable getTable();

    default Object invokeMethod(Object obj, String method, Object... args) {
        try {
            return getTable().get(obj.getClass().getName() + method).invoke(obj,args);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    static ReflectionHelper of(ReflectionTable table) {
        return () -> table;
    }
}
