package tokyo.nakanaka.selection;

import java.util.List;

import tokyo.nakanaka.commandArgument.PositionArgument;
import tokyo.nakanaka.logger.LogConstant;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.Vector3D;

public class PosCommandHandler implements SelSubCommandHandler {
	private PositionArgument posArg = new PositionArgument();
	private String label;
	
	public PosCommandHandler(String label) {
		this.label = label;
	}

	@Override
	public String getLabel() {
		return this.label;
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
		data.putVector3D(this.label, pos);
		return true;
	}

	@Override
	public List<String> onTabComplete(String[] args) {
		return this.posArg.onTabComplete(args);
	}
	
}
