import java
 
/**
 * The parent of a statement, excluding some common cases that don't really make
 * sense for nesting depth. For example, in `if (...) { } else if (...) { }` we don't
 * consider the second `if` nested. Blocks are also skipped.
 */
predicate realParent(Stmt inner, Stmt outer) {
  if skipParent(inner) then realParent(inner.getParent(), outer) else outer = inner.getParent()
}
 
predicate skipParent(Stmt s) {
  exists(Stmt parent | parent = s.getParent() |
    s instanceof IfStmt and parent.(IfStmt).getElse() = s
    or
    parent instanceof Block
  )
}
 
predicate nestingDepth(Stmt s, int depth) {
  depth = count(Stmt enclosing | realParent+(s, enclosing))
}
 
from Method m, int depth
where
  depth = max(Stmt s, int aDepth | s.getEnclosingCallable() = m and nestingDepth(s, aDepth) | aDepth)
select m, depth order by depth
public static void printCharacterCodes_Bad(String[] strings) {
    if (strings != null) {
        for (String s : strings) {
            if (s != null) {
                for (int i = 0; i < s.length(); i++) {
                    System.out.println(s.charAt(i) + "=" + (int) s.charAt(i));
                }
            }
        }
    }
}
public static void printAllCharInts(String s) {
    if (s != null) {
        for (int i = 0; i < s.length(); i++) {
            System.out.println(s.charAt(i) + "=" + (int) s.charAt(i));
        }
    }
}
public static void printCharacterCodes_Good(String[] strings) {
    if (strings != null) {
        for(String s : strings){
            printAllCharInts(s);
        }
    }
}