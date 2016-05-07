
package io.github.toandv.wci.message;

public interface MessageListener {

    /**
     * Called to receive a message sent by a message producer.
     * 
     */
    public void messageReceived(Message message);
}
