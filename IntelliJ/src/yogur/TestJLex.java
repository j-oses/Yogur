package yogur;

import java_cup.runtime.Symbol;
import yogur.cup.sym;
import yogur.jlex.YogurLex;

import java.io.*;

public class TestJLex {
    public static void main(String args[]) {
		File file = new File("./../tests/02_Expressions.yogur");

		try (FileInputStream is = new FileInputStream(file)) {
			YogurLex ylex = new YogurLex(new InputStreamReader(is));

			Symbol token = (Symbol) ylex.next_token();
			while (token.sym != sym.EOF) {
				System.out.println(sym.terminalNames[token.sym] + " " + token.left);
				token = (Symbol) ylex.next_token();
			}
		} catch (Exception e) {
			System.err.println("Parsing error");
			e.printStackTrace();
		}
	}
}
