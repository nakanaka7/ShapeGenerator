package tokyo.nakanaka.shapeGenerator;

import tokyo.nakanaka.world.World;

public interface PlayerEntity {
	World getWorld();
	int getX();
	int getY();
	int getZ();
	void print(String msg);
}
