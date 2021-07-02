package tokyo.nakanaka.selection;

import java.util.List;

import tokyo.nakanaka.Pair;
@Deprecated
public interface RegionStateMessageHandler {
	List<Pair<String, String>> onMessage(RegionBuilder builder);
}
