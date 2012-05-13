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

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.arsenico.BaseTest;
import org.arsenico.core.FileParser;
import org.junit.Test;

/**
 * Test su FileParser
 * 
 * @author Francesco Benincasa
 * 
 */
public class FileParserTest extends BaseTest {

	@Test
	public void testContent() throws Exception {
		String[] sql = { "DROP TABLE MAVEN_PROVA",
				"CREATE TABLE MAVEN_PROVA   (   ID NUMBER (10, 2),   DESCRIZIONE VARCHAR2 (256)  )" ,""};
		File path = new File("src/test/resources/test-case1/src/main/sql/dev0001.sql");

		List<String> listSql = FileParser.getSplittedContents(path, "/");
		int i=0;
		
		for (String item : listSql) {
			assertTrue(item.equals(sql[i++]));
			log.info("SQL: " + item);
		}
	}
}
