package tokyo.nakanaka.command;

public class ShiftCommand implements MoveCommand{
	private GenerateCommand originalCmd;
	
	public ShiftCommand(GenerateCommand originalCmd, int dx, int dy, int dz) {
		this.originalCmd = originalCmd;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void redo() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public GenerateCommand getOriginalCommand() {
		return this.originalCmd;
	}

}
