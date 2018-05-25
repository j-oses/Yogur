package yogur.codegen;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * A class representing a instruction with no labels on the output file.
 */
class PNonLabelledInstruction implements POutputLine {
	private String name;
	private List<Object> args;

	PNonLabelledInstruction(String name, Object... args) {
		this.name = name;
		this.args = Arrays.asList(args);
	}

	@Override
	public String generateCode(Map<String, Integer> labelAddresses) {
		String code = name;
		for (Object arg: args) {
			code += " " + arg;
		}
		code += ";";
		return code;
	}

	@Override
	public boolean isComment() {
		return false;
	}
}
