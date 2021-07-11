package tokyo.nakanaka;

import java.util.Deque;
import java.util.LinkedList;

import tokyo.nakanaka.command.UndoableCommand;

public class UndoCommandManager {
	private LinkedList<UndoableCommand> undoCmds = new LinkedList<>();
	private Deque<UndoableCommand> redoCmds = new LinkedList<>();
	
	public void add(UndoableCommand cmd) {
		undoCmds.offerLast(cmd);
		redoCmds.clear();
	}
	
	/**
	 * @return null if empty
	 */
	public UndoableCommand peekUndoCommand() {
		return this.undoCmds.peekLast();
	}
	
	/**
	 * @param index from last
	 * @return
	 */
	public UndoableCommand peekUndoCommand(int index) {
		return this.undoCmds.get(this.undoCmds.size() - index - 1);
	}
	
	/**
	 * @return null if empty
	 */
	public UndoableCommand getUndoCommand() {
		UndoableCommand cmd = undoCmds.pollLast();
		if(cmd != null) {
			redoCmds.offerFirst(cmd);
		}
		return cmd;
	}
	
	/**
	 * @return null if empty
	 */
	public UndoableCommand getRedoCommand() {
		UndoableCommand cmd = redoCmds.pollFirst();
		if(cmd != null) {
			undoCmds.offerLast(cmd);
		}
		return cmd;
	}
	
}
