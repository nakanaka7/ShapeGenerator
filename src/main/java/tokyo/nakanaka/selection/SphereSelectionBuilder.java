package tokyo.nakanaka.selection;

import java.util.List;

import tokyo.nakanaka.world.World;

public class SphereSelectionBuilder implements SelectionBuilder{

	@Override
	public boolean onLeftClickBlock(World world, int x, int y, int z) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onRightClickBlock(World world, int x, int y, int z) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onCommand(World world, int offsetX, int offsetY, int offsetZ, String[] args) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<String> onTabComplete(String[] args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getStateLines() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Selection build() {
		// TODO Auto-generated method stub
		return null;
	}

}
