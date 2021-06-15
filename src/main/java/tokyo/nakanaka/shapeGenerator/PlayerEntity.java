package tokyo.nakanaka.shapeGenerator;

import java.util.UUID;

import tokyo.nakanaka.world.World;

public interface PlayerEntity {
	UUID getUniqueID();
	World getWorld();
	int getX();
	int getY();
	int getZ();
	void print(String msg);
}
