package io.github.picodotdev.blogbitix.dockerswarm;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.Inet4Address;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitOption;
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
            details.put("hostname", Inet4Address.getLocalHost().getHostName());

            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            RequestContextHolder.getRequestAttributes();
            HttpServletRequest servletRequest = ((ServletRequestAttributes) requestAttributes).getRequest();
            details.put("Host", servletRequest.getHeader("Host"));
            details.put("X-Real-IP", servletRequest.getHeader("X-Real-IP"));

            File[] secrets = FileSystems.getDefault().getPath("/run/secrets/").toFile().listFiles();
            for(File file : secrets) {
                try {
                    String content = Files.lines(file.toPath()).collect(Collectors.joining("\n"));
                    details.put(file.getName().toString(), content);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        builder.withDetails(details);
    }
}
