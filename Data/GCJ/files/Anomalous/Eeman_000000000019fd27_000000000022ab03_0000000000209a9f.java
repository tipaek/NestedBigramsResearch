public static void displayCharWithCodes(String input) {
    if (input != null) {
        for (int index = 0; index < input.length(); index++) {
            char character = input.charAt(index);
            int charCode = (int) character;
            System.out.println(character + "=" + charCode);
        }
    }
}

public static void showCharacterCodes(String[] inputs) {
    if (inputs != null) {
        for (String input : inputs) {
            displayCharWithCodes(input);
        }
    }
}