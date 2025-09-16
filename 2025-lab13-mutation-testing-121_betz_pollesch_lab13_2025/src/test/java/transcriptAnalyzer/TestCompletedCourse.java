package transcriptAnalyzer;

import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.util.Objects;
import org.testng.annotations.DataProvider;

/**
 * This class is going to handle testing the Completed course class.
 * 
 * @author schilling
 *
 */
public class TestCompletedCourse {
	@DataProvider
	/**
	 * This data provider provides a set of potential course names, credits, and
	 * letter grades, all of which should result in a failure of the
	 * constructor.  The first part of the data provider is the course name, followed by the credits, followed by the letter grade earned.
	 */
	public Object[][] invalidOperationDataProvider() {
		return new Object[][] { 
			new Object[] { "", 1, LetterGradeEnum.A }, 
			new Object[] { "B", 2, LetterGradeEnum.AB },
			new Object[] { "B", 1, LetterGradeEnum.A }, 
			new Object[] { "B", 2, LetterGradeEnum.AB },
			};
	}

	@Test(groups = {
			"CompletedCourseTests" }, dataProvider = "invalidOperationDataProvider", expectedExceptions = IllegalArgumentException.class)
	/**
	 * This test will handle verification of normal operation of the completed
	 * course class.
	 * 
	 * @param courseName
	 *            This is the name of the course.
	 * @param courseCredits
	 *            This is the number of credits
	 * @param lg
	 *            This is the letter grade received.
	 */
	public void testConstructorInvalidOperation(String courseName, int courseCredits, LetterGradeEnum lg) {
		new CompletedCourse(courseName, courseCredits, lg);
	}

	@DataProvider
	/**
	 * This data provider will verify that the normal operation of the constructor
	 * works properly, as well as that the accessors function properly.
	 * @return
	 */
	public Object[][] normalOperationDataProvider() {
		return new Object[][] { new Object[] { "Basket Weaving 101", 1, LetterGradeEnum.A },
				new Object[] { "Basket Weaving 102", 2, LetterGradeEnum.AB },
				new Object[] { "Basket Weaving 103", 2, LetterGradeEnum.B },
				new Object[] { "Basket Weaving 105", 2, LetterGradeEnum.C },
				new Object[] { "Basket Weaving 107", 2, LetterGradeEnum.F },
		};
	}

	@Test(groups = { "CompletedCourseTests" }, dataProvider = "normalOperationDataProvider")
	/**
	 * This test will handle verification of normal operation of the completed
	 * course class.
	 *
	 * @param courseName
	 *            This is the name of the course.
	 * @param courseCredits
	 *            This is the number of credits
	 * @param lg
	 *            This is the letter grade received.
	 */
	public void testConstructorNormalOperation(String courseName, int courseCredits, LetterGradeEnum lg) {
		// Arrange
		CompletedCourse c = null;

		// Act
		c = new CompletedCourse(courseName, courseCredits, lg);

		// Assert
		assertNotNull(c);
		assertEquals(c.getCourseName(), courseName);
		assertEquals(c.getCourseCredits(), courseCredits);
		assertEquals(c.getLetterGrade(), lg);
		assertEquals(c.getQualityPoints(), lg.getQualityPoints() * courseCredits, 0.01);
	}

	@DataProvider
	/**
	 * This data provider will handle the equals scenario.  In short, each of these
	 * test cases contains one or more CompletedCourse sinatnce and they are either
	 * equal or not equal.
	 * @return
	 */
	public Object[][] equalsOperationDataProvider() {
		return new Object[][] { 
			new Object[] { new CompletedCourse("Bob", 1, LetterGradeEnum.A), new CompletedCourse("Bob", 1, LetterGradeEnum.A), true },
            new Object[] { new CompletedCourse("Bob", 1, LetterGradeEnum.A), new CompletedCourse("Bob", 2, LetterGradeEnum.A), false },
			new Object[] { new CompletedCourse("Bob", 1, LetterGradeEnum.A), new String("Hi"), false },
			new Object[] { new CompletedCourse("Bob", 1, LetterGradeEnum.A), new CompletedCourse("Bb", 1, LetterGradeEnum.A), false }, };
	}

	@Test(groups = { "CompletedCourseTests" }, dataProvider = "equalsOperationDataProvider")
	public void testEqualsOperation(CompletedCourse c, Object d, boolean same) {
		// Arrange

		// Act

		//Assert
		if (d == null) {
			assertEquals(c.equals(c), same);
		} else {
			assertEquals(c.equals(d), same);
		}

	}

	@Test(groups = { "CompletedCourseTests" }, dataProvider = "equalsOperationDataProvider")
	/**
	 * This test will verify that the hashcode generated is the same for equal objects.
	 */
	public void testHascodeOperation(CompletedCourse c, Object d, boolean same) {
		assertEquals(c.hashCode(), Objects.hash(c.getCourseCredits(), c.getCourseName(), c.getLetterGrade()));
	}
}
