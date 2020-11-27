package com.runyuanj.cloud.stream.context;

import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.NoArgsConstructor;
import org.springframework.cloud.stream.binding.BinderAwareChannelResolver;
import org.springframework.messaging.support.GenericMessage;

import static com.alibaba.fastjson.JSON.toJSONString;
import static com.alibaba.fastjson.serializer.SerializerFeature.WriteMapNullValue;
import static com.runyuanj.core.context.SpringContextProvider.getApplicationContext;

@NoArgsConstructor
public class EventDataContent {

    private static EventDataContent eventDataContent = null;

    private BinderAwareChannelResolver channelResolver =
            getApplicationContext().getBean(BinderAwareChannelResolver.class);

    private void send(String target, Object object) {
        this.channelResolver
                .resolveDestination(target)
                .send(new GenericMessage(toJSONString(object, new SerializerFeature[]{WriteMapNullValue})));
    }

    private static EventDataContent instance() {
        if (eventDataContent == null) {
            eventDataContent = new EventDataContent();
        }

        return eventDataContent;
    }

    public static void put(String target, Object object) {
        instance().send(target, object);
    }

    public static void put(Object object) {
        instance().send("runyuanj-stream", object);
    }

    public static void omlog(Object object) {
        instance().send("runyuanj-om-stream", object);
    }
}
