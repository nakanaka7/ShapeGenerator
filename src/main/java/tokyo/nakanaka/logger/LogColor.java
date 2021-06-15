package tokyo.nakanaka.logger;

public enum LogColor {
	DARK_RED("\u00A74"),
	RED("\u00A7c"),
	GOLD("\u00A76"),
	YELLOW("\u00A7e"),
	DARK_GREEN("\u00A72"),
	GREEN("\u00A7a"),
	AQUA("\u00A7b"),
	DARK_AQUA("\u00A73"),
	DARK_BLUE("\u00A71"),
	BLUE("\u00A79"),
	LIGHT_PURPLE("\u00A7d"),
	DARK_PURPLE("\u00A75"),
	WHITE("\u00A7f"),
	GRAY("\u00A77"),
	DARK_GRAY("\u00A78"),
	BLACK("\u00A70"),
	RESET("\u00A7r"),
	BOLD("\u00A7l"),
	ITALIC("\u00A7o"),
	UNDERLINE("\u00A7n"),
	STRIKE("\u00A7m");
	private String str;
	
	private LogColor(String str) {
		this.str = str;
	}
	
	@Override
	public String toString() {
		return this.str;
	}
	
}
