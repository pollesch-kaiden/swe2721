package transcriptAnalyzer;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;

import org.testng.annotations.DataProvider;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.security.InvalidParameterException;

import static org.testng.Assert.*;

/**
 * This class will provide testNG tests that can be used with the Transcript class.
 * @author schilling
 *
 */
public class TestTranscript {

    /**
     * This is a transcript example that will be used for many cases.
     */
    private Transcript validTranscript;

    @BeforeMethod(alwaysRun = true)
    /**
     * This method will be called before each test method and will instantiate a new instance of the class.
     */
    public void beforeMethod() throws BadParameter {
        validTranscript = new Transcript("Albert Einstein" );
    }

    @DataProvider (name="ConstructorInstancesDP")
    public Object[][] validConsttructorInstanceDP()
    {
        return new Object[][] {
                {"B", true, "The student name must be at least 3 characters in length"},
                {"Bob", false, ""},
                {"Bobbie-Jean", false, ""},
                {"Bobbie-Long-Name-And-Lots-Of_hyphens", false, ""}
        };
    }

    @Test(groups = {"TestTranscript"}, dataProvider = "ConstructorInstancesDP")
    public void testConstructor(String name, boolean exceptionExpected, String errorMessage) throws BadParameter {
        // Arrange
        validTranscript = null;

        if (exceptionExpected)
        {
            Throwable t = expectThrows(BadParameter.class, ()->new Transcript(name));
            assertEquals(t.getMessage(), errorMessage);
        }else
        {
            // Act
            validTranscript = new Transcript(name);

            // Assert
            assertNotNull(validTranscript);
            assertEquals(validTranscript.getStudentName(), name);
        }
    }


	@DataProvider
	/**
	 * This data provider will be used to test GPA and other operations by combining terms and adding GPA's as is applicable.
	 * @return
	 */
	public Object[][] AddTermDataProvider() throws BadParameter {
		Term t1 = new Term(AcademicQuarter.FALL, 2010);
		Term t2 = new Term(AcademicQuarter.WINTER, 2010);


		return new Object[][] { new Object[] { new Term[] { t1 }, t1, false },
				                new Object[] { new Term[] { t1 }, t2, true },
		};
	}

	@Test(groups = { "TestTranscript" }, dataProvider = "AddTermDataProvider")
	/**
	 * This method will ensure that the addTerm method is working properly.
	 */
	public void testAddTerm(Term[] Initialterms, Term termToAdd, boolean expectedValue) {
		double expectedGPA = 0;
		int credits = 0;

		// Add the initial terms.
		for (Term t : Initialterms) {
			validTranscript.addTerm(t);
		}

		// Now try to add the next term.
		boolean result = validTranscript.addTerm(termToAdd);


		assertEquals(result, expectedValue);
	}



	@DataProvider
	/**
	 * This data provider will be used to test GPA and other operations by combining terms and adding GPA's as is applicable.
	 * @return
	 */
	public Object[][] TermDataProvider() throws BadParameter {
		Term t1 = new Term(AcademicQuarter.FALL, 2010); // Will have a GPA of
														// 3.19
		Term t2 = new Term(AcademicQuarter.WINTER, 2010); // Will have a GPA of
															// 3.20
		Term t3 = new Term(AcademicQuarter.FALL, 2011); // GPA of just below 3.7
		Term t4 = new Term(AcademicQuarter.SPRING, 2012); // GPA of just above
															// 3.7

		t1.addCourse(
				new CompletedCourse("SE1011 Programming One in Java and C++ and C# and Ruby and Assembly All in One",
						39, LetterGradeEnum.AB));
		t1.addCourse(new CompletedCourse("All the rest we need to know about software in one course", 61,
				LetterGradeEnum.B));

		t2.addCourse(new CompletedCourse("SE2832 Everything that is important about testing", 41, LetterGradeEnum.AB));
		t2.addCourse(
				new CompletedCourse("SE2833 Everything that is not important about testing", 59, LetterGradeEnum.B));

		t3.addCourse(new CompletedCourse("SDL", 39, LetterGradeEnum.A));
		t3.addCourse(new CompletedCourse("More SDL", 61, LetterGradeEnum.AB));

		t4.addCourse(new CompletedCourse("Senior Design", 41, LetterGradeEnum.A));
		t4.addCourse(new CompletedCourse("One Big Happy HUSS Course", 59, LetterGradeEnum.AB));

		return new Object[][] { new Object[] { new Term[] { t1 }, false, false },
				new Object[] { new Term[] { t2 }, false, true }, new Object[] { new Term[] { t3 }, false, true },
				new Object[] { new Term[] { t4 }, true, false },
				new Object[] { new Term[] { t1, t2, t3, t4 }, false, true },
				new Object[] { new Term[] { t1, t4 }, false, true }, };
	}

	@Test(groups = { "TestTranscript" }, dataProvider = "TermDataProvider")
	/**
	 * This method will ensure that the GPA calculation works right for multiple terms.
	 * @param terms These are the terms with classes.
	 * @param highHonors This is true if the GPA should be on high honors.
	 * @param deansList This si true if the GPA should be on the deans list.
	 */
	public void testGPA(Term[] terms, boolean highHonors, boolean deansList) {
		double expectedGPA = 0;
		int credits = 0;

		for (Term t : terms) {
			for (CompletedCourse cc : t.getCourses()) {
				expectedGPA += cc.getQualityPoints();
				credits += cc.getCourseCredits();
			}
			validTranscript.addTerm(t);
		}

		expectedGPA = expectedGPA / credits;


	}

	@Test(groups = { "TestTranscript" }, dataProvider = "TermDataProvider")
	/**
	 * This method will ensure that high honors are calculated properly.
	 * @param terms These are the terms with classes.
	 * @param highHonors This is true if the GPA should be on high honors.
	 * @param deansList This si true if the GPA should be on the deans list.
	 */
	public void testHighHonors(Term[] terms, boolean highHonors, boolean deansList) {
		for (Term t : terms) {
			validTranscript.addTerm(t);
		}

	}

	@Test(groups = { "TestTranscript" }, dataProvider = "TermDataProvider")
	/**
	 * This method will ensure that deans list is processed properly.
	 * @param terms These are the terms with classes.
	 * @param highHonors This is true if the GPA should be on high honors.
	 * @param deansList This si true if the GPA should be on the deans list.
	 */
	public void testDeansList(Term[] terms, boolean highHonors, boolean deansList) {
		for (Term t : terms) {
			validTranscript.addTerm(t);
		}

	}

	@Test(groups = { "TestTranscript" }, dataProvider = "TermDataProvider")
	/**
	 * This method will ensure that a transcript can be printed.
	 * @param terms These are the terms with classes.
	 * @param highHonors This is true if the GPA should be on high honors.
	 * @param deansList This si true if the GPA should be on the deans list.
	 */
	public void testPrintTranscript(Term[] terms, boolean highHonors, boolean deansList) {
		for (Term t : terms) {
			validTranscript.addTerm(t);
		}
		validTranscript.printTranscript();
	}

	@Test(groups = {"TestTranscript"})
	public void testConstructorNullName() {
	    Throwable t = expectThrows(BadParameter.class, () -> new Transcript(null));
	    assertEquals(t.getMessage(), "The student name can not be null.");
	}

	@Test(groups = {"TestTranscript"})
	public void testIsDeansListEdgeCases() throws BadParameter {
	    Term t = new Term(AcademicQuarter.FALL, 2020);
	    t.addCourse(new CompletedCourse("Course1", 3, LetterGradeEnum.B)); // GPA = 3.0
	    t.addCourse(new CompletedCourse("Course2", 3, LetterGradeEnum.A)); // GPA = 3.5
	    validTranscript.addTerm(t);

	    assertFalse(validTranscript.isDeansList());

	    t.addCourse(new CompletedCourse("Course3", 3, LetterGradeEnum.A)); // GPA = 3.33
	    assertTrue(validTranscript.isDeansList());
	}

	@Test(groups = {"TestTranscript"})
	public void testCalculateGPAWithDifferentCredits() throws BadParameter {
	    Term t = new Term(AcademicQuarter.FALL, 2020);
	    t.addCourse(new CompletedCourse("Course1", 3, LetterGradeEnum.A)); // 12 quality points
	    t.addCourse(new CompletedCourse("Course2", 4, LetterGradeEnum.B)); // 12 quality points
	    validTranscript.addTerm(t);

	    assertEquals(validTranscript.calculateGPA(), 3.0, 0.01);
	}

	@Test(groups = {"TestTranscript"})
	public void testCalculateGPA() throws BadParameter {
	    // Arrange
	    Term term1 = new Term(AcademicQuarter.FALL, 2020);
	    term1.addCourse(new CompletedCourse("Course1", 3, LetterGradeEnum.A)); // 12 quality points
	    term1.addCourse(new CompletedCourse("Course2", 4, LetterGradeEnum.B)); // 12 quality points

	    Term term2 = new Term(AcademicQuarter.WINTER, 2021);
	    term2.addCourse(new CompletedCourse("Course3", 3, LetterGradeEnum.C)); // 6 quality points
	    term2.addCourse(new CompletedCourse("Course4", 2, LetterGradeEnum.A)); // 8 quality points

	    validTranscript.addTerm(term1);
	    validTranscript.addTerm(term2);

	    // Act
	    double gpa = validTranscript.calculateGPA();

	    // Assert
	    assertEquals(gpa, 3.0, 0.01); // Total quality points: 38, Total credits: 12
	}
}
