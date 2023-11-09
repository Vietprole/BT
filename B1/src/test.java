public class test{
    public static void main(String[] args) {
        String line = "abcd";
        StringBuilder UpperLower = new StringBuilder(line);
        //System.out.print((char)(line.charAt(0)+1));
        for (int i = 0; i < line.length(); i++) {
            if (i % 2 == 0) {
                UpperLower.appendCodePoint(line.charAt(i)-32);
            } else {
                UpperLower.append(line.charAt(i));
            }
        }
        System.out.print(UpperLower);
    }
}