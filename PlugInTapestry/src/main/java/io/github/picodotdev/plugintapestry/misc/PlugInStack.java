package io.github.picodotdev.plugintapestry.misc;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.internal.services.UrlAsset;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.javascript.JavaScriptAggregationStrategy;
import org.apache.tapestry5.services.javascript.JavaScriptStack;
import org.apache.tapestry5.services.javascript.StylesheetLink;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlugInStack implements JavaScriptStack {

	private final AssetSource assetSource;

	public PlugInStack(final AssetSource assetSource) {
		this.assetSource = assetSource;
	}

	@Override
	public String getInitialization() {
		return null;
	}

	@Override
	public List<String> getModules() {
		return Collections.emptyList();
	}

	@Override
	public List<Asset> getJavaScriptLibraries() {
		List<Asset> r = new ArrayList<>();
		r.add(assetSource.getClasspathAsset("META-INF/assets/tapestry5/bootstrap/js/dropdown.js"));
		r.add(new UrlAsset("https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.11.2/js/bootstrap-select.min.js", null));
		return r;
	}
	
	@Override
	public JavaScriptAggregationStrategy getJavaScriptAggregationStrategy() {
		return JavaScriptAggregationStrategy.COMBINE_AND_MINIMIZE;
	}

	@Override
	public List<StylesheetLink> getStylesheets() {
		List<StylesheetLink> r = new ArrayList<>();
		r.add(new StylesheetLink("https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.11.2/css/bootstrap-select.min.css"));
		r.add(new StylesheetLink(assetSource.getContextAsset("css/app.css", null)));
		return r;
	}

	@Override
	public List<String> getStacks() {
		return Collections.emptyList();
	}
}