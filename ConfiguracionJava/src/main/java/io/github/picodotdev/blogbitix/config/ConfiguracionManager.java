package io.github.picodotdev.blogbitix.config;

import java.io.FileReader;
import java.io.Reader;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;

public class ConfiguracionManager {

	private String fullName;
	private Path path;
	private Configuracion configuracion;

	private Thread thread;
	private boolean closed;

	public ConfiguracionManager(String fullName, Path path) {
		this.fullName = fullName;
		this.path = path;
	}

	public Configuracion getConfiguracion() {
		return configuracion;
	}

	public Map get() {
		return configuracion.get();
	}

	public ConfiguracionManager load() throws Exception {
		List<String> l = Arrays.asList(fullName.split("\\."));
		String name = l.get(l.size() - 1);
		String source = loadSource();

		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		JavaFileManager manager = new ClassFileManager(compiler.getStandardFileManager(null, null, null));

		List<JavaFileObject> files = new ArrayList<JavaFileObject>();
		files.add(new CharSequenceJavaFileObject(fullName, source));

		compiler.getTask(new NullWriter(), manager, null, null, null, files).call();
		configuracion = (Configuracion) manager.getClassLoader(null).loadClass(fullName).newInstance();

		return this;
	}

	public ConfiguracionManager monitor() throws Exception {
		closed = false;

		Runnable task = new Runnable() {
			@Override
			public void run() {
				while (!closed) {
					try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
						path.getParent().register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
						WatchKey watchKey = watchService.take();
						if (watchKey == null) {
							return;
						}
						for (WatchEvent<?> event : watchKey.pollEvents()) {
							Path p = (Path) event.context();							
							Path pp = path.getParent().resolve(p);
							if (path.equals(pp)) {
								load();
							}
						}
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
			}
		};

		thread = new Thread(task);
		thread.setDaemon(true);
		thread.start();

		return this;
	}

	public void close() throws Exception {
		closed = true;
	}

	private String loadSource() throws Exception {
		StringBuffer source = new StringBuffer();
		char[] buffer = new char[128 * 1024];
		Reader reader = new FileReader(path.toFile());
		int n = reader.read(buffer);
		while (n != -1) {
			source.append(buffer, 0, n);
			n = reader.read(buffer);
		}
		return source.toString();
	}
}
