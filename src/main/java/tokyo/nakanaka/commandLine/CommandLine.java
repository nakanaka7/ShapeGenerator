package tokyo.nakanaka.commandLine;

import java.util.List;

import tokyo.nakanaka.shapeGenerator.Player;

public interface CommandLine {
	String getLabel();
	List<String> getAliases();
	boolean onCommand(Player player, String[] args);
	List<String> onTabComplete(Player player, String[] args);
}
