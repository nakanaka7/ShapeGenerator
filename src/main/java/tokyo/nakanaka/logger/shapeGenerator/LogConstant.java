package tokyo.nakanaka.logger.shapeGenerator;

import static tokyo.nakanaka.logger.LogColor.*;

public enum LogConstant {
	HEAD_NORMAL(GOLD + "[ShapeGenerator]: " + RESET),
	HEAD_WARN(YELLOW + "[ShapeGenerator/WARN]: " + RESET),
	HEAD_ERROR(RED + "[ShapeGenerator/ERROR]: " + RESET),
	INDENT_NORMAL(GOLD + "*** " + RESET);
	
	private String str;
	
	private LogConstant(String str) {
		this.str = str;
	}
	
	@Override
	public String toString() {
		return this.str;
	}
	
}
