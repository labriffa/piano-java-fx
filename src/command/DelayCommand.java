package command;
import java.io.Serializable;

/**
 * The DelayCommand class provides an implementation for the CommandIF execute method.
 * Whose body forces the thread that it's called on to be delayed 'N' number of seconds. 
 *
 */

public class DelayCommand implements CommandIF, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long delay;

	public DelayCommand(long delay) {
		this.delay = delay;
	}
	
	@Override
	public void execute() {
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
