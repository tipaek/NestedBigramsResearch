import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

class A {
    public int s, e, index;
    public char person;

    public A(int s, int e, int index) {
        this.s = s;
        this.e = e;
        this.index = index;
    }
}

class AComparator implements Comparator<A> {

    @Override
    public int compare(A o1, A o2) {
        if (o1.s == o2.s && o1.e == o2.e) {
            return 0;
        }

        if (o1.s < o2.s || (o1.s == o2.s && o1.e < o2.e)) {
            return -1;
        }

        return 1;
    }
}

class AComparator2 implements Comparator<A> {

    @Override
    public int compare(A o1, A o2) {
        if (o1.index == o2.index) {
            return 0;
        }

        if (o1.index < o2.index) {
            return -1;
        }

        return 1;
    }
}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine());
        for (int q = 0; q < t; q++) {
            int n = Integer.parseInt(br.readLine());
            ArrayList<A> a = new ArrayList<>();

            for (int i = 0; i < n; i++) {
                String[] s = br.readLine().split(" ");
                a.add(new A(Integer.parseInt(s[0]), Integer.parseInt(s[1]), i));
            }

            a.sort(new AComparator());

            StringBuilder ans = new StringBuilder();
            boolean imp = false;

            a.get(0).person = 'C';
            for (int i = 1; i < a.size(); i++) {
                if (a.get(i).s < a.get(i - 1).e) {
                    a.get(i).person = a.get(i - 1).person == 'C' ? 'J' : 'C';
                } else {
                    a.get(i).person = a.get(i - 1).person;
                }
            }

            A[] a1 = a.stream().filter(x -> x.person == 'C').toArray(A[]::new);
            for (int i = 1; i < a1.length; i++) {
                if (a1[i].s < a1[i - 1].e) {
                    ans.append("IMPOSSIBLE");
                    imp = true;
                    break;
                }
            }

            if (!imp) {
                A[] a2 = a.stream().filter(x -> x.person == 'J').toArray(A[]::new);
                for (int i = 1; i < a2.length; i++) {
                    if (a2[i].s < a2[i - 1].e) {
                        ans.append("IMPOSSIBLE");
                        imp = true;
                        break;
                    }
                }
            }

            if (!imp) {
                a.sort(new AComparator2());
                for (int i = 0; i < a.size(); i++) {
                    ans.append(a.get(i).person);
                }
            }

            System.out.println("Case #" + (q + 1) + ": " + ans.toString());
        }
    }
}
