class Solution {
    public String reverseParentheses(String s) {
        Stack<StringBuilder> stack = new Stack<>();

        StringBuilder current = new StringBuilder();

        for (char ch : s.toCharArray()) {

            if (ch == '(') {
                // Save the current string
                stack.push(current);
                current = new StringBuilder();

            } else if (ch == ')') {
                // Reverse the substring inside parentheses
                current.reverse();

                // Get the string before '('
                StringBuilder previous = stack.pop();

                // Append reversed substring
                previous.append(current);

                current = previous;

            } else {
                // Normal character
                current.append(ch);
            }
        }

        return current.toString();
    }
}