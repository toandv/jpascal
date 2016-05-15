package io.github.toandv.wci.frontend.pascal.parsers;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import io.github.toandv.wci.frontend.Scanner;
import io.github.toandv.wci.frontend.Token;
import io.github.toandv.wci.frontend.TokenType;
import io.github.toandv.wci.frontend.pascal.PascalTokenType;
import io.github.toandv.wci.intermediate.icode.ICodeFactory;
import io.github.toandv.wci.intermediate.icode.ICodeNode;
import io.github.toandv.wci.intermediate.icode.ICodeNodeType;
import io.github.toandv.wci.intermediate.icode.impl.ICodeNodeTypeImpl;
import io.github.toandv.wci.intermediate.symtab.SymTabEntry;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static io.github.toandv.wci.frontend.pascal.PascalErrorCode.IDENTIFIER_UNDEFINED;
import static io.github.toandv.wci.frontend.pascal.PascalErrorCode.MISSING_RIGHT_PAREN;
import static io.github.toandv.wci.frontend.pascal.PascalErrorCode.UNEXPECTED_TOKEN;
import static io.github.toandv.wci.frontend.pascal.PascalTokenType.*;
import static io.github.toandv.wci.intermediate.icode.impl.ICodeKeyImpl.ID;
import static io.github.toandv.wci.intermediate.icode.impl.ICodeKeyImpl.VALUE;
import static io.github.toandv.wci.intermediate.icode.impl.ICodeNodeTypeImpl.*;
import static io.github.toandv.wci.intermediate.icode.impl.ICodeNodeTypeImpl.NOT;

/**
 * Created by toan on 5/8/16.
 */
public class ExpressionParser extends StatementParser {

    // Set of relational operators
    public static final Set<PascalTokenType> REL_OPS = Sets.immutableEnumSet(EQUALS, NOT_EQUALS, LESS_THAN, LESS_EQUALS, GREATER_THAN, GREATER_EQUALS);

    public static final Map<PascalTokenType, ICodeNodeType> REL_OPS_MAP;

    // Set of additive operators.
    public static final Set<PascalTokenType> ADD_OPS = Sets.immutableEnumSet(PLUS, MINUS, PascalTokenType.OR);

    // Map additive operator tokens to node types.
    public static final Map<PascalTokenType, ICodeNodeTypeImpl> ADD_OPS_OPS_MAP;

    // Set of multiplicative operators.
    public static final Set<PascalTokenType> MULT_OPS = Sets.immutableEnumSet(STAR, SLASH, DIV, PascalTokenType.MOD, PascalTokenType.AND);

    // Map multiplicative operator tokens to node types.
    public static final Map<PascalTokenType, ICodeNodeType> MULT_OPS_OPS_MAP;

    static {
        Map<PascalTokenType, ICodeNodeType> MUTABLE_REL_OPS_MAP = new HashMap<>();
        MUTABLE_REL_OPS_MAP.put(EQUALS, EQ);
        MUTABLE_REL_OPS_MAP.put(NOT_EQUALS, NE);
        MUTABLE_REL_OPS_MAP.put(LESS_THAN, LT);
        MUTABLE_REL_OPS_MAP.put(LESS_EQUALS, LE);
        MUTABLE_REL_OPS_MAP.put(GREATER_THAN, GT);
        MUTABLE_REL_OPS_MAP.put(GREATER_EQUALS, GE);
        REL_OPS_MAP = ImmutableMap.copyOf(MUTABLE_REL_OPS_MAP);
    }

    static {
        Map<PascalTokenType, ICodeNodeTypeImpl> MUTABLE_ADD_OPS_OPS_MAP = new HashMap<>();
        MUTABLE_ADD_OPS_OPS_MAP.put(PLUS, ADD);
        MUTABLE_ADD_OPS_OPS_MAP.put(MINUS, SUBTRACT);
        MUTABLE_ADD_OPS_OPS_MAP.put(PascalTokenType.OR, ICodeNodeTypeImpl.OR);
        ADD_OPS_OPS_MAP = ImmutableMap.copyOf(MUTABLE_ADD_OPS_OPS_MAP);
    }

    static {
        Map<PascalTokenType, ICodeNodeType> MUTABLE_MULT_OPS_OPS_MAP = new HashMap<>();
        MUTABLE_MULT_OPS_OPS_MAP.put(STAR, MULTIPLY);
        MUTABLE_MULT_OPS_OPS_MAP.put(SLASH, FLOAT_DIVIDE);
        MUTABLE_MULT_OPS_OPS_MAP.put(DIV, INTEGER_DIVIDE);
        MUTABLE_MULT_OPS_OPS_MAP.put(PascalTokenType.MOD, ICodeNodeTypeImpl.MOD);
        MUTABLE_MULT_OPS_OPS_MAP.put(PascalTokenType.AND, ICodeNodeTypeImpl.AND);
        MULT_OPS_OPS_MAP = ImmutableMap.copyOf(MUTABLE_MULT_OPS_OPS_MAP);
    }


    public ExpressionParser(Scanner scanner) {
        super(scanner);
    }

    public ExpressionParser(StatementParser statementParser) {
        super(statementParser);
    }

    @Override
    public ICodeNode parse(Token token) throws Exception {
        return parseExpression(token);
    }

    private ICodeNode parseExpression(Token token) throws Exception {
        // Parse a simple expression make it root of its tree.
        ICodeNode rootNode = parseSimpleExpresison(token);
        token = currentToken(); // Update current token.

        // Look for relation operator.
        if (REL_OPS.contains(token.getType())) {
            ICodeNodeType opNodeType = REL_OPS_MAP.get(token.getType());
            ICodeNode opNode = ICodeFactory.createICodeNode(opNodeType);

            opNode.addChild(rootNode);
            token = nextToken(); // Consume the operator token.

            // Parse the second simple expression.
            opNode.addChild(parseSimpleExpresison(token));
            rootNode = opNode;
        }
        return rootNode;
    }

    private ICodeNode parseSimpleExpresison(Token token) throws Exception {
        // Example: alpha + 3 + beta - 5.
        TokenType signType = null;

        // Look for leading + or - sign.
        if (token.getType() == MINUS || token.getType() == PLUS) {
            signType = token.getType();
            token = nextToken(); // Consume the leading sign.
        }

        // Parse a term and make it the root.
        ICodeNode rootNode = parseTerm(token);

        // Was there a leading - assign.
        if (signType == MINUS) {
            ICodeNode negateNode = ICodeFactory.createICodeNode(ICodeNodeTypeImpl.NEGATE);
            negateNode.addChild(rootNode);
            rootNode = negateNode;
        }

        token = currentToken();
        // Example: alpha + 3 + beta - 5
        // Loop over addictive operators.
        while (ADD_OPS.contains(token.getType())) {
            ICodeNodeTypeImpl opNodeType = ADD_OPS_OPS_MAP.get(token.getType());
            ICodeNode opNode = ICodeFactory.createICodeNode(opNodeType);
            opNode.addChild(rootNode);

            token = nextToken(); // Consume the operator.

            // Parse another factor.
            opNode.addChild(parseTerm(token));

            // The operator node becomes the root node.
            rootNode = opNode;

            token = currentToken(); // Update current token.
        }
        return rootNode;
    }

    private ICodeNode parseTerm(Token token) throws Exception {
        // Parse a factor and make its node the tree root.
        ICodeNode rootNode = parseFactor(token);

        // Update currnent token
        token = currentToken();

        while (MULT_OPS.contains(token.getType())) {
            ICodeNodeType opNodeType = MULT_OPS_OPS_MAP.get(token.getType());
            ICodeNode opNode = ICodeFactory.createICodeNode(opNodeType);
            opNode.addChild(rootNode);

            token = nextToken(); // Consume op node.

            // Parse another term.
            opNode.addChild(parseFactor(token));

            rootNode = opNode;

            token = currentToken(); // Update current token.
        }
        return rootNode;
    }

    private ICodeNode parseFactor(Token token) throws Exception {
        ICodeNode rootNode = null;
        switch ((PascalTokenType) token.getType()) {
            case IDENTIFIER:
                // look up the identifier in the symbol table.
                String name = token.getText().toLowerCase();
                SymTabEntry id = symTabStack.lookup(name);
                if (id == null) {
                    errorHandler.flag(token, IDENTIFIER_UNDEFINED, this);
                    id = symTabStack.enterLocal(name);
                }
                rootNode = ICodeFactory.createICodeNode(VARIABLE);
                rootNode.setAttribute(ID, id);
                id.appendLineNumber(token.getLineNumber());
                token = nextToken();
                break;
            case INTEGER:
                // Create an INTEGER_CONSTANT node as the root.
                rootNode = ICodeFactory.createICodeNode(INTEGER_CONSTANT);
                rootNode.setAttribute(VALUE, token.getValue());
                token = nextToken();
                break;
            case REAL:
                // Create an REAL_CONSTANT node as the root.
                rootNode = ICodeFactory.createICodeNode(REAL_CONSTANT);
                rootNode.setAttribute(VALUE, token.getValue());
                token = nextToken();
                break;
            case STRING:
                // Create an STRING_CONSTANT node as the root.
                rootNode = ICodeFactory.createICodeNode(STRING_CONSTANT);
                rootNode.setAttribute(VALUE, token.getValue());
                token = nextToken();
                break;
            case NOT:
                token = nextToken(); // Consume the NOT token.

                // Create an NOT node as the root.
                rootNode = ICodeFactory.createICodeNode(NOT);
                // Parse the child factor and add to the root
                rootNode.addChild(parseFactor(token));
                break;
            case LEFT_PAREN:
                token = nextToken(); // Consume the (.
                // Parse an expression and make it the root.
                rootNode = parseExpression(token);
                // Look for the matching ) token
                token = currentToken();
                if (token.getType() == RIGHT_PAREN) {
                    token = nextToken(); // Consume the matching ) token.
                } else {
                    errorHandler.flag(token, MISSING_RIGHT_PAREN, this);
                }
                break;
            default:
                errorHandler.flag(token, UNEXPECTED_TOKEN, this);
                break;
        }
        return rootNode;
    }

}
