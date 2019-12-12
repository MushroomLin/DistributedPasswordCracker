package passwordcracker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class JobUtils {
    public static Map<Long, Long> hashMap = new ConcurrentHashMap<>();
    private static long total;
    private static int length;
    public int pwdLength;
    public long jobLength;

    public long getNext() {
        if(pwdLength == 0 && hashMap.isEmpty()) {
            return -1;
        }
        if(total == 0 && !hashMap.isEmpty()) {
            List<Long> tempList = new ArrayList<>(hashMap.keySet());
            return tempList.get(0);
        }
        if(total == 0) {
            total = (long) Math.pow(length, --pwdLength);
        }
        hashMap.put(total, total);
        long result = total;
        total = Math.max(0, total - jobLength);
        return result;
    }

    public JobUtils(int length, int pwdLength, int jobLength) {
        JobUtils.length = length;
        this.jobLength = jobLength;
        this.pwdLength = pwdLength;
        total = (long) Math.pow(length, pwdLength);
    }
}