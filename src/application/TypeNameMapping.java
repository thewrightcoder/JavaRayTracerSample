package application;

// This class is designed to create a 1:1 mapping of the type name (internal) string
// to the user-facing (display) string.
public class TypeNameMapping {

	private String typeName = null;
	private String displayName = null;
	
	public TypeNameMapping(String type, String display) {
		typeName = type;
		displayName = display;
	}
	
	public String getTypeName() {
		return typeName;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	// This function is used by various Java system features.
	// It returns the user-friendly string that describes this class,
	// and is the entire point of this specific class.
	@Override
	public String toString() {
		return getDisplayName();
	}
	
}
