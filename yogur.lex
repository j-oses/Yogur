package yogur.jlex;

import yogur.cup.sym;
import yogur.cup.CustomSymbol;
import yogur.error.CompilationException;

import java.util.List;
import java.util.ArrayList;
%%
%cup
%eofval{
	return new CustomSymbol(sym.EOF, line());
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

	private int line() {
		return yyline + 1;
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

{def}					{return new CustomSymbol(sym.DEF, line()); }
{var}					{return new CustomSymbol(sym.VAR, line()); }
{class}					{return new CustomSymbol(sym.CLASS, line()); }
{if}					{return new CustomSymbol(sym.IF, line()); }
{else}					{return new CustomSymbol(sym.ELSE, line()); }
{while}					{return new CustomSymbol(sym.WHILE, line()); }
{for}					{return new CustomSymbol(sym.FOR, line()); }
{in}					{return new CustomSymbol(sym.IN, line()); }
{to}					{return new CustomSymbol(sym.TO, line()); }

{comentarioLinea}		{}
{comentarioMultilinea}	{}
{separador}				{}

{opAsignacion}			{return new CustomSymbol(sym.ASSIGN, line()); }
{opSuma}				{return new CustomSymbol(sym.SUM, line()); }
{opResta}				{return new CustomSymbol(sym.SUBS, line()); }
{opProducto}			{return new CustomSymbol(sym.PROD, line()); }
{opDivision}			{return new CustomSymbol(sym.DIV, line()); }
{opMod}					{return new CustomSymbol(sym.MOD, line()); }
{opAnd}					{return new CustomSymbol(sym.AND, line()); }
{opOr}					{return new CustomSymbol(sym.OR, line()); }
{opNot}					{return new CustomSymbol(sym.NOT, line()); }
{opLRange}				{return new CustomSymbol(sym.LRANGE, line()); }
{opRRange}				{return new CustomSymbol(sym.RRANGE, line()); }
{opDot}					{return new CustomSymbol(sym.DOT, line()); }
{opEq}					{return new CustomSymbol(sym.EQ, line()); }
{opNeq}					{return new CustomSymbol(sym.NEQ, line()); }
{opGeq}					{return new CustomSymbol(sym.GEQ, line()); }
{opGreater}				{return new CustomSymbol(sym.GT, line()); }
{opLeq}					{return new CustomSymbol(sym.LEQ, line()); }
{opLess}				{return new CustomSymbol(sym.LT, line()); }
{opColon}				{return new CustomSymbol(sym.COLON, line()); }
{opArrow}				{return new CustomSymbol(sym.ARROW, line()); }

{corcheteApertura}		{return new CustomSymbol(sym.LSQUARE, line()); }
{corcheteCierre}		{return new CustomSymbol(sym.RSQUARE, line()); }
{parentesisApertura}	{return new CustomSymbol(sym.LPAREN, line()); }
{parentesisCierre}		{return new CustomSymbol(sym.RPAREN, line()); }
{bloqueApertura}		{return new CustomSymbol(sym.LBRACKET, line()); }
{bloqueCierre}			{return new CustomSymbol(sym.RBRACKET, line()); }

{entero}				{return new CustomSymbol(sym.INT, new Integer(yytext()), line()); }
{boolean}				{return new CustomSymbol(sym.BOOL, new Boolean(yytext()), line()); }
{identificador} 		{return new CustomSymbol(sym.ID, yytext(), line()); }
{identificadorTipo}		{return new CustomSymbol(sym.TYPE, yytext(), line()); }
{delimitador}			{return new CustomSymbol(sym.DELIMITER, line()); }
{coma}					{return new CustomSymbol(sym.COMMA, line()); }

. { exceptions.add(new CompilationException("Extraneous character '" + yytext() + "'", line(), CompilationException.Scope.LexicalAnalyzer)); }
