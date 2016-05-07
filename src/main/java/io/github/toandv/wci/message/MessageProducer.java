
package io.github.toandv.wci.message;

public interface MessageProducer {

    /**
     * Add a listener to the listener list.
     */
    public void addMessageListener(MessageListener listener);

    /**
     * Remove a listener from the listener list.
     */
    public void removeMessageListener(MessageListener listener);

    /**
     * Notify listeners after setting the message.
     */
    public void sendMessage(Message message);
}
