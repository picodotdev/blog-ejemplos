package io.github.picodotdev.blogbitix.javaregexquantifiers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
		String[] tests = new String[] {
			"Greedy",     "xfooxxxxxxfoo", ".*foo",
			"Reluctant",  "xfooxxxxxxfoo", ".*?foo",
			"Possessive", "xfooxxxxxxfoo", ".*+foo",
			"Greedy",     "xfooxxxxxxfoo", ".{2,5}",
			"Reluctant",  "xfooxxxxxxfoo", ".{2,5}?",
			"Possessive", "xfooxxxxxxfoo", ".{2,5}+"
		};

		for (int i = 0; i < tests.length; i += 3) {
			String quantifier = tests[i];
			String string = tests[i + 1];
			String regex = tests[i + 2];

		    System.out.printf("%s, %s, %s%n", quantifier, string, regex);

		    {
		        Pattern pattern = Pattern.compile(regex);
		        Matcher matcher = pattern.matcher(string);
		        while (matcher.find()) {
		            System.out.printf("I found the text \"%s\" starting at index %d and ending at index %d.%n", matcher.group(), matcher.start(), matcher.end());
		        }
		    }

		    System.out.println();
		}
    }
}
