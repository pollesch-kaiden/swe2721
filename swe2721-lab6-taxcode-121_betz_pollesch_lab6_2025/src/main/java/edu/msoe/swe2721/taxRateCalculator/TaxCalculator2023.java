/*
 * Course: SWE2410-121
 * Spring 2024-2025
 * File header contains class TaxCalculator2023
 * Name: betzm
 * Created 3/10/2025
 */
package
        edu.msoe.swe2721.taxRateCalculator;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Course SWE2410-121
 * Spring 2024-2025
 * Class TaxCalculator2023 Purpose: calculate the taxes
 *
 * @author betzm
 * @version created on 3/10/2025 10:30 AM
 */
public class TaxCalculator2023 implements TaxCalculatorInterface {
    private double adjustedGrossIncome;
    private final int age;
    private final FilingStatus filingStatus;
    private final String name;
    private int spouseAge;
    private String spouseName;

    public TaxCalculator2023(String name, FilingStatus filingStatus, int age) throws TaxFilingException {
        validateName(name);
        validateAge(age);
        validateFilingStatus(filingStatus);
        if (filingStatus == FilingStatus.MARRIED_FILING_SEPARATELY || filingStatus == FilingStatus.MARRIED_FILING_JOINTLY) {
            throw new TaxFilingException("Spouse information required for married filing status");
        }
        this.name = name;
        this.filingStatus = filingStatus;
        this.age = age;
    }

    public TaxCalculator2023(String name, String spouseName, FilingStatus filingStatus, int age, int spouseAge) throws TaxFilingException {
        validateName(name);
        validateName(spouseName);
        validateAge(age);
        validateAge(spouseAge);
        validateFilingStatus(filingStatus);
        if (filingStatus != FilingStatus.MARRIED_FILING_SEPARATELY && filingStatus != FilingStatus.MARRIED_FILING_JOINTLY) {
            throw new TaxFilingException("Spouse information should not be provided for non-married filing status");
        }

        this.name = name;
        this.spouseName = spouseName;
        this.filingStatus = filingStatus;
        this.age = age;
        this.spouseAge = spouseAge;
    }

    private void validateName(String name) throws TaxFilingException {
        if (name == null || name.trim().isEmpty() || name.contains("_")) {
            throw new TaxFilingException("Invalid name");
        }
        String[] parts = name.split(" ");
        for (String part : parts) {
            if (part.length() < 2) {
                throw new TaxFilingException("Invalid name");
            }
        }
    }

    private void validateAge(int age) throws TaxFilingException {
        if (age <= 0) {
            throw new TaxFilingException("Age cannot be negative");
        }
    }

    private void validateFilingStatus(FilingStatus filingStatus) throws TaxFilingException {
        if (filingStatus == null) {
            throw new TaxFilingException("Filing status cannot be null");
        }
    }

    @Override
    public FilingStatus getFilingStatus() {
        return filingStatus;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSpouseName() {
        return spouseName;
    }

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public int getSpouseAge() {
        return spouseAge;
    }

    @Override
    public void setAdjustedGrossIncome(double adjustedGrossIncome) throws TaxFilingException {
        if (adjustedGrossIncome < 0) {
            throw new TaxFilingException("Adjusted Gross Income cannot be negative");
        }
        this.adjustedGrossIncome = adjustedGrossIncome;
    }

    @Override
    public double getAdjustedGrossIncome() {
        return adjustedGrossIncome;
    }

    @Override
    public boolean determineFilingNeed() {
        switch (filingStatus) {
            case SINGLE:
                return (age < 65 && adjustedGrossIncome >= 13850) || (age >= 65 && adjustedGrossIncome >= 15700);
            case HEAD_OF_HOUSEHOLD:
                return (age < 65 && adjustedGrossIncome >= 20800) || (age >= 65 && adjustedGrossIncome >= 22650);
            case MARRIED_FILING_JOINTLY:
                if (age < 65 && spouseAge < 65) {
                    return adjustedGrossIncome >= 27700;
                } else if (age > 65 && spouseAge > 65) {
                    return adjustedGrossIncome >= 30700;
                } else if (age >= 65 || spouseAge >= 65) {
                    return adjustedGrossIncome >= 29200;
                }
            case MARRIED_FILING_SEPARATELY:
                return adjustedGrossIncome >= 5;
            default:
                return false;
        }
    }

    @Override
    public double obtainStandardDeduction() {
        switch (filingStatus) {
            case SINGLE:
                return age < 65 ? 13850 : 15700;
            case HEAD_OF_HOUSEHOLD:
                return age < 65 ? 20800 : 22650;
            case MARRIED_FILING_JOINTLY:
                if (age < 65 && spouseAge < 65) {
                    return 27700;
                } else if (age >= 65 && spouseAge >= 65) {
                    return 30700;
                } else {
                    return 29200;
                }
            case MARRIED_FILING_SEPARATELY:
                return age < 65 ? 13850 : 15350;
            default:
                return 0;
        }
    }

    @Override
    public double obtainTaxableIncome() {
        double standardDeduction = obtainStandardDeduction();
        double taxableIncome = adjustedGrossIncome - standardDeduction;
        return Math.max(taxableIncome, 0);
    }

    @Override
    public double getTaxDue() {
        double taxableIncome = obtainTaxableIncome();
        double taxDue = 0;

        if (filingStatus == FilingStatus.SINGLE || filingStatus == FilingStatus.MARRIED_FILING_SEPARATELY) {
            taxDue = calculateTax(taxableIncome, new double[]{11000, 44725, 95375, 182100, 231250, 578125}, new double[]{0.10, 0.12, 0.22, 0.24, 0.32, 0.35, 0.37});
        } else if (filingStatus == FilingStatus.MARRIED_FILING_JOINTLY) {
            taxDue = calculateTax(taxableIncome, new double[]{22000, 89450, 190750, 364200, 462500, 693750}, new double[]{0.10, 0.12, 0.22, 0.24, 0.32, 0.35, 0.37});
        } else if (filingStatus == FilingStatus.HEAD_OF_HOUSEHOLD) {
            taxDue = calculateTax(taxableIncome, new double[]{15700, 59850, 95350, 182100, 231250, 578100}, new double[]{0.10, 0.12, 0.22, 0.24, 0.32, 0.35, 0.37});
        }

        return taxDue;
    }

    private double calculateTax(double income, double[] brackets, double[] rates) {
        double tax = 0.0;
        for (int i = brackets.length - 1; i >= 0; i--) {
            if (income > brackets[i]) {
                tax += (income - brackets[i]) * rates[i + 1];
                income = brackets[i];
            }
        }
        tax += income * rates[0]; // Apply the lowest rate to the remaining income
        // Round the tax to two decimal places
        BigDecimal bd = new BigDecimal(tax).setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    @Override
    public double getNetTaxRate() {
        if (adjustedGrossIncome == 0) {
            return 0;
        }
        double netTaxRate = (getTaxDue() / adjustedGrossIncome) * 100;
        // Round the net tax rate to one decimal place
        BigDecimal bd = new BigDecimal(netTaxRate).setScale(1, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}