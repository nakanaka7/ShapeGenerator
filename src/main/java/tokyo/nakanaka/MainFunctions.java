package tokyo.nakanaka;

import tokyo.nakanaka.player.User;
import tokyo.nakanaka.selection.RegionBuildingData;
import tokyo.nakanaka.selection.SelectionBuildingData;
import tokyo.nakanaka.selection.SelectionShape;
import tokyo.nakanaka.selection.selectionStrategy.SelectionStrategySource;

public class MainFunctions {
	public static void setDefaultSelection(SelectionStrategySource selStrtgSource, User user) {
		SelectionShape shape = selStrtgSource.getDefaultSelectionShape();
		user.setSelectionShape(shape);
		RegionBuildingData regionData = selStrtgSource.get(shape).newRegionBuildingData();
		SelectionBuildingData selData = new SelectionBuildingData(user.getWorld(), regionData);
		user.setSelectionBuildingData(selData);
	}
}
