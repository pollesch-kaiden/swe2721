package edu.msoe.swe2721.taxRateCalculator;

/**
 * This interface defines a class that can be used to calculate ones Federal Income Taxes based upon filing status and Adjusted Gross Income.
 */
public interface TaxCalculatorInterface {
	/**
	 * Obtain the filing status for the tax return.
	 * 
	 * @return The filing status value will be returned.
	 */
	public abstract FilingStatus getFilingStatus();

	/**
	 * This method will obtain the name of the taxpayer.
	 * @return The name of the taxpayer is returned.
	 */
	public abstract String getName();

	/**
	 * This method will obtain the name of the spouse.
	 * @return The name of the spouse will be returned.  If Single or head of household, null will be returned.
	 */
	public abstract String getSpouseName();


	/**
	 * Obtain the age of the filer. Age must be greater than 0.
	 * 
	 * @return The age of the filer.
	 */
	public abstract int getAge();

	/**
	 * Obtain the age of the spouse. If there is no spouse based on the filing
	 * type, a value of 0 will be returned.
	 * 
	 * @return The age of the spouse.
	 */
	public abstract int getSpouseAge();

	
	/**
	 * This method will set the gross income. The gross income must be greater
	 * than or equal to 0. Otherwise, the change will not occur.
	 * 
	 * @param adjustedGrossIncome
	 *            This is the gross income for the taxpayer.
	 * @throws TaxFilingException An exception will be thrown if the gross income is negative.
	 */
	public abstract void setAdjustedGrossIncome(double adjustedGrossIncome) throws TaxFilingException;
	
	/**
	 * Obtain the gross income.
	 * 
	 * @return The gross income will be returned.
	 */
	public abstract double getAdjustedGrossIncome();
	
	/**
	 * This routine will determine if a tax return is required. A tax return is
	 * required based on income and filing status.
	 * 
	 * @return true will be returned if a tax return is required. False will be
	 *         returned otherwise.
	 */
	public abstract boolean determineFilingNeed();
	
	/**
	 * Obtain the standard deduction.  The standard deduction is based upon filing status and age.
	 * 
	 * @return This method will return the standard deduction.
	 */
	public abstract double obtainStandardDeduction();

	/**
	 * Obtain the taxable income. Taxable income is the adjusted gross income (AGI) minus the
	 * standard deduction.  Taxable income must never be less than 0.
	 * 
	 * @return The taxable income will be returned.
	 */
	public abstract double obtainTaxableIncome();

	/**
	 * Calculate the tax due. Calculation will be based upon IRS tax tables, the filing status, and the standard deduction.
	 * 
	 * @return The tax due will be returned.
	 */
	public abstract double getTaxDue();

	/**
	 * Calculate the net tax rate.  The net tax rate is defined as the tax due divided by the gross income.
	 * @return The net tax rate will be returned.  The value is returned as a percent.  A value of 1.00 represents 1%.  A value of 9.5 represents 9.5%.  A value of 0.5 represents 0.5%.
	 */
	public abstract double getNetTaxRate();
}