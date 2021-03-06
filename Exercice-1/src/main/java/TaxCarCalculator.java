import java.math.BigDecimal;
import java.util.*;

public class TaxCarCalculator implements TaxCalculator<Car> {

	protected static final Date CO2_TAX_DATE;

    protected static List<TaxByMaxValue> taxByCO2 = new ArrayList<TaxByMaxValue>();

	static {
		Calendar cal = new GregorianCalendar();

		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.MONTH, Calendar.MARCH);
		cal.set(Calendar.YEAR, 2001);

		CO2_TAX_DATE = cal.getTime();

        taxByCO2.add(new TaxByMaxValue(100, "65"));
        taxByCO2.add(new TaxByMaxValue(120, "75"));
        taxByCO2.add(new TaxByMaxValue(150, "105"));
        taxByCO2.add(new TaxByMaxValue(165, "125"));
        taxByCO2.add(new TaxByMaxValue(185, "145"));
        taxByCO2.add(new TaxByMaxValue(Integer.MAX_VALUE, "160"));
	}


	public BigDecimal calculate(Car car) {
		if (isRegistrationDateAfterFirstMarch2001(car)) {
            return calculateTaxByCO2(car);
		}
		if (car.getEngineSize() <= 1550) {
			return BigDecimal.valueOf(110L);
		}
		return BigDecimal.valueOf(165L);
	}

    private BigDecimal calculateTaxByCO2(Car car) {

        for(TaxByMaxValue taxByMaxValue : taxByCO2) {
            if (car.getCO2() <= taxByMaxValue.getMaxValue())
                return taxByMaxValue.getTax();
        }

        throw new IllegalStateException("Should never happen " + car.getCO2());
    }

    protected boolean isRegistrationDateAfterFirstMarch2001(Car car) {
        return car.isRegisteredAfter(CO2_TAX_DATE);
    }
}
