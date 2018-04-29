package yogur;

import java_cup.runtime.Symbol;
import yogur.cup.CustomSymbol;
import yogur.cup.YogurParser;
import yogur.cup.sym;
import yogur.jlex.YogurLex;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class TestJLex {
    public static void main(String args[]) {
		File file = new File("./../tests/02_Expressions.yogur");

		try (FileInputStream is = new FileInputStream(file)) {
			YogurLex ylex = new YogurLex(is);

			CustomSymbol token = (CustomSymbol)ylex.next_token();
			while (token.sym != sym.EOF) {
				System.out.println(sym.terminalNames[token.sym] + " " + token.getLine());
				token = (CustomSymbol)ylex.next_token();
			}
		} catch (Exception e) {
			System.err.println("Parsing error");
			e.printStackTrace();
		}
	}
}
