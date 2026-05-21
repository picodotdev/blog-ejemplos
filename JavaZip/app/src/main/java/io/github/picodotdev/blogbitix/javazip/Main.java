package io.github.picodotdev.blogbitix.javazip;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class Main {

    private static final List<String> FILES = List.of("alice29.txt", "asyoulik.txt", "cp.html", "fields.c", "grammar.lsp", "kennedy.xls", "lcet10.txt", "plrabn12.txt", "ptt5", "sum", "xargs.1");

    public Main() {
    }

    public void mkdirs() {
        new File("target").mkdirs();
    }

    public void compress() throws Exception {
        ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(new File("target/file.zip"))));
        try (zos) {
            for (String f : FILES) {
                System.out.printf("Deflating %s...%n", f);
                InputStream resource = getClass().getClassLoader().getResourceAsStream("cantrbry/" + f);

                zos.putNextEntry(new ZipEntry(f));
                zos.write(resource.readAllBytes());
                zos.closeEntry();
            }
        }
    }

    public void list() throws Exception {
        ZipFile zf = new ZipFile(new File("target/file.zip"));
        try (zf) {
            Enumeration<? extends ZipEntry> entries = zf.entries();
            for (ZipEntry ze : Collections.list(entries)) {
                System.out.printf("File %s (compressed: %s, size: %s, ratio: %.2f)%n", ze.getName(), ze.getCompressedSize(), ze.getSize(), (double) ze.getSize() / ze.getCompressedSize());
            }
        }
    }

    public void decompress() throws Exception {
        ZipFile zf = new ZipFile(new File("target/file.zip"));
        try (zf) {
            Enumeration<? extends ZipEntry> entries = zf.entries();
            for (ZipEntry ze : Collections.list(entries)) {
                System.out.printf("Inflating %s (compressed: %s, size: %s, ratio: %.2f)%n", ze.getName(), ze.getCompressedSize(), ze.getSize(), (double) ze.getSize() / ze.getCompressedSize());
                InputStream is = zf.getInputStream(ze);
                FileOutputStream fos = new FileOutputStream(new File("target", ze.getName()));
                try (is; fos) {
                    fos.write(is.readAllBytes());
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Main main = new Main();
        main.mkdirs();
        main.compress();
        main.list();
        main.decompress();
    }
}
