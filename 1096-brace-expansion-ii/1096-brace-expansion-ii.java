import java.util.*;

class Solution {
    private int index = 0;

    public List<String> braceExpansionII(String expression) {
        Set<String> result = parse(expression);

        List<String> ans = new ArrayList<>(result);
        Collections.sort(ans);

        return ans;
    }

    // Parse a sequence of expressions until '}' or end
    private Set<String> parse(String s) {
        Set<String> result = new HashSet<>();
        result.add("");

        while (index < s.length() && s.charAt(index) != '}') {

            if (s.charAt(index) == ',') {
                // Union: start parsing next expression
                index++;

                Set<String> next = parse(s);
                result.addAll(next);

            } else {
                // Concatenation
                Set<String> next;

                if (s.charAt(index) == '{') {
                    index++; // skip '{'
                    next = parse(s);
                    index++; // skip '}'
                } else {
                    // Single character
                    next = new HashSet<>();
                    next.add(String.valueOf(s.charAt(index)));
                    index++;
                }

                result = concatenate(result, next);
            }
        }

        return result;
    }

    private Set<String> concatenate(Set<String> a, Set<String> b) {
        Set<String> result = new HashSet<>();

        for (String x : a) {
            for (String y : b) {
                result.add(x + y);
            }
        }

        return result;
    }
}