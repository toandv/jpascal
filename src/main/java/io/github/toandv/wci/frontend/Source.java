
package io.github.toandv.wci.frontend;

import io.github.toandv.wci.frontend.pascal.tokens.PascalToken;
import io.github.toandv.wci.message.*;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * The framework class representing the source program.
 *
 * @author toandv
 */
public class Source implements AutoCloseable, MessageProducer {

    private BufferedReader reader;

    private String line;

    private int lineNum;

    private int currentPos;

    private MessageHandler messageHandler;

    public Source(BufferedReader reader) {
        this.reader = reader;
        this.currentPos = -2; // set to 2 to read the first line

        messageHandler = new MessageHandler();
    }

    /**
     * Return the current char at the the current position
     *
     * @return
     */
    public char currentChar() throws Exception {
        // first line?
        if (currentPos == -2) {
            readLine();
            return nextChar();
        }

        // at end of file?
        if (line == null) {
            return PascalToken.EOF_CHAR;
        }

        // at end of line?
        if (currentPos == -1 || currentPos == line.length()) {
            return PascalToken.EOL_CHAR;
        }

        // Need to read next line?
        if (currentPos > line.length()) {
            readLine();
            return nextChar();
        }
        // return char at current pos
        return line.charAt(currentPos);
    }

    /**
     * Consume the current char and return the next char.
     * <p>
     * If nextChar() is called first, this source cannot read anything at all.
     *
     * @return
     */
    public char nextChar() throws Exception {
        currentPos++;
        return currentChar();
    }

    /**
     * Return the source char following the current char without consuming the current char
     *
     * @return the following char
     */
    public char peekChar() throws Exception {
        currentChar();

        if (line == null) {
            return PascalToken.EOF_CHAR;
        }
        int nextPos = currentPos + 1;

        return nextPos < line.length() ? line.charAt(nextPos) : PascalToken.EOL_CHAR;
    }

    /**
     * Read the next source line
     *
     * @throws IOException
     */
    private void readLine() throws IOException {
        line = reader.readLine(); // null at the end of the source
        currentPos = -1;

        if (line != null) {
            lineNum++;
        }

        if (line != null) {
            sendMessage(new Message(MessageType.SOURCE_LINE, new Object[]{lineNum, line}));
        }
    }

    /**
     * Close the source
     */
    public void close() throws Exception {
        if (reader != null) {
            reader.close();
        }
    }

    public String getLine() {
        return line;
    }

    public int getLineNum() {
        return lineNum;
    }

    public int getCurrentPos() {
        return currentPos;
    }

    @Override
    public void addMessageListener(MessageListener listener) {
        messageHandler.addListener(listener);
    }

    @Override
    public void removeMessageListener(MessageListener listener) {
        messageHandler.removeListener(listener);
    }

    @Override
    public void sendMessage(Message message) {
        messageHandler.sendMessage(message);
    }

}
