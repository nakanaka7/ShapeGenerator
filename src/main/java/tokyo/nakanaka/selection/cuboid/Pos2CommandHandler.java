package tokyo.nakanaka.selection.cuboid;

import java.util.List;

import tokyo.nakanaka.commandArgument.PositionArgument;
import tokyo.nakanaka.logger.LogConstant;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.selection.RegionBuilder;
import tokyo.nakanaka.selection.RegionBuildingData;
import tokyo.nakanaka.selection.SelSubCommandHandler;

public class Pos2CommandHandler implements SelSubCommandHandler{
	private PositionArgument pos2Arg = new PositionArgument();

	@Override
	public boolean onCommand(RegionBuilder builder, Logger logger, BlockVector3D playerPos, String[] args) {
		Vector3D pos2;
		try {
			pos2 = this.pos2Arg.onParse(playerPos, args);
		}catch(IllegalArgumentException e) {
			logger.print(LogConstant.HEAD_ERROR + "Can not parse coodinate");
			return true;
		}
		((CuboidRegionBuilder)builder).setPos2(pos2);
		return true;
	}

	@Override
	public List<String> onTabComplete(String[] args) {
		return this.pos2Arg.onTabComplete(args);
	}

	@Override
	public boolean onCommand(RegionBuildingData data, Logger logger, BlockVector3D playerPos, String[] args) {
		Vector3D pos2;
		try {
			pos2 = this.pos2Arg.onParse(playerPos, args);
		}catch(IllegalArgumentException e) {
			logger.print(LogConstant.HEAD_ERROR + "Can not parse coodinate");
			return true;
		}
		data.putVector3D("pos2", pos2);
		return true;
	}

	@Override
	public String getLabel() {
		return "pos2";
	}

}
