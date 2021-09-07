package tokyo.nakanaka.shapeGenerator.commandArgument;

import java.util.List;

import tokyo.nakanaka.block.Block;

public interface BlockCommandArgument {
	Block onParsing(String arg);
	List<String> onTabComplete(String arg);
}
