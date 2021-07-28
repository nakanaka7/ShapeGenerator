package tokyo.nakanaka.commandSender;

public interface PositionalCommandSender extends CommandSender{
	public int getX();
	public int getY();
	public int getZ();
}
