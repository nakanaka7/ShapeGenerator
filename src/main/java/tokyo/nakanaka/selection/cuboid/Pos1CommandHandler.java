package tokyo.nakanaka.selection.cuboid;

import java.util.List;

import tokyo.nakanaka.commandArgument.PositionArgument;
import tokyo.nakanaka.logger.LogConstant;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.selection.RegionBuildingData;
import tokyo.nakanaka.selection.SelSubCommandHandler;

public class Pos1CommandHandler implements SelSubCommandHandler{
	private PositionArgument pos1Arg = new PositionArgument();
	private LengthCalculator lengthCalc = new LengthCalculator();

	@Override
	public String getLabel() {
		return "pos1";
	}
	
	@Override
	public boolean onCommand(RegionBuildingData data, Logger logger, BlockVector3D playerPos, String[] args) {
		Vector3D pos1;
		try {
			pos1 = this.pos1Arg.onParse(playerPos, args);
		}catch(IllegalArgumentException e) {
			logger.print(LogConstant.HEAD_ERROR + "Can not parse coodinate");
			return true;
		}
		data.putVector3D("pos1", pos1);
		Vector3D pos2 = data.getVector3D("pos2");
		if(pos2 != null) {
			data.putDouble("width", this.lengthCalc.calcWidth(pos1, pos2));
			data.putDouble("height", this.lengthCalc.calcHeight(pos1, pos2));
			data.putDouble("length", this.lengthCalc.calcLength(pos1, pos2));
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(String[] args) {
		return this.pos1Arg.onTabComplete(args);
	}
	
}
