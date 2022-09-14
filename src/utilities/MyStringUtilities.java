package utilities;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyStringUtilities {

	public static String replaceFromTo(int min, int max, Matcher m, String stringReplacement) {
	    StringBuffer sb = new StringBuffer();
	    int i = 0;
	    while (m.find()) {
	    	if (i >= min && i <= max) {
		    	m.appendReplacement(
	                sb,
	                stringReplacement
		        );

	    	} else if (i >= max)
	    		break;
	    	i++;
	    }
	    m.appendTail(sb);
	    return sb.toString();
	}

	public static String replaceAllList(Matcher m, ArrayList<String> imgSrcList) {
	    StringBuffer sb = new StringBuffer();
	    for (String element : imgSrcList) {
	        if (m.find()) {
	            m.appendReplacement(
	                sb,
	                element
	            );
	        }
	    }
	    m.appendTail(sb);
	    return sb.toString();

	}
	
	public static String prettifyNumber(String num) {
		if (!Pattern.matches("(\\d+,\\d{1,2})", num)) { //test if it's already prettified
			String newNum = num.replace('.', ',');  // 10.0 -> 10,0
			
			String[] decimalSplit = newNum.split(",");
			
			String[] digitsDec = decimalSplit[0].split("");
			int i, j;
			j = 0;
			for(i = digitsDec.length-1; i >= 0; i--) {   //10000 -> 10.000
				if (j % 3 == 0 && i != digitsDec.length-1)
					digitsDec[i] += ".";
				j++;
			}
			
			decimalSplit[0] = String.join("", digitsDec);
			
			if (decimalSplit.length == 1) {  //10 -> 10,00
				newNum += ",00";
			} else if (decimalSplit[1].length() == 1) {  //10,1 -> 10,10
				decimalSplit[1] += "0";
				newNum = String.join(",", decimalSplit);
			} else {
			    newNum = String.join(",", decimalSplit);
			}
					
			return newNum;
		} else 
			return num;
	}
	
	public static String deprettifyNumber(String num) {
		
		if (!Pattern.matches("[+-]?([0-9]*[.])?[0-9]+", num)) { //test if it's already deprettified
			String[] decimalSplit = num.split(",");
			decimalSplit[0] = decimalSplit[0].replaceAll("\\.", "");
			return String.join(".", decimalSplit);
		} else
			return num;
	}
}
