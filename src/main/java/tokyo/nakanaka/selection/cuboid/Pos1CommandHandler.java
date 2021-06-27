package tokyo.nakanaka.selection.cuboid;

import java.util.List;

import tokyo.nakanaka.commadHelp.CommandHelp;
import tokyo.nakanaka.commadHelp.Parameter;
import tokyo.nakanaka.commadHelp.Parameter.Type;
import tokyo.nakanaka.commandArgument.PositionArgument;
import tokyo.nakanaka.logger.LogConstant;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.selection.RegionBuilder;
import tokyo.nakanaka.selection.SelCommandHandler;

public class Pos1CommandHandler implements SelCommandHandler{
	private PositionArgument pos1Arg = new PositionArgument();

	@Override
	public CommandHelp getCommandHelp() {
		return new CommandHelp.Builder("pos1")
				.description("Set pos1, which is a corner of cuboid")
				.addParameter(new Parameter(Type.OPTIONAL, "x"), "x coordinate, if given, type y and z together")
				.addParameter(new Parameter(Type.OPTIONAL, "y"), "y coordinate, if given, type x and z together")
				.addParameter(new Parameter(Type.OPTIONAL, "z"), "z coordinate, if given, type x and y together")
				.build();
	}
	
	@Override
	public void onCommand(RegionBuilder builder, Logger logger, BlockVector3D playerPos, String[] args) {
		Vector3D pos1;
		try {
			pos1 = this.pos1Arg.onParse(playerPos, args);
		}catch(IllegalArgumentException e) {
			logger.print(LogConstant.HEAD_ERROR + "Can not parse coodinate");
			return;
		}
		((CuboidRegionBuilder)builder).setPos1(pos1);
	}

	@Override
	public List<String> onTabComplete(String[] args) {
		return this.pos1Arg.onTabComplete(args);
	}

}
