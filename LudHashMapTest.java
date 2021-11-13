import java.util.HashMap;
import java.util.Map.Entry;

public class LudHashMapTest {
    public static void main(String[] args) {
        
        final int amount = 100;
        final int stringLength = 15;
        final int updates = 50;
        final int removes = 25;

        LudHashMap ludMap = new LudHashMap();
        HashMap<String, Integer> realMap = new HashMap<String, Integer>();

        // find some random strings
        String [] randomStrings = new String [amount];
        int index = 0;
        while (index < amount) {
            String randomString = createRandomString(stringLength);
            System.out.println(randomString);
            if (!arrayContains(randomStrings, randomString)) {
                // System.out.println("Added randomString " + randomString);
                randomStrings[index] = randomString;
                index++;
            }
        }
        
        // put random strings in both hashmaps
        for (String key : randomStrings) {
            int ran = randomIntBetween(0, 1000);
            ludMap.put(key, ran);
            realMap.put(key, ran);
        }

        // update same key in both hashmaps
        for (int i = 0; i < updates; i++) {
            String key = randomStrings[randomIntBetween(0, amount-1)];
            ludMap.put(key, i);
            realMap.put(key, i);
        }

        // remove same keys in both hashmaps
        for (int i = 0; i < removes; i++) {
            String key = randomStrings[randomIntBetween(0, amount-1)];
            ludMap.remove(key);
            realMap.remove(key);
        }

        for (Entry <String, Integer> entry : realMap.entrySet()) {
            if (ludMap.get(entry.getKey()) != -1) {
                System.out.print("Nøkkel " + entry.getKey() + " finnes");
                if (ludMap.get(entry.getKey()) == entry.getValue()) {
                    System.out.println(" med lik verdi " + entry.getValue());
                } else {
                    System.out.println(" men med ulik verdi " + entry.getValue());
                }
            } else {
                System.out.println("Nøkkel " + entry.getKey() + " finnes ikke");
            }
        }

        System.out.println("Ferdig!");
    }

    public void testProgram() {
        int startAsciiIndex = 33;
        int stopAsciiIndex = 127;
        LudHashMap lhm = new LudHashMap(); // key is string, value is int
        
        System.out.println("\nInserting keys and values");
        for (int i = startAsciiIndex; i < stopAsciiIndex; i++) {
            lhm.put((char)i + "", i); // '(char)i + ""' will find the ASCII of i, then cast to String
        }

        System.out.println("\nTesting get method");
        for (int i = startAsciiIndex; i < stopAsciiIndex; i++) {
            System.out.println("Is ASCII of " + (char)i + " = " + i + "?: " + (lhm.get((char)i + "") == i));
            lhm.put((char)i + "", i); // '(char)i + ""' will find the ASCII of i, then cast to String
        }

        System.out.println("\nTesting remove method");
        lhm.remove("A");
        System.out.println("After remove, return value of 'A' = -1 ?: " + (lhm.get("A") == -1));
        lhm.remove("U");
        System.out.println("After remove, return value of 'U' = -1 ?: " + (lhm.get("U") == -1));
        lhm.remove("^");
        System.out.println("After remove, return value of '^' = -1 ?: " + (lhm.get("^") == -1));
        
        System.out.println("\nTesting update method");
        lhm.put("Q", 100);
        System.out.println("After update, return value of 'Q' = 100 ?: " + (lhm.get("Q") == 100));
        lhm.put(" ", 100);
        System.out.println("After update, return value of 'space' = 100 ?: " + (lhm.get(" ") == 100));
        lhm.put("Y", 100);
        System.out.println("After update, return value of 'Y' = 100 ?: " + (lhm.get("Y") == 100));

        lhm.printAllEntries();  
    }

    public static boolean arrayContains(String [] array, String x) {
        for (String s : array) {
            // System.out.println("test");
            if (s != null && s.equals(x)) {
                return true;
            }
        }
        return false;
    }

    public static String createRandomString(int length) {
        String randomString = "";
        for (int i = 0; i < length; i++) {
            String randomSymbol = (char) randomIntBetween(33, 127) + "";
            if (!randomSymbol.equals("\\")) {
                randomString = randomString + randomSymbol;
            }
            // System.out.println("test");
        }
        return randomString;
    }

    public static int randomIntBetween(int min, int max) {
        return (int) (Math.random() * (max-min) + min);
    }
}
