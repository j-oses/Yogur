package yogur.codegen;

import java.util.Map;

/**
 * A class representing a single-line comment on the output file.
 */
class PComment implements POutputLine {
	private String comment;

	PComment(String comment) {
		this.comment = comment;
	}

	@Override
	public String generateCode(Map<String, Integer> labelAddresses) {
		return "\\\\ " + comment;
	}

	@Override
	public boolean isComment() {
		return true;
	}
}
