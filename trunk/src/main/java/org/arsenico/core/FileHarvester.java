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

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.arsenico.Settings;

/**
 * Data la configurazione relativa al directory sql e la filter sui nomi,
 * provvede a recuperare un elenco di file ordinati dietro ordine alfabetico.
 * 
 * @author Francesco Benincasa
 * 
 */
public class FileHarvester {
	/**
	 * Analizza la directory e restituisce l'elenco dei file che soddisfano il
	 * pattern.
	 * 
	 * @param sqlDirectory
	 * @param fileNamePattern
	 * @return
	 * @throws Exception
	 */
	public Set<String> parse(String sqlDirectoryName, String fileNamePattern)
			throws Exception {
		Set<String> ret = new HashSet<String>();
		Settings settings = Settings.getInstance();
		final Logger log = settings.getLogger();
		File sqlDirectory = new File(sqlDirectoryName);

		fileNamePattern = fileNamePattern.replace(".", "\\.");
		fileNamePattern = fileNamePattern.replace("*", "(.*)");

		final Pattern pattern = Pattern.compile(fileNamePattern);

		log.info("Harvesting path " + sqlDirectory.getAbsolutePath());

		if (sqlDirectory.isDirectory()) {
			// It is also possible to filter the list of returned files.
			// This example does not return any files that start with `.'.
			FilenameFilter filter = new FilenameFilter() {
				public boolean accept(File dir, String name) {
					Matcher mat = pattern.matcher(name);
					Boolean val = mat.matches();
					
					return val;
				}
			};
			String[] children = sqlDirectory.list(filter);

			for (String item : children) {
				ret.add(item);
				log.debug("Candidate file " + item);
			}
		}

		return ret;

	}
}
