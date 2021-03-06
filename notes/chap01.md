# The conceptual design of compilers and intepreters

Compilers and intepreters can be classified both as programming language translators.
A translator consists of *front end* and *back end*. As a result, a compiler and an intepreter can share the same front end,
but they will each have a different back end.

The front end of a translator reads the source program and performs the inital translation stage. Its primary components are the *parser*, the *scanner*, the *token*, and the *source*.

The *parser* controls the translation process in the front end. It repeatedly asks the *scanner* for the next token, and it analyzes the sequences of tokens to determine what high-level language elements it is translating, such as arithmetic expressions, assingment statements, or procedure declarations. 
The *parser* also verifies that what it sees is systactically correct as written in the source program; in other words, the *parser* detects and flags any syntax errors. What the *parser* does is called *parsing*, the *parser* parses the source program to translate it.

The *scanner* reads the characters of the source programm sequentially and constructs tokens, which are the low-level elements of the source language. For example, Pascal tokens include reserved words such as *BEGIN, END, IF, THEN, ELSE*, or *indentifiers* that names of *variables, prodecures, functions*, and *special symbols* such as *= := + - / **. What the *scanner* does is called *scanning*, and scans the source program to break it apart into tokens.

The conceptual design of the front end:

![alt text] (https://github.com/StudyInDepth/wci/blob/master/notes/images/front-end.png)

An arrow represents a command issued by one compoent to another. 
* The parser tels the scanner to get the next token.
* The scanner reads characters from the source and constructs a new token.
* The token also reads characters from the source, for some purposes, later digging into.

A compiler ultimately translates a source program into machine language object code, sot the primary component of its back end is a code generator.
An intepreter executes the program, so the primary component of its back edn is an executor.

If we want the compiler and the intepreter to share the same front end, so their different back ends need a common intermidate interface with the front end.
The front end performs the initial translation stage, generating intermediate code and a symbol table in the intermediate tier that serve as the common interface.

The intermediate code may be an in-memory tree data structures that represents the statments of the source program.
The symbol table contains information about the symbols (such as indetifiers) contained in the source program.

A compiler's backend processes the intermediate code and the symbol table to generate machine code verion of the source program.
An intepreter's backend processes the intermediate code and the symbol table to execute the program.

To further software reuse, we can design the intermediate code and the symbol table structures to be language independent.
In other words, we can use the same structures for different source languagesymbol table structures to be language independent.

In other words, we can use the same structures for different source languages. Therefore, the back end will also be language independent, it doesn't need to know or core what the source language was.

Below is the more complete conceptual design of a compiler or an intepreter:

![alt text] (https://github.com/StudyInDepth/wci/blob/master/notes/images/conceptual-design-fe-be.png)

## Syntax and Semantics

* The syntax of a programming language is its set of grammar rules that determine whether a statement or expression is correctly written in that language.
* The semantics of language give the meaning of a statement or an expression written in that language.

The parser performs actions based on both th source language's syntax and semantics.
* Scanning the source program and extracting tokens are the syntactic actions. 
* Entering identifiers into symbol table or looking them up are semantic actions
* Generating intermediate code is semantic
* Syntactic actions occur only in the front end, while sematic actions occur in both front end and back end.
* The intermedate code and the symbol table store semantic information.

## Lexcial, Syntax, and Semantic Analyses 

* Lexical analysis is the formal term for scanning, and thus a scanner can be called a lexcial analyzer - Lexer, Tokenizer.
* Syntax analysis is the formal term for parsing, and thus a parser is called a syntax analyzer.
* Sematic analysis involves checking that sematic rules are't broken, for example type checking, which ensures types of operands are consistent with their operators.
* Other operations of semantic analysis are building the symbol table and generating the intemediate code.



 
