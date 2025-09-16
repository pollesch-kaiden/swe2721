package transcriptAnalyzer;
/**
 * This enumeration will keep track of the order of a set of classes.
 * 
 * @author schilling
 *
 */
public enum AcademicQuarter {
	FALL(3), WINTER(4), SPRING(1), SUMMER(2);
	private final int order;

	AcademicQuarter(int qp) {
		this.order = qp;
	}

	public int getOrder() {
		return this.order;
	}
}
