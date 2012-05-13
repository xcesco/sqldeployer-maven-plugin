package org.arsenico.core;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileParser {
	
	/**
	 * Rimuove i commenti inline (che iniziano con --)
	 * @param line
	 * @return
	 */
	private static String removeInlineComments(String line) {
		final int indexComment = line.indexOf("--");
        String lineWithoutComments = (indexComment != -1) ? line.substring(0, indexComment) : line;
        
        return lineWithoutComments;
	}

	/**
	 * Rimuove i commenti multiline
	 * @param script
	 * 
	 * @return
	 */
	private static String removeMultilineComments(StringBuilder script) {
        return script.toString().replaceAll("/\\*(.*?)\\*/", "").trim();
	}
	

	/**
	 * Dato un file suddivide il contenuto in base al separator
	 * 
	 * @param file
	 * 		file da analizzare
	 * @param contentSeparator
	 * 		separatore da utilizzare
	 * @return
	 * 		elenco di righe di testo contenute nel file
	 */
	public static List<String> getSplittedContents(File file, String contentSeparator) {
		List<String> strings = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();

		try {
			// use buffering, reading one line at a time
			// FileReader always assumes default encoding is OK!
			BufferedReader input = new BufferedReader(new FileReader(file));
			try {
				String line = null; // not declared within while loop
				/*
				 * readLine is a bit quirky : it returns the content of a line
				 * MINUS the newline. it returns null only for the END of the
				 * stream. it returns an empty String if two newlines appear in
				 * a row.
				 */
				while ((line = input.readLine()) != null) {

					if (line.trim().equals(contentSeparator)) {
						strings.add(removeMultilineComments(sb));
						sb = new StringBuilder();
					} else {
						sb.append(removeInlineComments(line));
						sb.append(" ");
					}
				}

				if (sb.toString().trim().length() > 0) {
					strings.add(removeMultilineComments(sb));
				}

			} finally {
				input.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return strings;
	}
}
