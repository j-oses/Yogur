package yogur;

import java_cup.runtime.Symbol;
import yogur.cup.YogurParser;
import yogur.jlex.YogurLex;

import java.io.File;
import java.io.FileInputStream;

public class TestCup {
	public static void main(String args[]) {
		File file = new File("./../examples/simple.yogur");

		try (FileInputStream is = new FileInputStream(file)) {
			YogurLex jlex = new YogurLex(is);
			YogurParser p = new YogurParser(jlex);

			Symbol s = p.debug_parse();
			System.out.println(s);
		} catch (Exception e) {
			System.err.println("Parsing error");
			e.printStackTrace();
		}
	}
}
