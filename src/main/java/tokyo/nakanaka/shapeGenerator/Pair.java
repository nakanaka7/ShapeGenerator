package tokyo.nakanaka.shapeGenerator;

@Deprecated
public class Pair<E1, E2> {
	private E1 e1;
	private E2 e2;
	
	public Pair(E1 e1, E2 e2) {
		this.e1 = e1;
		this.e2 = e2;
	}

	public E1 getFirst() {
		return e1;
	}

	public E2 getSecond() {
		return e2;
	}
	
}
