package io.github.picodotdev.blogbitix.jcommander;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

import java.util.stream.Collectors;

public class Main {

    public static void main(String[] argv) {
        Arguments arguments = new Arguments();
        JCommander jcommander = JCommander.newBuilder().addObject(arguments).build();

        try {
            jcommander.parse(argv);
        } catch (ParameterException e) {
            jcommander.usage();
        }

        System.out.printf("Main: required=%s, optional=%s, values=%s, help=%s%n%n",
                arguments.isRequired(),
                arguments.isOptional(),
                arguments.getValues().stream().collect(Collectors.joining(",")),
                arguments.isHelp());

        if (arguments.isHelp()) {
            jcommander.usage();
        }
    }
}
