package transcriptAnalyzer;
/**
 * This enumeration will store a letter grade for a given class. the letter
 * grade follows the MSOE grading scale.
 * 
 * @author schilling
 *
 */
public enum LetterGradeEnum {
	A(4), AB(3.5), B(3), BC(2.5), C(2.0), CD(1.5), D(1.0), F(0);
	private final double qualityPoints;

	LetterGradeEnum(double qp) {
		this.qualityPoints = qp;
	}

	public double getQualityPoints() {
		return this.qualityPoints;
	}
}
