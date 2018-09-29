package com.xuancao.programframes.Rxbus;

import io.reactivex.Flowable;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;

public class RxBus {

    private final FlowableProcessor<Object> bus = PublishProcessor.create().toSerialized();


    public void send(final Object event) {
        synchronized (RxBus.class) {
            if (this.hasSubscribers()) {
                bus.onNext(event);
            }
        }
    }

    /**
     * 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
     * @param eventType
     * @param <T>
     * @return
     */
    public <T> Flowable<T> toObservable(Class<T> eventType) {
        return bus.ofType(eventType);
    }

    /**
     * 判断是否有订阅者
     *
     * @return
     */
    public boolean hasSubscribers() {
        return bus.hasSubscribers();
    }

}
