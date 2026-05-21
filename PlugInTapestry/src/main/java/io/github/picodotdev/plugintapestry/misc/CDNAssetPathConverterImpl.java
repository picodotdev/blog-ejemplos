package io.github.picodotdev.plugintapestry.misc;

import java.util.Map;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.apache.tapestry5.services.AssetPathConverter;

import io.github.picodotdev.plugintapestry.services.AppModule;

public class CDNAssetPathConverterImpl implements AssetPathConverter {

	private String protocol;
	private String host;
	private String port;
	private String path;

	private Map<String, String> resources = CollectionFactory.newMap();

	public CDNAssetPathConverterImpl(@Inject @Symbol(AppModule.CDN_DOMAIN_PROTOCOL) String protocol,
			@Inject @Symbol(AppModule.CDN_DOMAIN_HOST) String host,
			@Inject @Symbol(AppModule.CDN_DOMAIN_PORT) String port,
			@Inject @Symbol(AppModule.CDN_DOMAIN_PATH) String path) {

		this.protocol = protocol;
		this.host = host;
		this.port = (port == null || port.equals("")) ? "" : ":" + port;
		this.path = (path == null || path.equals("")) ? "" : "/" + path;
	}

	@Override
	public String convertAssetPath(String assetPath) {
		if (resources.containsKey(assetPath)) {
			return resources.get(assetPath);
		}
		String result = String.format("%s://%s%s%s%s", protocol, host, port, path, assetPath);
		resources.put(assetPath, result);
		return result;
	}

	@Override
	public boolean isInvariant() {
		return true;
	}
}