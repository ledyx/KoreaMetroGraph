package io.github.devwillee.koreametrograph.api;

public abstract class MetroGraphFactory {
    public static <T extends MetroGraphFactory> MetroGraphFactory getFactory(Class<T> clazz) {
        MetroGraphFactory factory = null;

        try {
            factory = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return factory;
    }

    public abstract MetroGraph create();
}
