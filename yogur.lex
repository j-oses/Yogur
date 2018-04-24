package yogur.jlex;

import yogur.cup.sym;
import yogur.cup.CustomSymbol;
import yogur.error.CompilationException;

import java.util.List;
import java.util.ArrayList;
%%
%cup
%eofval{
	return new CustomSymbol(sym.EOF, yyline);
%eofval}
%class YogurLex
%public
%unicode
%line
%{
	private List<CompilationException> exceptions = new ArrayList<>();

	public List<CompilationException> getExceptions() {
		return exceptions;
	}
%}

letra = ([A-Z]|[a-z])
digito = [0-9]
entero = {digito}+
boolean = ((true)|(false))
separador = [ \t\b\r]
delimitador = \n
comentarioLinea = //[^\n]*
comentarioMultilinea = /\*(.|\n)*\*/
identificador = [a-z]({letra}|{digito}|_)*
identificadorTipo = [A-Z]({letra}|{digito}|_)*
coma = \,

opAsignacion = "="
opSuma = \+
opResta = \-
opProducto = \*
opDivision = /
opMod = %
opAnd = ((and)|(&&))
opOr = ((or)|(\|\|))
opNot = (\!|(not))
opDot = \.
opLRange = \[:
opRRange = :\]
opEq = "=="
opNeq = "!="
opGeq = ">="
opGreater = ">"
opLeq = "<="
opLess = "<"
opColon = :
opArrow = "->"

corcheteApertura = \[
corcheteCierre = \]
parentesisApertura = \(
parentesisCierre = \)
bloqueApertura = \{
bloqueCierre = \}

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

{def}					{return new CustomSymbol(sym.DEF, yyline); }
{var}					{return new CustomSymbol(sym.VAR, yyline); }
{class}					{return new CustomSymbol(sym.CLASS, yyline); }
{if}					{return new CustomSymbol(sym.IF, yyline); }
{else}					{return new CustomSymbol(sym.ELSE, yyline); }
{while}					{return new CustomSymbol(sym.WHILE, yyline); }
{for}					{return new CustomSymbol(sym.FOR, yyline); }
{in}					{return new CustomSymbol(sym.IN, yyline); }
{to}					{return new CustomSymbol(sym.TO, yyline); }

{comentarioLinea}		{}
{comentarioMultilinea}	{}
{separador}				{}

{opAsignacion}			{return new CustomSymbol(sym.ASSIGN, yyline); }
{opSuma}				{return new CustomSymbol(sym.SUM, yyline); }
{opResta}				{return new CustomSymbol(sym.SUBS, yyline); }
{opProducto}			{return new CustomSymbol(sym.PROD, yyline); }
{opDivision}			{return new CustomSymbol(sym.DIV, yyline); }
{opMod}					{return new CustomSymbol(sym.MOD, yyline); }
{opAnd}					{return new CustomSymbol(sym.AND, yyline); }
{opOr}					{return new CustomSymbol(sym.OR, yyline); }
{opNot}					{return new CustomSymbol(sym.NOT, yyline); }
{opLRange}				{return new CustomSymbol(sym.LRANGE, yyline); }
{opRRange}				{return new CustomSymbol(sym.RRANGE, yyline); }
{opDot}					{return new CustomSymbol(sym.DOT, yyline); }
{opEq}					{return new CustomSymbol(sym.EQ, yyline); }
{opNeq}					{return new CustomSymbol(sym.NEQ, yyline); }
{opGeq}					{return new CustomSymbol(sym.GEQ, yyline); }
{opGreater}				{return new CustomSymbol(sym.GT, yyline); }
{opLeq}					{return new CustomSymbol(sym.LEQ, yyline); }
{opLess}				{return new CustomSymbol(sym.LT, yyline); }
{opColon}				{return new CustomSymbol(sym.COLON, yyline); }
{opArrow}				{return new CustomSymbol(sym.ARROW, yyline); }

{corcheteApertura}		{return new CustomSymbol(sym.LSQUARE, yyline); }
{corcheteCierre}		{return new CustomSymbol(sym.RSQUARE, yyline); }
{parentesisApertura}	{return new CustomSymbol(sym.LPAREN, yyline); }
{parentesisCierre}		{return new CustomSymbol(sym.RPAREN, yyline); }
{bloqueApertura}		{return new CustomSymbol(sym.LBRACKET, yyline); }
{bloqueCierre}			{return new CustomSymbol(sym.RBRACKET, yyline); }

{entero}				{return new CustomSymbol(sym.INT, new Integer(yytext()), yyline); }
{boolean}				{return new CustomSymbol(sym.BOOL, new Boolean(yytext()), yyline); }
{identificador} 		{return new CustomSymbol(sym.ID, yytext(), yyline); }
{identificadorTipo}		{return new CustomSymbol(sym.TYPE, yytext(), yyline); }
{delimitador}			{return new CustomSymbol(sym.DELIMITER, yyline); }
{coma}					{return new CustomSymbol(sym.COMMA, yyline); }

. { exceptions.add(new CompilationException(yytext(), yyline, CompilationException.Scope.LexicalAnalyzer)); }
