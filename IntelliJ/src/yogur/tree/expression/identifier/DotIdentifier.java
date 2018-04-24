package yogur.tree.expression.identifier;

import java.util.ArrayList;
import java.util.List;

public class DotIdentifier implements VarIdentifier {
	private List<BaseIdentifier> identifiers = new ArrayList<>();

	public DotIdentifier(BaseIdentifier baseId) {
		identifiers.add(baseId);
	}

	public DotIdentifier(DotIdentifier id, BaseIdentifier baseId) {
		identifiers = id.identifiers;
		identifiers.add(baseId);
	}
}
