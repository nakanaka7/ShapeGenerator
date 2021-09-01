package tokyo.nakanaka.shapeGenerator.selSubCommandHandler;

import java.util.List;
import java.util.Map;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.commadHelp.ParameterHelp;
import tokyo.nakanaka.math.Vector3D;
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
	public boolean onCommand(UserData userData, Player player, String[] subArgs) {
		boolean success = this.posHandler.onCommand(userData, player, subArgs);
		if(!success) {
			return false;
		}
		Map<String, Object> regDataMap = userData.getSelectionData().getRegionDataMap();
		Vector3D pos1 = (Vector3D) regDataMap.get("pos1");
		Vector3D pos2 = (Vector3D) regDataMap.get("pos2");
		if(pos1 != null) {
			regDataMap.put("width", this.lengthCalc.calcWidth(pos1, pos2));
			regDataMap.put("height", this.lengthCalc.calcHeight(pos1, pos2));
			regDataMap.put("length", this.lengthCalc.calcLength(pos1, pos2));
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(UserData userData, Player player, String[] subArgs) {
		return this.posHandler.onTabComplete(userData, player, subArgs);
	}
	
}
