package interview;

import java.util.*;

public class MaxVowelSub {
    public static int countVowels(Deque<Character> dq) {
        int count = 0;
        for (Character c : dq)
            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
                count++;

            }
        return count;
    }

    public static void findMaxStringWithVowel(String s, int k) {
        Deque<Character> dq = new ArrayDeque<Character>();
        for (Character c : s.toCharArray()) {
            dq.addLast(c);
        }
        int startIndex = 0;
        int vowelCount = 0;
        Deque<Character> res = new ArrayDeque<Character>();
        Map<Integer,Integer> mp=new HashMap<>();
        int index = -1;
        for (Character c : dq) {
            index++;
            res.addLast(c);
            if (res.size() == k  && index < dq.size()-k) {
                if(countVowels(res) > vowelCount) vowelCount = countVowels(res);
                res.removeFirst();
                mp.put(index-k,vowelCount);
            }

        }
       int vcalue=Collections.max(mp.values());

        mp.forEach((key, value) -> {
            if (value.equals(vcalue)) {
               System.out.println(s.substring(key+1,key+k+1));
               System.exit(1);
            }
        });
    }

    public static void main(String[] args) {
  findMaxStringWithVowel("aeiou",3);
    }
}
