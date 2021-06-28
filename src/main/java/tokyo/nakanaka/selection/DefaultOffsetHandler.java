package tokyo.nakanaka.selection;

import tokyo.nakanaka.math.Vector3D;

public interface DefaultOffsetHandler {
	String getDefaultOffsetLabel();
	Vector3D getDefaultOffset(RegionBuilder builder);
}
