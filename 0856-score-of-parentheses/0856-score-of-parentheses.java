class Solution {
    public int scoreOfParentheses(String s) {
        Stack<Integer> stack = new Stack<>();
        stack.push(0);

        for (char c : s.toCharArray()) {
            if (c == '(') {
                stack.push(0);
            } else {
                int a = stack.pop();

                int score;
                if (a == 0) {
                    score = 1;
                } else {
                    score = 2 * a;
                }

                stack.push(stack.pop() + score);
            }
        }

        return stack.pop();
    }
}