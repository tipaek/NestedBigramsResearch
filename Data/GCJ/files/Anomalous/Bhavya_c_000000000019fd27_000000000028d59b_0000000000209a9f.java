for (int i = 0; i < first; i++) {
    sb.append('(');
}
sb.append(first);

for (int i = 1; i < chars.length; i++) {
    int currentDigit = Character.getNumericValue(chars[i]);

    if (currentDigit == num) {
        sb.append(currentDigit);
    } else if (currentDigit > num) {
        int diff = currentDigit - num;
        for (int j = 0; j < diff; j++) {
            sb.append('(');
            brackets++;
        }
        sb.append(currentDigit);
    } else {
        int diff = num - currentDigit;
        for (int j = 0; j < diff; j++) {
            sb.append(')');
            brackets--;
        }
        sb.append(currentDigit);
    }

    num = currentDigit;
}

while (brackets-- > 0) {
    sb.append(')');
}

System.out.println("Case #" + (tn++) + ": " + sb.toString());