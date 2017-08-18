/*
 * 3-SUM in quadratic time. Design an algorithm for the 3-SUM problem 
 * that takes time proportional to n2 in the worst case. 
 * You may assume that you can sort the n integers in time proportional to n2 or better.
 */
import java.util.Arrays;
import java.util.HashMap;

public class ThreeSumQuadratic {
    public static int count(int[] a){
        int cnt = 0;
        int n = a.length;
        HashMap<Integer,Integer> map = new HashMap<Integer,Integer>();
        for (int i = 0; i < n; i++) {
            map.put(a[i], i);
        }
        
        for(int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int twoSummValue = a[i] + a[j];                
                int forNullValue = -twoSummValue; 
                Integer needIndex = map.get(forNullValue);
                if (needIndex != null && needIndex > i && needIndex > j) {
                    System.out.println("a[" + i + "] = " + a[i] + ", a[" + j + "] = " + a[j] + ", a[" + needIndex + "] = " + (forNullValue));
                    cnt++;
                }
            }
        }
        return cnt;
    }
    public static void main(String[] args){
        int[] a = { 100, -30, -20, -10, 40, -90, 10, 5 };
        System.out.println(Arrays.toString(a));
        System.out.println(count(a));
    }
}