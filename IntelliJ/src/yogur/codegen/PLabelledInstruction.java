package yogur.codegen;

import java.util.Map;

/**
 * A class representing a labelled instruction on the output file.
 */
class PLabelledInstruction implements POutputLine {
	private String label;
	private Object arg;
	private String name;

	PLabelledInstruction(String name, String label) {
		this(name, null, label);
	}

	PLabelledInstruction(String name, Object arg, String label) {
		this.label = label;
		this.name = name;
		this.arg = arg;
	}

	@Override
	public String generateCode(Map<String, Integer> labelAddresses) {
		// Manages the translation from label to offset, using the map received as an argument.
		return name + " " + (arg != null ? arg + " " : "") + labelAddresses.get(label) + ";" + " \\\\ " + label;
	}

	@Override
	public boolean isComment() {
		return false;
	}
}
