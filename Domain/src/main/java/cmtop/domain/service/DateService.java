package cmtop.domain.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateService {

	public static long converterDataEmTimestamp(int dia, int mes, int ano) {
		GregorianCalendar gregorianCalendar = new GregorianCalendar();
		gregorianCalendar.set(ano, mes, dia);

		return gregorianCalendar.getTimeInMillis();
	}

	public static long converterDataEmTimestamp(Date date) {
		GregorianCalendar gregorianCalendar = new GregorianCalendar();
		gregorianCalendar.setTime(date);

		return gregorianCalendar.getTimeInMillis();
	}

	public static String converterDataEmString(Date date) {
		return converterTimestampParaDataString(converterDataEmTimestamp(date));
	}

	public static Date converterTimestampEmData(long timestamp) {
		GregorianCalendar gregorianCalendar = new GregorianCalendar();
		gregorianCalendar.setTimeInMillis(timestamp);

		return gregorianCalendar.getTime();
	}

	public static long converterDataStringParaLong(String string) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		Date data = format.parse(string);
		return DateService.converterDataEmTimestamp(data);
	}

	public static String converterTimestampParaDataString(long timestamp) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		Date date = converterTimestampEmData(timestamp);
		return format.format(date);
	}

}
