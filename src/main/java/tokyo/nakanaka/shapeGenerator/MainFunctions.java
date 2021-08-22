package tokyo.nakanaka.shapeGenerator;

import tokyo.nakanaka.selection.RegionBuildingData;
import tokyo.nakanaka.selection.SelectionBuildingData;
import tokyo.nakanaka.selection.SelectionShape;
import tokyo.nakanaka.selection.selectionStrategy.SelectionStrategySource;
import tokyo.nakanaka.shapeGenerator.user.UserOld;

public class MainFunctions {
	public static void setDefaultSelection(SelectionStrategySource selStrtgSource, UserOld user) {
		SelectionShape shape = selStrtgSource.getDefaultSelectionShape();
		user.setSelectionShape(shape);
		RegionBuildingData regionData = selStrtgSource.get(shape).newRegionBuildingData();
		SelectionBuildingData selData = new SelectionBuildingData(user.getWorld(), regionData);
		user.setSelectionBuildingData(selData);
	}
}
