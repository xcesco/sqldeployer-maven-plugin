/**
 * 
 */
package org.arsenico;

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

import org.arsenico.Settings;
import org.arsenico.core.DatabaseSupportedType;
import org.arsenico.core.Logger;
import org.arsenico.database.HibernateUtil;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.jmock.Mockery;

/**
 * Base per tutti i test junit. Contiene i metodi before e after per misurare i
 * tempi di esecuzione dei test.
 * 
 * @author Francesco Benincasa
 * 
 */
public class BaseTest {

	/**
	 * Crea una session di test
	 * @param b
	 * 		se true fa creare le tabelle.
	 * 
	 * @return
	 * @throws Exception 
	 */
	protected Session createSession(boolean b) throws Exception {
		return HibernateUtil.createSessionFactory("jdbc:hsqldb:mem:testdb;shutdown=true", "sa", "", b,
				DatabaseSupportedType.HLSQLDB).openSession();
	}
	
	protected Mockery context;

	private long startTime;
	private long endTime;

	protected Logger log;

	/**
	 * metodo eseguito prima di ogni test
	 */
	@Before
	public void before() {
		// creiamo un log per il test
		log = new TestLoggerImpl();

		startTime = System.currentTimeMillis();

		Settings app = Settings.getInstance();
		app.setLogger(log);
		context = new Mockery();

		log.info("Test start");
	}

	/**
	 * metodo eseguito dopo ogni test
	 */
	@After
	public void after() {
		Settings app = Settings.getInstance();
		Logger log = app.getLogger();

		endTime = System.currentTimeMillis();

		log.info("Test end in " + (endTime - startTime) + " ms.");
	}

	/**
	 * test empty per evitare che il lancio di questo junit dia errore.
	 */
	@Test
	public void test() {
		log.info("Empty test");
	}
}
