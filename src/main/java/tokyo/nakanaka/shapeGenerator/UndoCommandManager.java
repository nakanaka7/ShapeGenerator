package tokyo.nakanaka.shapeGenerator;

import java.util.Deque;
import java.util.LinkedList;

import tokyo.nakanaka.UndoableCommand;
import tokyo.nakanaka.annotation.PrivateAPI;

@PrivateAPI
public class UndoCommandManager {
	private LinkedList<UndoableCommand> undoCmds = new LinkedList<>();
	private Deque<UndoableCommand> redoCmds = new LinkedList<>();
	
	public void add(UndoableCommand cmd) {
		undoCmds.offerLast(cmd);
		redoCmds.clear();
	}

	public boolean undo() {
		UndoableCommand cmd = undoCmds.pollLast();
		if(cmd == null) {
			return false;
		}
		cmd.undo();	
		redoCmds.offerFirst(cmd);
		return true;
	}
	
	public boolean redo() {
		UndoableCommand cmd = redoCmds.pollFirst();
		if(cmd == null) {
			return false;
		}
		cmd.redo();
		undoCmds.offerLast(cmd);
		return true;
	}
	
	public int undoSize() {
		return this.undoCmds.size();
	}
	
	/**
	 * @return null if empty
	 */
	public UndoableCommand getUndoCommand(int index) {
		return this.undoCmds.get(index);
	}

	/**
	 * @return null if empty
	 */
	public UndoableCommand getLastUndoCommand() {
		return this.undoCmds.peekLast();
	}
	
}
