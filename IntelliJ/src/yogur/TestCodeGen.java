package yogur;

import yogur.codegen.IntegerReference;
import yogur.cup.YogurParser;
import yogur.ididentification.IdentifierAnalyzer;
import yogur.jflex.YogurLex;
import yogur.tree.Program;
import yogur.typeanalysis.TypeAnalyzer;
import yogur.utils.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class TestCodeGen {
	private static String TESTDIR = "./../tests/";

	public static void main(String args[]) {
		File file = new File("./../tests/exampleOne.yogur");

		//testFile(file);
		testAll();
	}

	public static void testAll(){
		File dir = new File(TESTDIR);

		File[] directoryListing = dir.listFiles();
		if (directoryListing != null) {
			for (File file : directoryListing) {
				System.out.println(">>>> TESTING FILE " + file.getName() + " <<<<");
				testFile(file);
			}
		} else {
			System.err.println("Empty testing directory");
		}

	}

	public static void testFile(File file) {
		YogurParser p = null;

		try (FileInputStream is = new FileInputStream(file)) {
			YogurLex jlex = new YogurLex(new InputStreamReader(is));
			p = new YogurParser(jlex);

			Program prog = (Program)p.parse().value;

			if (!jlex.getExceptions().isEmpty()) {
				for (Exception e: jlex.getExceptions()) {
					Log.error(e);
				}
				return;
			}

			if (!p.getExceptions().isEmpty()) {
				for (Exception e: jlex.getExceptions()) {
					Log.error(e);
				}
				return;
			}

			IdentifierAnalyzer identifierAnalyzer = new IdentifierAnalyzer(prog);
			identifierAnalyzer.decorateTree();

			TypeAnalyzer typeAnalyzer = new TypeAnalyzer(prog);
			typeAnalyzer.decorateTree();

			prog.performMemoryAssignment(new IntegerReference(0), new IntegerReference(0));

			System.out.println("Success!" + prog);
		} catch (Exception e) {
			Log.error(e);
		}
	}
}
