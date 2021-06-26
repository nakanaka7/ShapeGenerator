package tokyo.nakanaka;

import tokyo.nakanaka.player.Player;

public class CommandLine {
	private Player player;
	private String alias;
	private String[] args;
	
	public CommandLine(Player player, String alias, String[] args) {
		this.player = player;
		this.alias = alias;
		this.args = args;
	}

	public String getAlias() {
		return alias;
	}

	public Player getPlayer() {
		return player;
	}

	public String[] getArgs() {
		return args;
	}

}
