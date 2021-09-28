package tokyo.nakanaka.shapeGenerator.sgSubCommandHelp;

import tokyo.nakanaka.annotation.PrivateAPI;
import tokyo.nakanaka.logger.LogColor;

/**
 * Stores colors for command log
 */
@PrivateAPI
public record CommandLogColor(LogColor main, LogColor error) {
}
