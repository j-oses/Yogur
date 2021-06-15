package yogur;

import yogur.codegen.IntegerReference;
import yogur.codegen.PMachineOutputStream;
import yogur.cup.YogurParser;
import yogur.ididentification.IdentifierTable;
import yogur.tree.Program;
import yogur.utils.CompilationException;
import yogur.utils.Log;
import yogur.jflex.YogurLex;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Compiler {
	private static final Log.Level LOG_LEVEL = Log.Level.Warning;

	public static void main(String args[]) {
		if (args.length < 1) {
			Log.error("Not enough arguments. Usage:\n" +
					"\tcompilador inputPath [outputPath]");
			return;
		}

		String input = args[0];

		File inputFile = new File(input);
		String output;

		if (args.length >= 2) {
			output = args[1];
		} else {
			output = inputFile.getAbsolutePath() + ".txt";
		}

		compile(inputFile, output);
	}

	protected static void compile(File inputFile, String outputName) {
		Log.setMinimumLogLevel(LOG_LEVEL);
		File outputFile = new File(outputName);

		try (FileInputStream is = new FileInputStream(inputFile);
			 PMachineOutputStream outputStream = new PMachineOutputStream(outputFile)) {
			if (!inputFile.isFile()) {
				throw new IOException("File at path " + inputFile.getPath() + " does not exist");
			}

			// LEXICAL AND SYNTACTICAL ANALYSIS
			YogurLex lexicalAnalyzer = new YogurLex(new InputStreamReader(is));
			YogurParser syntacticalAnalyzer = new YogurParser(lexicalAnalyzer);
			Program program;

			try {
				// We catch the lexical & syntactical exceptions separately, to check the exception list of the parsers.
				program = (Program) syntacticalAnalyzer.debug_parse().value;
			} catch (IOException e) {
				throw e;
			} catch (Exception generalEx) {
				List<CompilationException> exceptionList = lexicalAnalyzer.getExceptions();
				exceptionList.addAll(syntacticalAnalyzer.getExceptions());

				if (exceptionList.isEmpty()) {
					throw generalEx;
				}

				for (CompilationException e: exceptionList) {
					Log.error(e);
				}

				return;
			}

			// IDENTIFIER IDENTIFICATION
			IdentifierTable idTable = new IdentifierTable();
			program.performIdentifierAnalysis(idTable);

			// TYPE ANALYSIS
			program.performTypeAnalysis();

			// CODE GENERATION
			program.performMemoryAssignment(new IntegerReference(0), new IntegerReference(0));
			program.generateCode(outputStream);
		} catch (CompilationException e) {
			Log.error(e);
		} catch (IOException e) {
			Log.error("An error occurred when reading or writing", e);
		} catch (Exception e) {
			// The execution should never reach this point. For NullPointerExceptions and the like.
			Log.error("Unknown failure occurred", e);
			e.printStackTrace();
		}
	}
}
