package search;

import java.util.Objects;

public class Node {
	public final Node parent;
	public final Action action;
	public final State state;
	
	public Node(Node parent, Action action, State state) {
		this.parent = parent;
		this.action = action;
		this.state = state;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Node node = (Node) o;

		if (!Objects.equals(parent, node.parent)) return false;
		if (!Objects.equals(action, node.action)) return false;
		return Objects.equals(state, node.state);
	}

	@Override
	public int hashCode() {
		int result = parent != null ? parent.hashCode() : 0;
		result = 31 * result + (action != null ? action.hashCode() : 0);
		result = 31 * result + (state != null ? state.hashCode() : 0);
		return result;
	}
}
