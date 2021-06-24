package tokyo.nakanaka.commandArgument;

import java.util.List;

public interface CommandArgumentList<E> {
	E onParse(String[] args);
	List<String> onTabComplete(String[] args);
}
