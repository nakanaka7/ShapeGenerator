package tokyo.nakanaka.selection.selSubCommandHandler;

import java.util.List;

import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.selection.RegionBuildingData;

public class CuboidPos2CommandHandler implements SelSubCommandHandler{
	private PosCommandHandler posHandler = new PosCommandHandler("pos2");
	private LengthCalculator lengthCalc = new LengthCalculator();

	@Override
	public String getLabel() {
		return this.posHandler.getLabel();
	}
	
	@Override
	public String getDescription() {
		return this.posHandler.getDescription();
	}
	
	@Override
	public String getUsage() {
		return this.posHandler.getUsage();
	}
	
	@Override
	public boolean onCommand(RegionBuildingData data, Logger logger, BlockVector3D playerPos, String[] args) {
		boolean success = this.posHandler.onCommand(data, logger, playerPos, args);
		if(!success) {
			return false;
		}
		Vector3D pos1 = data.getVector3D("pos1");
		Vector3D pos2 = data.getVector3D("pos2");
		if(pos1 != null) {
			data.putDouble("width", this.lengthCalc.calcWidth(pos1, pos2));
			data.putDouble("height", this.lengthCalc.calcHeight(pos1, pos2));
			data.putDouble("length", this.lengthCalc.calcLength(pos1, pos2));
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(String[] args) {
		return this.posHandler.onTabComplete(args);
	}
	
}
