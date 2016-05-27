package io.github.toandv.jpascal.frontend

/**
  * Created by toan on 5/27/16.
  */

sealed abstract class TokenType(val text: String)

sealed abstract class ReservedWord(override val text: String) extends TokenType(text)

sealed abstract class SpecialSymbol(override val text: String) extends TokenType(text)

sealed abstract class Identifier(override val text: String) extends TokenType(text)

object ReservedWord {
  case object AND       extends ReservedWord("and")
  case object ARRAY     extends ReservedWord("array")
  case object BEGIN     extends ReservedWord("begin")
  case object CASE      extends ReservedWord("case")
  case object DIV       extends ReservedWord("div")
  case object DO        extends ReservedWord("do")
  case object DOWNTO    extends ReservedWord("downto")
  case object ELSE      extends ReservedWord("else")
  case object END       extends ReservedWord("end")
  case object FILE      extends ReservedWord("file")
  case object FOR       extends ReservedWord("for")
  case object FUNCTION  extends ReservedWord("function")
  case object GOTO      extends ReservedWord("goto")
  case object IF        extends ReservedWord("if")
  case object IN        extends ReservedWord("in")
  case object LABEL     extends ReservedWord("label")
  case object MOD       extends ReservedWord("mod")
  case object NIL       extends ReservedWord("nil")
  case object NOT       extends ReservedWord("not")
  case object OF        extends ReservedWord("of")
  case object OR        extends ReservedWord("or")
  case object PACKED    extends ReservedWord("packed")
  case object PROCEDURE extends ReservedWord("procedure")
  case object PROGRAM   extends ReservedWord("program")
  case object RECORD    extends ReservedWord("record")
  case object REPEAT    extends ReservedWord("repeat")
  case object SET       extends ReservedWord("set")
  case object THEN      extends ReservedWord("then")
  case object TO        extends ReservedWord("to")
  case object TYPE      extends ReservedWord("type")
  case object UNTIL     extends ReservedWord("until")
  case object VAR       extends ReservedWord("var")
  case object WHILE     extends ReservedWord("while")
  case object WITH      extends ReservedWord("with")

  val reservedWords = Map(
   AND.text       -> AND,
   ARRAY.text     -> ARRAY,
   BEGIN.text     -> BEGIN,
   CASE.text      -> CASE,
   DIV.text       -> DIV,
   DO.text        -> DO,
   DOWNTO.text    -> DOWNTO,
   ELSE.text      -> ELSE,
   END.text       -> END,
   FILE.text      -> FILE,
   FOR.text       -> FOR,
   FUNCTION.text  -> FUNCTION,
   GOTO.text      -> GOTO,
   IF.text        -> GOTO,
   IN.text        -> IN,
   LABEL.text     -> LABEL,
   MOD.text       -> MOD,
   NIL.text       -> NIL,
   NOT.text       -> NOT,
   OF.text        -> OF,
   OR.text        -> OR,
   PACKED.text    -> PACKED,
   PROCEDURE.text -> PROCEDURE,
   PROGRAM.text   -> PROGRAM,
   RECORD.text    -> RECORD,
   REPEAT.text    -> RECORD,
   THEN.text      -> THEN,
   TO.text        -> TO,
   TYPE.text      -> TYPE,
   UNTIL.text     -> UNTIL,
   VAR.text       -> VAR,
   WHILE.text     -> WHILE,
   WITH.text      -> WITH
  )

  def withText(text: String) = reservedWords(text)
  def tryWithText(text: String) = reservedWords.get(text)
}

object SpecialSymbol {
//  PLUS("+"), MINUS("-"), STAR("*"), SLASH("/"), COLON_EQUALS(":="),
//  DOT("."), COMMA(","), SEMICOLON(";"), COLON(":"), QUOTE("'"),
//  EQUALS("="), NOT_EQUALS("<>"), LESS_THAN("<"), LESS_EQUALS("<="),
//  GREATER_EQUALS(">="), GREATER_THAN(">"), LEFT_PAREN("("), RIGHT_PAREN(")"),
//  LEFT_BRACKET("["), RIGHT_BRACKET("]"), LEFT_BRACE("{"), RIGHT_BRACE("}"),
//  UP_ARROW("^"), DOT_DOT(".."),
}

object Identifier {
//  val IDENTIFIER: = null
//  ,
//  val INTEGER: = null
//  ,
//  val REAL: = null
//  ,
//  val STRING: = null
//  ,
//  val ERROR: = null
//  ,
//  val END_OF_FILE: = null;
//

}

