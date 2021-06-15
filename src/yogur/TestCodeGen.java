package yogur;

import java.io.File;

public class TestCodeGen extends Compiler {
	private static final String TEST_DIR = "./../tests/";
	private static final String CODE_GEN_DIR = "./../tests/gencode/";

	public static void main(String args[]) {
		testAll();
	}

	public static void testAll(){
		File dir = new File(TEST_DIR);

		File[] directoryListing = dir.listFiles();
		if (directoryListing != null) {
			for (File file : directoryListing) {
				if (file.isFile() && !file.isHidden()) {
					System.out.println(">>>> TESTING FILE " + file.getName() + " <<<<");
					compile(file, CODE_GEN_DIR + file.getName() + ".txt");
				}
			}
		} else {
			System.err.println("Empty testing directory");
		}
	}
}
