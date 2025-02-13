class Solution {
    
    private static final String[] words = new String[]{
        "zero",
        "one",
        "two",
        "three",
        "four",
        "five",
        "six",
        "seven",
        "eight",
        "nine"
    };
    
    public int solution(String s) {
        for (int i = 0; i < words.length; i++) {
            s = s.replace(words[i], i+"");
        }
        
        return Integer.parseInt(s);
    }
}