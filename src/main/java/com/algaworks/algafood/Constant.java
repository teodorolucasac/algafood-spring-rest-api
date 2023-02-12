package com.algaworks.algafood;

public class Constant {

//    public static void main(String[] args) {
//
//        String texto = "transformarEmSnakeCase01 transformarEmSnakeCase02 transformarEmSnakeCase03 transformarEmSnakeCase04";
//
//        var regex = "[A-Z]";
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(texto);
//        String snakeCase = texto.replaceAll(regex, "_");
//
//        System.out.println(snakeCase);
//
//        String result = ""
//    }

    public static String camelToSnake(String str) {

        // Empty String
        String result = "";

        // Append first character(in lower case)
        // to result string
        char c = str.charAt(0);
        result = result + Character.toLowerCase(c);

        // Traverse the string from
        // ist index to last index
        for (int i = 1; i < str.length(); i++) {

            char ch = str.charAt(i);

            // Check if the character is upper case
            // then append '_' and such character
            // (in lower case) to result string
            if (Character.isUpperCase(ch)) {
                result = result + '_';
                result = result + ch;
            }

            // If the character is lower case then
            // add such character into result string
            else {
                result = result + ch;
            }
        }

        // return the result
        return result.toUpperCase();
    }

    public static void main(String args[])
    {
        // Given string str
        String str[] = {
                "GeeksForGeeks",
                "TesteDeTeste"
        };

        // Print the modified string
        int i;
        for (i = 0; i < str.length; i++){
            System.out.print(camelToSnake(str[i]));
            System.out.println();
        }
    }
}
