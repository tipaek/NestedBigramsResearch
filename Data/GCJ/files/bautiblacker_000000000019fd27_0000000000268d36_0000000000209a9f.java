public void nestingDepth(int case, String s) {
    String[] aux = String.split("\n");
    int index = case - 1;
    for(String c : aux) {
        System.out.print("Case #%d: ", case - index);
        index--;
        System.out.println(nestNumber(c));
    }
}

private String nestNumber(String s) {
    int n = Integer.parse(s);
    int prefix = "";
    for(int i = 0; i < n; i++) {
        prefix += "(";
        s += ")";
    }
    return prefix + s;
}