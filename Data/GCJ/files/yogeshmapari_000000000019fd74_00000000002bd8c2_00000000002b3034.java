import java.util.*;



public class sol {
    private static String o1 = "Case #%d: %s";
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            int t = scanner.nextInt();
            for (int case = 1; case <= t; ++case) {
                new sol().getAnswer(case, scanner);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getAnswer(int case, Scanner scanner) {
        int n = scanner.nextInt();
        List<String> list = new ArrayList<>();
        for (int i = 0 ; i < n ; i++) {
            String str = scanner.next();
            list.add(str);

        }

        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.length() - o2.length();
            }
        });

        for (int i = 0 ; i < n ; i++) {
            System.out.println(list.get(i));
        }

        boolean pattern = true;
        String prev = list.get(0);
        int index = prev.indexOf("*");
        String prevLeft = prev.substring(0, index);
        String prevRight = "" ;
        if (index < prev.length()) {
            prevRight = prev.substring(index+1);
        }

        System.out.println(String.format("11 prevLeft =%s , prevRight = %s" , prevLeft, prevRight));

        for (int i = 0 ; i < list.size() ; i++) {
            String str = list.get(i);
            index = str.indexOf("*");
            String curLeft = str.substring(0, index);
            String curRight = "" ;
            if (index < str.length()) {
                curRight = str.substring(index+1);
            }

            char[] c1 = prevLeft.toCharArray();
            char[] c2 = curLeft.toCharArray();

//            char[] c1 = prev.toCharArray();
//            char[] c2 = str.toCharArray();
            int n1 = c1.length , n2 = c2.length;
            int i1 = 0 , i2 = 0;
            // test case 1 & 2 before *
            while (i1 < n1 && i2 < n2) {
                if (c1[i1] == c2[i2]) {
                    i1++;
                    i2++;
                } else {
                    pattern = false;
                    break;
                }
            }
            System.out.println(String.format("11 i1 =%d , i2 = %d , pattern = %s" , i1, i2, pattern));

            if (!pattern) {
                break;
            }

            if (curLeft.length() > prevLeft.length()) {
                prevLeft = curLeft;
            }

            
            c1 = prevRight.toCharArray();
            c2 = curRight.toCharArray();
            n1 = c1.length ;
            n2 = c2.length;

            int i3 = n1-1 , i4 = n2-1;
            while (i3 >= 0 && i4 >= 0) {
                if (c1[i3] == c2[i4]) {
                    i3--;
                    i4--;
                } else {
                    pattern = false;
                    break;
                }
            }
            System.out.println(String.format("22 i3 =%d , i4 = %d , pattern = %s" , i3, i4, pattern));

            if (!pattern) {
                break;
            }

            if (curRight.length() > prevRight.length()) {
                prevRight = curRight;
            }

            System.out.println(String.format("22 prevLeft =%s , prevRight = %s" , prevLeft, prevRight));

        }

        String r = "*";
        if (pattern) {
            r = prevLeft + prevRight ;
        }

        System.out.println(String.format(o1, case, r));
    }

}