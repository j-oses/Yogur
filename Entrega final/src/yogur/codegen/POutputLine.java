package yogur.codegen;

import java.util.Map;

/**
 * A interface which represents a line on the machine code output on the compiler.
 */
interface POutputLine {
	/**
	 * Generates the string of its line of code.
	 * @param labelAddresses the map with the label absolute addresses.
	 * @return the string.
	 */
	String generateCode(Map<String, Integer> labelAddresses);

	/**
	 * Whether this line represents a comment.
	 * @return
	 */
	boolean isComment();
}
