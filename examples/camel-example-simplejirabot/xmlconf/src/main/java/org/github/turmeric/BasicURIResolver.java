package org.github.turmeric;

import java.net.URI;
import java.net.URL;

import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamSource;

public class BasicURIResolver implements URIResolver {

	public BasicURIResolver() {
	}

	public Source resolve(String arg0, String arg1) throws TransformerException {
		try {
		return new StreamSource(new URL(arg0).openStream());
		} catch (Exception ex) {
			return null;
		}
	}

}
