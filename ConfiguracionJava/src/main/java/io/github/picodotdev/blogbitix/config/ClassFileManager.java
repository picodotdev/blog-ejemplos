package io.github.picodotdev.blogbitix.config;

import java.io.IOException;
import java.security.SecureClassLoader;
import java.util.HashMap;
import java.util.Map;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;
import javax.tools.StandardJavaFileManager;

public class ClassFileManager extends ForwardingJavaFileManager {

	private Map<String, Object> classes;

	/**
	 * Will initialize the manager with the specified standard java file manager
	 * 
	 * @param standardManger
	 */
	public ClassFileManager(StandardJavaFileManager standardManager) {
		super(standardManager);
		classes = new HashMap<>();
	}

	/**
	 * Will be used by us to get the class loader for our compiled class. It creates an anonymous
	 * class extending the SecureClassLoader which uses the byte code created by the compiler and
	 * stored in the JavaClassObject, and returns the Class for it
	 */
	@Override
	public ClassLoader getClassLoader(Location location) {
		return new SecureClassLoader() {
			@Override
			public Class<?> loadClass(String name) throws ClassNotFoundException {
				Object o = classes.get(name);
				if (o instanceof Class) {
					return (Class) o;
				} else if (o instanceof JavaClassObject) {
					JavaClassObject object = (JavaClassObject) o;
					byte[] b = object.getBytes();
					Class<?> clazz = defineClass(name, object.getBytes(), 0, b.length);
					classes.put(name, clazz);
					return clazz;
				} else {
					return super.loadClass(name);					
				} 
			}
		};
	}

	/**
	 * Gives the compiler an instance of the JavaClassObject so that the compiler can write the byte
	 * code into it.
	 */
	@Override
	public JavaFileObject getJavaFileForOutput(Location location, String className, Kind kind, FileObject sibling) throws IOException {
		JavaClassObject object = new JavaClassObject(className, kind);
		classes.put(className, object);
		return object;
	}
}
