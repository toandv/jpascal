# Pascal syntax diagrams

## Expression

** expression ::= <simple_expression> | <simple_expression> "=" | <> | ">" | "<" | ">=" | "<=" <simple_expression> **
** simple_expression ::= "+" | "-" <term> | <term> "+" | "-" | "OR" <term> **
** term ::= <factor> "*" | "/" | "MOD" | "DIV" | "AND" <factor>
** factor ::= <variable> | <number> | <string> | "NOT" <factor> | (<expression>)**