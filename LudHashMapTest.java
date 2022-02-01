package LudHashMap.LudHashMap;

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

        // remove same random keys in both hashmaps
        for (int i = 0; i < removes; i++) {
            String key = randomStrings[randomIntBetween(0, amount-1)];
            ludMap.remove(key);
            realMap.remove(key);
        }

        // check that ludMap contains all elements in HashMap
        for (Entry <String, Integer> entry : realMap.entrySet()) {
            if (ludMap.contains(entry.getKey())) {
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

        // Check that HashMap contains all elements in LudMap
        System.out.println("\nLudMaps element mot HashMaps:");
        for (String key : ludMap.keySet()) {
            if (realMap.containsKey(key)) {
                System.out.print("Nøkkel " + key + " finnes");
                if (realMap.get(key) == ludMap.get(key)) {
                    System.out.println(" med lik verdi " + ludMap.get(key));
                } else {
                    System.out.println(" men med ulik verdi " + ludMap.get(key));
                }
            } else {
                System.out.println("Nøkkel " + key + " finnes ikke");
            }
        }
        System.out.println("Ferdig!");
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
