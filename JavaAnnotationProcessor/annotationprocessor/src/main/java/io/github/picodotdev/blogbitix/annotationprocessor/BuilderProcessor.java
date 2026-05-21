package io.github.picodotdev.blogbitix.annotationprocessor;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class BuilderProcessor extends AbstractProcessor {

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_11;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Set.of(Builder.class.getName());
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment environment) {
        Set<Element> annotatedElements = new HashSet<>();
        for (TypeElement annotation : annotations) {
            Set<? extends Element> elements = environment.getElementsAnnotatedWith(annotation);
            annotatedElements.addAll(elements);
        }

        for (Element element : annotatedElements) {
            try {
                generateBuilder((TypeElement) element);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    private void generateBuilder(TypeElement element) throws IOException {
        String name = element.getQualifiedName() + "Builder";
        JavaFileObject javaFileObject = processingEnv.getFiler().createSourceFile(name, element);
        try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(javaFileObject.openOutputStream()))) {
            String packageName = processingEnv.getElementUtils().getPackageOf(element).getQualifiedName().toString();
            String className = element.getSimpleName().toString();
            String builderName = className + "Builder";
            Map<String, String> properties = getTypeProperties(element);
            String propertiesDeclaration = properties.keySet().stream().map(k -> "    private " + properties.get(k) + " " + k + ";").collect(Collectors.joining("\n"));
            String methodsDeclaration = properties.keySet().stream().map(k -> "    public " + builderName + " " + k + "(" + properties.get(k) + " " + k + ") {\n        this." + k + " = " + k + ";\n        return this;\n    }").collect(Collectors.joining("\n"));
            String buildMethod = "    public " + className + " build() {\n        return new " + className + "(" + properties.keySet().stream().collect(Collectors.joining(", ")) + ");\n    }";

            pw.println("package " + packageName + ";");
            pw.println();
            pw.println("public class " + builderName + " {");
            pw.println();
            pw.println(propertiesDeclaration);
            pw.println();
            pw.println(methodsDeclaration);
            pw.println();
            pw.println(buildMethod);
            pw.println("}");
        }
    }

    private Map<String, String> getTypeProperties(TypeElement type) {
        Map<String, String> properties = new LinkedHashMap<>();
        processingEnv.getElementUtils().getAllMembers(type).stream().filter(e -> e.getKind().equals(ElementKind.FIELD)).forEach(e -> {
            properties.put(e.getSimpleName().toString(), e.asType().toString());
        });
        return properties;
    }
}
