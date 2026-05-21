package io.github.picodotdev.blogbitix.nio;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.*;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermissions;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.Future;

public class Main {

    public static void main(String[] args) throws Exception {
        // Path
        System.out.println("# info");
        Path relative = Paths.get(".");
        Path absolute = relative.toAbsolutePath().normalize();

        System.out.printf("Relative: %s%n", relative);
        System.out.printf("Absolute: %s%n", absolute);
        System.out.printf("Name count: %d%n", absolute.getNameCount());
        System.out.printf("Parent: %s%n", absolute.getParent());
        System.out.printf("Subpath(0, 2): %s%n", absolute.subpath(0, 2));

        // ls -la
        System.out.println();
        System.out.println("# ls -la");
        Files.walk(relative, 1).sorted((p1, p2) -> {
            return p1.getFileName().toString().compareTo(p2.getFileName().toString());
        }).forEach(path -> {
            try {
                System.out.println(toLine(path));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // File operations
        Path file = Paths.get("build.gradle");
        Path backup = Paths.get("build.gradle.backup");
        Path rename = Paths.get("build.gradle.backup.1");
        Files.copy(file, backup, StandardCopyOption.REPLACE_EXISTING);
        Files.move(backup, rename, StandardCopyOption.REPLACE_EXISTING);
        Files.delete(rename);

        // Read
        System.out.println("");
        System.out.println("# build.gradle");
        Files.readAllLines(file).stream().forEach(l -> {
            System.out.println(l);
        });

        //
        System.out.println("");
        System.out.println("# async with Future");
        {
            AsynchronousFileChannel channel = AsynchronousFileChannel.open(file, StandardOpenOption.READ);
            ByteBuffer buffer = ByteBuffer.allocate(100_000);
            Future<Integer> result = channel.read(buffer, 0);
            // ...
            Integer bytesRead = result.get();
            System.out.println(new String(buffer.array(), "utf-8"));
        }

        System.out.println("");
        System.out.println("# async with callback");
        {
            AsynchronousFileChannel channel = AsynchronousFileChannel.open(file, StandardOpenOption.READ);
            ByteBuffer buffer = ByteBuffer.allocate(100_000);
            channel.read(buffer, 0, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                public void completed(Integer result, ByteBuffer buffer) {
                    try {
                        System.out.println(new String(buffer.array(), "utf-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }

                public void failed(Throwable exception, ByteBuffer attachment) {
                    System.out.println(exception.getMessage());
                }
            });

            Thread.sleep(2000);
        }
    }

    private static String toLine(Path path) throws IOException  {
        Map<String, Object> attributtes = Files.readAttributes(path, "posix:*");
        PosixFileAttributes posixFileAttributes = Files.getFileAttributeView(path, PosixFileAttributeView.class).readAttributes();
        String type = (posixFileAttributes.isDirectory()) ? "d": "-";
        String permissions = PosixFilePermissions.toString(posixFileAttributes.permissions());
        String owner = posixFileAttributes.owner().getName();
        String group = posixFileAttributes.group().getName();
        long size = posixFileAttributes.size();
        ZonedDateTime date = posixFileAttributes.lastModifiedTime().toInstant().atZone(ZoneId.of("Europe/Madrid"));
        String lasModified = DateTimeFormatter.ofPattern("d MMM HH:mm").format(date);
        String name = path.getFileName().toString();
        return String.format("%s%s %16s %10s %5d %12s %s", type, permissions, owner, group, size, lasModified, name);
    }
}
