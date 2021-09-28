package tokyo.nakanaka.shapeGenerator;

import tokyo.nakanaka.annotation.PrivateAPI;
import tokyo.nakanaka.logger.LogColor;

/**
 * Stores colors for command log
 */
@PrivateAPI
public record CommandLogColor(LogColor main, LogColor error) {
}
