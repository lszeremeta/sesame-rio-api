/* 
 * Licensed to Aduna under one or more contributor license agreements.  
 * See the NOTICE.txt file distributed with this work for additional 
 * information regarding copyright ownership. 
 *
 * Aduna licenses this file to you under the terms of the Aduna BSD 
 * License (the "License"); you may not use this file except in compliance 
 * with the License. See the LICENSE.txt file distributed with this work 
 * for the full License.
 *
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or 
 * implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package org.openrdf.rio;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import info.aduna.lang.FileFormat;

import org.openrdf.model.Statement;

/**
 * Represents the concept of an RDF data serialization format. RDF formats are
 * identified by a {@link #getName() name} and can have one or more associated
 * MIME types, zero or more associated file extensions and can specify a
 * (default) character encoding. Some formats are able to encode context
 * information while other are not; this is indicated by the value of
 * {@link #supportsContexts}.
 * 
 * @author Arjohn Kampman
 */
public class RDFFormat extends FileFormat {

	/*-----------*
	 * Constants *
	 *-----------*/

	/**
	 * Indicates that calls to {@link RDFHandler#handleNamespace(String, String)}
	 * may be serialised when serializing to this format.
	 * 
	 * @since 2.7.0
	 */
	public static final boolean SUPPORTS_NAMESPACES = true;

	/**
	 * Indicates that all calls to
	 * {@link RDFHandler#handleNamespace(String, String)} will be ignored when
	 * serializing to this format.
	 * 
	 * @since 2.7.0
	 */
	public static final boolean NO_NAMESPACES = false;

	/**
	 * Indicates that the {@link Statement#getContext()} URI may be serialized
	 * for this format.
	 * 
	 * @since 2.7.0
	 */
	public static final boolean SUPPORTS_CONTEXTS = true;

	/**
	 * Indicates that the {@link Statement#getContext()} URI will NOT be
	 * serialized for this format.
	 * 
	 * @since 2.7.0
	 */
	public static final boolean NO_CONTEXTS = false;

	/**
	 * The <a href="http://www.w3.org/TR/rdf-syntax-grammar/">RDF/XML</a> file
	 * format.
	 * <p>
	 * Several file extensions are accepted for RDF/XML documents, including
	 * <code>.rdf</code>, <code>.rdfs</code> (for RDF Schema files),
	 * <code>.owl</code> (for OWL ontologies), and <code>.xml</code>. The media
	 * type is <code>application/rdf+xml</code>, but <code>application/xml</code>
	 * is also accepted. The character encoding is UTF-8.
	 * </p>
	 * 
	 * @see <a href="http://www.w3.org/TR/rdf-syntax-grammar/">RDF/XML Syntax
	 *      Specification (Revised)</a>
	 */
	public static final RDFFormat RDFXML = new RDFFormat("RDF/XML", Arrays.asList("application/rdf+xml",
			"application/xml"), Charset.forName("UTF-8"), Arrays.asList("rdf", "rdfs", "owl", "xml"),
			SUPPORTS_NAMESPACES, NO_CONTEXTS);

	/**
	 * The <a href="http://www.w3.org/TR/rdf-testcases/#ntriples">N-Triples</a>
	 * file format.
	 * <p>
	 * The file extension <code>.nt</code> is recommend for N-Triples documents.
	 * The media type is <code>text/plain</code> and encoding is in 7-bit
	 * US-ASCII.
	 * </p>
	 * 
	 * @see <a href="http://www.w3.org/TR/rdf-testcases/#ntriples">N-Triples</a>
	 */
	public static final RDFFormat NTRIPLES = new RDFFormat("N-Triples", "text/plain",
			Charset.forName("US-ASCII"), "nt", NO_NAMESPACES, NO_CONTEXTS);

	/**
	 * The <a href="http://www.w3.org/TeamSubmission/turtle/">Turtle</a> file
	 * format.
	 * <p>
	 * The file extension <code>.ttl</code> is recommend for Turtle documents.
	 * The media type is <code>text/turtle</code>, but
	 * <code>application/x-turtle</code> is also accepted. Character encoding is
	 * UTF-8.
	 * </p>
	 * 
	 * @see <a href="http://www.w3.org/TeamSubmission/turtle/">Turtle - Terse RDF
	 *      Triple Language</a>
	 */
	public static final RDFFormat TURTLE = new RDFFormat("Turtle", Arrays.asList("text/turtle",
			"application/x-turtle"), Charset.forName("UTF-8"), Arrays.asList("ttl"), SUPPORTS_NAMESPACES,
			NO_CONTEXTS);

	/**
	 * The <a href="https://www.researchgate.net/publication/309695477_RDF_Data_in_Property_Graph_Model>canonical YARS</a> file
	 * format.
	 * <p>
	 * The file extension <code>.yarsc</code> is recommend for YARS documents.
	 * The media type is <code>text/yarsc</code>, but
	 * <code>application/x-yarsc</code> is also accepted. Character encoding is
	 * UTF-8.
	 * </p>
	 *
	 * @see <a href="https://www.researchgate.net/publication/309695477_RDF_Data_in_Property_Graph_Model">YARS - Yet Another RDF Serialization</a>
	 */
	public static final RDFFormat YARS = new RDFFormat("YARS", Arrays.asList("text/yarsc",
			"application/x-yarsc"), Charset.forName("UTF-8"), Arrays.asList("yarsc"), NO_NAMESPACES,
			NO_CONTEXTS);

	/**
	 * The <a href="http://www.w3.org/TeamSubmission/n3/">N3/Notation3</a> file
	 * format.
	 * <p>
	 * The file extension <code>.n3</code> is recommended for N3 documents. The
	 * media type is <code>text/n3</code>, but <code>text/rdf+n3</code> is also
	 * accepted. Character encoding is UTF-8.
	 * </p>
	 * 
	 * @see <a href="http://www.w3.org/TeamSubmission/n3/">Notation3 (N3): A
	 *      readable RDF syntax</a>
	 */
	public static final RDFFormat N3 = new RDFFormat("N3", Arrays.asList("text/n3", "text/rdf+n3"),
			Charset.forName("UTF-8"), Arrays.asList("n3"), SUPPORTS_NAMESPACES, NO_CONTEXTS);

	/**
	 * The <a href="http://swdev.nokia.com/trix/">TriX</a> file format, an
	 * XML-based RDF serialization format that supports recording of named
	 * graphs.
	 * <p>
	 * The file extension <code>.xml</code> is recommended for TriX documents,
	 * <code>.trix</code> is also accepted. The media type is
	 * <code>application/trix</code> and the encoding is UTF-8.
	 * </p>
	 * 
	 * @see <a href="http://swdev.nokia.com/trix/">TriX: RDF Triples in XML</a>
	 */
	public static final RDFFormat TRIX = new RDFFormat("TriX", "application/trix", Charset.forName("UTF-8"),
			Arrays.asList("xml", "trix"), SUPPORTS_NAMESPACES, SUPPORTS_CONTEXTS);

	/**
	 * The <a
	 * href="http://www.wiwiss.fu-berlin.de/suhl/bizer/TriG/Spec/">TriG</a> file
	 * format, a Turtle-based RDF serialization format that supports recording of
	 * named graphs.
	 * <p>
	 * The file extension <code>.trig</code> is recommend for TriG documents. The
	 * media type is <code>application/x-trig</code> and the encoding is UTF-8.
	 * </p>
	 * 
	 * @see <a href="http://www.wiwiss.fu-berlin.de/suhl/bizer/TriG/Spec/">The
	 *      TriG Syntax</a>
	 */
	public static final RDFFormat TRIG = new RDFFormat("TriG", "application/x-trig", Charset.forName("UTF-8"),
			"trig", SUPPORTS_NAMESPACES, SUPPORTS_CONTEXTS);

	/**
	 * A binary RDF format.
	 * <p>
	 * The file extension <code>.brf</code> is recommend for binary RDF
	 * documents. The media type is <code>application/x-binary-rdf</code>.
	 * </p>
	 * 
	 * @see <a
	 *      href="http://rivuli-development.com/2011/11/binary-rdf-in-sesame/">Binary
	 *      RDF in Sesame</a>
	 */
	public static final RDFFormat BINARY = new RDFFormat("BinaryRDF", "application/x-binary-rdf", null, "brf",
			SUPPORTS_NAMESPACES, SUPPORTS_CONTEXTS);

	/**
	 * The <a href="http://sw.deri.org/2008/07/n-quads/">N-Quads</a> file format,
	 * an RDF serialization format that supports recording of named graphs.
	 * <p>
	 * The file extension <code>.nq</code> is recommended for N-Quads documents.
	 * The media type is <code>text/x-nquads</code> and the encoding is 7-bit
	 * US-ASCII.
	 * </p>
	 * 
	 * @see <a href="http://sw.deri.org/2008/07/n-quads/">N-Quads: Extending
	 *      N-Triples with Context</a>
	 * @since 2.6.6
	 */
	public static final RDFFormat NQUADS = new RDFFormat("N-Quads", "text/x-nquads",
			Charset.forName("US-ASCII"), "nq", NO_NAMESPACES, SUPPORTS_CONTEXTS);

	/**
	 * The <a href="http://json-ld.org/spec/latest/json-ld-syntax/">JSON-LD</a>
	 * file format, an RDF serialization format that supports recording of named
	 * graphs.
	 * <p>
	 * The file extension <code>.jsonld</code> is recommended for JSON-LD
	 * documents. The media type is <code>application/ld+json</code> and the
	 * encoding is UTF-8.
	 * </p>
	 * 
	 * @see <a href="http://json-ld.org/spec/latest/json-ld-syntax/">JSON-LD
	 *      1.0</a>
	 * @since 2.7.0
	 */
	public static final RDFFormat JSONLD = new RDFFormat("JSON-LD", "application/ld+json",
			Charset.forName("UTF-8"), "jsonld", SUPPORTS_NAMESPACES, SUPPORTS_CONTEXTS);

	/**
	 * The Talis <a
	 * href="http://dvcs.w3.org/hg/rdf/raw-file/default/rdf-json/index.html"
	 * >RDF/JSON</a> file format, an RDF serialization format that supports
	 * recording of named graphs.
	 * <p>
	 * The file extension <code>.rj</code> is recommended for RDF/JSON documents.
	 * The media type is <code>application/rdf+json</code> and the encoding is
	 * UTF-8.
	 * </p>
	 * 
	 * @see <a
	 *      href="http://dvcs.w3.org/hg/rdf/raw-file/default/rdf-json/index.html">RDF
	 *      1.1 JSON Serialisation (RDF/JSON)</a>
	 * @since 2.7.0
	 */
	public static final RDFFormat RDFJSON = new RDFFormat("RDF/JSON", "application/rdf+json",
			Charset.forName("UTF-8"), "rj", NO_NAMESPACES, SUPPORTS_CONTEXTS);

	/**
	 * The <a href="http://www.w3.org/TR/xhtml-rdfa/">RDFa</a> file format, an
	 * RDF serialization format.
	 * <p>
	 * The file extension <code>.xhtml</code> is recommended for RDFa documents.
	 * The preferred media type is <code>application/xhtml+xml</code> and the
	 * encoding is UTF-8.
	 * </p>
	 * 
	 * @see <a href="http://www.w3.org/TR/xhtml-rdfa/">XHTML+RDFa 1.1</a>
	 * @since 2.7.0
	 */
	public static final RDFFormat RDFA = new RDFFormat("RDFa", Arrays.asList("application/xhtml+xml",
			"application/html", "text/html"), Charset.forName("UTF-8"), Arrays.asList("xhtml", "html"),
			SUPPORTS_NAMESPACES, NO_CONTEXTS);

	/*------------------*
	 * Static variables *
	 *------------------*/

	/**
	 * List of known RDF file formats.
	 */
	// FIXME: remove/deprecate this list?
	private static List<RDFFormat> RDF_FORMATS = new ArrayList<RDFFormat>(12);

	/*--------------------*
	 * Static initializer *
	 *--------------------*/

	static {
		// FIXME: base available format on available parsers/writers?
		register(RDFXML);
		register(NTRIPLES);
		register(TURTLE);
		register(N3);
		register(TRIX);
		register(TRIG);
		register(BINARY);
		register(NQUADS);
		register(JSONLD);
		register(RDFJSON);
		register(RDFA);
	}

	/*----------------*
	 * Static methods *
	 *----------------*/

	/**
	 * Returns all known/registered RDF formats.
	 */
	public static Collection<RDFFormat> values() {
		return Collections.unmodifiableList(RDF_FORMATS);
	}

	/**
	 * Registers the specified RDF file format.
	 * 
	 * @param name
	 *        The name of the RDF file format, e.g. "RDF/XML".
	 * @param mimeType
	 *        The MIME type of the RDF file format, e.g.
	 *        <tt>application/rdf+xml</tt> for the RDF/XML file format.
	 * @param fileExt
	 *        The (default) file extension for the RDF file format, e.g.
	 *        <tt>rdf</tt> for RDF/XML files.
	 */
	public static RDFFormat register(String name, String mimeType, String fileExt, Charset charset) {
		RDFFormat rdfFormat = new RDFFormat(name, mimeType, charset, fileExt, false, false);
		register(rdfFormat);
		return rdfFormat;
	}

	/**
	 * Registers the specified RDF file format.
	 */
	public static void register(RDFFormat rdfFormat) {
		RDF_FORMATS.add(rdfFormat);
	}

	/**
	 * Tries to determine the appropriate RDF file format based on the a MIME
	 * type that describes the content type.
	 * 
	 * @param mimeType
	 *        A MIME type, e.g. "application/rdf+xml".
	 * @return An RDFFormat object if the MIME type was recognized, or
	 *         <tt>null</tt> otherwise.
	 * @see #forMIMEType(String,RDFFormat)
	 * @see #getMIMETypes()
	 */
	public static RDFFormat forMIMEType(String mimeType) {
		return forMIMEType(mimeType, null);
	}

	/**
	 * Tries to determine the appropriate RDF file format based on the a MIME
	 * type that describes the content type. The supplied fallback format will be
	 * returned when the MIME type was not recognized.
	 * 
	 * @param mimeType
	 *        A file name.
	 * @return An RDFFormat that matches the MIME type, or the fallback format if
	 *         the extension was not recognized.
	 * @see #forMIMEType(String)
	 * @see #getMIMETypes()
	 */
	public static RDFFormat forMIMEType(String mimeType, RDFFormat fallback) {
		return matchMIMEType(mimeType, RDF_FORMATS, fallback);
	}

	/**
	 * Tries to determine the appropriate RDF file format based on the extension
	 * of a file name.
	 * 
	 * @param fileName
	 *        A file name.
	 * @return An RDFFormat object if the file extension was recognized, or
	 *         <tt>null</tt> otherwise.
	 * @see #forFileName(String,RDFFormat)
	 * @see #getFileExtensions()
	 */
	public static RDFFormat forFileName(String fileName) {
		return forFileName(fileName, null);
	}

	/**
	 * Tries to determine the appropriate RDF file format based on the extension
	 * of a file name. The supplied fallback format will be returned when the
	 * file name extension was not recognized.
	 * 
	 * @param fileName
	 *        A file name.
	 * @return An RDFFormat that matches the file name extension, or the fallback
	 *         format if the extension was not recognized.
	 * @see #forFileName(String)
	 * @see #getFileExtensions()
	 */
	public static RDFFormat forFileName(String fileName, RDFFormat fallback) {
		return matchFileName(fileName, RDF_FORMATS, fallback);
	}

	/**
	 * Returns the RDF format whose name matches the specified name.
	 * 
	 * @param formatName
	 *        A format name.
	 * @return The RDF format whose name matches the specified name, or
	 *         <tt>null</tt> if there is no such format.
	 */
	public static RDFFormat valueOf(String formatName) {
		for (RDFFormat format : RDF_FORMATS) {
			if (format.getName().equalsIgnoreCase(formatName)) {
				return format;
			}
		}

		return null;
	}

	public static List<String> getAcceptParams(Iterable<RDFFormat> rdfFormats, boolean requireContext,
			RDFFormat preferredFormat)
	{
		List<String> acceptParams = new ArrayList<String>();

		for (RDFFormat format : rdfFormats) {
			// Determine a q-value that reflects the necessity of context
			// support and the user specified preference
			int qValue = 10;

			if (requireContext && !format.supportsContexts()) {
				// Prefer context-supporting formats over pure triple-formats
				qValue -= 5;
			}

			if (preferredFormat != null && !preferredFormat.equals(format)) {
				// Prefer specified format over other formats
				qValue -= 2;
			}

			if (!format.supportsNamespaces()) {
				// We like reusing namespace prefixes
				qValue -= 1;
			}

			for (String mimeType : format.getMIMETypes()) {
				String acceptParam = mimeType;

				if (qValue < 10) {
					acceptParam += ";q=0." + qValue;
				}

				acceptParams.add(acceptParam);
			}
		}

		return acceptParams;
	}

	/*-----------*
	 * Variables *
	 *-----------*/

	/**
	 * Flag indicating whether the RDFFormat can encode namespace information.
	 */
	private boolean supportsNamespaces = false;

	/**
	 * Flag indicating whether the RDFFormat can encode context information.
	 */
	private boolean supportsContexts = false;

	/*--------------*
	 * Constructors *
	 *--------------*/

	/**
	 * Creates a new RDFFormat object.
	 * 
	 * @param name
	 *        The name of the RDF file format, e.g. "RDF/XML".
	 * @param mimeType
	 *        The MIME type of the RDF file format, e.g.
	 *        <tt>application/rdf+xml</tt> for the RDF/XML file format.
	 * @param charset
	 *        The default character encoding of the RDF file format. Specify
	 *        <tt>null</tt> if not applicable.
	 * @param fileExtension
	 *        The (default) file extension for the RDF file format, e.g.
	 *        <tt>rdf</tt> for RDF/XML files.
	 */
	public RDFFormat(String name, String mimeType, Charset charset, String fileExtension,
			boolean supportsNamespaces, boolean supportsContexts)
	{
		this(name, Arrays.asList(mimeType), charset, Arrays.asList(fileExtension), supportsNamespaces,
				supportsContexts);
	}

	/**
	 * Creates a new RDFFormat object.
	 * 
	 * @param name
	 *        The name of the RDF file format, e.g. "RDF/XML".
	 * @param mimeType
	 *        The MIME type of the RDF file format, e.g.
	 *        <tt>application/rdf+xml</tt> for the RDF/XML file format.
	 * @param charset
	 *        The default character encoding of the RDF file format. Specify
	 *        <tt>null</tt> if not applicable.
	 * @param fileExtensions
	 *        The RDF format's file extensions, e.g. <tt>rdf</tt> for RDF/XML
	 *        files. The first item in the list is interpreted as the default
	 *        file extension for the format.
	 */
	public RDFFormat(String name, String mimeType, Charset charset, Collection<String> fileExtensions,
			boolean supportsNamespaces, boolean supportsContexts)
	{
		this(name, Arrays.asList(mimeType), charset, fileExtensions, supportsNamespaces, supportsContexts);
	}

	/**
	 * Creates a new RDFFormat object.
	 * 
	 * @param name
	 *        The name of the RDF file format, e.g. "RDF/XML".
	 * @param mimeTypes
	 *        The MIME types of the RDF file format, e.g.
	 *        <tt>application/rdf+xml</tt> for the RDF/XML file format. The first
	 *        item in the list is interpreted as the default MIME type for the
	 *        format.
	 * @param charset
	 *        The default character encoding of the RDF file format. Specify
	 *        <tt>null</tt> if not applicable.
	 * @param fileExtensions
	 *        The RDF format's file extensions, e.g. <tt>rdf</tt> for RDF/XML
	 *        files. The first item in the list is interpreted as the default
	 *        file extension for the format.
	 */
	public RDFFormat(String name, Collection<String> mimeTypes, Charset charset,
			Collection<String> fileExtensions, boolean supportsNamespaces, boolean supportsContexts)
	{
		super(name, mimeTypes, charset, fileExtensions);

		this.supportsNamespaces = supportsNamespaces;
		this.supportsContexts = supportsContexts;
	}

	/*---------*
	 * Methods *
	 *---------*/

	/*
	 * Return <tt>true</tt> if the RDFFormat supports the encoding of
	 * namespace/prefix information.
	 */
	public boolean supportsNamespaces() {
		return supportsNamespaces;
	}

	/*
	 * Return <tt>true</tt> if the RDFFormat supports the encoding of
	 * contexts/named graphs.
	 */
	public boolean supportsContexts() {
		return supportsContexts;
	}
}
