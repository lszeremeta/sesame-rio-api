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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

import org.openrdf.model.Model;
import org.openrdf.model.Namespace;
import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.ValueFactory;
import org.openrdf.model.impl.LinkedHashModel;
import org.openrdf.model.impl.ValueFactoryImpl;
import org.openrdf.rio.helpers.ContextStatementCollector;
import org.openrdf.rio.helpers.ParseErrorLogger;

/**
 * Static methods for parsing and writing RDF for all available syntaxes.
 * <p>
 * It includes methods for searching for {@link RDFFormat}s based on MIME types
 * and file extensions, creating {@link RDFParser}s and {@link RDFWriter}s, and
 * directly parsing and writing.
 * 
 * @author Arjohn Kampman
 * @author Peter Ansell
 */
public class Rio {

	/**
	 * Tries to match a MIME type against the list of RDF formats that can be
	 * parsed.
	 * 
	 * @param mimeType
	 *        A MIME type, e.g. "application/rdf+xml".
	 * @return An RDFFormat object if a match was found, or <tt>null</tt>
	 *         otherwise.
	 * @see #getParserFormatForMIMEType(String, RDFFormat)
	 */
	public static RDFFormat getParserFormatForMIMEType(String mimeType) {
		return getParserFormatForMIMEType(mimeType, null);
	}

	/**
	 * Tries to match a MIME type against the list of RDF formats that can be
	 * parsed. This method calls
	 * {@link RDFFormat#matchMIMEType(String, Iterable)} with the specified MIME
	 * type, the keys of {@link RDFParserRegistry#getInstance()} and the fallback
	 * format as parameters.
	 * 
	 * @param mimeType
	 *        A MIME type, e.g. "application/rdf+xml".
	 * @param fallback
	 *        The format that will be returned if no match was found.
	 * @return The matching RDFFormat, or <tt>fallback</tt> if no match was
	 *         found.
	 */
	public static RDFFormat getParserFormatForMIMEType(String mimeType, RDFFormat fallback) {
		return RDFFormat.matchMIMEType(mimeType, RDFParserRegistry.getInstance().getKeys(), fallback);
	}

	/**
	 * Tries to match the extension of a file name against the list of RDF
	 * formats that can be parsed.
	 * 
	 * @param fileName
	 *        A file name.
	 * @return An RDFFormat object if a match was found, or <tt>null</tt>
	 *         otherwise.
	 * @see #getParserFormatForFileName(String, RDFFormat)
	 */
	public static RDFFormat getParserFormatForFileName(String fileName) {
		return getParserFormatForFileName(fileName, null);
	}

	/**
	 * Tries to match the extension of a file name against the list of RDF
	 * formats that can be parsed. This method calls
	 * {@link RDFFormat#matchFileName(String, Iterable, info.aduna.lang.FileFormat)}
	 * with the specified MIME type, the keys of
	 * {@link RDFParserRegistry#getInstance()} and the fallback format as
	 * parameters.
	 * 
	 * @param fileName
	 *        A file name.
	 * @param fallback
	 *        The format that will be returned if no match was found.
	 * @return The matching RDFFormat, or <tt>fallback</tt> if no match was
	 *         found.
	 */
	public static RDFFormat getParserFormatForFileName(String fileName, RDFFormat fallback) {
		return RDFFormat.matchFileName(fileName, RDFParserRegistry.getInstance().getKeys(), fallback);
	}

	/**
	 * Tries to match a MIME type against the list of RDF formats that can be
	 * written.
	 * 
	 * @param mimeType
	 *        A MIME type, e.g. "application/rdf+xml".
	 * @return An RDFFormat object if a match was found, or <tt>null</tt>
	 *         otherwise.
	 * @see #getWriterFormatForMIMEType(String, RDFFormat)
	 */
	public static RDFFormat getWriterFormatForMIMEType(String mimeType) {
		return getWriterFormatForMIMEType(mimeType, null);
	}

	/**
	 * Tries to match a MIME type against the list of RDF formats that can be
	 * written. This method calls
	 * {@link RDFFormat#matchMIMEType(String, Iterable, info.aduna.lang.FileFormat)}
	 * with the specified MIME type, the keys of
	 * {@link RDFWriterRegistry#getInstance()} and the fallback format as
	 * parameters.
	 * 
	 * @param mimeType
	 *        A MIME type, e.g. "application/rdf+xml".
	 * @param fallback
	 *        The format that will be returned if no match was found.
	 * @return The matching RDFFormat, or <tt>fallback</tt> if no match was
	 *         found.
	 */
	public static RDFFormat getWriterFormatForMIMEType(String mimeType, RDFFormat fallback) {
		return RDFFormat.matchMIMEType(mimeType, RDFWriterRegistry.getInstance().getKeys(), fallback);
	}

	/**
	 * Tries to match the extension of a file name against the list of RDF
	 * formats that can be written.
	 * 
	 * @param fileName
	 *        A file name.
	 * @return An RDFFormat object if a match was found, or <tt>null</tt>
	 *         otherwise.
	 * @see #getWriterFormatForFileName(String, RDFFormat)
	 */
	public static RDFFormat getWriterFormatForFileName(String fileName) {
		return getWriterFormatForFileName(fileName, null);
	}

	/**
	 * Tries to match the extension of a file name against the list of RDF
	 * formats that can be written. This method calls
	 * {@link RDFFormat#matchFileName(String, Iterable, info.aduna.lang.FileFormat)}
	 * with the specified MIME type, the keys of
	 * {@link RDFWriterRegistry#getInstance()} and the fallback format as
	 * parameters.
	 * 
	 * @param fileName
	 *        A file name.
	 * @param fallback
	 *        The format that will be returned if no match was found.
	 * @return The matching RDFFormat, or <tt>fallback</tt> if no match was
	 *         found.
	 */
	public static RDFFormat getWriterFormatForFileName(String fileName, RDFFormat fallback) {
		return RDFFormat.matchFileName(fileName, RDFWriterRegistry.getInstance().getKeys(), fallback);
	}

	/**
	 * Convenience methods for creating RDFParser objects. This method uses the
	 * registry returned by {@link RDFParserRegistry#getInstance()} to get a
	 * factory for the specified format and uses this factory to create the
	 * appropriate parser.
	 * 
	 * @throws UnsupportedRDFormatException
	 *         If no parser is available for the specified RDF format.
	 */
	public static RDFParser createParser(RDFFormat format)
		throws UnsupportedRDFormatException
	{
		RDFParserFactory factory = RDFParserRegistry.getInstance().get(format);

		if (factory != null) {
			return factory.getParser();
		}

		throw new UnsupportedRDFormatException("No parser factory available for RDF format " + format);
	}

	/**
	 * Convenience methods for creating RDFParser objects that use the specified
	 * ValueFactory to create RDF model objects.
	 * 
	 * @throws UnsupportedRDFormatException
	 *         If no parser is available for the specified RDF format.
	 * @see #createParser(RDFFormat)
	 * @see RDFParser#setValueFactory(ValueFactory)
	 */
	public static RDFParser createParser(RDFFormat format, ValueFactory valueFactory)
		throws UnsupportedRDFormatException
	{
		RDFParser rdfParser = createParser(format);
		rdfParser.setValueFactory(valueFactory);
		return rdfParser;
	}

	/**
	 * Convenience methods for creating RDFWriter objects. This method uses the
	 * registry returned by {@link RDFWriterRegistry#getInstance()} to get a
	 * factory for the specified format and uses this factory to create the
	 * appropriate writer.
	 * 
	 * @throws UnsupportedRDFormatException
	 *         If no writer is available for the specified RDF format.
	 */
	public static RDFWriter createWriter(RDFFormat format, OutputStream out)
		throws UnsupportedRDFormatException
	{
		RDFWriterFactory factory = RDFWriterRegistry.getInstance().get(format);

		if (factory != null) {
			return factory.getWriter(out);
		}

		throw new UnsupportedRDFormatException("No writer factory available for RDF format " + format);
	}

	/**
	 * Convenience methods for creating RDFWriter objects. This method uses the
	 * registry returned by {@link RDFWriterRegistry#getInstance()} to get a
	 * factory for the specified format and uses this factory to create the
	 * appropriate writer.
	 * 
	 * @throws UnsupportedRDFormatException
	 *         If no writer is available for the specified RDF format.
	 */
	public static RDFWriter createWriter(RDFFormat format, Writer writer)
		throws UnsupportedRDFormatException
	{
		RDFWriterFactory factory = RDFWriterRegistry.getInstance().get(format);

		if (factory != null) {
			return factory.getWriter(writer);
		}

		throw new UnsupportedRDFormatException("No writer factory available for RDF format " + format);
	}

	/**
	 * Adds RDF data from an {@link InputStream} to a {@link Model}, optionally
	 * to one or more named contexts.
	 * 
	 * @param in
	 *        An InputStream from which RDF data can be read.
	 * @param baseURI
	 *        The base URI to resolve any relative URIs that are in the data
	 *        against.
	 * @param dataFormat
	 *        The serialization format of the data.
	 * @param contexts
	 *        The contexts to add the data to. If one or more contexts are
	 *        supplied the method ignores contextual information in the actual
	 *        data. If no contexts are supplied the contextual information in the
	 *        input stream is used, if no context information is available the
	 *        data is added without any context.
	 * @return A {@link Model} containing the parsed statements.
	 * @throws IOException
	 *         If an I/O error occurred while reading from the input stream.
	 * @throws UnsupportedRDFormatException
	 *         If no {@link RDFParser} is available for the specified RDF format.
	 * @throws RDFParseException
	 *         If an error was found while parsing the RDF data.
	 * @since 2.7.1
	 */
	public static Model parse(InputStream in, String baseURI, RDFFormat dataFormat, Resource... contexts)
		throws IOException, RDFParseException, UnsupportedRDFormatException
	{
		return parse(in, baseURI, dataFormat, new ParserConfig(), ValueFactoryImpl.getInstance(),
				new ParseErrorLogger(), contexts);
	}

	/**
	 * Adds RDF data from a {@link Reader} to a {@link Model}, optionally to one
	 * or more named contexts. <b>Note: using a Reader to upload byte-based data
	 * means that you have to be careful not to destroy the data's character
	 * encoding by enforcing a default character encoding upon the bytes. If
	 * possible, adding such data using an InputStream is to be preferred.</b>
	 * 
	 * @param reader
	 *        A Reader from which RDF data can be read.
	 * @param baseURI
	 *        The base URI to resolve any relative URIs that are in the data
	 *        against.
	 * @param dataFormat
	 *        The serialization format of the data.
	 * @param contexts
	 *        The contexts to add the data to. If one or more contexts are
	 *        specified the data is added to these contexts, ignoring any context
	 *        information in the data itself.
	 * @return A {@link Model} containing the parsed statements.
	 * @throws IOException
	 *         If an I/O error occurred while reading from the reader.
	 * @throws UnsupportedRDFormatException
	 *         If no {@link RDFParser} is available for the specified RDF format.
	 * @throws RDFParseException
	 *         If an error was found while parsing the RDF data.
	 * @since 2.7.1
	 */
	public static Model parse(Reader reader, String baseURI, RDFFormat dataFormat, Resource... contexts)
		throws IOException, RDFParseException, UnsupportedRDFormatException
	{
		return parse(reader, baseURI, dataFormat, new ParserConfig(), ValueFactoryImpl.getInstance(),
				new ParseErrorLogger(), contexts);
	}

	/**
	 * Adds RDF data from an {@link InputStream} to a {@link Model}, optionally
	 * to one or more named contexts.
	 * 
	 * @param in
	 *        An InputStream from which RDF data can be read.
	 * @param baseURI
	 *        The base URI to resolve any relative URIs that are in the data
	 *        against.
	 * @param dataFormat
	 *        The serialization format of the data.
	 * @param settings
	 *        The {@link ParserConfig} containing settings for configuring the
	 *        parser.
	 * @param valueFactory
	 *        The {@link ValueFactory} used by the parser to create statements.
	 * @param errors
	 *        The {@link ParseErrorListener} used by the parser to signal errors,
	 *        including errors that do not generate an {@link RDFParseException}.
	 * @param contexts
	 *        The contexts to add the data to. If one or more contexts are
	 *        supplied the method ignores contextual information in the actual
	 *        data. If no contexts are supplied the contextual information in the
	 *        input stream is used, if no context information is available the
	 *        data is added without any context.
	 * @return A {@link Model} containing the parsed statements.
	 * @throws IOException
	 *         If an I/O error occurred while reading from the input stream.
	 * @throws UnsupportedRDFormatException
	 *         If no {@link RDFParser} is available for the specified RDF format.
	 * @throws RDFParseException
	 *         If an error was found while parsing the RDF data.
	 * @since 2.7.3
	 */
	public static Model parse(InputStream in, String baseURI, RDFFormat dataFormat, ParserConfig settings,
			ValueFactory valueFactory, ParseErrorListener errors, Resource... contexts)
		throws IOException, RDFParseException, UnsupportedRDFormatException
	{
		Model result = new LinkedHashModel();
		RDFParser parser = createParser(dataFormat, valueFactory);
		parser.setParserConfig(settings);
		parser.setParseErrorListener(errors);
		parser.setRDFHandler(new ContextStatementCollector(result, valueFactory, contexts));
		try {
			parser.parse(in, baseURI);
		}
		catch (RDFHandlerException e) {
			// LinkedHashModel and StatementCollector do not throw these exceptions
			throw new RuntimeException(e);
		}
		return result;
	}

	/**
	 * Adds RDF data from a {@link Reader} to a {@link Model}, optionally to one
	 * or more named contexts. <b>Note: using a Reader to upload byte-based data
	 * means that you have to be careful not to destroy the data's character
	 * encoding by enforcing a default character encoding upon the bytes. If
	 * possible, adding such data using an InputStream is to be preferred.</b>
	 * 
	 * @param reader
	 *        A Reader from which RDF data can be read.
	 * @param baseURI
	 *        The base URI to resolve any relative URIs that are in the data
	 *        against.
	 * @param dataFormat
	 *        The serialization format of the data.
	 * @param settings
	 *        The {@link ParserConfig} containing settings for configuring the
	 *        parser.
	 * @param valueFactory
	 *        The {@link ValueFactory} used by the parser to create statements.
	 * @param errors
	 *        The {@link ParseErrorListener} used by the parser to signal errors,
	 *        including errors that do not generate an {@link RDFParseException}.
	 * @param contexts
	 *        The contexts to add the data to. If one or more contexts are
	 *        specified the data is added to these contexts, ignoring any context
	 *        information in the data itself.
	 * @return A {@link Model} containing the parsed statements.
	 * @throws IOException
	 *         If an I/O error occurred while reading from the reader.
	 * @throws UnsupportedRDFormatException
	 *         If no {@link RDFParser} is available for the specified RDF format.
	 * @throws RDFParseException
	 *         If an error was found while parsing the RDF data.
	 * @since 2.7.3
	 */
	public static Model parse(Reader reader, String baseURI, RDFFormat dataFormat, ParserConfig settings,
			ValueFactory valueFactory, ParseErrorListener errors, Resource... contexts)
		throws IOException, RDFParseException, UnsupportedRDFormatException
	{
		Model result = new LinkedHashModel();
		RDFParser parser = createParser(dataFormat, valueFactory);
		parser.setParserConfig(settings);
		parser.setParseErrorListener(errors);
		parser.setRDFHandler(new ContextStatementCollector(result, valueFactory, contexts));
		try {
			parser.parse(reader, baseURI);
		}
		catch (RDFHandlerException e) {
			// LinkedHashModel and StatementCollector do not throw these exceptions
			throw new RuntimeException(e);
		}
		return result;
	}

	/**
	 * Writes the given statements to the given {@link OutputStream} in the given
	 * format.
	 * <p>
	 * If the collection is a {@link Model}, its namespaces will also be written.
	 * 
	 * @param model
	 *        A collection of statements, such as a {@link Model}, to be written.
	 * @param output
	 *        The {@link OutputStream} to write the statements to.
	 * @param dataFormat
	 *        The {@link RDFFormat} to use when writing the statements.
	 * @throws RDFHandlerException
	 *         Thrown if there is an error writing the statements.
	 * @throws UnsupportedRDFormatException
	 *         If no {@link RDFWriter} is available for the specified RDF format.
	 * @since 2.7.1
	 */
	public static void write(Iterable<Statement> model, OutputStream output, RDFFormat dataFormat)
		throws RDFHandlerException
	{
		write(model, output, dataFormat, new WriterConfig());
	}

	/**
	 * Writes the given statements to the given {@link Writer} in the given
	 * format.
	 * <p>
	 * If the collection is a {@link Model}, its namespaces will also be written.
	 * 
	 * @param model
	 *        A collection of statements, such as a {@link Model}, to be written.
	 * @param output
	 *        The {@link Writer} to write the statements to.
	 * @param dataFormat
	 *        The {@link RDFFormat} to use when writing the statements.
	 * @throws RDFHandlerException
	 *         Thrown if there is an error writing the statements.
	 * @throws UnsupportedRDFormatException
	 *         If no {@link RDFWriter} is available for the specified RDF format.
	 * @since 2.7.1
	 */
	public static void write(Iterable<Statement> model, Writer output, RDFFormat dataFormat)
		throws RDFHandlerException
	{
		write(model, output, dataFormat, new WriterConfig());
	}

	/**
	 * Writes the given statements to the given {@link OutputStream} in the given
	 * format.
	 * <p>
	 * If the collection is a {@link Model}, its namespaces will also be written.
	 * 
	 * @param model
	 *        A collection of statements, such as a {@link Model}, to be written.
	 * @param output
	 *        The {@link OutputStream} to write the statements to.
	 * @param dataFormat
	 *        The {@link RDFFormat} to use when writing the statements.
	 * @param settings
	 *        The {@link WriterConfig} containing settings for configuring the
	 *        writer.
	 * @throws RDFHandlerException
	 *         Thrown if there is an error writing the statements.
	 * @throws UnsupportedRDFormatException
	 *         If no {@link RDFWriter} is available for the specified RDF format.
	 * @since 2.7.3
	 */
	public static void write(Iterable<Statement> model, OutputStream output, RDFFormat dataFormat,
			WriterConfig settings)
		throws RDFHandlerException
	{
		final RDFWriter writer = Rio.createWriter(dataFormat, output);
		writer.setWriterConfig(settings);
		write(model, writer);
	}

	/**
	 * Writes the given statements to the given {@link Writer} in the given
	 * format.
	 * <p>
	 * If the collection is a {@link Model}, its namespaces will also be written.
	 * 
	 * @param model
	 *        A collection of statements, such as a {@link Model}, to be written.
	 * @param output
	 *        The {@link Writer} to write the statements to.
	 * @param dataFormat
	 *        The {@link RDFFormat} to use when writing the statements.
	 * @param settings
	 *        The {@link WriterConfig} containing settings for configuring the
	 *        writer.
	 * @throws RDFHandlerException
	 *         Thrown if there is an error writing the statements.
	 * @throws UnsupportedRDFormatException
	 *         If no {@link RDFWriter} is available for the specified RDF format.
	 * @since 2.7.3
	 */
	public static void write(Iterable<Statement> model, Writer output, RDFFormat dataFormat,
			WriterConfig settings)
		throws RDFHandlerException
	{
		final RDFWriter writer = Rio.createWriter(dataFormat, output);
		writer.setWriterConfig(settings);
		write(model, writer);
	}

	/**
	 * Writes the given statements to the given {@link RDFHandler}.
	 * <p>
	 * If the collection is a {@link Model}, its namespaces will also be written.
	 * 
	 * @param model
	 *        A collection of statements, such as a {@link Model}, to be written.
	 * @throws RDFHandlerException
	 *         Thrown if there is an error writing the statements.
	 * @since 2.7.3
	 */
	public static void write(Iterable<Statement> model, RDFHandler writer)
		throws RDFHandlerException
	{
		writer.startRDF();

		if (model instanceof Model) {
			for (Namespace nextNamespace : ((Model)model).getNamespaces()) {
				writer.handleNamespace(nextNamespace.getPrefix(), nextNamespace.getName());
			}
		}

		for (final Statement st : model) {
			writer.handleStatement(st);
		}
		writer.endRDF();
	}

	public static void main(String[] args)
		throws IOException, RDFParseException, RDFHandlerException, UnsupportedRDFormatException
	{
		if (args.length < 2) {
			System.out.println("Usage: java org.openrdf.rio.Rio <inputFile> <outputFile>");
			return;
		}

		// Create parser for input file
		String inputFile = args[0];
		FileInputStream inStream = new FileInputStream(inputFile);
		RDFFormat inputFormat = getParserFormatForFileName(inputFile, RDFFormat.RDFXML);
		RDFParser rdfParser = createParser(inputFormat);

		// Create writer for output file
		String outputFile = args[1];
		FileOutputStream outStream = new FileOutputStream(outputFile);
		RDFFormat outputFormat = getWriterFormatForFileName(outputFile, RDFFormat.RDFXML);
		RDFWriter rdfWriter = createWriter(outputFormat, outStream);

		rdfParser.setRDFHandler(rdfWriter);
		rdfParser.parse(inStream, "file:" + inputFile);

		inStream.close();
		outStream.close();
	}
}
