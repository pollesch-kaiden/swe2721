package transcriptAnalyzer;

import java.util.Objects;

/**
 * This class will hold information about a given course that has been
 * completed.
 * 
 * @author schilling
 *
 */
public class CompletedCourse implements Comparable<CompletedCourse> {
	/**
	 * This is the name of the course.
	 */
	private final String courseName;
	/**
	 * This is the number of credits for the given course. Must be positive.
	 */
	private final int courseCredits;

	/**
	 * This is the letter grade for the given course.
	 */
	private final LetterGradeEnum letterGrade;

	/**
	 * This will instantiate a new course.
	 * 
	 * @param courseName
	 *            This is the name of the course. Must be non-null and longer
	 *            than 1 character.
	 * @param courseCredits
	 *            This is the credits for the given course. Must be positive.
	 * @param letterGrade
	 *            This is the letter grade the student received in the course.
	 * @throws IllegalArgumentException
	 *             This will be thrown if the course name is invalid or the
	 *             credits is out of range.
	 */
	public CompletedCourse(String courseName, int courseCredits, LetterGradeEnum letterGrade) throws IllegalArgumentException {
		super();
		if (courseName.length() < 2) {
			throw new IllegalArgumentException("Course Name must be 2 or more characters in length.");
		}

		if (courseCredits < 0) {
			throw new IllegalArgumentException("Credits must be 0 or greater.");
		}

		this.courseName = courseName;
		this.courseCredits = courseCredits;
		this.letterGrade = letterGrade;
	}

	/**
	 * This will obtain the course name for the given course.
	 * 
	 * @return The name of the course.
	 */
	public String getCourseName() {
		return courseName;
	}

	/**
	 * This method will return the course credits for the given course.
	 * 
	 * @return The number of credits for the course.
	 */
	public int getCourseCredits() {
		return courseCredits;
	}

	/**
	 * This method will return the letter grade for the given course.
	 * 
	 * @return The letter grade for the course.
	 */
	public LetterGradeEnum getLetterGrade() {
		return letterGrade;
	}

	/**
	 * This method will return the quality points earned for this course.
	 * 
	 * @return The number of quality points is the letter grade quality points
	 *         multiplied by the number of credits.
	 */
	public double getQualityPoints() {
		double pts = 0;
		switch (letterGrade)
		{
			case A:
				pts = 4.0;
				break;
			case AB:
				pts = 3.5;
				break;
			case B:
				pts = 3.0;
				break;
			case BC:
				pts = 2.5;
				break;
			case C:
				pts = 2.0;
				break;
			case CD:
				pts = 1.5;
				break;
			case D:
				pts = 1.0;
				break;
			default:
			case F:
				pts = 0.0;
		}
		return pts * courseCredits;
	}

	@Override
	public boolean equals(Object o) {
		// If the object is compared with itself then return true
		if (o == this) {
			return true;
		}

		/*
		 * Check if o is an instance of Complex or not "null instanceof [type]"
		 * also returns false
		 */
		if (!(o instanceof CompletedCourse)) {
			return false;
		}

		CompletedCourse cc = (CompletedCourse) o;
		if ((cc.courseCredits == this.courseCredits) && (cc.courseName.equals(this.courseName))
				&& (cc.letterGrade == this.letterGrade)) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public int hashCode() {
		return Objects.hash(this.courseCredits, this.courseName, this.letterGrade);
	}

	/**
	 * Compares this object with the specified object for order.  Returns a
	 * negative integer, zero, or a positive integer as this object is less
	 * than, equal to, or greater than the specified object.
	 *
	 * <p>The implementor must ensure
	 * {@code sgn(x.compareTo(y)) == -sgn(y.compareTo(x))}
	 * for all {@code x} and {@code y}.  (This
	 * implies that {@code x.compareTo(y)} must throw an exception iff
	 * {@code y.compareTo(x)} throws an exception.)
	 *
	 * <p>The implementor must also ensure that the relation is transitive:
	 * {@code (x.compareTo(y) > 0 && y.compareTo(z) > 0)} implies
	 * {@code x.compareTo(z) > 0}.
	 *
	 * <p>Finally, the implementor must ensure that {@code x.compareTo(y)==0}
	 * implies that {@code sgn(x.compareTo(z)) == sgn(y.compareTo(z))}, for
	 * all {@code z}.
	 *
	 * <p>It is strongly recommended, but <i>not</i> strictly required that
	 * {@code (x.compareTo(y)==0) == (x.equals(y))}.  Generally speaking, any
	 * class that implements the {@code Comparable} interface and violates
	 * this condition should clearly indicate this fact.  The recommended
	 * language is "Note: this class has a natural ordering that is
	 * inconsistent with equals."
	 *
	 * <p>In the foregoing description, the notation
	 * {@code sgn(}<i>expression</i>{@code )} designates the mathematical
	 * <i>signum</i> function, which is defined to return one of {@code -1},
	 * {@code 0}, or {@code 1} according to whether the value of
	 * <i>expression</i> is negative, zero, or positive, respectively.
	 *
	 * @param o the object to be compared.
	 * @return a negative integer, zero, or a positive integer as this object
	 * is less than, equal to, or greater than the specified object.
	 * @throws NullPointerException if the specified object is null
	 * @throws ClassCastException   if the specified object's type prevents it
	 *                              from being compared to this object.
	 */
	@Override
	public int compareTo(CompletedCourse o) {
		String s1 = o.getCourseName() + o.getLetterGrade()+o.getCourseCredits();
		String s2 = this.getCourseName() + this.getLetterGrade()+this.getCourseCredits();
		return s1.compareTo(s2);
	}
}
