package transcriptAnalyzer;

import java.util.*;


public class Term implements Comparable<Term> {
	private final AcademicQuarter academicTerm;
	private final int year;
	private final SortedSet<CompletedCourse> courses;

	/**
	 * This method will instantiate a new instance of a term.
	 * 
	 * @param academicTerm
	 *            This is the quarter for the term.
	 * @param year
	 *            This is the year. The year is expressed based upon fall of the
	 *            year in which the student started.
	 * @throws BadParameter
	 *             This will be thrown if the year is less than 1900.
	 */
	public Term(AcademicQuarter academicTerm, int year) throws BadParameter {
		super();
		if (year < 1900) {
			throw new BadParameter("Invalid year.");
		}
		courses = new TreeSet<>();
		this.academicTerm = academicTerm;
		this.year = year;
	}

	/**
	 * This method will return the academic term.
	 * 
	 * @return The academic quarter for the course will be obtained.
	 */
	public AcademicQuarter getAcademicTerm() {
		return academicTerm;
	}

	/**
	 * This method will return the year for the term.
	 * 
	 * @return The year for the given term.
	 */
	public int getYear() {
		return year;
	}

	/**
	 * This method will return the list of completed courses for this term.
	 * 
	 * @return The completed courses will be returned.
	 */
	public SortedSet<CompletedCourse> getCourses() {
		return courses;
	}

	/**
	 * This method will add a given course to the set of courses taken this
	 * term.
	 * 
	 * @param c
	 *            This is the course that is to be added.
	 * @return true if the course is not already in the set. False otherwise.
	 */
	public boolean addCourse(CompletedCourse c) {
		return courses.add(c);
	}

	/**
	 * This will add an array of courses to the set of courses for the term.
	 * 
	 * @param c The array of courses taken in the term.
	 */
	public void addCourses(CompletedCourse[] c) {
		int index = 0;
		while (index < c.length)
		{
			addCourse(c[index]);
			index++;
		}
	}

	/**
	 * This method will calculate the GPA for the given term.
	 * 
	 * @return The GPA for the term will be returned.
	 */
	public double calculateTermGPA() {
		int credits = 0;
		double points = 0.0;

		for (CompletedCourse cc : this.courses) {
			credits += cc.getCourseCredits();
			points += cc.getQualityPoints();
		}
		return points / credits;
	}

	/**
	 * This method will determine if the student is in good standing or not.
	 * 
	 * @return The return will be true if the GPA is 2.0 or higher.
	 */
	public boolean inGoodStanding() {
		// Note: The multiplication is done to ensure that the result is only to 2 decimal places.
	    return ((int)(100*calculateTermGPA())) >= 200;
	}

	/**
	 * This method will indicate whether or not the given student is on
	 * probation.
	 * 
	 * @return The return will be true if the GPA is below a 2.0 or false if the
	 *         GPA is 2.0 or higher.
	 */
	public boolean isOnProbation() {
		// Note: The multiplication is done to ensure that the result is only to 2 decimal places.
		return ((int)(100*calculateTermGPA())) < 200;
	}

	/**
	 * This method will indicate whether the student is on the honors list for
	 * the given term.
	 * 
	 * @return True will be returned if the gpa is greater than or equal to 3.2.
	 */
	public boolean isOnHonorsList() {
		// Note: The multiplication is done to ensure that the result is only to 2 decimal places.
		return ((int)(100*calculateTermGPA())) >= 320;
	}

	@Override
	public int compareTo(Term arg0) {
		// If the objects are equal, return 0.
		if (this.equals(arg0)) {
			return 0;
		}

		// The following line compares the terms.  To do this, the year is multiplied by ten and then the academic term is added in.  Thus fall term of 1998 would be represented as 19983.
		// This allows for a simple numeric comparison to be used to sort these items.
		int comparison = year * 10 + academicTerm.getOrder() - (arg0.year * 10 + arg0.academicTerm.getOrder());

		if (comparison == 0) {
			// The terms are the same.
			return 0;
		} else if (comparison >= 0) {
			return -1;
		} else if (comparison <= 0){
			return 1;
		}
		else {
			return 0;
		}
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
		if (!(o instanceof Term)) {
			return false;
		}

		Term t = (Term) o;
		if ((t.academicTerm == this.academicTerm) && (t.courses.equals(this.courses))
				&& (t.year == this.year)) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public int hashCode() {
		return Objects.hash(this.academicTerm, this.courses, this.year);
	}



}
