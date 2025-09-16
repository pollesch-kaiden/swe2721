package transcriptAnalyzer;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * This class will hold a transcript. A transcript is a set of terms containing
 * a set of courses.
 * 
 * @author schilling
 *
 */
public class Transcript {
	/**
	 * These are the terms that have been completed.
	 */
	private final SortedSet<Term> completedTerms;
	/**
	 * This is the name of the student.
	 */
	private final String studentName;

	/**
	 * This will construct a new instance of a transcript.
	 * 
	 * @param studentName
	 *            This is the name of the student.
	 * @throws BadParameter
	 *             This exception will be thrown if the name is too short (<3
	 *             characters) or is null.
	 */
	public Transcript(String studentName) throws BadParameter {
		super();
		if ((studentName == null)){
			throw new BadParameter("The student name can not be null.");
		}

		if ((studentName.length() <= 2)) {
			throw new BadParameter("The student name must be at least 3 characters in length");
		}

		this.studentName = studentName;
		completedTerms = new TreeSet<>();
	}

	/**
	 * This method will return the student name.
	 * @return The name of the student.
	 */
	public String getStudentName() {
		return studentName;
	}

	/**
	 * This method will add a term to the transcript.
	 * 
	 * @param t
	 *            This is the term to add.
	 * @return True if the term can be added. false otherwise.
	 */
	public boolean addTerm(Term t) {
		return completedTerms.add(t);
	}

	/**
	 * This method will print a transcript of all courses taken.
	 */
	public void printTranscript() {
		String delimiter = "===============================================================================================";
		System.out.println(studentName);
		for (Term t : completedTerms) {
			System.out.println(delimiter);
			System.out.println(t.getAcademicTerm().name() + " " + t.getYear());
			for (CompletedCourse c : t.getCourses()) {
				System.out.println(c.getCourseName() + "\t" + c.getCourseCredits() + "\t" + c.getLetterGrade());
			}
			System.out.println("GPA: " + t.calculateTermGPA() + "\tGood standing: " + t.inGoodStanding()
					+ "\tProbation: " + t.isOnProbation() + "\tHonors: " + t.isOnHonorsList());
			System.out.println(delimiter);
		}
		if (completedTerms.size() >= 2) {
			System.out.println("Overall GPA: " + calculateGPA() + "\tHigh Honors: " + isHighHonors() + "\tDean's List: "
					+ isDeansList());
		}
		else
		{
			System.out.println("Overall GPA: " + calculateGPA() + "\tHigh Honors: " + isHighHonors() + "\tDean's List: "
					+ isDeansList());
		}
	}

	/**
	 * This method will determine if the student is on the dean's list.
	 * 
	 * @return true if the overall gpa is between 3.20 (inc.) and 3.70 (excl.)
	 */
	public boolean isDeansList() {
		// This calculation is done to get the GPA into the nearest hundredth of a point.
		int gpa = (int)(100*calculateGPA());
		boolean retVal;
		if (gpa < 320) {
			retVal = false;
		} else if (gpa >= 370) {
			retVal = false;
		} else {
			retVal = true;
		}
		return retVal;
	}

	/**
	 * This will determine if the student has achieved high honors.
	 * 
	 * @return true if gpa >= 3.70 false otw.
	 */
	public boolean isHighHonors() {
		// Note: Multiple by 100 to keep accurate to the nearest 0.01.
		return ((int)(100*calculateGPA())) >= 370;
	}

	/**
	 * This will calculate the GPA.
	 * 
	 * @return The sum of all quality points divided by the credits taken will
	 *         be returned.
	 */
	public double calculateGPA() {
		double totalQualityPoints = 0;
		int totalCreditsAttempted = 0;

		for (Term t : completedTerms) {
			for (CompletedCourse c : t.getCourses()) {
				totalQualityPoints += c.getQualityPoints();
				totalCreditsAttempted += c.getCourseCredits();
			}
		}
		double gpa = totalQualityPoints / totalCreditsAttempted;
		return gpa;
	}
}
