package yogur.jlex;
import yogur.cup.sym;
import yogur.cup.CustomSymbol;
import yogur.error.CompilationException;
import java.util.List;
import java.util.ArrayList;


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
	public List<CompilationException> getExceptions() {
		return exceptions;
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
		/* 38 */ YY_NO_ANCHOR,
		/* 39 */ YY_NO_ANCHOR,
		/* 40 */ YY_NO_ANCHOR,
		/* 41 */ YY_NO_ANCHOR,
		/* 42 */ YY_NO_ANCHOR,
		/* 43 */ YY_NO_ANCHOR,
		/* 44 */ YY_NO_ANCHOR,
		/* 45 */ YY_NO_ANCHOR,
		/* 46 */ YY_NO_ANCHOR,
		/* 47 */ YY_NOT_ACCEPT,
		/* 48 */ YY_NO_ANCHOR,
		/* 49 */ YY_NO_ANCHOR,
		/* 50 */ YY_NO_ANCHOR,
		/* 51 */ YY_NO_ANCHOR,
		/* 52 */ YY_NO_ANCHOR,
		/* 53 */ YY_NOT_ACCEPT,
		/* 54 */ YY_NO_ANCHOR,
		/* 55 */ YY_NO_ANCHOR,
		/* 56 */ YY_NO_ANCHOR,
		/* 57 */ YY_NO_ANCHOR,
		/* 58 */ YY_NO_ANCHOR,
		/* 59 */ YY_NO_ANCHOR,
		/* 60 */ YY_NO_ANCHOR,
		/* 61 */ YY_NO_ANCHOR,
		/* 62 */ YY_NO_ANCHOR,
		/* 63 */ YY_NO_ANCHOR,
		/* 64 */ YY_NO_ANCHOR,
		/* 65 */ YY_NO_ANCHOR,
		/* 66 */ YY_NO_ANCHOR,
		/* 67 */ YY_NO_ANCHOR,
		/* 68 */ YY_NO_ANCHOR,
		/* 69 */ YY_NO_ANCHOR,
		/* 70 */ YY_NO_ANCHOR,
		/* 71 */ YY_NO_ANCHOR,
		/* 72 */ YY_NO_ANCHOR,
		/* 73 */ YY_NO_ANCHOR,
		/* 74 */ YY_NO_ANCHOR,
		/* 75 */ YY_NO_ANCHOR,
		/* 76 */ YY_NO_ANCHOR,
		/* 77 */ YY_NO_ANCHOR,
		/* 78 */ YY_NO_ANCHOR,
		/* 79 */ YY_NO_ANCHOR,
		/* 80 */ YY_NO_ANCHOR,
		/* 81 */ YY_NO_ANCHOR,
		/* 82 */ YY_NO_ANCHOR
	};
	private int yy_cmap[] = unpackFromString(1,65538,
"19:8,21:2,20,19:2,17,19:18,21,28,19:3,25,26,19,35,36,18,23,44,24,32,16,39:1" +
"0,30,19,34,22,33,19:2,42:26,29,19,31,19,43,19,5,41,7,1,2,3,41,12,10,41:2,8," +
"41,14,13,41:2,6,9,15,40,4,11,41:3,37,27,38,19:65410,0:2")[0];

	private int yy_rmap[] = unpackFromString(1,83,
"0,1,2,3,1:4,4,1,5,1,6,7,8,1:2,9,10,1:4,11,12,1,13:4,14,1:8,13:5,15,13:2,15," +
"16,17,13,1,13,18,19,20,21,22,13,23,24,25,26,27,28,29,30,31,32,33,34,35,36,3" +
"7,38,39,40,41,42,13,43,44,45")[0];

	private int yy_nxt[][] = unpackFromString(46,45,
"1,2,74,76,77,78,79,80,79:2,48,81,79,54,82,56,3,4,5,6,7,4,8,9,10,11,49,55,12" +
",13,14,15,16,17,18,19,20,21,22,23,79:2,24,6,25,-1:46,79,57,79:13,-1:23,58,7" +
"9:2,58:2,-1:17,30,-1,47,-1:48,31,-1:55,32,-1:33,34,-1:52,35,-1:45,36,-1:35," +
"37,-1:44,38,-1:61,23,-1:6,24:15,-1:23,24:5,-1:2,79:15,-1:23,58,79:2,58:2,-1" +
":2,30:19,-1,30:24,-1,47:16,-1,53,47:26,-1,79:2,26,79:10,27,79,-1:23,58,79:2" +
",58:2,-1:27,33,-1:19,47:15,44,-1,53,47:26,-1,79:5,28,79:9,-1:23,58,79:2,58:" +
"2,-1:28,51,-1:18,79:5,67,79:6,29,79:2,-1:23,58,79:2,58:2,-1:2,79:2,39,79:12" +
",-1:23,58,79:2,58:2,-1:2,79:8,68,79:6,-1:23,58,79:2,58:2,-1:2,79:7,69,79:7," +
"-1:23,58,79:2,58:2,-1:2,79:5,40,79:9,-1:23,58,79:2,58:2,-1:2,79:5,41,79:9,-" +
"1:23,58,79:2,58:2,-1:2,52,79:14,-1:23,58,79:2,58:2,-1:2,79:4,75,79:10,-1:23" +
",58,79:2,58:2,-1:2,79:9,70,79:5,-1:23,58,79:2,58:2,-1:2,79:14,50,-1:23,58,7" +
"9:2,58:2,-1:2,79:15,-1:23,58,71,79,58:2,-1:2,79,42,79:13,-1:23,58,79:2,58:2" +
",-1:2,79:8,71,79:6,-1:23,58,79:2,58:2,-1:2,79:7,73,79:7,-1:23,58,79:2,58:2," +
"-1:2,79,43,79:13,-1:23,58,79:2,58:2,-1:2,79:8,45,79:6,-1:23,58,79:2,58:2,-1" +
":2,79,46,79:13,-1:23,58,79:2,58:2,-1:2,79:7,59,79:7,-1:23,58,79:2,58:2,-1:2" +
",79:8,72,79:6,-1:23,58,79:2,58:2,-1:2,79:4,60,79:7,61,79:2,-1:23,58,79:2,58" +
":2,-1:2,79:4,62,79:10,-1:23,58,79:2,58:2,-1:2,79:13,63,79,-1:23,58,79:2,58:" +
"2,-1:2,79:7,64,79:7,-1:23,58,79:2,58:2,-1:2,79:11,65,79:3,-1:23,58,79:2,58:" +
"2,-1:2,79:12,66,79:2,-1:23,58,79:2,58:2,-1");

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

	return new CustomSymbol(sym.EOF, yyline);
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
						{return new CustomSymbol(sym.ID, yytext(), yyline); }
					case -3:
						break;
					case 3:
						{return new CustomSymbol(sym.DIV, yyline); }
					case -4:
						break;
					case 4:
						{}
					case -5:
						break;
					case 5:
						{return new CustomSymbol(sym.PROD, yyline); }
					case -6:
						break;
					case 6:
						{ exceptions.add(new CompilationException("Extraneous character '" + yytext() + "'", yyline, CompilationException.Scope.LexicalAnalyzer)); }
					case -7:
						break;
					case 7:
						{return new CustomSymbol(sym.DELIMITER, yyline); }
					case -8:
						break;
					case 8:
						{return new CustomSymbol(sym.ASSIGN, yyline); }
					case -9:
						break;
					case 9:
						{return new CustomSymbol(sym.SUM, yyline); }
					case -10:
						break;
					case 10:
						{return new CustomSymbol(sym.SUBS, yyline); }
					case -11:
						break;
					case 11:
						{return new CustomSymbol(sym.MOD, yyline); }
					case -12:
						break;
					case 12:
						{return new CustomSymbol(sym.NOT, yyline); }
					case -13:
						break;
					case 13:
						{return new CustomSymbol(sym.LSQUARE, yyline); }
					case -14:
						break;
					case 14:
						{return new CustomSymbol(sym.COLON, yyline); }
					case -15:
						break;
					case 15:
						{return new CustomSymbol(sym.RSQUARE, yyline); }
					case -16:
						break;
					case 16:
						{return new CustomSymbol(sym.DOT, yyline); }
					case -17:
						break;
					case 17:
						{return new CustomSymbol(sym.GT, yyline); }
					case -18:
						break;
					case 18:
						{return new CustomSymbol(sym.LT, yyline); }
					case -19:
						break;
					case 19:
						{return new CustomSymbol(sym.LPAREN, yyline); }
					case -20:
						break;
					case 20:
						{return new CustomSymbol(sym.RPAREN, yyline); }
					case -21:
						break;
					case 21:
						{return new CustomSymbol(sym.LBRACKET, yyline); }
					case -22:
						break;
					case 22:
						{return new CustomSymbol(sym.RBRACKET, yyline); }
					case -23:
						break;
					case 23:
						{return new CustomSymbol(sym.INT, new Integer(yytext()), yyline); }
					case -24:
						break;
					case 24:
						{return new CustomSymbol(sym.TYPE, yytext(), yyline); }
					case -25:
						break;
					case 25:
						{return new CustomSymbol(sym.COMMA, yyline); }
					case -26:
						break;
					case 26:
						{return new CustomSymbol(sym.IF, yyline); }
					case -27:
						break;
					case 27:
						{return new CustomSymbol(sym.IN, yyline); }
					case -28:
						break;
					case 28:
						{return new CustomSymbol(sym.OR, yyline); }
					case -29:
						break;
					case 29:
						{return new CustomSymbol(sym.TO, yyline); }
					case -30:
						break;
					case 30:
						{}
					case -31:
						break;
					case 31:
						{return new CustomSymbol(sym.EQ, yyline); }
					case -32:
						break;
					case 32:
						{return new CustomSymbol(sym.ARROW, yyline); }
					case -33:
						break;
					case 33:
						{return new CustomSymbol(sym.AND, yyline); }
					case -34:
						break;
					case 34:
						{return new CustomSymbol(sym.NEQ, yyline); }
					case -35:
						break;
					case 35:
						{return new CustomSymbol(sym.LRANGE, yyline); }
					case -36:
						break;
					case 36:
						{return new CustomSymbol(sym.RRANGE, yyline); }
					case -37:
						break;
					case 37:
						{return new CustomSymbol(sym.GEQ, yyline); }
					case -38:
						break;
					case 38:
						{return new CustomSymbol(sym.LEQ, yyline); }
					case -39:
						break;
					case 39:
						{return new CustomSymbol(sym.DEF, yyline); }
					case -40:
						break;
					case 40:
						{return new CustomSymbol(sym.FOR, yyline); }
					case -41:
						break;
					case 41:
						{return new CustomSymbol(sym.VAR, yyline); }
					case -42:
						break;
					case 42:
						{return new CustomSymbol(sym.ELSE, yyline); }
					case -43:
						break;
					case 43:
						{return new CustomSymbol(sym.BOOL, new Boolean(yytext()), yyline); }
					case -44:
						break;
					case 44:
						{}
					case -45:
						break;
					case 45:
						{return new CustomSymbol(sym.CLASS, yyline); }
					case -46:
						break;
					case 46:
						{return new CustomSymbol(sym.WHILE, yyline); }
					case -47:
						break;
					case 48:
						{return new CustomSymbol(sym.ID, yytext(), yyline); }
					case -48:
						break;
					case 49:
						{ exceptions.add(new CompilationException("Extraneous character '" + yytext() + "'", yyline, CompilationException.Scope.LexicalAnalyzer)); }
					case -49:
						break;
					case 50:
						{return new CustomSymbol(sym.NOT, yyline); }
					case -50:
						break;
					case 51:
						{return new CustomSymbol(sym.OR, yyline); }
					case -51:
						break;
					case 52:
						{return new CustomSymbol(sym.AND, yyline); }
					case -52:
						break;
					case 54:
						{return new CustomSymbol(sym.ID, yytext(), yyline); }
					case -53:
						break;
					case 55:
						{ exceptions.add(new CompilationException("Extraneous character '" + yytext() + "'", yyline, CompilationException.Scope.LexicalAnalyzer)); }
					case -54:
						break;
					case 56:
						{return new CustomSymbol(sym.ID, yytext(), yyline); }
					case -55:
						break;
					case 57:
						{return new CustomSymbol(sym.ID, yytext(), yyline); }
					case -56:
						break;
					case 58:
						{return new CustomSymbol(sym.ID, yytext(), yyline); }
					case -57:
						break;
					case 59:
						{return new CustomSymbol(sym.ID, yytext(), yyline); }
					case -58:
						break;
					case 60:
						{return new CustomSymbol(sym.ID, yytext(), yyline); }
					case -59:
						break;
					case 61:
						{return new CustomSymbol(sym.ID, yytext(), yyline); }
					case -60:
						break;
					case 62:
						{return new CustomSymbol(sym.ID, yytext(), yyline); }
					case -61:
						break;
					case 63:
						{return new CustomSymbol(sym.ID, yytext(), yyline); }
					case -62:
						break;
					case 64:
						{return new CustomSymbol(sym.ID, yytext(), yyline); }
					case -63:
						break;
					case 65:
						{return new CustomSymbol(sym.ID, yytext(), yyline); }
					case -64:
						break;
					case 66:
						{return new CustomSymbol(sym.ID, yytext(), yyline); }
					case -65:
						break;
					case 67:
						{return new CustomSymbol(sym.ID, yytext(), yyline); }
					case -66:
						break;
					case 68:
						{return new CustomSymbol(sym.ID, yytext(), yyline); }
					case -67:
						break;
					case 69:
						{return new CustomSymbol(sym.ID, yytext(), yyline); }
					case -68:
						break;
					case 70:
						{return new CustomSymbol(sym.ID, yytext(), yyline); }
					case -69:
						break;
					case 71:
						{return new CustomSymbol(sym.ID, yytext(), yyline); }
					case -70:
						break;
					case 72:
						{return new CustomSymbol(sym.ID, yytext(), yyline); }
					case -71:
						break;
					case 73:
						{return new CustomSymbol(sym.ID, yytext(), yyline); }
					case -72:
						break;
					case 74:
						{return new CustomSymbol(sym.ID, yytext(), yyline); }
					case -73:
						break;
					case 75:
						{return new CustomSymbol(sym.ID, yytext(), yyline); }
					case -74:
						break;
					case 76:
						{return new CustomSymbol(sym.ID, yytext(), yyline); }
					case -75:
						break;
					case 77:
						{return new CustomSymbol(sym.ID, yytext(), yyline); }
					case -76:
						break;
					case 78:
						{return new CustomSymbol(sym.ID, yytext(), yyline); }
					case -77:
						break;
					case 79:
						{return new CustomSymbol(sym.ID, yytext(), yyline); }
					case -78:
						break;
					case 80:
						{return new CustomSymbol(sym.ID, yytext(), yyline); }
					case -79:
						break;
					case 81:
						{return new CustomSymbol(sym.ID, yytext(), yyline); }
					case -80:
						break;
					case 82:
						{return new CustomSymbol(sym.ID, yytext(), yyline); }
					case -81:
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
