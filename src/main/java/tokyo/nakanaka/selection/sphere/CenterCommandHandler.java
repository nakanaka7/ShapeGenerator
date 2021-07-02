package tokyo.nakanaka.selection.sphere;

import java.util.List;

import tokyo.nakanaka.commandArgument.PositionArgument;
import tokyo.nakanaka.logger.LogConstant;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.selection.RegionBuildingData;
import tokyo.nakanaka.selection.SelSubCommandHandler;

public class CenterCommandHandler implements SelSubCommandHandler{
	private PositionArgument posArg = new PositionArgument();
	
	@Override
	public String getLabel() {
		return "center";
	}

	@Override
	public boolean onCommand(RegionBuildingData data, Logger logger, BlockVector3D playerPos, String[] args) {
		Vector3D pos;
		try {
			pos = this.posArg.onParse(playerPos, args);
		}catch(IllegalArgumentException e) {
			logger.print(LogConstant.HEAD_ERROR + "Can not parse coodinate");
			return true;
		}
		data.putVector3D("center", pos);
		return true;
	}

	@Override
	public List<String> onTabComplete(String[] args) {
		return this.posArg.onTabComplete(args);
	}

}
