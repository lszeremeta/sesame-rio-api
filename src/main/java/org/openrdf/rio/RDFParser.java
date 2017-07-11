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

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Collection;

import org.openrdf.model.ValueFactory;
import org.openrdf.rio.helpers.BasicParserSettings;

/**
 * An interface for RDF parsers. All implementing classes should define a public
 * zero-argument constructor to allow them to be created through reflection.
 */
public interface RDFParser {

	/*-----------*
	 * Constants *
	 *-----------*/

	/**
	 * @deprecated These settings are not recognised and will be removed in a
	 *             future version. Use
	 *             {@link BasicParserSettings#FAIL_ON_UNKNOWN_DATATYPES}
	 *             {@link BasicParserSettings#NORMALIZE_DATATYPE_VALUES} and
	 *             {@link BasicParserSettings#VERIFY_DATATYPE_VALUES} instead.
	 */
	@Deprecated
	public enum DatatypeHandling {
		/**
		 * Indicates that datatype semantics should be ignored.
		 */
		IGNORE,

		/**
		 * Indicates that values of datatyped literals should be verified.
		 */
		VERIFY,

		/**
		 * Indicates that values of datatyped literals should be normalized to
		 * their canonical representation.
		 */
		NORMALIZE
	}

	/*---------*
	 * Methods *
	 *---------*/

	/**
	 * Gets the RDF format that this parser can parse.
	 */
	public RDFFormat getRDFFormat();

	/**
	 * Sets the ValueFactory that the parser will use to create Value objects for
	 * the parsed RDF data.
	 * 
	 * @param valueFactory
	 *        The value factory that the parser should use.
	 */
	public void setValueFactory(ValueFactory valueFactory);

	/**
	 * Sets the RDFHandler that will handle the parsed RDF data.
	 */
	public void setRDFHandler(RDFHandler handler);

	/**
	 * Sets the ParseErrorListener that will be notified of any errors that this
	 * parser finds during parsing.
	 */
	public void setParseErrorListener(ParseErrorListener el);

	/**
	 * Sets the ParseLocationListener that will be notified of the parser's
	 * progress during the parse process.
	 */
	public void setParseLocationListener(ParseLocationListener ll);

	/**
	 * Sets all supplied parser configuration options.
	 * 
	 * @param config
	 *        a parser configuration object.
	 */
	public void setParserConfig(ParserConfig config);

	/**
	 * Retrieves the current parser configuration as a single object.
	 * 
	 * @return a parser configuration object representing the current
	 *         configuration of the parser.
	 */
	public ParserConfig getParserConfig();

	/**
	 * @return A collection of {@link RioSetting}s that are supported by this
	 *         RDFParser.
	 * @since 2.7.0
	 */
	public Collection<RioSetting<?>> getSupportedSettings();

	/**
	 * Sets whether the parser should verify the data it parses (default value is
	 * <tt>true</tt>).
	 * 
	 * @deprecated Since 2.7.0. Use {@link #getParserConfig()} with
	 *             {@link BasicParserSettings#FAIL_ON_UNKNOWN_DATATYPES},
	 *             {@link BasicParserSettings#VERIFY_DATATYPE_VALUES}, and/or
	 *             {@link BasicParserSettings#NORMALIZE_DATATYPE_VALUES} instead.
	 */
	@Deprecated
	public void setVerifyData(boolean verifyData);

	/**
	 * Set whether the parser should preserve bnode identifiers specified in the
	 * source (default is <tt>false</tt>).
	 */
	public void setPreserveBNodeIDs(boolean preserveBNodeIDs);

	/**
	 * Sets whether the parser should stop immediately if it finds an error in
	 * the data (default value is <tt>true</tt>).
	 * 
	 * @deprecated Since 2.7.0. Use {@link #getParserConfig()} with
	 *             {@link ParserConfig#addNonFatalError(RioSetting)} to select
	 *             which errors will not always fail the parse prematurely.
	 */
	@Deprecated
	public void setStopAtFirstError(boolean stopAtFirstError);

	/**
	 * Sets the datatype handling mode. There are three modes for handling
	 * datatyped literals: <em>ignore</em>, <em>verify</em>and <em>normalize</em>
	 * . If set to <em>ignore</em>, no special action will be taken to handle
	 * datatyped literals. If set to <em>verify</em>, any literals with known
	 * (XML Schema built-in) datatypes are checked to see if their values are
	 * valid. If set to <em>normalize</em>, the literal values are not only
	 * checked, but also normalized to their canonical representation. The
	 * default value is <em>verify</em>.
	 * 
	 * @param datatypeHandling
	 *        A datatype handling option.
	 * @deprecated Since 2.7.0. Use {@link #getParserConfig()} with
	 *             {@link BasicParserSettings#FAIL_ON_UNKNOWN_DATATYPES},
	 *             {@link BasicParserSettings#VERIFY_DATATYPE_VALUES}, and/or
	 *             {@link BasicParserSettings#NORMALIZE_DATATYPE_VALUES} instead.
	 */
	@Deprecated
	public void setDatatypeHandling(DatatypeHandling datatypeHandling);

	/**
	 * Parses the data from the supplied InputStream, using the supplied baseURI
	 * to resolve any relative URI references.
	 * 
	 * @param in
	 *        The InputStream from which to read the data.
	 * @param baseURI
	 *        The URI associated with the data in the InputStream.
	 * @throws IOException
	 *         If an I/O error occurred while data was read from the InputStream.
	 * @throws RDFParseException
	 *         If the parser has found an unrecoverable parse error.
	 * @throws RDFHandlerException
	 *         If the configured statement handler has encountered an
	 *         unrecoverable error.
	 */
	public void parse(InputStream in, String baseURI)
		throws IOException, RDFParseException, RDFHandlerException;

	/**
	 * Parses the data from the supplied Reader, using the supplied baseURI to
	 * resolve any relative URI references.
	 * 
	 * @param reader
	 *        The Reader from which to read the data.
	 * @param baseURI
	 *        The URI associated with the data in the InputStream.
	 * @throws IOException
	 *         If an I/O error occurred while data was read from the InputStream.
	 * @throws RDFParseException
	 *         If the parser has found an unrecoverable parse error.
	 * @throws RDFHandlerException
	 *         If the configured statement handler has encountered an
	 *         unrecoverable error.
	 */
	public void parse(Reader reader, String baseURI)
		throws IOException, RDFParseException, RDFHandlerException;
}
