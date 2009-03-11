package org.eclipse.swordfish.core.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;



public class ReflectionUtil {
    public static Object getField(Object instance, String fieldName) {
        try {
            Field field = instance.getClass().getDeclaredField(fieldName);
            Assert.notNull(field);
            field.setAccessible(true);
            return field.get(instance);
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
    }

    public static Object getDeclaredField(Object instance, Class<?> declaredClass, String fieldName) {
        try {
            Field field = declaredClass.getDeclaredField(fieldName);
            Assert.notNull(field);
            field.setAccessible(true);
            return field.get(instance);
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
    }

    public static List<Object> getAnonymousClassInstanceValues(Object anonymousClassInstance) {
        try {
            List<Object> ret = new ArrayList<Object>();
            for (Field field : anonymousClassInstance.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                ret.add(field.get(anonymousClassInstance));
            }
            return ret;
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
    }
}
