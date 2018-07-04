package com.example.gabri.licentafrontend.Activities.Chat_Activities;

import rx.Observable;

/**
 * Created by gabri on 5/13/2018.
 */

public interface ConnectionProvider {

    /**
     * Subscribe this for receive stomp messages
     */
    Observable<String> messages();

    /**
     * Sending stomp messages via you ConnectionProvider.
     * onError if not connected or error detected will be called, or onCompleted id sending started
     * TODO: send messages with ACK
     */
    Observable<Void> send(String stompMessage);

    /**
     * Subscribe this for receive #LifecycleEvent events
     */

    Observable<LifecycleEvent> getLifecycleReceiver();
}
