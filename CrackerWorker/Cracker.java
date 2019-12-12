package passwordcracker;

import java.io.UnsupportedEncodingException;

class Cracker {
    private static int LENGTH = 52;
    public static char[] characters = new char[LENGTH];

    public String iterate(long start, long end, int size, String hash) throws UnsupportedEncodingException {
        while(start <= end) {
            long newStart = start;
            StringBuilder result = new StringBuilder();
            for(int i = size; i > 0; i--) {
                int digit = (int) (newStart % LENGTH);
                result.append(characters[digit]);
                newStart /= LENGTH;
            }
            String password = result.toString();

            MD5 md5 = new MD5();
            md5.Update(password, null);
            String hashed = md5.asHex();

            if (hash.equals(hashed))  {
                return password;
            }
            start++;
        }
        return null;
    }
}