package yogur.codegen;

import java.util.Map;

interface POutputLine {
	String generateCode(Map<String, Integer> labelAddresses);
	boolean isComment();
}
