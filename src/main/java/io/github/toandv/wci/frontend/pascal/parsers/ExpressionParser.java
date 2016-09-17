package io.github.toandv.wci.frontend.pascal.parsers;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import io.github.toandv.wci.frontend.Parser;
import io.github.toandv.wci.frontend.Scanner;
import io.github.toandv.wci.frontend.Token;
import io.github.toandv.wci.frontend.TokenType;
import io.github.toandv.wci.frontend.pascal.PascalTokenType;
import io.github.toandv.wci.intermediate.icode.ICodeFactory;
import io.github.toandv.wci.intermediate.icode.ICodeNode;
import io.github.toandv.wci.intermediate.icode.ICodeNodeType;
import io.github.toandv.wci.intermediate.icode.impl.ICodeKeyImpl;
import io.github.toandv.wci.intermediate.icode.impl.ICodeNodeTypeImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static io.github.toandv.wci.frontend.pascal.PascalTokenType.*;
import static io.github.toandv.wci.intermediate.icode.impl.ICodeNodeTypeImpl.*;

/**
 * Created by toan on 5/8/16.
 */
public class ExpressionParser extends StatementParser {

    // Set of relational operators
    public static final Set<PascalTokenType> REL_OPS = Sets.immutableEnumSet(EQUALS, NOT_EQUALS, LESS_THAN, LESS_EQUALS,
            GREATER_THAN, GREATER_EQUALS);

    public static final Map<PascalTokenType, ICodeNodeType> REL_OPS_MAP;

    // Set of additive operators.
    public static final Set<PascalTokenType> ADD_OPS = Sets.immutableEnumSet(PLUS, MINUS, PascalTokenType.OR);

    // Map additive operator tokens to node types.
    public static final Map<PascalTokenType, ICodeNodeTypeImpl> ADD_OPS_OPS_MAP;

    // Set of multiplicative operators.
    public static final Set<PascalTokenType> MULT_OPS = Sets.immutableEnumSet(STAR, SLASH, PascalTokenType.MOD,
            PascalTokenType.AND);

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
        MUTABLE_MULT_OPS_OPS_MAP.put(SLASH, DIVIDE);
        MUTABLE_MULT_OPS_OPS_MAP.put(PascalTokenType.MOD, ICodeNodeTypeImpl.MOD);
        MUTABLE_MULT_OPS_OPS_MAP.put(PascalTokenType.AND, ICodeNodeTypeImpl.AND);
        MULT_OPS_OPS_MAP = ImmutableMap.copyOf(MUTABLE_MULT_OPS_OPS_MAP);
    }

    public ExpressionParser(Parser parent) {
        super(parent);
    }

    public ExpressionParser(Scanner scanner) {
        super(scanner);
    }

    @Override
    public ICodeNode parse(Token token) throws Exception {
        return parseExpression(token);
    }

    private ICodeNode parseExpression(Token token) throws Exception {
        // Parse a simple expression make it root of its tree.
        ICodeNode rootNode = parseSimpleExpression(token);
        token = currentToken(); // Update current token.

        // Look for relation operator.
        if (REL_OPS.contains(token.getType())) {
            ICodeNodeType opNodeType = REL_OPS_MAP.get(token.getType());
            ICodeNode opNode = ICodeFactory.createICodeNode(opNodeType);

            opNode.addChild(rootNode);
            token = nextToken(); // Consume the operator token.

            // Parse the second simple expression.
            ICodeNode rightHandExpression = parseSimpleExpression(token);
            opNode.addChild(rightHandExpression);
            rootNode = opNode;
            rootNode.setMultiValuesAttribute(ICodeKeyImpl.EBNF_SYMBOL, PascalNonTerminal.EXPRESSION);
        }
        rootNode.setMultiValuesAttribute(ICodeKeyImpl.EBNF_SYMBOL, PascalNonTerminal.EXPRESSION);
        return rootNode;
    }

    private ICodeNode parseSimpleExpression(Token token) throws Exception {
        // Example: alpha + 3 + beta - 5.
        TokenType signType = null;

        // Look for leading + or - sign.
        if (token.getType() == MINUS || token.getType() == PLUS) {
            signType = token.getType();
            token = nextToken(); // Consume the leading sign.
        }

        // Parse a term and make it the root.
        // Create a new parser every time in favour of stateless and thread-safety
        TermParser  termParser = new TermParser(this);
        ICodeNode rootNode = termParser.parse(token);

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
            ICodeNode rightHandleNode = termParser.parse(token);
            opNode.addChild(rightHandleNode);

            // The operator node becomes the root node.
            rootNode = opNode;
            rootNode.setMultiValuesAttribute(ICodeKeyImpl.EBNF_SYMBOL, PascalNonTerminal.SIMPLE_EXPRESSION);
            token = currentToken(); // Update current token.
        }
        rootNode.setMultiValuesAttribute(ICodeKeyImpl.EBNF_SYMBOL, PascalNonTerminal.SIMPLE_EXPRESSION);
        return rootNode;
    }


}
