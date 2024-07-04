public void nestingDepth(int caseNumber, String s) {
    String[] lines = s.split("\n");
    int index = caseNumber - 1;
    for (String line : lines) {
        System.out.printf("Case #%d: ", caseNumber - index);
        index--;
        System.out.println(nestNumber(line));
    }
}

private String nestNumber(String s) {
    int n = Integer.parseInt(s);
    StringBuilder result = new StringBuilder();
    for (int i = 0; i < n; i++) {
        result.append("(");
    }
    result.append(s);
    for (int i = 0; i < n; i++) {
        result.append(")");
    }
    return result.toString();
}