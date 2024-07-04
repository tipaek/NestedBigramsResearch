import java.util.*;

public class Example {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("Hello");
        list.add("World");
        
        for (String str : list) {
            System.out.println(str);
        }
    }
}
```

Rewritten code with the same functionality:
```java
import java.util.ArrayList;
import java.util.List;

public class Example {
    public static void main(String[] args) {
        List<String> words = new ArrayList<>();
        words.add("Hello");
        words.add("World");
        
        for (String word : words) {
            System.out.println(word);
        }
    }
}