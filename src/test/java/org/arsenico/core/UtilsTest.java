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

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;

import org.arsenico.BaseTest;
import org.arsenico.core.FileHarvester;
import org.arsenico.core.FileParser;
import org.arsenico.core.Utils;
import org.arsenico.database.DatabaseAdapter;
import org.jmock.Expectations;
import org.junit.Test;

public class UtilsTest extends BaseTest {

	@SuppressWarnings("unchecked")
	@Test
	public void test01() throws Exception
	{
		File path=new File("src/test/resources/test-case1/src/main/sql");
		
		FileHarvester fh=new FileHarvester();
		Set<String> files=fh.parse(path.getAbsolutePath(), "dev*.sql");
		assertTrue(files.size()>0);
		
		final DatabaseAdapter database = context.mock(DatabaseAdapter.class);
		
		final Set<String> i=new HashSet<String>();
		i.add("dev0001.sql");
		
		context.checking(new Expectations() {{
			allowing (database).retrieveFileAlreadyParsed();
			will(returnValue(i)); }
	    { allowing(database).executeSql(with(any(List.class))); will(returnValue(true));}
	  //  { allowing(database).insertLog(with(any(LogItem.class))); will(returnValue(true)); }
	    });
		
		Set<String> parsed=database.retrieveFileAlreadyParsed();
		Set<String> ret=Utils.diff(files, parsed);
		List<String> retList=Utils.convertToOrderedList(ret);
		
		for (String item: retList)
		{
			log.debug(item);
			List<String> sql=FileParser.getSplittedContents(new File(path, item), "/");
			
			boolean value=database.executeSql(sql);
			
			Assert.assertEquals(value,true);
			log.debug("Eseguo "+sql);
		}
		
	}
}
