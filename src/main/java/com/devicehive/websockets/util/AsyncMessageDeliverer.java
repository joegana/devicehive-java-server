package com.devicehive.websockets.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Asynchronous;
import javax.inject.Singleton;
import javax.websocket.Session;
import java.io.IOException;

@Singleton
public class AsyncMessageDeliverer {

    private static final Logger logger = LoggerFactory.getLogger(AsyncMessageDeliverer.class);

    @Asynchronous
    public void deliverMessages(final Session session) throws IOException {

        boolean acquired = false;
        try {
            acquired = WebsocketSession.getQueueLock(session).tryLock();
            if (acquired) {
                WebsocketSession.deliverMessages(session);
            }
        } finally {
            if (acquired) {
                WebsocketSession.getQueueLock(session).unlock();
            }
        }

    }


}


