package io.github.toandv.wci.message;

import java.util.ArrayList;
import java.util.List;

public class MessageHandler {
    // XXX mutable???
    private Message message;
    // message
    private List<MessageListener> listeners; // listener

    /**
     * Constructor.
     */
    public MessageHandler() {
        this.listeners = new ArrayList<MessageListener>();
    }

    /**
     * Add a listener to the listener list.
     */
    public void addListener(MessageListener listener) {
        listeners.add(listener);
    }

    /**
     * Remove a listener from the listener list.
     */
    public void removeListener(MessageListener listener) {
        listeners.remove(listener);
    }

    /**
     * Notify listeners after setting the message.
     * 
     */
    public void sendMessage(Message message) {
        this.message = message;
        notifyListeners();
    }

    /**
     * Notify each listener in the listener list by calling the listener's
     * messageReceived() method.
     */
    private void notifyListeners() {
        for (MessageListener listener : listeners) {
            listener.messageReceived(message);
        }
    }
}
