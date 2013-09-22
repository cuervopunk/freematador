package com.freematador.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	public DateUtils() {
			
	}

	public static Date getFirstDayMonth() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of day !
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);

		// get start of the month
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}
	
	public static Date getLastDayMonth() {
        Date today = new Date();  

        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(today);  

        calendar.add(Calendar.MONTH, 1);  
        calendar.set(Calendar.DAY_OF_MONTH, 1);  
        calendar.add(Calendar.DATE, -1);  
		calendar.clear(Calendar.HOUR_OF_DAY);
		calendar.clear(Calendar.MINUTE);
		calendar.clear(Calendar.SECOND);
		calendar.clear(Calendar.MILLISECOND);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		
        Date lastDayOfMonth = calendar.getTime();  

        return lastDayOfMonth;
    }  
	
	public static Date getToday() {
		Date today = new Date(System.currentTimeMillis());
		return today;
	}
	
	public static void main(String[] argv) {
		System.out.println("First: "+ getFirstDayMonth());
		System.out.println("Last: "+ getLastDayMonth());
	}

	
}
