package io.github.picodotdev.blogbitix.dockerswarm;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.net.Inet4Address;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class HostInfoContributor implements InfoContributor {

    @Override
    public void contribute(Info.Builder builder) {
        Map<String, Object> details = new HashMap<>();
        try {
            details.put("ip", Inet4Address.getLocalHost().getHostAddress());
            details.put("host", Inet4Address.getLocalHost().getHostName());

            Path path = FileSystems.getDefault().getPath("/run/secrets/");
            path.forEach((Path file) -> {
                try {
                    String content = Files.lines(file).collect(Collectors.joining("\n"));
                    details.put(file.getFileName().toString(), content);
                } catch (Exception e) {
                }

            });
        } catch (Exception e) {
            details.put("ip", "unknown");
            details.put("host", "unknown");
        }
        builder.withDetails(details);
    }
}
