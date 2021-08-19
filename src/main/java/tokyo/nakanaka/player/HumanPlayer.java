package tokyo.nakanaka.player;

import tokyo.nakanaka.shapeGenerator.Item;

public interface HumanPlayer {
	void giveItem(Item item, int num);
	String getName();
}
