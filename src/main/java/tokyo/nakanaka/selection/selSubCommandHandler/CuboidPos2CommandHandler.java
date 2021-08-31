package tokyo.nakanaka.selection.selSubCommandHandler;

import java.util.List;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.commadHelp.ParameterHelp;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.selection.RegionBuildingData;
import tokyo.nakanaka.shapeGenerator.user.UserData;

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
	public List<ParameterHelp> getParameterHelpList() {
		return this.posHandler.getParameterHelpList();
	}
	
	@Override
	public boolean onCommand(RegionBuildingData data, Player player, String[] args) {
		boolean success = this.posHandler.onCommand(data, player, args);
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
	public List<String> onTabComplete(UserData user, Player player, String[] subArgs) {
		return this.posHandler.onTabComplete(user, player, subArgs);
	}
	
}
