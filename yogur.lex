import java_cup.runtime.Symbol;
%%
%cup
%eofval{
  return sym.EOF;
%eofval}

letra = ([A-Z]|[a-z])
digito = [0-9]
entero = [\+,\-]?{digito}*
separador = [\t\b\r\n]
delimitador = $
comentario = //[^\n]*
abrircom = /\*
cerrarcom = \*/
identificador = {letra}({letra}|{digito}|_)*
identificadorTipo = [A-Z]({letra}|{digito}|_)*

opAsignacion = \=
opSuma = \+
opResta = \-
opProducto = \*
opDivision = /
opAnd = (and)|(&&)
opOr = (or)|(\|\|)
opNot = !|(not)
opAcceso = \.
opRange = :
opEq = \=\=
opGeq = >\=
opGreater = >
opLeq = <\=
opLess = <

corcheteApertura = \[
corcheteCierre = \]
parentesisApertura = \(
parentesisCierre = \)
bloqueApertura = \{
bloqueCierre = \}

true = true
false = false
def = def
var = var
class = class
if = if
else = else
while = while
for = for
in = in
to = to

%%

"var"			{return new Symbol(sym.VAR); }
"if"			{return new Symbol(sym.IF); }
"while"			{return new Symbol(sym.WHILE); }
"for"			{return new Symbol(sym.FOR); }
"print"			{return new Symbol(sym.PRINT); }

"=="			{return new Symbol(sym.EQ); }
"#"				{return new Symbol(sym.NEQ); }
"<"				{return new Symbol(sym.LT); }
"<="			{return new Symbol(sym.LTE); }
">"				{return new Symbol(sym.GT); }
">="			{return new Symbol(sym.GTE); }

"("				{return new Symbol(sym.LPAREN); }
")"				{return new Symbol(sym.RPAREN); }
"["				{return new Symbol(sym.LSQUARE); }
"]"				{return new Symbol(sym.RSQUARE); }
"{"				{return new Symbol(sym.LBRACKET); }
"}"				{return new Symbol(sym.RBRACKET); }

","				{return new Symbol(sym.COMMA); }
":"             {return new Symbol(sym.COLON); }
";"				{return new Symbol(sym.SEMICOLON); }
"="			    {return new Symbol(sym.ASSIGN); }
"+"				{return new Symbol(sym.PLUS); }
"-"				{return new Symbol(sym.MINUS); }
"*"				{return new Symbol(sym.MULT); }
"/"				{return new Symbol(sym.DIV); }
"."				{return new Symbol(sym.PERIOD); }

[0-9]+			{return new Symbol(sym.INT, new Integer(yytext())); }
[a-zA-Z]+ 		{return new Symbol(sym.ID, yytext()); }

[ \t\r\n\f]			{/* ignore white space */}
. {System.err.println("Illegal character: " + yytext());}
