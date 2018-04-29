package yogur.jlex;
import yogur.cup.sym;
import yogur.cup.CustomSymbol;
import yogur.error.CompilationException;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;


public class YogurLex implements java_cup.runtime.Scanner {
	private final int YY_BUFFER_SIZE = 512;
	private final int YY_F = -1;
	private final int YY_NO_STATE = -1;
	private final int YY_NOT_ACCEPT = 0;
	private final int YY_START = 1;
	private final int YY_END = 2;
	private final int YY_NO_ANCHOR = 4;
	private final int YY_BOL = 65536;
	private final int YY_EOF = 65537;

	private List<CompilationException> exceptions = new ArrayList<>();
	private HashMap<String, Integer> reservedWords = new HashMap<>();
	public List<CompilationException> getExceptions() {
		return exceptions;
	}
	private int line() {
		return yyline + 1;
	}
	private java.io.BufferedReader yy_reader;
	private int yy_buffer_index;
	private int yy_buffer_read;
	private int yy_buffer_start;
	private int yy_buffer_end;
	private char yy_buffer[];
	private int yyline;
	private boolean yy_at_bol;
	private int yy_lexical_state;

	public YogurLex (java.io.Reader reader) {
		this ();
		if (null == reader) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(reader);
	}

	public YogurLex (java.io.InputStream instream) {
		this ();
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}

	private YogurLex () {
		yy_buffer = new char[YY_BUFFER_SIZE];
		yy_buffer_read = 0;
		yy_buffer_index = 0;
		yy_buffer_start = 0;
		yy_buffer_end = 0;
		yyline = 0;
		yy_at_bol = true;
		yy_lexical_state = YYINITIAL;

	reservedWords.put("def", sym.DEF);
	reservedWords.put("var", sym.VAR);
	reservedWords.put("class", sym.CLASS);
	reservedWords.put("if", sym.IF);
	reservedWords.put("else", sym.ELSE);
	reservedWords.put("while", sym.WHILE);
	reservedWords.put("for", sym.FOR);
	reservedWords.put("in", sym.IN);
	reservedWords.put("to", sym.TO);
	}

	private boolean yy_eof_done = false;
	private final int YYINITIAL = 0;
	private final int yy_state_dtrans[] = {
		0
	};
	private void yybegin (int state) {
		yy_lexical_state = state;
	}
	private int yy_advance ()
		throws java.io.IOException {
		int next_read;
		int i;
		int j;

		if (yy_buffer_index < yy_buffer_read) {
			return yy_buffer[yy_buffer_index++];
		}

		if (0 != yy_buffer_start) {
			i = yy_buffer_start;
			j = 0;
			while (i < yy_buffer_read) {
				yy_buffer[j] = yy_buffer[i];
				++i;
				++j;
			}
			yy_buffer_end = yy_buffer_end - yy_buffer_start;
			yy_buffer_start = 0;
			yy_buffer_read = j;
			yy_buffer_index = j;
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}

		while (yy_buffer_index >= yy_buffer_read) {
			if (yy_buffer_index >= yy_buffer.length) {
				yy_buffer = yy_double(yy_buffer);
			}
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}
		return yy_buffer[yy_buffer_index++];
	}
	private void yy_move_end () {
		if (yy_buffer_end > yy_buffer_start &&
		    '\n' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
		if (yy_buffer_end > yy_buffer_start &&
		    '\r' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
	}
	private boolean yy_last_was_cr=false;
	private void yy_mark_start () {
		int i;
		for (i = yy_buffer_start; i < yy_buffer_index; ++i) {
			if ('\n' == yy_buffer[i] && !yy_last_was_cr) {
				++yyline;
			}
			if ('\r' == yy_buffer[i]) {
				++yyline;
				yy_last_was_cr=true;
			} else yy_last_was_cr=false;
		}
		yy_buffer_start = yy_buffer_index;
	}
	private void yy_mark_end () {
		yy_buffer_end = yy_buffer_index;
	}
	private void yy_to_mark () {
		yy_buffer_index = yy_buffer_end;
		yy_at_bol = (yy_buffer_end > yy_buffer_start) &&
		            ('\r' == yy_buffer[yy_buffer_end-1] ||
		             '\n' == yy_buffer[yy_buffer_end-1] ||
		             2028/*LS*/ == yy_buffer[yy_buffer_end-1] ||
		             2029/*PS*/ == yy_buffer[yy_buffer_end-1]);
	}
	private java.lang.String yytext () {
		return (new java.lang.String(yy_buffer,
			yy_buffer_start,
			yy_buffer_end - yy_buffer_start));
	}
	private int yylength () {
		return yy_buffer_end - yy_buffer_start;
	}
	private char[] yy_double (char buf[]) {
		int i;
		char newbuf[];
		newbuf = new char[2*buf.length];
		for (i = 0; i < buf.length; ++i) {
			newbuf[i] = buf[i];
		}
		return newbuf;
	}
	private final int YY_E_INTERNAL = 0;
	private final int YY_E_MATCH = 1;
	private java.lang.String yy_error_string[] = {
		"Error: Internal error.\n",
		"Error: Unmatched input.\n"
	};
	private void yy_error (int code,boolean fatal) {
		java.lang.System.out.print(yy_error_string[code]);
		java.lang.System.out.flush();
		if (fatal) {
			throw new Error("Fatal Error.\n");
		}
	}
	private int[][] unpackFromString(int size1, int size2, String st) {
		int colonIndex = -1;
		String lengthString;
		int sequenceLength = 0;
		int sequenceInteger = 0;

		int commaIndex;
		String workString;

		int res[][] = new int[size1][size2];
		for (int i= 0; i < size1; i++) {
			for (int j= 0; j < size2; j++) {
				if (sequenceLength != 0) {
					res[i][j] = sequenceInteger;
					sequenceLength--;
					continue;
				}
				commaIndex = st.indexOf(',');
				workString = (commaIndex==-1) ? st :
					st.substring(0, commaIndex);
				st = st.substring(commaIndex+1);
				colonIndex = workString.indexOf(':');
				if (colonIndex == -1) {
					res[i][j]=Integer.parseInt(workString);
					continue;
				}
				lengthString =
					workString.substring(colonIndex+1);
				sequenceLength=Integer.parseInt(lengthString);
				workString=workString.substring(0,colonIndex);
				sequenceInteger=Integer.parseInt(workString);
				res[i][j] = sequenceInteger;
				sequenceLength--;
			}
		}
		return res;
	}
	private int yy_acpt[] = {
		/* 0 */ YY_NOT_ACCEPT,
		/* 1 */ YY_NO_ANCHOR,
		/* 2 */ YY_NO_ANCHOR,
		/* 3 */ YY_NO_ANCHOR,
		/* 4 */ YY_NO_ANCHOR,
		/* 5 */ YY_NO_ANCHOR,
		/* 6 */ YY_NO_ANCHOR,
		/* 7 */ YY_NO_ANCHOR,
		/* 8 */ YY_NO_ANCHOR,
		/* 9 */ YY_NO_ANCHOR,
		/* 10 */ YY_NO_ANCHOR,
		/* 11 */ YY_NO_ANCHOR,
		/* 12 */ YY_NO_ANCHOR,
		/* 13 */ YY_NO_ANCHOR,
		/* 14 */ YY_NO_ANCHOR,
		/* 15 */ YY_NO_ANCHOR,
		/* 16 */ YY_NO_ANCHOR,
		/* 17 */ YY_NO_ANCHOR,
		/* 18 */ YY_NO_ANCHOR,
		/* 19 */ YY_NO_ANCHOR,
		/* 20 */ YY_NO_ANCHOR,
		/* 21 */ YY_NO_ANCHOR,
		/* 22 */ YY_NO_ANCHOR,
		/* 23 */ YY_NO_ANCHOR,
		/* 24 */ YY_NO_ANCHOR,
		/* 25 */ YY_NO_ANCHOR,
		/* 26 */ YY_NO_ANCHOR,
		/* 27 */ YY_NO_ANCHOR,
		/* 28 */ YY_NO_ANCHOR,
		/* 29 */ YY_NO_ANCHOR,
		/* 30 */ YY_NO_ANCHOR,
		/* 31 */ YY_NO_ANCHOR,
		/* 32 */ YY_NO_ANCHOR,
		/* 33 */ YY_NO_ANCHOR,
		/* 34 */ YY_NO_ANCHOR,
		/* 35 */ YY_NO_ANCHOR,
		/* 36 */ YY_NO_ANCHOR,
		/* 37 */ YY_NO_ANCHOR,
		/* 38 */ YY_NOT_ACCEPT,
		/* 39 */ YY_NO_ANCHOR,
		/* 40 */ YY_NO_ANCHOR,
		/* 41 */ YY_NO_ANCHOR,
		/* 42 */ YY_NO_ANCHOR,
		/* 43 */ YY_NO_ANCHOR,
		/* 44 */ YY_NO_ANCHOR,
		/* 45 */ YY_NOT_ACCEPT,
		/* 46 */ YY_NO_ANCHOR,
		/* 47 */ YY_NO_ANCHOR,
		/* 48 */ YY_NOT_ACCEPT,
		/* 49 */ YY_NO_ANCHOR,
		/* 50 */ YY_NOT_ACCEPT,
		/* 51 */ YY_NO_ANCHOR,
		/* 52 */ YY_NO_ANCHOR,
		/* 53 */ YY_NO_ANCHOR,
		/* 54 */ YY_NO_ANCHOR,
		/* 55 */ YY_NO_ANCHOR,
		/* 56 */ YY_NO_ANCHOR,
		/* 57 */ YY_NO_ANCHOR,
		/* 58 */ YY_NO_ANCHOR,
		/* 59 */ YY_NO_ANCHOR
	};
	private int yy_cmap[] = unpackFromString(1,65538,
"4:8,37:2,5,4:2,2,4:18,37,17,4:3,9,13,4,34,35,3,7,39,8,22,1,25:10,20,4,24,6," +
"23,4:2,32:26,19,4,21,4,33,4,10,31:2,12,27,28,31:5,29,31,11,14,31:2,15,30,18" +
",26,31:5,36,16,38,4:65410,0:2")[0];

	private int yy_rmap[] = unpackFromString(1,60,
"0,1,2,3,1:2,4,5,1,6,1,7,8,9,10,1:2,11,12,13,14,1:2,15,1:2,16,1:3,17,1:5,18," +
"17,18,19,20,17,21,17,1,3,22,23,15,17,24,25,26,27,28,29,30,17,31,32")[0];

	private int yy_nxt[][] = unpackFromString(33,40,
"1,2,3,4,5,6,7,8,9,10,11,56,57,39,40,57,46,12,58,13,14,15,16,17,18,19,57:2,5" +
"9,57:3,20,5,21,22,23,3,24,25,-1:41,26,-1,38,-1:38,45,-1:2,6,-1:31,45,-1:4,6" +
",-1:2,6,-1:31,6,24,-1:7,27,-1:56,28,-1:26,57,47,57,-1,57:2,-1:2,57,-1:6,49," +
"57:6,49:2,-1:12,31,-1:53,32,-1:40,33,-1:24,34,-1:39,35,-1:58,19,-1:24,20:3," +
"-1,20:2,-1:2,20,-1:6,20:9,-1:8,48,-1:2,42,-1:31,48,-1:3,26:4,-1,26:34,-1:10" +
",57:3,-1,57:2,-1:2,57,-1:6,49,57:6,49:2,-1:7,38,-1,50,38:36,-1:13,29,-1:36," +
"57:3,-1,57,30,-1:2,57,-1:6,49,57:6,49:2,-1:8,42,-1:2,42,-1:31,42,-1:18,44,-" +
"1:33,57:2,43,-1,57:2,-1:2,57,-1:6,49,57:6,49:2,-1:7,36,-1,50,38:36,-1:10,57" +
":3,-1,57:2,-1:2,41,-1:6,49,57:6,49:2,-1:16,57:3,-1,57:2,-1:2,57,-1:6,49,54," +
"57:5,49:2,-1:16,57:3,-1,57:2,-1:2,57,-1:6,49,57:3,55,57:2,49:2,-1:16,57:3,-" +
"1,57:2,-1:2,57,-1:6,49,57,37,57:4,49:2,-1:16,57:3,-1,57:2,-1:2,57,-1:6,49,5" +
"7:4,54,57,49:2,-1:16,57:3,-1,51,57,-1:2,57,-1:6,49,57:6,49:2,-1:16,57:3,-1," +
"57,52,-1:2,57,-1:6,49,57:6,49:2,-1:16,53,57:2,-1,57:2,-1:2,57,-1:6,49,57:6," +
"49:2,-1:6");

	public java_cup.runtime.Symbol next_token ()
		throws java.io.IOException {
		int yy_lookahead;
		int yy_anchor = YY_NO_ANCHOR;
		int yy_state = yy_state_dtrans[yy_lexical_state];
		int yy_next_state = YY_NO_STATE;
		int yy_last_accept_state = YY_NO_STATE;
		boolean yy_initial = true;
		int yy_this_accept;

		yy_mark_start();
		yy_this_accept = yy_acpt[yy_state];
		if (YY_NOT_ACCEPT != yy_this_accept) {
			yy_last_accept_state = yy_state;
			yy_mark_end();
		}
		while (true) {
			if (yy_initial && yy_at_bol) yy_lookahead = YY_BOL;
			else yy_lookahead = yy_advance();
			yy_next_state = YY_F;
			yy_next_state = yy_nxt[yy_rmap[yy_state]][yy_cmap[yy_lookahead]];
			if (YY_EOF == yy_lookahead && true == yy_initial) {

	return new CustomSymbol(sym.EOF, line());
			}
			if (YY_F != yy_next_state) {
				yy_state = yy_next_state;
				yy_initial = false;
				yy_this_accept = yy_acpt[yy_state];
				if (YY_NOT_ACCEPT != yy_this_accept) {
					yy_last_accept_state = yy_state;
					yy_mark_end();
				}
			}
			else {
				if (YY_NO_STATE == yy_last_accept_state) {
					throw (new Error("Lexical Error: Unmatched Input."));
				}
				else {
					yy_anchor = yy_acpt[yy_last_accept_state];
					if (0 != (YY_END & yy_anchor)) {
						yy_move_end();
					}
					yy_to_mark();
					switch (yy_last_accept_state) {
					case 1:
						
					case -2:
						break;
					case 2:
						{return new CustomSymbol(sym.DIV, line()); }
					case -3:
						break;
					case 3:
						{}
					case -4:
						break;
					case 4:
						{return new CustomSymbol(sym.PROD, line()); }
					case -5:
						break;
					case 5:
						{ exceptions.add(new CompilationException("Extraneous character '" + yytext() + "'", line(), CompilationException.Scope.LexicalAnalyzer)); }
					case -6:
						break;
					case 6:
						{return new CustomSymbol(sym.DELIMITER, line()); }
					case -7:
						break;
					case 7:
						{return new CustomSymbol(sym.ASSIGN, line()); }
					case -8:
						break;
					case 8:
						{return new CustomSymbol(sym.SUM, line()); }
					case -9:
						break;
					case 9:
						{return new CustomSymbol(sym.SUBS, line()); }
					case -10:
						break;
					case 10:
						{return new CustomSymbol(sym.MOD, line()); }
					case -11:
						break;
					case 11:
						{if (reservedWords.containsKey(yytext())) {
							return new CustomSymbol(reservedWords.get(yytext()), line());
						} else {
							return new CustomSymbol(sym.ID, yytext(), line());
						}}
					case -12:
						break;
					case 12:
						{return new CustomSymbol(sym.NOT, line()); }
					case -13:
						break;
					case 13:
						{return new CustomSymbol(sym.LSQUARE, line()); }
					case -14:
						break;
					case 14:
						{return new CustomSymbol(sym.COLON, line()); }
					case -15:
						break;
					case 15:
						{return new CustomSymbol(sym.RSQUARE, line()); }
					case -16:
						break;
					case 16:
						{return new CustomSymbol(sym.DOT, line()); }
					case -17:
						break;
					case 17:
						{return new CustomSymbol(sym.GT, line()); }
					case -18:
						break;
					case 18:
						{return new CustomSymbol(sym.LT, line()); }
					case -19:
						break;
					case 19:
						{return new CustomSymbol(sym.INT, new Integer(yytext()), line()); }
					case -20:
						break;
					case 20:
						{return new CustomSymbol(sym.TYPE, yytext(), line()); }
					case -21:
						break;
					case 21:
						{return new CustomSymbol(sym.LPAREN, line()); }
					case -22:
						break;
					case 22:
						{return new CustomSymbol(sym.RPAREN, line()); }
					case -23:
						break;
					case 23:
						{return new CustomSymbol(sym.LBRACKET, line()); }
					case -24:
						break;
					case 24:
						{return new CustomSymbol(sym.RBRACKET, line()); }
					case -25:
						break;
					case 25:
						{return new CustomSymbol(sym.COMMA, line()); }
					case -26:
						break;
					case 26:
						{}
					case -27:
						break;
					case 27:
						{return new CustomSymbol(sym.EQ, line()); }
					case -28:
						break;
					case 28:
						{return new CustomSymbol(sym.ARROW, line()); }
					case -29:
						break;
					case 29:
						{return new CustomSymbol(sym.AND, line()); }
					case -30:
						break;
					case 30:
						{return new CustomSymbol(sym.OR, line()); }
					case -31:
						break;
					case 31:
						{return new CustomSymbol(sym.NEQ, line()); }
					case -32:
						break;
					case 32:
						{return new CustomSymbol(sym.LRANGE, line()); }
					case -33:
						break;
					case 33:
						{return new CustomSymbol(sym.RRANGE, line()); }
					case -34:
						break;
					case 34:
						{return new CustomSymbol(sym.GEQ, line()); }
					case -35:
						break;
					case 35:
						{return new CustomSymbol(sym.LEQ, line()); }
					case -36:
						break;
					case 36:
						{}
					case -37:
						break;
					case 37:
						{return new CustomSymbol(sym.BOOL, new Boolean(yytext()), line()); }
					case -38:
						break;
					case 39:
						{ exceptions.add(new CompilationException("Extraneous character '" + yytext() + "'", line(), CompilationException.Scope.LexicalAnalyzer)); }
					case -39:
						break;
					case 40:
						{if (reservedWords.containsKey(yytext())) {
							return new CustomSymbol(reservedWords.get(yytext()), line());
						} else {
							return new CustomSymbol(sym.ID, yytext(), line());
						}}
					case -40:
						break;
					case 41:
						{return new CustomSymbol(sym.NOT, line()); }
					case -41:
						break;
					case 42:
						{return new CustomSymbol(sym.LBRACKET, line()); }
					case -42:
						break;
					case 43:
						{return new CustomSymbol(sym.AND, line()); }
					case -43:
						break;
					case 44:
						{return new CustomSymbol(sym.OR, line()); }
					case -44:
						break;
					case 46:
						{ exceptions.add(new CompilationException("Extraneous character '" + yytext() + "'", line(), CompilationException.Scope.LexicalAnalyzer)); }
					case -45:
						break;
					case 47:
						{if (reservedWords.containsKey(yytext())) {
							return new CustomSymbol(reservedWords.get(yytext()), line());
						} else {
							return new CustomSymbol(sym.ID, yytext(), line());
						}}
					case -46:
						break;
					case 49:
						{if (reservedWords.containsKey(yytext())) {
							return new CustomSymbol(reservedWords.get(yytext()), line());
						} else {
							return new CustomSymbol(sym.ID, yytext(), line());
						}}
					case -47:
						break;
					case 51:
						{if (reservedWords.containsKey(yytext())) {
							return new CustomSymbol(reservedWords.get(yytext()), line());
						} else {
							return new CustomSymbol(sym.ID, yytext(), line());
						}}
					case -48:
						break;
					case 52:
						{if (reservedWords.containsKey(yytext())) {
							return new CustomSymbol(reservedWords.get(yytext()), line());
						} else {
							return new CustomSymbol(sym.ID, yytext(), line());
						}}
					case -49:
						break;
					case 53:
						{if (reservedWords.containsKey(yytext())) {
							return new CustomSymbol(reservedWords.get(yytext()), line());
						} else {
							return new CustomSymbol(sym.ID, yytext(), line());
						}}
					case -50:
						break;
					case 54:
						{if (reservedWords.containsKey(yytext())) {
							return new CustomSymbol(reservedWords.get(yytext()), line());
						} else {
							return new CustomSymbol(sym.ID, yytext(), line());
						}}
					case -51:
						break;
					case 55:
						{if (reservedWords.containsKey(yytext())) {
							return new CustomSymbol(reservedWords.get(yytext()), line());
						} else {
							return new CustomSymbol(sym.ID, yytext(), line());
						}}
					case -52:
						break;
					case 56:
						{if (reservedWords.containsKey(yytext())) {
							return new CustomSymbol(reservedWords.get(yytext()), line());
						} else {
							return new CustomSymbol(sym.ID, yytext(), line());
						}}
					case -53:
						break;
					case 57:
						{if (reservedWords.containsKey(yytext())) {
							return new CustomSymbol(reservedWords.get(yytext()), line());
						} else {
							return new CustomSymbol(sym.ID, yytext(), line());
						}}
					case -54:
						break;
					case 58:
						{if (reservedWords.containsKey(yytext())) {
							return new CustomSymbol(reservedWords.get(yytext()), line());
						} else {
							return new CustomSymbol(sym.ID, yytext(), line());
						}}
					case -55:
						break;
					case 59:
						{if (reservedWords.containsKey(yytext())) {
							return new CustomSymbol(reservedWords.get(yytext()), line());
						} else {
							return new CustomSymbol(sym.ID, yytext(), line());
						}}
					case -56:
						break;
					default:
						yy_error(YY_E_INTERNAL,false);
					case -1:
					}
					yy_initial = true;
					yy_state = yy_state_dtrans[yy_lexical_state];
					yy_next_state = YY_NO_STATE;
					yy_last_accept_state = YY_NO_STATE;
					yy_mark_start();
					yy_this_accept = yy_acpt[yy_state];
					if (YY_NOT_ACCEPT != yy_this_accept) {
						yy_last_accept_state = yy_state;
						yy_mark_end();
					}
				}
			}
		}
	}
}
