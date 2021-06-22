package tokyo.nakanaka;

public interface Scheduler {
	void scheduleLater(int tick, Runnable runnable);
}
