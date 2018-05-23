package yogur.typeanalysis;

/**
 * An interface representing types that can be used by the type system, but which may not necessarily be
 * valid, declarable types on a program. The valid Types are a subset of all the MetaTypes.
 */
public interface MetaType {
	int getSize();
}
