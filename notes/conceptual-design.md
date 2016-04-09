# The conceptual design of compilers and intepreters

1. Compilers and intepreters can be classified both as programming language translators.
A translator consists of *front end* and *back end*. As a result, a compiler and an intepreter can share the same front end,
but they will each have a different back end.

The front end of e translator reads the source program and performs the inital translation stage. Its primary components are the *parser*, the *scanner*, the *token*, and the *source*.

The *parser* controls the translation process in the front end. It repeatedly asks the *scanner* for the next token, and it analyzes the sequences of tokens to determine what high-level language elements it is traslating, such as arithmetic expressions, assingment statements, or procedure declarations. 
The *parser* alse verifies that what it sees is systactically correct as written in the source program; in other words, the *parser* detects and flags any syntax errors. What the *parser* does is called *parsing*, the parser parses the source program to translate it.

The *scanner* reads the characters of the source programm sequentially and constructs tokens, which are the low-level elements of the source language. For example, Pascal tokens include reserved words such as *BEGIN, END, IF, THEN, ELSE*, or *indentifiers* that names of *variables, prodecures, functions*, and *special symbols* such as *= := + - / **. What the *scanner* does is called *scanning*, and scannes the source program to break it apart into tokens.



 

