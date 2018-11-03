package io.github.picodotdev.blogbitix.javaregexformatter;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static final String SQL_REGEXP_REFERENCEGROPUS = "(?<keyword>select|from|where|and)|(?<string>'[^']*')|(?<number>\\d+)|(?<other>.)";
    private static final String JAVA_REGEXP_REFERENCEGROPUS = "(?<keyword>public|static|void|class)|(?<type>String|int)(?<typequalifier>\\[\\]|\\s)|(?<string>\"[^\"]*\")|(?<number>\\d+)|(?<other>[\\s\\S])";

    public static void main(String[] args) {
        AnsiConsole.systemInstall();
        AnsiConsole.out.println(Ansi.ansi().reset());

        printSql();
        System.out.println();
        printJava();

        AnsiConsole.out.println(Ansi.ansi().reset());
        AnsiConsole.systemUninstall();
    }

    private static void printSql() {
        Pattern p = Pattern.compile(SQL_REGEXP_REFERENCEGROPUS);
        Matcher m = p.matcher("select * from dual where a = '1' and b = 23");

        StringBuffer formatedSql = new StringBuffer();
        while (m.find()) {
            String keyword = m.group("keyword");
            String string = m.group("string");
            String number = m.group("number");
            String other = m.group("other");
            if (keyword != null) {
                formatedSql.append(Ansi.ansi().fg(Ansi.Color.MAGENTA).a(keyword));
            }
            if (string != null) {
                formatedSql.append(Ansi.ansi().fg(Ansi.Color.YELLOW).a(string));
            }
            if (number != null) {
                formatedSql.append(Ansi.ansi().fg(Ansi.Color.BLUE).a(number));
            }
            if (other != null) {
                formatedSql.append(Ansi.ansi().reset().a(other));
            }
        }
        AnsiConsole.out.println(Ansi.ansi().reset().a(formatedSql.toString()));
    }

    private static void printJava() {
        Pattern p = Pattern.compile(JAVA_REGEXP_REFERENCEGROPUS);
        Matcher m = p.matcher("public class Main {\n\tpublic static void main(String[] args) {\n\t\tint number = 3;\n\t\tSystem.out.println(\"Hello World!\");\n\t}\n}");

        StringBuffer formatedSql = new StringBuffer();
        while (m.find()) {
            String keyword = m.group("keyword");
            String type = m.group("type");
            String typeQualifier = m.group("typequalifier");
            String string = m.group("string");
            String number = m.group("number");
            String other = m.group("other");

            if (keyword != null) {
                formatedSql.append(Ansi.ansi().fg(Ansi.Color.MAGENTA).a(keyword));
            }
            if (type != null) {
                formatedSql.append(Ansi.ansi().fg(Ansi.Color.CYAN).a(type));
            }
            if (typeQualifier != null) {
                formatedSql.append(Ansi.ansi().reset().a(typeQualifier));
            }
            if (string != null) {
                formatedSql.append(Ansi.ansi().fg(Ansi.Color.YELLOW).a(string));
            }
            if (number != null) {
                formatedSql.append(Ansi.ansi().fg(Ansi.Color.BLUE).a(number));
            }
            if (other != null) {
                formatedSql.append(Ansi.ansi().reset().a(other));
            }
        }
        AnsiConsole.out.println(Ansi.ansi().reset().a(formatedSql.toString()));
    }
}
