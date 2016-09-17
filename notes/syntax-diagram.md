# Pascal syntax diagrams

## Expression

** expression ::= <simple_expression> | <simple_expression> "=" | <> | ">" | "<" | ">=" | "<=" <simple_expression> **
** simple_expression ::= "+" | "-" <term> | <term> "+" | "-" | "OR" <term> **
** term ::= <factor> "*" | "/" | "MOD" | "DIV" | "AND" <factor>
** factor ::= <variable> | <number> | <string> | "NOT" <factor> | (<expression>)**

https://en.wikipedia.org/wiki/Syntax_diagram
https://en.wikipedia.org/wiki/Extended_Backus%E2%80%93Naur_Form