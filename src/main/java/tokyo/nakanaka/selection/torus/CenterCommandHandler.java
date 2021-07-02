package tokyo.nakanaka.selection.torus;

import java.util.List;

import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.selection.RegionBuildingData;
import tokyo.nakanaka.selection.SelSubCommandHandler;

public class CenterCommandHandler implements SelSubCommandHandler{

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean onCommand(RegionBuildingData data, Logger logger, BlockVector3D playerPos, String[] args) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<String> onTabComplete(String[] args) {
		// TODO Auto-generated method stub
		return null;
	}

}
