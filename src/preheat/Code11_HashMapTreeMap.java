package preheat;

import java.util.HashMap;
import java.util.TreeMap;

/**
 * @Author: duccio
 * @Date: 19, 03, 2022
 * @Description: Fundamentals of HashMap and TreeMap
 * @Note:   1. HashMap is in O(1) for CURD of simple data type like Integer, and is O(str.length) for String
 *          2. Key of classes like Integer and String in HashMap is based on values, not variable addresses
 *          3. Key of self-defined classes like Node in HashMap is based on addresses
 *          ======
 *          1. TreeMap is in O(logN) for CURD
 *          2. Keys in TreeMap are ordered
 *          3. Keys in TreeMap mush be comparable
 */
public class Code11_HashMapTreeMap {

    // self-defined Node
    public static class Node {
        int value;

        public Node(int val) {
            value = val;
        }
    }

    public static void main(String[] args) {
        // HashMap
        HashMap<String, String> map1 = new HashMap<>();
        map1.put("abc", "i am abc");
        System.out.println(map1.containsKey("abc"));
        System.out.println(map1.get("abc"));

        map1.put("abc", "i am xyz");
        System.out.println(map1.get("abc"));

        map1.remove("abc");
        System.out.println(map1.containsKey("abc"));
        System.out.println(map1.get("abc"));

        map1.put("abc", "i am abc");
        String str1 = "abc";
        String str2 = "abc";
        System.out.println(str1 == str2);  // true, because "==" compares the content of String
        System.out.println(map1.get(str1));  // i am abc
        System.out.println(map1.get(str2));  // i am abc

        HashMap<Integer, String> map2 = new HashMap<>();
        map2.put(123, "i am 123");

        Integer int1 = 123;
        Integer int2 = 123;
        System.out.println(int1 == int2);  // true!!!
        System.out.println(map2.get(int1));  // i am 123
        System.out.println(map2.get(int2));  // i am 123

        HashMap<Integer, String> map3 = new HashMap<>();
        map3.put(1234, "i am 1234");

        Integer int3 = 1234;
        Integer int4 = 1234;
        System.out.println(int3 == int4);  // false!!!
        System.out.println(map3.get(int3));  // i am 1234
        System.out.println(map3.get(int4));  // i am 1234

        Node node1 = new Node(1);
        Node node2 = new Node(1);
        HashMap<Node, String> map4 = new HashMap<>();
        map4.put(node1, "i have 1");
        System.out.println(map4.get(node1));  // i have 1
        System.out.println(map4.get(node2));  // null


        // TreeMap
        System.out.println("==========");

        TreeMap<Integer, String> treeMap1 = new TreeMap<>();

        treeMap1.put(3, "i am 3");
        treeMap1.put(0, "i am 0");
        treeMap1.put(7, "i am 7");
        treeMap1.put(2, "i am 2");
        treeMap1.put(5, "i am 5");
        treeMap1.put(9, "i am 9");

        System.out.println(treeMap1.firstKey());  // 0
        System.out.println(treeMap1.lastKey());  // 9
        System.out.println(treeMap1.floorKey(5));  // 5
        System.out.println(treeMap1.floorKey(6));  // 5
        System.out.println(treeMap1.ceilingKey(5));  // 5
        System.out.println(treeMap1.ceilingKey(6));  // 7
    }

}
