package tokyo.nakanaka.commandArgument;

import java.util.List;

import tokyo.nakanaka.block.Block;

public interface BlockArgument {
	Block onParsing(String arg);
	List<String> onTabComplete(String arg);
}
