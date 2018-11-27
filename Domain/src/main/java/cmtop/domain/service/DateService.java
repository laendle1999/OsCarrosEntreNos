package cmtop.domain.service;

import java.util.Date;
import java.util.GregorianCalendar;

public class DateService {

	public static long converterDataEmTimestamp(int dia, int mes, int ano) {
		GregorianCalendar gregorianCalendar = new GregorianCalendar();
		gregorianCalendar.set(ano, mes, dia);

		return gregorianCalendar.getTimeInMillis();
	}

	public static Date converterTimestampEmData(long timestamp) {
		GregorianCalendar gregorianCalendar = new GregorianCalendar();
		gregorianCalendar.setTimeInMillis(timestamp);

		return gregorianCalendar.getTime();
	}

}
