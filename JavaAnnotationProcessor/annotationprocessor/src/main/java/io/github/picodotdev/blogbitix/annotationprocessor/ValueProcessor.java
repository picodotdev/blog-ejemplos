package io.github.picodotdev.blogbitix.annotationprocessor;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ValueProcessor extends AbstractProcessor {

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_11;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Set.of(Value.class.getName());
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
                List<? extends Element> executableEmentls = element.getEnclosedElements().stream().filter(t -> {
                    return t.getKind().equals(ElementKind.METHOD);
                }).collect(Collectors.toList());
                boolean hasHashCode = executableEmentls.stream().anyMatch(e -> {
                    return e.getSimpleName().toString().equals("hashCode");
                });
                boolean hasEquals = executableEmentls.stream().anyMatch(e -> {
                    return e.getSimpleName().toString().equals("equals");
                });
                boolean hasToString = executableEmentls.stream().anyMatch(e -> {
                    return e.getSimpleName().toString().equals("toString");
                });

                if (!hasHashCode || !hasEquals || !hasToString) {
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, "Class " + element.getSimpleName() + " should override hashCode, equals and toString methods");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return true;
    }
}
