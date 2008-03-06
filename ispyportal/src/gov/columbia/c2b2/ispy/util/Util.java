package gov.columbia.c2b2.ispy.util;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.*;
import java.util.*; 
/**
 * General web programming utilities, especially for front-end development.
 */
public class Util {

    static Random rand = new Random();

    /**
     * Deletes a directory and all sub-directories.
     *
     * @param dir dir to remove
     */
    public static void deleteDirectory(File dir) {
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory())
                    deleteDirectory(files[i]);
                else
                    files[i].delete();
                    
            }
        }
        dir.delete();
    }
    
    /**
     * 
     * This method will return a list of file object based on the input directory.
     *  
     */
    public static File[] getAllFiles(File dir)
    {
      
       if (dir.isDirectory()) {
           File[] files = dir.listFiles();
           return files;
       }
       return null;
       
       
    }
    
    
    /**
     * 
     * This method will sort an array of files by modification date descending 
     *  
     */
    public static File[] sortFilesByDateDesc(File[] files)
    {      
    	Arrays.sort( files, new FileDesnComparator()); 	 
         return files;
    }

    /**
     * 
     * This method will sort an array of files by modification date Ascending
     *  
     */
    public static File[] sortFilesByDateAsc(File[] files)
    {
      
    	Arrays.sort( files, new FileAscComparator());
    	 
  
       return files;
    }

    /**
     * 
     * File Descending comparator
     *  
     */
    private static class FileDesnComparator implements Comparator
    {
    	public int compare(Object o1,
                     Object o2)
        {
        	if (((File)o1).lastModified() > ((File)o2).lastModified()) {
				return -1;
			} else if (((File)o1).lastModified() < ((File)o2).lastModified()) {
				return +1;
			} else {
				return 0;
			}
         }
      }

    
    /**
     * 
     * File Ascending comparator
     *  
     */
    private static class FileAscComparator implements Comparator
    {
        public int compare(Object o1,
                     Object o2)
        {
        	if (((File)o1).lastModified() < ((File)o2).lastModified()) {
				return -1;
			} else if (((File)o1).lastModified() > ((File)o2).lastModified()) {
				return +1;
			} else {
				return 0;
			}
         }
      }

    
    /**
     * Reads a file in to a String.
     *
     * @param file the file to read
     * @return String string representation of the file
     */
    public static String getFileAsString(File file) throws FileNotFoundException, IOException {
        return new String(getFileAsByteArray(file));
    }

    /**
     * read entire file
     *
     * @param file file to read
     * @return byte[] array of bytes
     */
    public static byte[] getFileAsByteArray(File file) throws FileNotFoundException, IOException {
        InputStream in = null;
        byte[] b = new byte[(int) file.length()];
        try {
            in = new FileInputStream(file);
            in.read(b);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }
        return b;
    }

    /**
     * String replace.
     *
     * @param str        String to operate on.
     * @param findString search string to replace
     * @param withString string with which to replace search string
     * @return String resulting string
     */
    public static String replace(String str, String findString, String withString) {
        return replace(str, findString, withString, 0);
    }

    /**
     * String replace.
     *
     * @param str        string to operate on.
     * @param findString search string to replace
     * @param withString string with which to replace search string
     * @param startPos   starting character position of replace
     * @return String resulting string
     */
    public static String replace(String str, String findString, String withString, int startPos) {
        String prefix = "";
        String string = "";

        if (startPos > 0 && startPos < str.length()) {
            prefix = str.substring(0, startPos);
            string = str.substring(startPos, str.length());
        } else {
            string = new String(str);
        }

        int pos = string.indexOf(findString);
        while (pos != -1) {
            string = (new StringBuffer(string)).replace(pos, pos + findString.length(), withString).toString();
            pos = string.indexOf(findString, pos + withString.length());
        }
        return prefix + string;
    }

    /**
     * Run a process.
     *
     * @param command command to execute
     * @param command command parameters
     * @return Program output as String object
     */
    public static String execute(String command, String arguments) {
        try {
            Runtime runtime = Runtime.getRuntime();
            String execCommand = command + " " + arguments;
            Process proc = runtime.exec(execCommand);
            InputStream stdin = proc.getInputStream();
            InputStreamReader isr = new InputStreamReader(stdin);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            String lines = "";

            while ((line = br.readLine()) != null) {
                lines += "\n" + line;
            }

            proc.waitFor();
            return lines;

        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }

    /**
     * Converts an Object to a String, null becomes "".
     *
     * @param a Object to convert
     * @return not-null string
     */
    public static String toNonNullString(Object a) {
        if (a == null) return "";
        String s = a.toString();
        return s.trim();
    }

    /**
     * Converts an String to a non-null String (null becomes "").
     *
     * @param s string to convert
     * @return non-null String
     */
    public static String toNonNullString(String s) {
        if (s == null) return "";
        return s.trim();
    }

    /**
     * Converts a String to a long, null or unparseable becomes 0.
     *
     * @param s string to convert
     * @return long value
     */
    public static long parseLong(String s) {
        if (s == null) return 0;
        long l = 0;
        try {
            l = Long.parseLong(s, 10);
            return l;
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * Converts a String to an int, null or unparseable becomes 0.
     *
     * @param s string to convert
     * @return int value
     */
    public static int parseInt(String s) {
        if (s == null) return 0;
        int i = 0;
        try {
            i = Integer.parseInt(s, 10);
            return i;
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * Converts <i>empty</i> (null or "") String to "&amp;nbsp;".
     * String of non-zero length are unaffected.
     *
     * @param s string to convert
     * @return not-null string
     */
    public static String toNbspString(String s) {
        if (Util.isEmpty(s)) return "&nbsp;";
        return s.trim();
    }

    /**
     * Converts first positive integer number in string to int ignoring trailing spaces
     *
     * @param s
     * @return first positive integer number in string
     */
    public static int parseFirstInt(String s) {
        if (s == null) return 0;
        s = s.trim();
        int len = s.length();
        int i = 0;
        while ((i < len) && Character.isDigit(s.charAt(i))) {
            i++;
        }
        if (i != len) s = s.substring(0, i);
        return Util.parseInt(s);
    }

    /**
     * Converts a String to an Integer object.
     *
     * @param s string to convert
     * @return Integer value or null if unparseable
     */
    public static Integer parseInteger(String s) {
        if (s == null) return null;
        Integer i = null;
        try {
            i = Integer.valueOf(s);
        } catch (NumberFormatException e) {
        }
        return i;
    }

    /**
     * Converts a String to a double, returning 0.0 if unparseable.
     *
     * @param s String to convert
     * @return double value
     */
    public static double parseDouble(String s) {
        if (s == null) return 0;
        Double d = null;
        try {
            d = Double.valueOf(s);
            return d.doubleValue();
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    /**
     * Converts a String to a boolean
     *
     * @param s String to convert
     * @return boolean value or <code>false</code> if s null
     */
    public static boolean stringToBoolean(String s) {
        if (s == null) return false;
        Boolean b = Boolean.valueOf(s);
        return b.booleanValue();
    }

    /**
     * @return <code>true</code> if string is empty or is null.
     */
    public static boolean isEmpty(String s) {
        if (s == null) return true;
        return (s.length() == 0);
    } //isEmpty()

    /**
     * Checks whether the Integer is null or zero.
     *
     * @param i Integer object
     * @return true if array is null or zero
     */
    public static boolean isEmptyOrZero(Integer i) {
        if (i == null) return true;
        return (i.intValue() == 0);
    }

    /**
     * @param c Collection
     * @return <code>true</code> if Collection (List, Map, Set, etc.) is empty or is null.
     */
    public static boolean isEmpty(Collection c) {
        return (c == null) || (c.size() == 0);
    }

    /**
     * Checks the Date for emptyness (null).
     *
     * @param value
     * @return <code>true</code> if null.
     */
    public static boolean isEmpty(Date value) {
        if (value == null)
            return true;
        else
            return false;
    }

    /**
     * @return <code>true</code> if the String is null, empty or consists of only whitespace.
     */
    public static boolean isEmptyOrWhiteSpace(String s) {
        if (isEmpty(s)) return true;
        String ss = s.trim();
        return isEmpty(ss);
    }

    /**
     * Checks a char to see if it is valid for HTML or XML output.
     *
     * @return <code>true for all alpha-numeric chars including extended set (128-255) and special tab,
     *         newline and carriage return characters, <false> for other chars with ASCII code less than 32.
     */
    public static boolean isValidXMLChar(char c) {
        if (c >= 32) return true;
        if ((c == '\n') || (c == '\r') || (c == '\t')) return true;
        return false;
    }

    /**
     * Encodes a char to XML-valid form replacing &,',",<,> with special XML encoding.
     *
     * @param ch char to convert
     * @return XML-encoded text
     */
    public static String encodeXMLChar(char ch) {
        String result = "";
        if (!isValidXMLChar(ch)) return "";
        if (ch == '&')
            result = "&amp;";
        else if (ch == '\"')
            result = "&quot;";
        else if (ch == '\'')
            result = "&#39;";
        else if (ch == '<')
            result = "&lt;";
        else if (ch == '>')
            result = "&gt;";
        else
            result += ch;
        return result;
    }

    /**
     * Encodes a char to XML-valid, replacing all whitespace with nbsp.
     *
     * @param ch char to convert
     * @return XML-encoded text
     */
    public static String encodeNbspXMLChar(char ch) {
        if (ch == ' ') {
            return "&nbsp;";
        } else {
            return encodeXMLChar(ch);
        }
    }

    /**
     * Encodes a char to XML-valid, replacing all newlines with BR tags.
     *
     * @param ch char to convert
     * @return XML-encoded text
     */
    public static String encodeBRXMLChar(char ch) {
        if (ch == '\n') {
            return "<BR/>";
        } else {
            return encodeXMLChar(ch);
        }
    }

    public static char INVALID_CHAR_REPLACE = '?';

    /**
     * Ensures that the returned value is a legal UTF character.
     */
    public static char validateUtf8Char(char ch) {
        if (ch < 128)
            return ch;
        else if ((ch & 0xf0) != 224)
            return INVALID_CHAR_REPLACE;
        else
            return ch;
    }

    /**
     * Ensures that the returned value contains only legal UTF characters.
     * Illegal characters are replaced by '?'.
     */
    public static String validateUtf8String(String s) {
        if (isEmpty(s)) return "";
        char[] chars = new char[s.length()];
        for (int i = 0; i < s.length(); i++) {
            chars[i] = validateUtf8Char(s.charAt(i));
        }
        return new String(chars);
    }

    /**
     * encodes text to HTML-valid form replacing &,',",<,> with special HTML encoding
     *
     * @param s string to convert
     * @return HTML-encoded text
     */
    public static String encodeXML(String s) {
        if (isEmpty(s)) return toNbspString(s);
        int len = s.length();
        StringBuffer result = new StringBuffer(len);
        for (int i = 0; i < len; i++) {
            char ch = s.charAt(i);
            result.append(encodeXMLChar(ch));
        }
        String sResult = result.toString();

        return validateUtf8String(sResult);
    }

    public static String encodeJS(String s) {
        if (isEmpty(s)) return "";
        int len = s.length();
        StringBuffer result = new StringBuffer(len);
        for (int i = 0; i < len; i++) {
            char ch = s.charAt(i);
            if (ch == '\'' || ch == '"') {
                result.append('\\');
            }
            result.append(ch);
        }
        return result.toString();
    }


    /**
     * Encodes a long in to valid XML.
     */
    public static String encodeXML(long l) {
        return encodeXML("" + l);
    }

    /**
     * Encodes an int in to valid XML.
     */
    public static String encodeXML(int i) {
        return encodeXML("" + i);
    }

    /**
     * Encodes a Date in to valid XML.
     */
    public static String encodeXML(Date d) {
        return encodeXML(formatDateTime(d));
    }

    /**
     * Encodes a string in to XML, converting all spaces to nbsp.
     */
    public static String encodeNbspXML(String s) {
        if (isEmpty(s)) return toNbspString(s);
        int len = s.length();
        StringBuffer result = new StringBuffer(len);
        for (int i = 0; i < len; i++) {
            char ch = s.charAt(i);
            result.append(encodeNbspXMLChar(ch));
        }
        String sResult = result.toString();

        return validateUtf8String(sResult);
    }

    /**
     * Encodes text to a JavaScript-valid form replacing \,',",<,>,\n characters
     *
     * @param s string to convert
     * @return JavaScript-encoded text
     */
    public static String encodeJavaScript(String s) {
        if (isEmpty(s)) return toNonNullString(s);
        int len = s.length();
        StringBuffer result = new StringBuffer(len);
        for (int i = 0; i < len; i++) {
            char ch = s.charAt(i);
            if (ch == '\\')
                result.append("\\\\");
            else if (ch == '\'')
                result.append("\\'");
            else if (ch == '\"')
                result.append("\\\"");
            else if (ch == '\n')
                result.append("\\n");
            else if (ch == '\r') /* Do nothing */
                ;
            else
                result.append(ch);
        }
        return result.toString();
    }


    /**
     * Take a string and make it safe to use as a javascript element name
     *
     * @param s
     * @return
     */
    public static String encodeSafeJavaScriptVarName(String s) {
        if (isEmpty(s)) return toNonNullString(s);
        int len = s.length();
        StringBuffer result = new StringBuffer(len);
        for (int i = 0; i < len; i++) {
            char ch = s.charAt(i);
            if (ch == ' ') {
                ;
            } else
                result.append(ch);

        }
        return result.toString();
    }

    /**
     * @param i integer to convert
     * @return the int itself or nbsp if i=0
     */
    public static String toNbspString(int i) {
        String s;
        if (i == 0)
            s = "&nbsp;";
        else
            s = "" + i;
        return s;
    }

    /**
     * @param l long value to convert
     * @return the long itself or nbsp if i=0
     */
    public static String toNbspString(long l) {
        String s;
        if (l == 0)
            s = "&nbsp;";
        else
            s = "" + String.valueOf(l);
        return s;
    }

    /**
     * Escapes a String so as to be a legal URL - converts spaces to +, converts
     * chars less than 32 to the %<hex code>, etc.
     */
    public static String encodeURL(String before) {
        String spaceCode = "+";
        StringBuffer after = new StringBuffer();
        int i = 0;
        while (i < before.length()) {
            if (before.charAt(i) == ' ') {
                // handle spaces escaped as + signs
                after.append(spaceCode);
            } else if (before.charAt(i) < '0' || before.charAt(i) > 'z' ||
                    before.charAt(i) > '9' && before.charAt(i) < 'A' ||
                    before.charAt(i) > 'Z' && before.charAt(i) < 'a') {
                // special chars
                after.append('%');
                int high = (int) before.charAt(i) / 16;
                after.append(Character.forDigit(high, 16));
                int low = (int) before.charAt(i) - high * 16;
                after.append(Character.forDigit(low, 16));
            } else
                after.append(before.charAt(i));
            i++;
        }
        return after.toString();
    }

    /**
     * Formats date in short form using default locale.
     *
     * @return String representation of given Date for default locale.
     */
    public static String formatDateShort(Date date) {
        if (date == null) return "";
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
        return df.format(date);
    }

    /**
     * Formats date in short form using default locale.
     *
     * @return String representation of given Date for default locale.
     */
    public static String formatDateTimeShort(Date date) {
        if (date == null) return "";
        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
        return df.format(date);
    }

    /**
     * Formats date in short form using default locale.
     *
     * @return String representation of given Date for default locale.
     */
    public static String formatDateShortLocale(Date date, Locale locale) {
        if (date == null) return "";
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, locale);
        return df.format(date);
    }

    /**
     * Formats date in short form using given locale.
     *
     * @return String representation of given Date for given locale.
     */
    public static String formatDateLong(Date date) {
        if (date == null) return "";
        DateFormat df = DateFormat.getDateInstance(DateFormat.LONG);
        return df.format(date);
    }

    /**
     * Formats date in long form using given locale.
     *
     * @return String representation of given Date for given locale.
     */
    public static String formatDateLongLocale(Date date, Locale locale) {
        if (date == null) return "";
        DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, locale);
        return df.format(date);
    }

    public static String formatWbrString(String string, int maxWordLength) {
        if (string == null) {
            return "";
        }
        StringBuffer result = new StringBuffer();
        StringTokenizer st = new StringTokenizer(string, " \t\n\r", true);
        while (st.hasMoreTokens()) {
            String word = st.nextToken();
            for (int i = 0; i < word.length(); i++) {
                result.append(word.charAt(i));
                if (i > 0 && i % maxWordLength == 0) {
                    result.append("<wbr>");
                }
            }
        }

        return result.toString();
    }

    /**
     * Convert to XML, converting all newlines to BR tags.
     */
    public static String encodeBRXML(String s) {
        if (isEmpty(s)) return toNbspString(s);
        int len = s.length();
        StringBuffer result = new StringBuffer(len);
        for (int i = 0; i < len; i++) {
            char ch = s.charAt(i);
            result.append(encodeBRXMLChar(ch));
        }
        String sResult = result.toString();

        return validateUtf8String(sResult);
    }

    /**
     * Produces an MD5 hash from a String
     *
     * @param value the input to be updated before the digest is completed
     */
    public static String getStringMD5fromString(String value) {
        MessageDigest algorithm = null;
        try {
            algorithm = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        byte digest[] = algorithm.digest(value.getBytes());

        StringBuffer MD5 = new StringBuffer();

        String str_unit;
        for (int i = 0; i < digest.length; i++) {
            str_unit = Integer.toHexString(0xFF & digest[i]);
            if (str_unit.length() <= 1) str_unit = "0" + str_unit;
            MD5.append(str_unit);
        }
        return MD5.toString();

    }

    /**
     * Completes the hash computation and produce MD5 Hex string from input String
     *
     * @param value the input to be updated before the digest is completed
     */
    public static String getHexStringMD5fromString(String value) {
        MessageDigest algorithm = null;
        try {
            algorithm = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        byte digest[] = algorithm.digest(value.getBytes());

        StringBuffer hexString = new StringBuffer();

        for (int i = 0; i < digest.length; i++) {
            hexString.append(Integer.toHexString(0xFF & digest[i]));
            hexString.append(" ");
        }
        return hexString.toString();

    }

    /**
     * Converts Integer to int returning 0 if Integer is null.
     *
     * @return intValue() or 0 for null object.
     */
    public static int toInt(Integer i) {
        return (i == null) ? 0 : i.intValue();
    }

    /**
     * Converts Boolean object to boolean primative, returning false if object is null.
     *
     * @return booleanValue() or false for null object.
     */
    public static boolean toBoolean(Boolean b) {
        return (b == null) ? false : b.booleanValue();
    }

    /**
     * @return true if the given objects are different (including nulls).
     */
    public static boolean areDifferent(Object o1, Object o2) {
        if (o1 == null && o2 == null) return false;
        if (o1 != null && !o1.equals(o2)) return true;
        if (o2 != null && !o2.equals(o1)) return true;
        return false;
    }

    /**
     * Gets the user's locale, defaulting to Locale.US if not available.
     *
     * @return current user Locale
     */
    public static Locale getLocale(HttpServletRequest request) {
        Locale result = null;
        try {
            result = request.getLocale();
        } catch (NullPointerException e) {/* Use default locale */
        }
        if (result == null) result = Locale.US;
        return result;
    }

    /**
     * Joins strings together ignoring nulls
     *
     * @param s1        prefix
     * @param s2        suffix
     * @param separator should not be null!
     * @return (s1 + separator + s2)
     */
    public static String joinStrings(String s1, String s2, String separator) {
        if (isEmpty(s1)) return toNonNullString(s2);
        if (isEmpty(s2)) return toNonNullString(s1);
        return s1 + separator + s2;
    }

    /**
     * Joins strings together ignoring nulls, with no separator
     *
     * @param s1 prefix
     * @param s2 suffix
     * @return (s1 + separator + s2)
     */
    public static String joinStrings(String s1, String s2) {
        return joinStrings(s1, s2, "");
    }

    /**
     * Removes time part from the given date
     *
     * @param date date to truncate
     * @return truncated date with 00:00:00 time (or null)
     */
    public static Date toCalendarDate(Date date) {
        if (date == null) return null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.clear(Calendar.HOUR);
        cal.clear(Calendar.HOUR_OF_DAY);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
        return cal.getTime();
    }

    /**
     * @return String representation of Boolean value.
     */
    public static String toYesNo(Boolean value) {
        return (value == null || Boolean.FALSE.equals(value)) ? "No" : "Yes";
    }

    /**
     * @return String representation of boolean value.
     */
    public static String toYesNo(boolean value) {
        return value ? "Yes" : "No";
    }

    /**
     * Returns Date object for current day with time info set to 0 (ie. midnight).
     *
     * @return java.util.Date with current year,month,day
     */
    public static Date getToday() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * Returns Date object for current time.
     *
     * @return java.util.Date with current year,month,day
     */
    public static Date getNow() {
        Calendar cal = Calendar.getInstance();
        return cal.getTime();
    }

    public static int getCurrentYear() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.YEAR);
    }

    /**
     * @return String representation of current Date and Time for current locale
     */
    public static String formatDateTime(Date date) {
        if (date == null) return "";
        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, Locale.US);
        //df.setTimeZone(getTimeZone());
        return df.format(date);
    }

    /**
     * Formats a number in the US locale (ie. 1,000,000).
     *
     * @param number
     * @return
     */
    public static String formatNumber(int number) {
        NumberFormat nf = NumberFormat.getInstance(Locale.US);
        return nf.format(number);
    }

    /**
     * Converts a string to a date, where the string must be in the following format:<p>
     * <code>
     * MM/dd/yyyy/hh:mm
     * </code>
     */
    public static Date stringToDate(String dateString) {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy/hh:mm");
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            System.out.println("error parsing " + dateString);
        }
        return date;
    }

    /**
     * Make a string safe for a Database query.
     * Actions:
     * <UL><LI>Searches for apostrophes (single quotes),
     * and makes safe for SQL.
     * </UL>
     *
     * @param safeItUp string to make safe
     * @return SQL-safe string
     */
    public static String sqlSafeString(String safeItUp) {
        String retString = safeItUp;
        for (int i = retString.indexOf("'");
             i < retString.length() && i > -1;
             i = retString.indexOf("'", i)) {
            retString = retString.substring(0, i) +
                    "'" +
                    retString.substring(i);
            i += 2;
        }
        return retString;
    }

    /**
     * Converts a string in to a form that can appear in an HTML attribute (single- and double-quotes are escaped).
     */
    public static String htmlSafeQuotes(String toSafe) {
        int len = toSafe.length();
        StringBuffer result = new StringBuffer(len);
        for (int i = 0; i < len; i++) {
            char ch = toSafe.charAt(i);
            if (ch == '\"')
                result.append("&quot;");
            else if (ch == '\'')
                result.append("&#39;");
            else
                result.append(ch);
        }
        String sResult = result.toString();
        return sResult;
    }

    /**
     * Validates an email address.
     * NOTE: Taken from http://www.h2co3.com/blog/archives/000013.html
     */

    public static boolean isValidEmailAddress(String email) {
        if (email != null) {
            String regex = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*$";
            return email.matches(regex);
        } else {
            return false;
        }
    }

    public static boolean isValidZipCode(String zip) {
        if (zip != null) {
            return zip.matches("^[_A-Za-z0-9-]+");
        } else {
            return false;
        }
    }

    /**
     * Returns only the alphabetical letters from a String.
     * For example:
     * <pre>
     * encodeTextCharactersOnly("[!A8B-C.") -> "ABC"
     * </pre>
     */
    public static String encodeTextCharactersOnly(String toProcess) {
        StringBuffer ret = new StringBuffer();
        for (int i = 0; i < toProcess.length(); i++) {
            if (Character.isLetter(toProcess.charAt(i))) {
                ret.append(toProcess.charAt(i));
            }
        }
        return ret.toString();
    }

    /**
     * Returns only the alphabetical characters or numbers of the given String.
     */
    public static String encodeTextOrDigitsOnly(String toProcess) {
        StringBuffer ret = new StringBuffer();
        for (int i = 0; i < toProcess.length(); i++) {
            if (Character.isLetterOrDigit(toProcess.charAt(i))) {
                ret.append(toProcess.charAt(i));
            }
        }
        return ret.toString();
    }

    // see http://hotwired.lycos.com/webmonkey/reference/special_characters/
    static Object[][] entities = {
            // {"#39", new Integer(39)},       // ' - apostrophe
            // {"quot", new Integer(34)},      // " - double-quote
            // {"amp", new Integer(38)},       // & - ampersand
            // {"lt", new Integer(60)},        // < - less-than
            // {"gt", new Integer(62)},        // > - greater-than
            // {"nbsp", new Integer(160)},     // non-breaking space
            {"copy", new Integer(169)}, // - copyright
            {"reg", new Integer(174)}, // - registered trademark
            {"Agrave", new Integer(192)}, // - uppercase A, grave accent
            {"Aacute", new Integer(193)}, // - uppercase A, acute accent
            {"Acirc", new Integer(194)}, // - uppercase A, circumflex accent
            {"Atilde", new Integer(195)}, // - uppercase A, tilde
            {"Auml", new Integer(196)}, // - uppercase A, umlaut
            {"Aring", new Integer(197)}, // - uppercase A, ring
            {"AElig", new Integer(198)}, // - uppercase AE
            {"Ccedil", new Integer(199)}, // - uppercase C, cedilla
            {"Egrave", new Integer(200)}, // - uppercase E, grave accent
            {"Eacute", new Integer(201)}, // - uppercase E, acute accent
            {"Ecirc", new Integer(202)}, // - uppercase E, circumflex accent
            {"Euml", new Integer(203)}, // - uppercase E, umlaut
            {"Igrave", new Integer(204)}, // - uppercase I, grave accent
            {"Iacute", new Integer(205)}, // - uppercase I, acute accent
            {"Icirc", new Integer(206)}, // - uppercase I, circumflex accent
            {"Iuml", new Integer(207)}, // - uppercase I, umlaut
            {"ETH", new Integer(208)}, // - uppercase Eth, Icelandic
            {"Ntilde", new Integer(209)}, // - uppercase N, tilde
            {"Ograve", new Integer(210)}, // - uppercase O, grave accent
            {"Oacute", new Integer(211)}, // - uppercase O, acute accent
            {"Ocirc", new Integer(212)}, // - uppercase O, circumflex accent
            {"Otilde", new Integer(213)}, // - uppercase O, tilde
            {"Ouml", new Integer(214)}, // - uppercase O, umlaut
            {"Oslash", new Integer(216)}, // - uppercase O, slash
            {"Ugrave", new Integer(217)}, // - uppercase U, grave accent
            {"Uacute", new Integer(218)}, // - uppercase U, acute accent
            {"Ucirc", new Integer(219)}, // - uppercase U, circumflex accent
            {"Uuml", new Integer(220)}, // - uppercase U, umlaut
            {"Yacute", new Integer(221)}, // - uppercase Y, acute accent
            {"THORN", new Integer(222)}, // - uppercase THORN, Icelandic
            {"szlig", new Integer(223)}, // - lowercase sharps, German
            {"agrave", new Integer(224)}, // - lowercase a, grave accent
            {"aacute", new Integer(225)}, // - lowercase a, acute accent
            {"acirc", new Integer(226)}, // - lowercase a, circumflex accent
            {"atilde", new Integer(227)}, // - lowercase a, tilde
            {"auml", new Integer(228)}, // - lowercase a, umlaut
            {"aring", new Integer(229)}, // - lowercase a, ring
            {"aelig", new Integer(230)}, // - lowercase ae
            {"ccedil", new Integer(231)}, // - lowercase c, cedilla
            {"egrave", new Integer(232)}, // - lowercase e, grave accent
            {"eacute", new Integer(233)}, // - lowercase e, acute accent
            {"ecirc", new Integer(234)}, // - lowercase e, circumflex accent
            {"euml", new Integer(235)}, // - lowercase e, umlaut
            {"igrave", new Integer(236)}, // - lowercase i, grave accent
            {"iacute", new Integer(237)}, // - lowercase i, acute accent
            {"icirc", new Integer(238)}, // - lowercase i, circumflex accent
            {"iuml", new Integer(239)}, // - lowercase i, umlaut
            {"igrave", new Integer(236)}, // - lowercase i, grave accent
            {"iacute", new Integer(237)}, // - lowercase i, acute accent
            {"icirc", new Integer(238)}, // - lowercase i, circumflex accent
            {"iuml", new Integer(239)}, // - lowercase i, umlaut
            {"eth", new Integer(240)}, // - lowercase eth, Icelandic
            {"ntilde", new Integer(241)}, // - lowercase n, tilde
            {"ograve", new Integer(242)}, // - lowercase o, grave accent
            {"oacute", new Integer(243)}, // - lowercase o, acute accent
            {"ocirc", new Integer(244)}, // - lowercase o, circumflex accent
            {"otilde", new Integer(245)}, // - lowercase o, tilde
            {"ouml", new Integer(246)}, // - lowercase o, umlaut
            {"oslash", new Integer(248)}, // - lowercase o, slash
            {"ugrave", new Integer(249)}, // - lowercase u, grave accent
            {"uacute", new Integer(250)}, // - lowercase u, acute accent
            {"ucirc", new Integer(251)}, // - lowercase u, circumflex accent
            {"uuml", new Integer(252)}, // - lowercase u, umlaut
            {"yacute", new Integer(253)}, // - lowercase y, acute accent
            {"thorn", new Integer(254)}, // - lowercase thorn, Icelandic
            {"yuml", new Integer(255)}, // - lowercase y, umlaut
            {"euro", new Integer(8364)}, // Euro symbol
    };
    static Map e2i = new HashMap();
    static Map i2e = new HashMap();

    static {
        for (int i = 0; i < entities.length; ++i) {
            e2i.put(entities[i][0], entities[i][1]);
            i2e.put(entities[i][1], entities[i][0]);
        }
    }

    /**
     * Turns funky characters into HTML entity equivalents<p>
     * e.g. <tt>"bread" & "butter"</tt> => <tt>&amp;quot;bread&amp;quot; &amp;amp; &amp;quot;butter&amp;quot;</tt>.
     * Update: supports nearly all HTML entities, including funky accents. See the source code for more detail.
     *
     * @see #htmlunescape(String)
     */
    public static String htmlescape(String s1) {
        StringBuffer buf = new StringBuffer();
        int i;
        for (i = 0; i < s1.length(); ++i) {
            char ch = s1.charAt(i);
            String entity = (String) i2e.get(new Integer((int) ch));
            if (entity == null) {
                if (((int) ch) > 128) {
                    buf.append("&#" + ((int) ch) + ";");
                } else if (ch == '\'') {
                    buf.append("&#39;");
                } else {
                    buf.append(ch);
                }
            } else {
                buf.append("&" + entity + ";");
            }
        }
        return buf.toString();
    }

    /**
     * Given a string containing entity escapes, returns a string
     * containing the actual Unicode characters corresponding to the
     * escapes.
     * <p/>
     * Note: nasty bug fixed by Helge Tesgaard (and, in parallel, by
     * Alex, but Helge deserves major props for emailing me the fix).
     * 15-Feb-2002 Another bug fixed by Sean Brown <sean@boohai.com>
     *
     * @see #htmlescape(String)
     */
    public static String htmlunescape(String s1) {
        StringBuffer buf = new StringBuffer();
        int i;
        for (i = 0; i < s1.length(); ++i) {
            char ch = s1.charAt(i);
            if (ch == '&') {
                int semi = s1.indexOf(';', i + 1);
                if (semi == -1) {
                    buf.append(ch);
                    continue;
                }
                String entity = s1.substring(i + 1, semi);
                Integer iso;
                if (entity.charAt(0) == '#') {
                    iso = new Integer(entity.substring(1));
                } else {
                    iso = (Integer) e2i.get(entity);
                }
                if (iso == null) {
                    buf.append("&" + entity + ";");
                } else {
                    buf.append((char) (iso.intValue()));
                }
                i = semi;
            } else {
                buf.append(ch);
            }
        }
        return buf.toString();
    }

    /**
     * Parses string expecting "MM/dd/yyyy HH:mm:ss" format
     *
     * @param s
     * @return Date or null if parsing error
     */
    public static Date parseStandardUSDate(String s) {
        if (s == null)
            return null;
        try {
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            return df.parse(s);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * Formats date in short form using default locale.
     *
     * @return String representation of given Date for default locale.
     */
    public static String formatDateUS(Date date) {
        if (date == null) return "";
        DateFormat df = new SimpleDateFormat("MM/dd/yy");
        return df.format(date);
    }

    public static String formatDateStandard(Date date) {
        if (date == null) return "";
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        return df.format(date);
    }

    public static String toHtml(String str) {
        return str == null ? "" : htmlescape(str);
    }

    public static boolean isEquals(Object o1, Object o2) {
        return
                o1 == null && o2 == null ? true :
                        o1 == null || o2 == null ? false :
                                o1.equals(o2);
    }

    public static int random() {
        return rand.nextInt();
    }

    public static String makeSecureUrl(String url) {
        if (url.startsWith("https:")) {
            return url;
        } else if (url.startsWith("http:")) {
            return "https:" + url.substring(6);
        } else {
            return url;
        }
    }

    /**
     * Formats phone number.
     *
     * @return String phone number in format xxx-xxx-xxxx.
     */
    public static String formatPhoneNumber(String number) {
        if (number == null) {
            return "";
        }
        number = number.replace(' ', '-').replace('(', '-').replace(')', '-');
        for (; ;) {
            int pos = number.indexOf('-');
            if (pos == - 1) {
                break;
            }
            number = number.substring(0, pos) + number.substring(pos + 1);
        }
        if (number.length() > 6) {
            number = "(" + number.substring(0, 3) + ")" + number.substring(3, 6) + "-" + number.substring(6);
        }
        return number;
    }

    public static String encodeWordChars(String string) {
        return
                string
                        .replace((char) 151, '-')
                        .replace((char) 147, '"')
                        .replace((char) 148, '"')
                        .replace((char) 146, '\'');
    }

    private static final DecimalFormat SIZE_FORMAT = new DecimalFormat("0.##");

    public static String getFriendlySize(long size) {
        double s = size;
        if (s > Math.pow(2, 30)) {
            s = s / Math.pow(2, 30);
            return SIZE_FORMAT.format(s) + " GB";
        }
        if (s > Math.pow(2, 20)) {
            s = s / Math.pow(2, 20);
            return SIZE_FORMAT.format(s) + " MB";
        }
        if (s > Math.pow(2, 10)) {
            s = s / Math.pow(2, 10);
            return SIZE_FORMAT.format(s) + " KB";
        }
        return size + " Bytes";
    }
    
    /**
     *  This method will covert each input string to Date object. 
     *  Return true if the first input date is between in fromDate and toDate.
     *  Otherwise, return false.
     */
    public static boolean isInDateRange(Date theDate, Date fromDate, Date toDate)
    {
         if (theDate.before(fromDate) || theDate.after(toDate))
            return false;
         else
        	 return true;

    }


}
