import java.io.*;
import java.math.*;
import java.util.*;
import java.util.stream.*;

public Solution {
    
  public static void main(final String[] args) {
    final Reader in = new Reader();

    for (int x = 1; x <= in.T; x++) {
      final List<String> t = in.next();
      
      Collections.sort(t);
      final String a = t.get(0)
      boolean flag = true;
      for (int i = 1; i < a.size(); i++) {
        if (!a.endsWith(t.get(i)) {
            flag = false;
            break;
        }
      }

      if (flag)
        System.out.println("Case #" + x + ": " + a);
      else
        
        System.out.println("Case #" + x + ": *");
    }
  }
    
  private static class Reader implements Iterator<List<String>> {
    private final Scanner in;
    private final int T;

    public Reader() {
      in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
      T = in.nextInt();
    }

    @Override
    public boolean hasNext() {
      return in.hasNext();
    }

    @Override
    public List<String> next() {
      final int n = in.nextInt();

      return IntStream
          .range(0, n)
          .mapToObj(i -> in.next().substring(1))
          .collect(Collectors.toList());
    }
  }
    
}