package tokyo.nakanaka.selection.cuboid;

import java.util.List;

import tokyo.nakanaka.commandArgument.PositionArgument;
import tokyo.nakanaka.logger.LogConstant;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.selection.RegionBuilder;
import tokyo.nakanaka.selection.SelSubCommandHandler;

public class Pos2CommandHandler implements SelSubCommandHandler{
	private PositionArgument pos2Arg = new PositionArgument();

	@Override
	public void onCommand(RegionBuilder builder, Logger logger, BlockVector3D playerPos, String[] args) {
		Vector3D pos2;
		try {
			pos2 = this.pos2Arg.onParse(playerPos, args);
		}catch(IllegalArgumentException e) {
			logger.print(LogConstant.HEAD_ERROR + "Can not parse coodinate");
			return;
		}
		((CuboidRegionBuilder)builder).setPos2(pos2);
	}

	@Override
	public List<String> onTabComplete(String[] args) {
		return this.pos2Arg.onTabComplete(args);
	}
}
