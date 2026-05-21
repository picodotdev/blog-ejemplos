package io.github.picodotdev.blogbitix.config;

import java.util.ArrayList;
import java.util.List;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;

public class Main1 {

	public static void main(String[] args) throws Exception {
		// Definir la clase
		String[] sources = new String[] { "package io.github.picodotdev.blogbitix.config;",
				"import java.util.HashMap;",
				"import java.util.Map;",
				"public class AppConfiguracion implements Configuracion {",
					"private static Map<String, Object> config;",
					"static {",
						"config = new HashMap<>();",
						"config.put(\"propiedad\", 11);",
					"}",
					"public Map get() {",
						"return config;",
					"}",
				"}" };

		String source = join(sources);
		String name = "io.github.picodotdev.blogbitix.config.AppConfiguracion";
	
		// Compilar la clase
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		JavaFileManager manager = new ClassFileManager(compiler.getStandardFileManager(null, null, null));

		List<JavaFileObject> files = new ArrayList<JavaFileObject>();
		files.add(new CharSequenceJavaFileObject(name, source));

		compiler.getTask(new NullWriter(), manager, null, null, null, files).call();

		// Cargar e instanciar la case
		Configuracion configuracion = (Configuracion) manager.getClassLoader(null).loadClass(name).newInstance();
		
		// Invocar un método de la clase
		System.out.println(configuracion.get());
	}

	private static String join(String[] s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length; i++) {
			sb.append(s[i]);
		}
		return sb.toString();
	}
}