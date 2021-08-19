package tokyo.nakanaka.shapeGenerator.user;

import tokyo.nakanaka.shapeGenerator.Item;

public interface HumanPlayer {
	void giveItem(Item item, int num);
	String getName();
}
