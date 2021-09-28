package tokyo.nakanaka.shapeGenerator.sgSubCommandHelp;

import java.util.List;

public interface RootCommandHelp extends CommandHelp {
    /**
     * Return multiple lines which contains the information for the command
     * This is used by HelpCommandHandler class
     * @return multiple lines which contains the information for the command
     */
    List<String> toMultipleLines();
}
