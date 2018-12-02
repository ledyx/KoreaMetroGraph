package io.github.devwillee.koreametrograph.api;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public abstract class MetroGraphFactory {
    private static final ConcurrentHashMap<String, MetroGraph> instanceManager = new ConcurrentHashMap<>();

    public static <T extends MetroGraphFactory> MetroGraph create(Class<T> clazz) {
        MetroGraph instance = null;

        try {
            if(instanceManager.containsKey(clazz.getName())) {
                instance = instanceManager.get(clazz.getName());
            }
            else {
                MetroGraphFactory factory = clazz.newInstance();
                instance = new MetroGraph(factory.getVerticesJSONPath(), factory.getEdgesJSONPath());
                factory.adjust(instance);
                instanceManager.put(clazz.getName(), instance);
            }
        } catch (InstantiationException | IllegalAccessException | IOException e) {
            e.printStackTrace();
        }

        return instance;
    }

    protected abstract String getVerticesJSONPath();
    protected abstract String getEdgesJSONPath();

    /**
     * 이전/다음역이 여러개 인 경우 처리와 이어지지 않은 노선에 대한 삭제, 혹은 주노선과 부노선등을 설정한다.
     * @param metroGraph
     */
    public abstract void adjust(MetroGraph metroGraph);
}
