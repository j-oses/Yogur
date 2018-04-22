package yogur;

import java_cup.runtime.Symbol;
import yogur.cup.sym;
import yogur.jlex.YogurLex;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.File;

public class TestJLex {
    public static void main(String args[]) {
		try {
			File file = new File("./../examples/simple.yogur");
			FileInputStream is = new FileInputStream(file);
			YogurLex ylex = new YogurLex(is);

			Symbol token = ylex.next_token();
			while (token.sym != sym.EOF) {
				System.out.println(sym.terminalNames[token.sym] + " ");
				token = ylex.next_token();
			}
		} catch (IOException e) {
			System.err.println("Parsing error");
			e.printStackTrace();
		}
	}
}
