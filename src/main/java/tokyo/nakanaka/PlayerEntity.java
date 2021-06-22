package tokyo.nakanaka;

import java.util.UUID;

import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.world.World;
@Deprecated
public interface PlayerEntity extends Logger{
	UUID getUniqueID();
	World getWorld();
	int getX();
	int getY();
	int getZ();
}
