package actions;

public abstract class Action implements Actionable {
	// The parameters associate with the argument.
	protected String[] parameters;

	public Action(String[] parameters) {
		this.parameters = parameters;
	}
}
