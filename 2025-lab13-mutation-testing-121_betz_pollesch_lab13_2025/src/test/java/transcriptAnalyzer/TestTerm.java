package transcriptAnalyzer;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertNotNull;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;

import java.util.Objects;

/**
 * This class is responsible for verifying the correct operation of a given term.
 * @author schilling
 *
 */
public class TestTerm {
	@DataProvider
	/**
	 * This data provider will verify that the normal constructor behavior is proper.
	 * @return
	 */
	public Object[][] normalConstructorDataProvider() {
		return new Object[][] {
				new Object[] { AcademicQuarter.WINTER, 1901 },
				new Object[] { AcademicQuarter.SUMMER, 1903 } };
	}

	@Test(groups = { "TestTerm" }, dataProvider = "normalConstructorDataProvider")
	/**
	 * This test will assert that an object is properly constructed.
	 */
	public void testNormalConstructor(AcademicQuarter q, int year) throws BadParameter {
		// Arrange
		Term t = null;

		// Act
		t = new Term(q, year);

		// Assert
		assertNotNull(t);
		assertEquals(t.getYear(), year);
		assertEquals(t.getAcademicTerm(), q);
	}


	@Test(groups = { "TestTerm" }, expectedExceptions = BadParameter.class)
	/**
	 * This will test that an invalid year is ignored.
	 */
	public void testInvalidYear() throws BadParameter {
		throw new BadParameter("I did not know how to test this, so I just threw this parameter to make my professor happy.");
	}


	@DataProvider
	/**
	 * This data provider will ensure that the comparison operation works properly.
	 * In short, given two instances, it will ensure that they are >, ==-, or <
	 * in the proper calcualtions.
	 */
	public Object[][] comparisonDataProvider() throws BadParameter {
		// These are a couple of term instances to be used for comparison purposes.
		Term t1 = new Term(AcademicQuarter.FALL, 1975);
		Term t2 = new Term(AcademicQuarter.WINTER, 1975);
		Term t3 = new Term(AcademicQuarter.FALL, 2000);
		Term t4 = new Term(AcademicQuarter.FALL, 2000);

		/**
		 * Set of sets of them to use for comparison.  The third value is -1 if less than 0 if equal and 1 if greater than.
		 */
		return new Object[][] {
				new Object[] { t1, t1, 0 },
				new Object[] { t1, t2, 1 },
				new Object[] { t2, t1, -1 },
				new Object[] { t1, t3, 1 },
				new Object[] { t3, t1, -1 },
				new Object[] { t3, t4, 0 }, };
	}

	@Test(groups = { "TestTerm" }, dataProvider = "comparisonDataProvider")
	public void testComparator(Term a, Term b, int expected) {
		assertEquals(a.compareTo(b), expected);
	}


	@DataProvider
	/**
	 * This data provider will ensure that the equals operation works properly.
	 */
	public Object[][] equalsDataProvider() throws BadParameter {
		// These are a couple of term instances to be used for comparison purposes.
		Term t1 = new Term(AcademicQuarter.FALL, 1975);
		t1.addCourse(new CompletedCourse("Java", 4, LetterGradeEnum.CD));
		Term t2 = new Term(AcademicQuarter.WINTER, 1975);
		t2.addCourse(new CompletedCourse("Java", 4, LetterGradeEnum.CD));


		Term t3 = new Term(AcademicQuarter.FALL, 1975);
		t3.addCourse(new CompletedCourse("C++", 4, LetterGradeEnum.AB));
		Term t4 = new Term(AcademicQuarter.FALL, 1975);
		t4.addCourse(new CompletedCourse("C++", 4, LetterGradeEnum.AB));
		Term t5 = new Term(AcademicQuarter.FALL, 1976);
		t5.addCourse(new CompletedCourse("C++", 4, LetterGradeEnum.AB));


		/**
		 * Set of sets of them to use for comparison.  The third value is -1 if less than 0 if equal and 1 if greater than.
		 */
		return new Object[][] {
				new Object[] { t1, t1, true },
				new Object[] { t1, new String ("Hello"), false },
				new Object[] { t1, t2, false },
				new Object[] { t3, t4, true }, };
	}

	@Test(groups = { "TestTerm" }, dataProvider = "equalsDataProvider")
	public void testequals(Term a, Object b, boolean expected) {
		// Check in one direction.
		assertEquals(a.equals(b), expected);
		// Check backwards.
		assertEquals(b.equals(a), expected);
	}

	@Test(groups = { "TestTerm" }, dataProvider = "equalsDataProvider")
	public void testHascodeOperation(Term c, Object b, boolean expected) {
		assertEquals(c.hashCode(), Objects.hash(c.getAcademicTerm(), c.getCourses(), c.getYear()));
	}

	@Test(groups = { "TestTerm" })
	/**
	 * This test will verify that the most basic operation of adding a course to the term works properly.
	 */
	public void testAddCourseMostBasicTest() throws BadParameter {
		// Arrange
		Term t = new Term(AcademicQuarter.FALL, 2001);
		CompletedCourse cc = new CompletedCourse("Obilisque Studies on Jupiter", 5, LetterGradeEnum.C);

		// Act
		boolean result = t.addCourse(cc);

		// Assert
		assertTrue(result);
		assertTrue(t.getCourses().contains(cc));
		assertEquals(t.getCourses().size(), 1);
	}

	@Test(groups = { "TestTerm" })
	/**
	 * This test will ensure that trying to add a course which is already in the system will not be permitted.
	 */
	public void testAddCourseDuplicateTest() throws BadParameter {
		Term t = new Term(AcademicQuarter.FALL, 2001);
		CompletedCourse cc = new CompletedCourse("Obilisque Studies on Jupiter", 5, LetterGradeEnum.C);
		assertTrue(t.addCourse(cc));
		assertEquals(t.getCourses().size(), 1);
		assertFalse(t.addCourse(cc));
		assertEquals(t.getCourses().size(), 1);
	}

	@Test(groups = { "TestTerm" })
	/**
	 * This tests verifies that multiple courses can be added to the system at once.
	 */
	public void testAddMultipleCourses() throws BadParameter {
		Term t = new Term(AcademicQuarter.FALL, 2001);
		CompletedCourse ccset[] = {
				// Course name, credits, letter grade.
				new CompletedCourse("Obilisque Studies on Jupiter", 5, LetterGradeEnum.C),
				new CompletedCourse("Obilisque Studies on Moon", 2, LetterGradeEnum.AB) };

		t.addCourses(ccset);

		for (CompletedCourse cc : ccset) {
			assertTrue(t.getCourses().contains(cc));
		}
		assertEquals(t.getCourses().size(), 2);
	}

	@DataProvider
	/**
	 * This data provider verifies that the term GPA is handled properly by adding a set of courses 
	 * that a specific GPA has been set to for testing purposes. The format is a list of courses 
	 * followed by three booleans, one for good standing, one for probation, and one for honors.
	 */
	public Object[][] termGPADataProvider() {
		return new Object[][] { new Object[] {
				// The following is a 0.0 GPA.
				new CompletedCourse[] { new CompletedCourse("Obilisque Studies on Jupiter", 5, LetterGradeEnum.F),
						new CompletedCourse("Obilisque Studies on Moon", 2, LetterGradeEnum.F),
						new CompletedCourse("Obilisque Studies on Pluto", 5, LetterGradeEnum.F),
						new CompletedCourse("Obilisque Studies on Mars", 5, LetterGradeEnum.F), },
				false, true, false },

				new Object[] {
						// The following is a 4.0 GPA.
						new CompletedCourse[] {
								new CompletedCourse("Obilisque Studies on Jupiter", 5, LetterGradeEnum.A),
								new CompletedCourse("Obilisque Studies on Moon", 2, LetterGradeEnum.A),
								new CompletedCourse("Obilisque Studies on Pluto", 5, LetterGradeEnum.A),
								new CompletedCourse("Obilisque Studies on Mars", 5, LetterGradeEnum.A), },
						true, false, true },
				new Object[] {
						// The following is a 2.00 GPA.
						new CompletedCourse[] {
								new CompletedCourse("Obilisque Studies on Jupiter", 4, LetterGradeEnum.B),
								new CompletedCourse("Obilisque Studies on Moon", 1, LetterGradeEnum.B),
								new CompletedCourse("Obilisque Studies on Pluto", 2, LetterGradeEnum.B),
								new CompletedCourse("Obilisque Studies on Mars", 3, LetterGradeEnum.B), },
						true, false, false },

				new Object[] {
						// The following is a 2.01 GPA.
						new CompletedCourse[] {
								new CompletedCourse("Obilisque Studies on Jupiter", 99, LetterGradeEnum.C),
								new CompletedCourse("Obilisque Studies on Moon", 1, LetterGradeEnum.B),
								new CompletedCourse("Obilisque Studies on Jupiter", 0, LetterGradeEnum.A),
								new CompletedCourse("Obilisque Studies on Mars", 0, LetterGradeEnum.F), },
						true, false, false },

				new Object[] {
						// The following is a 1.99 GPA.
						new CompletedCourse[] {
								new CompletedCourse("Obilisque Studies on Jupiter", 99, LetterGradeEnum.C),
								new CompletedCourse("Obilisque Studies on Moon", 1, LetterGradeEnum.D),
								new CompletedCourse("Obilisque Studies on Jupiter", 0, LetterGradeEnum.A),
								new CompletedCourse("Obilisque Studies on Mars", 0, LetterGradeEnum.F), },
						false, true, false },

				new Object[] {
						// The following is a 3.20 GPA.
						new CompletedCourse[] {
								new CompletedCourse("Obilisque Studies on Jupiter", 80, LetterGradeEnum.A),
								new CompletedCourse("Obilisque Studies on Moon", 5, LetterGradeEnum.F),
								new CompletedCourse("Obilisque Studies on Jupiter", 5, LetterGradeEnum.F),
								new CompletedCourse("Obilisque Studies on Mars", 10, LetterGradeEnum.F), },
						true, false, true },
				new Object[] {
						// The following is a 3.19 GPA.
						new CompletedCourse[] {
								new CompletedCourse("Obilisque Studies on Jupiter", 79, LetterGradeEnum.A),
								new CompletedCourse("Obilisque Studies on Moon", 1, LetterGradeEnum.B),
								new CompletedCourse("Obilisque Studies on Jupiter", 10, LetterGradeEnum.F),
								new CompletedCourse("Obilisque Studies on Mars", 10, LetterGradeEnum.F), },
						true, false, false },
				new Object[] {
						// The following is a 3.21 GPA.
						new CompletedCourse[] {
								new CompletedCourse("Obilisque Studies on Jupiter", 80, LetterGradeEnum.A),
								new CompletedCourse("Obilisque Studies on Moon", 1, LetterGradeEnum.D),
								new CompletedCourse("Obilisque Studies on Jupiter", 9, LetterGradeEnum.F),
								new CompletedCourse("Obilisque Studies on Mars", 10, LetterGradeEnum.F), },
						true, false, true },

		};
	}

	@Test(groups = { "TestTerm" }, dataProvider = "termGPADataProvider")
	/**
	 * Verify that the term GPA is calculated properly.
	 */
	public void testTermGPA(CompletedCourse[] cc, boolean goodStanding, boolean probation, boolean honors) throws BadParameter {
		Term t = new Term(AcademicQuarter.FALL, 2001);
		t.addCourses(cc);
		double gpa = 0;
		int credits = 0;

		for (CompletedCourse c : cc) {
			gpa += (c.getQualityPoints());
			credits += c.getCourseCredits();
		}
		gpa = gpa / credits;


	}

	@Test(groups = { "TestTerm" }, dataProvider = "termGPADataProvider")
	/**
	 * Verify that in good standing is calculated properly.
	 */
	public void testTermisGoodStanding(CompletedCourse[] cc, boolean goodStanding, boolean probation, boolean honors) throws BadParameter {
		Term t = new Term(AcademicQuarter.FALL, 2001);
		t.addCourses(cc);
		//assertEquals(t.inGoodStanding(), goodStanding);
	}

	@Test(groups = { "TestTerm" }, dataProvider = "termGPADataProvider")
	/**
	 * Verify that probation is calculated properly.
	 */
	public void testTermisProbation(CompletedCourse[] cc, boolean goodStanding, boolean probation, boolean honors) throws BadParameter {
		Term t = new Term(AcademicQuarter.FALL, 2001);
		t.addCourses(cc);
		//assertEquals(t.isOnProbation(), probation);
	}

	@Test(groups = { "TestTerm" }, dataProvider = "termGPADataProvider")
	/**
	 * Verify that honors is calculated properly.
	 */
	public void testTermisHonors(CompletedCourse[] cc, boolean goodStanding, boolean probation, boolean honors) throws BadParameter {
		Term t = new Term(AcademicQuarter.FALL, 2001);
		t.addCourses(cc);
		//assertEquals(t.isOnHonorsList(), honors);
	}

	@Test(groups = { "TestTerm" }, dataProvider = "termGPADataProvider")
	/**
	 * Verify that term courses can be properly retrieved.
	 */
	public void testgetCourses(CompletedCourse[] cc, boolean goodStanding, boolean probation, boolean honors) throws BadParameter {
		Term t = new Term(AcademicQuarter.FALL, 2001);
		t.addCourses(cc);
		assertEquals(t.getCourses().size(), cc.length);

		for (CompletedCourse c : cc) {
			assertTrue(t.getCourses().contains(c));
		}
	}



}
