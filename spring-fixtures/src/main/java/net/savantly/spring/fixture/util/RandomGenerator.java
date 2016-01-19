package net.savantly.spring.fixture.util;

import java.math.BigDecimal;
import java.util.Random;

import org.joda.time.DateTime;

public class RandomGenerator {
	
	static final Random random = new Random();
	static final String alphaString = "abcdefghijklmnopqrstuvwxyz";
	static final String digitString = "0123456789";
	static final String alphaNumerics = String.format("%s%s", alphaString, digitString);

	// *******************
	// Strings 
	//
	public static String getRandomAlphaNumericString(int length){
		return getRandomString(alphaNumerics, length);
	}
	
	public static String getRandomAlphaString(int length){
		return getRandomString(alphaString, length);
	}
	
	public static String getRandomAlphaWordString(int wordCount, int maxWordLength){
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < wordCount; i++) {
			buffer.append(getRandomAlphaString(maxWordLength));
			buffer.append(' ');
		}
		return buffer.toString();
	}
	
	public static String getRandomNumericString(int length){
		return getRandomString(digitString, length);
	}

	public static String getRandomString(String characters, int length) {
		char[] text = new char[length];
	    for (int i = 0; i < length; i++)
	    {
	        text[i] = characters.charAt(random.nextInt(characters.length()));
	    }
	    return new String(text);
	}
	
	public static String getRandomString(String... stringsToPickFrom){
		return stringsToPickFrom[random.nextInt(stringsToPickFrom.length)];
	}
	
	public static String getRandomAddress(){
		String roadType = getRandomString("Rd.", "Ln.", "Blvd.", "St.");
		String numericPart = getRandomNumericString(4);
		String roadName = getRandomAlphaString(8);
		return String.format("%s %s %s", numericPart, roadName, roadType);
	}
	
	// *******************
	// DateTimes 
	//
	public static DateTime getRandomDateTime(boolean futureDateTime){
		DateTime dateTime = new DateTime();
		int operand = random.nextInt(20000);
		if (futureDateTime) {
			operand *= -1;
		}
		dateTime.minusDays(operand);
		return dateTime;
	}
	
	// *******************
	// Decimals 
	//
	public static BigDecimal getRandomMoneyValue(int maxAmount, int decimalPlaces, boolean negative){
		int dollars = random.nextInt(maxAmount);
		String cents = getRandomNumericString(decimalPlaces);
		String total = String.format("%s.%s", dollars, cents);
		BigDecimal returnValue = new BigDecimal(total);
		return returnValue;
	}
	
}
