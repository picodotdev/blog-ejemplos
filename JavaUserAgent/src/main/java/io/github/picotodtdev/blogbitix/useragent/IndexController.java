package io.github.picotodtdev.blogbitix.useragent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blueconic.browscap.Capabilities;
import com.blueconic.browscap.UserAgentParser;
import com.blueconic.browscap.UserAgentService;

@Controller
public class IndexController {

    @Autowired
    private UserAgentParser userAgentParser;

    @GetMapping("/")
    @ResponseBody
    public String index(@RequestHeader("User-Agent") String userAgent) {
	Capabilities capabilities = userAgentParser.parse(userAgent);

	String browser = capabilities.getBrowser();
	String browserType = capabilities.getBrowserType();
	String browserMajorVersion = capabilities.getBrowserMajorVersion();
	String deviceType = capabilities.getDeviceType();
	String platform = capabilities.getPlatform();
	String platformVersion = capabilities.getPlatformVersion();

	return String.format("User-Agent: %s\nBrowser: %s, Type: %s, Version: %s, Device: %s, Platform: %s, PlatformVersion: %s", userAgent, browser, browserType, browserMajorVersion, deviceType, platform, platformVersion);
    }
}
