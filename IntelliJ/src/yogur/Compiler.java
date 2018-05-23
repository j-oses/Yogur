package yogur;

import yogur.codegen.IntegerReference;
import yogur.codegen.PMachineOutputStream;
import yogur.cup.YogurParser;
import yogur.ididentification.IdentifierAnalyzer;
import yogur.jflex.YogurLex;
import yogur.tree.Program;
import yogur.utils.CompilationException;
import yogur.utils.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Compiler {
	public static void main(String args[]) {
		if (args.length < 1) {
			Log.error("Not enough arguments. Usage:\n" +
					"\tcompilador inputPath [outputPath]");
			return;
		}

		String input = args[0];
		File inputFile = new File(input);
		String output = null;

		if (args.length >= 2) {
			output = args[1];
		} else {
			output = inputFile.getAbsolutePath() + ".txt";
		}

		compile(inputFile, output);
	}

	private static void compile(File inputFile, String outputName) {
		File outputFile = new File(outputName);

		try (FileInputStream is = new FileInputStream(inputFile);
			 PMachineOutputStream outputStream = new PMachineOutputStream(outputFile)) {
			YogurLex jlex = new YogurLex(new InputStreamReader(is));
			YogurParser p = new YogurParser(jlex);

			Program program = (Program)p.parse().value;

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

			IdentifierAnalyzer identifierAnalyzer = new IdentifierAnalyzer(program);
			identifierAnalyzer.decorateTree();

			program.performTypeAnalysis();
			program.performMemoryAssignment(new IntegerReference(0), new IntegerReference(0));

			program.generateCode(outputStream);
			outputStream.flush();
		} catch (CompilationException e) {
			Log.error(e);
		} catch (IOException e) {
			Log.error("An error occurred when reading or writing", e);
		} catch (Exception e) {
			Log.error("Unknown failure occurred", e);
			e.printStackTrace();
		}
	}
}
