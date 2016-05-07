
package io.github.toandv.wci.backend;

import io.github.toandv.wci.backend.compiler.CodeGenerator;
import io.github.toandv.wci.backend.intepreter.Executor;

public class BackendFactory {

    public static Backend createBackend(String operation) throws Exception {
        if (operation.equalsIgnoreCase("compile")) {
            return new CodeGenerator();
        }
        else if (operation.equalsIgnoreCase("execute")) {
            return new Executor();
        }
        else {
            throw new Exception("Backend factory: Invalid operation '" + operation + "'");
        }
    }
}
