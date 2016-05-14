
package io.github.toandv.wci;

import java.io.BufferedReader;
import java.io.FileReader;

import io.github.toandv.wci.backend.Backend;
import io.github.toandv.wci.backend.BackendFactory;
import io.github.toandv.wci.frontend.FrontendFactory;
import io.github.toandv.wci.frontend.Parser;
import io.github.toandv.wci.frontend.Source;
import io.github.toandv.wci.intermediate.icode.ICode;
import io.github.toandv.wci.intermediate.symtab.SymTab;
import io.github.toandv.wci.intermediate.symtab.SymTabStack;
import io.github.toandv.wci.message.Message;
import io.github.toandv.wci.message.MessageListener;
import io.github.toandv.wci.utils.CrossReferencer;
import io.github.toandv.wci.utils.JsonUtils;
import io.github.toandv.wci.utils.ParseTreePrinter;

public class Pascal {

    private static final String FLAGS = "[-ix]";

    private static final String USAGE = "Usage: Pascal execute|compile " + FLAGS + " <source file path>";

    private Parser parser; // language-independent parser

    private Source source; // language-independent scanner

    private ICode iCode; // generated intermediate code

    private SymTab symTab; // generated symbol table

    private SymTabStack symTabStack; // symbol table stack

    private Backend backend; // backend

    public Pascal(String operation, String filePath, String flags) {
        try {
            boolean intermediate = flags.indexOf('i') > -1;
            boolean xref = flags.indexOf('x') > -1;

            source = new Source(new BufferedReader(new FileReader(filePath)));
            source.addMessageListener(new SourceMessageListener());

            parser = FrontendFactory.createParser("Pascal", "top-down", source);
            parser.addMessageListener(new ParserMessageListener());

            backend = BackendFactory.createBackend(operation);
            backend.addMessageListener(new BackendMessageListener());

            parser.parse();
            source.close();

            if (parser.getErrorCount() == 0) {
                iCode = parser.getICode();
                symTabStack = parser.getSymTabStack();
                symTab = parser.getSymTab();

                if (xref) {
                    CrossReferencer crossReferencer = new CrossReferencer();
                    crossReferencer.print(symTabStack);
                }

                if (intermediate) {
                    ParseTreePrinter treePrinter = new ParseTreePrinter(System.out);
                    treePrinter.print(iCode);
                }

                backend.process(iCode, symTab);
            }
        } catch (Exception ex) {
            System.out.println("***** Internal translator error. *****");
            ex.printStackTrace();
        }
    }

    public static void main(String args[]) {
        try {
            args = new String[] { "compile", "-ix", "/home/toan/Study/Dropbox/WS/wci/src/test/resources/assignments" };
            String operation = args[0];
            // Operation.
            if (!(operation.equalsIgnoreCase("compile") || operation.equalsIgnoreCase("execute"))) {
                throw new Exception();
            }

            int i = 0;
            String flags = "";

            // Flags.
            while ((++i < args.length) && (args[i].charAt(0) == '-')) {
                flags += args[i].substring(1);
            }

            // Source path.
            if (i < args.length) {
                String path = args[i];
                new Pascal(operation, path, flags);
            } else {
                throw new Exception();
            }
        } catch (Exception ex) {
            System.out.println(USAGE);
        }
    }

    private static class JsonFormatMessageListner implements MessageListener {
        @Override
        public void messageReceived(Message message) {
            System.out.println(JsonUtils.toJson(message));
        }
    }

    private static class BackendMessageListener extends JsonFormatMessageListner {
    }

    private class SourceMessageListener extends JsonFormatMessageListner {
    }

    private class ParserMessageListener extends JsonFormatMessageListner {
    }
}
