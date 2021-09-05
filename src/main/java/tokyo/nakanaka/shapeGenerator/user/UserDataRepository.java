package tokyo.nakanaka.shapeGenerator.user;

import java.util.HashMap;
import java.util.Map;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.World;
import tokyo.nakanaka.shapeGenerator.SelectionData;
import tokyo.nakanaka.shapeGenerator.SelectionHandler;
import tokyo.nakanaka.shapeGenerator.SelectionShape;

public class UserDataRepository {
	private SelectionHandler selHandler;
	private Map<User, UserData> userDataMap = new HashMap<>();
	
	public UserDataRepository(SelectionHandler selHandler) {
		this.selHandler = selHandler;
	}

	/**
	 * Get UserData. If there is a user data for the player, return it, otherwise create new one and return it.
	 * @param player player
	 * @return UserData which the user has
	 */
	public UserData prepareUserData(Player player) {
		User user = new User(player.getUniqueID());
		UserData userData = this.userDataMap.get(user);
		if(userData == null) {
			userData = new UserData();
			SelectionShape defaultShape = SelectionShape.CUBOID;
			userData.setSelectionShape(defaultShape);
			World world = player.getEntityPosition().world();
			SelectionData selData = this.selHandler.newSelectionData(defaultShape);
			selData.setWorld(world);
			userData.setSelectionData(selData);
			this.userDataMap.put(user, userData);
		}
		return userData;
	}
	
}
