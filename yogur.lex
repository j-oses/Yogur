/* Jorge Osés Grijalba
 * Álvaro Rodríguez García
 */

package yogur.jlex;

import java_cup.runtime.Symbol;

import yogur.cup.sym;
import yogur.error.CompilationException;

import java.util.List; 
import java.util.ArrayList;
import java.util.HashMap;
%%
%cup
%eofval{
	return new Symbol(sym.EOF, line(), column());
%eofval}
%class YogurLex
%public
%unicode
%line
%column
%init{
	reservedWords.put("def", sym.DEF);
	reservedWords.put("var", sym.VAR);
	reservedWords.put("class", sym.CLASS);
	reservedWords.put("if", sym.IF);
	reservedWords.put("else", sym.ELSE);
	reservedWords.put("while", sym.WHILE);
	reservedWords.put("for", sym.FOR);
	reservedWords.put("in", sym.IN);
	reservedWords.put("to", sym.TO);
%init}
%{
	private List<CompilationException> exceptions = new ArrayList<>();

	private HashMap<String, Integer> reservedWords = new HashMap<>();

	public List<CompilationException> getExceptions() {
		return exceptions;
	}

	private int line() {
		return yyline + 1;
	}

	private int column() {
		return yycolumn + 1;
	}
%}

letra = ([A-Z]|[a-z])
digito = [0-9]
entero = {digito}+
boolean = ((true)|(false))
separador = [ \t\b\r]
delimitador = ({separador}*\n{separador}*)+
comentarioLinea = "//"[^\n]*
comentarioMultilinea = "/*"(.|\n)*"*/"
identificador = [a-z]({letra}|{digito}|_)*
identificadorTipo = [A-Z]({letra}|{digito}|_)*
coma = \,

opAsignacion = "="
opSuma = \+
opResta = \-
opProducto = \*
opDivision = "/"
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
bloqueApertura = \{({separador}*\n{separador}*)*
bloqueCierre = ({separador}*\n{separador}*)*\}

%%

{comentarioLinea}		{}
{comentarioMultilinea}	{}

{opAsignacion}			{return new Symbol(sym.ASSIGN, line(), column()); }
{opSuma}				{return new Symbol(sym.SUM, line(), column()); }
{opResta}				{return new Symbol(sym.SUBS, line(), column()); }
{opProducto}			{return new Symbol(sym.PROD, line(), column()); }
{opDivision}			{return new Symbol(sym.DIV, line(), column()); }
{opMod}					{return new Symbol(sym.MOD, line(), column()); }
{opAnd}					{return new Symbol(sym.AND, line(), column()); }
{opOr}					{return new Symbol(sym.OR, line(), column()); }
{opNot}					{return new Symbol(sym.NOT, line(), column()); }
{opLRange}				{return new Symbol(sym.LRANGE, line(), column()); }
{opRRange}				{return new Symbol(sym.RRANGE, line(), column()); }
{opDot}					{return new Symbol(sym.DOT, line(), column()); }
{opEq}					{return new Symbol(sym.EQ, line(), column()); }
{opNeq}					{return new Symbol(sym.NEQ, line(), column()); }
{opGeq}					{return new Symbol(sym.GEQ, line(), column()); }
{opGreater}				{return new Symbol(sym.GT, line(), column()); }
{opLeq}					{return new Symbol(sym.LEQ, line(), column()); }
{opLess}				{return new Symbol(sym.LT, line(), column()); }
{opColon}				{return new Symbol(sym.COLON, line(), column()); }
{opArrow}				{return new Symbol(sym.ARROW, line(), column()); }

{entero}				{return new Symbol(sym.INT, line(), column(), new Integer(yytext())); }
{boolean}				{return new Symbol(sym.BOOL, line(), column(), new Boolean(yytext())); }
{identificador} 		{if (reservedWords.containsKey(yytext())) {
							return new Symbol(reservedWords.get(yytext()), line(), column());
						} else {
							return new Symbol(sym.ID, line(), column(), yytext());
						}}
{identificadorTipo}		{return new Symbol(sym.TYPE, line(), column(), yytext()); }

{corcheteApertura}		{return new Symbol(sym.LSQUARE, line(), column()); }
{corcheteCierre}		{return new Symbol(sym.RSQUARE, line(), column()); }
{parentesisApertura}	{return new Symbol(sym.LPAREN, line(), column()); }
{parentesisCierre}		{return new Symbol(sym.RPAREN, line(), column()); }
{bloqueApertura}		{return new Symbol(sym.LBRACKET, line(), column()); }
{bloqueCierre}			{return new Symbol(sym.RBRACKET, line(), column()); }

{delimitador}			{return new Symbol(sym.DELIMITER, line(), column()); }
{coma}					{return new Symbol(sym.COMMA, line(), column()); }
{separador}				{}

. { exceptions.add(new CompilationException("Extraneous character '" + yytext() + "'", line(), column(), CompilationException.Scope.LexicalAnalyzer)); }
