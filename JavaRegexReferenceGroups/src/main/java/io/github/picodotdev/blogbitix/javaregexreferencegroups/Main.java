package io.github.picodotdev.blogbitix.javaregexreferencegroups;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        String text = "Lorem ipsum ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat fugiat nulla pariatur. Excepteur sint sint occaecat cupidatat non proident proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";

        System.out.println("Palabras duplicadas:");
        Pattern p = Pattern.compile("\\s([a-zA-Z]+)\\s\\1");
        Matcher m = p.matcher(text);
        while (m.find()) {
            System.out.printf("* %s%n", m.group(1));
        }

        System.out.println("");
        System.out.println("Texto sin palabras duplicadas:");
        System.out.println(text.replaceAll("\\s([a-zA-Z]+)\\s\\1", " $1"));

        System.out.println("");
        String emails = "pedro@gmail.com\njuan@gmail.com\nsonia@gmail.com";
        {
            String emailregexp = "((?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\]))";
            System.out.println(emails.replaceAll(emailregexp, "<a href=\"$1\">$1</a>"));
        }

        System.out.println("");
        {
            String emailregexp = "(?<email>(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\]))";
            System.out.println(emails.replaceAll(emailregexp, "<a href=\"${email}\">${email}</a>"));
        }
    }
}
