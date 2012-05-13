/**
 * 
 */
package org.arsenico.database;

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

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import junit.framework.Assert;

import org.arsenico.BaseTest;
import org.arsenico.database.HibernateUtil;
import org.arsenico.database.LogRegistry;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.junit.Test;

/**
 * Test la connessione al database.
 * 
 * @author Francesco Benincasa
 * 
 */
public class HibernateUtilTest extends BaseTest {

	/**
	 * Test: provo a creare due entità nel database e a leggerle.
	 * 
	 * @throws HibernateException
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testInsertAndSelect() throws Exception {
		Session session = createSession(true);
		session.beginTransaction();
		// Creo una nuova persona
		LogRegistry p = new LogRegistry();
		p.setFileName("prova" + System.currentTimeMillis());
		p.setMessages("ciao" + System.currentTimeMillis());
		p.setInsertDate(new Timestamp(System.currentTimeMillis()));
		session.save(p);

		List<LogRegistry> lista = session.createQuery("from LogRegistry").list();
		Assert.assertEquals(lista.size() == 1, true);
		log.info("Trovati " + lista.size() + " elementi");

		p = new LogRegistry();
		p.setFileName("prova" + System.currentTimeMillis());
		p.setMessages("ciao" + System.currentTimeMillis());
		p.setInsertDate(new Timestamp(System.currentTimeMillis()));
		session.save(p);

		lista = session.createQuery("from LogRegistry").list();
		Assert.assertEquals(lista.size() == 2, true);
		log.info("Trovati " + lista.size() + " elementi");

		session.getTransaction().commit();
		session.close();
	}
	
	@Test
	public void testSelect() throws Exception {
		Session session = createSession(true);
		session.beginTransaction();
		
		// Creo una nuova persona
		LogRegistry p = new LogRegistry();
		p.setFileName("prova" + System.currentTimeMillis());
		p.setMessages("ciao" + System.currentTimeMillis());
		p.setInsertDate(new Timestamp(System.currentTimeMillis()));
		session.save(p);
		
		// Creo una nuova persona
		p = new LogRegistry();
		p.setFileName("prova2" + System.currentTimeMillis());
		p.setMessages("ciao2" + System.currentTimeMillis());
		p.setInsertDate(new Timestamp(System.currentTimeMillis()));
		session.save(p);
		
		// Creo una nuova persona
		p = new LogRegistry();
		p.setFileName("prova3" + System.currentTimeMillis());
		p.setMessages("ciao3" + System.currentTimeMillis());
		p.setInsertDate(new Timestamp(System.currentTimeMillis()));
		session.save(p);
		
		// Creo una nuova persona
		@SuppressWarnings("unchecked")
		List<LogRegistry> lista = session.createQuery("from LogRegistry order by id desc").list();
		
		log.info("Trovati " + lista.size() + " elementi");
		
		for (LogRegistry item: lista)
		{
			log.info(String.format("%s %s %s %s %s",item.getSvnUrl(), item.getBuildNumber(), item.getInsertDate(), item.getFileName(), item.getMessages()));
		}
		
		session.getTransaction().commit();
		session.close();
	}

	/**
	 * Test sulla presenza o meno della tabella. Il database viene creato senza
	 * l'eventuale creazione della tabella.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testTableExist() throws Exception {
		Session session = createSession(true);
		boolean res = HibernateUtil.tableExists(session, "MAVEN_SQL_LOG");
		log.info("Tabella esiste = " + res);
		Assert.assertEquals(res, true);
	}


	/**
	 * Ulteriore test di creazione
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCreate() throws Exception {

		Session session = createSession(false);

		boolean exist = HibernateUtil.tableExists(session, "MAVEN_SQL_LOG");

		if (!exist) {
			session.close();
			session = createSession(true);
		}

		session.beginTransaction();

		// Creo una nuova persona
		LogRegistry p = new LogRegistry();
		p.setInsertDate(new Timestamp(System.currentTimeMillis()));
		p.setMessages("prova" + System.currentTimeMillis());
		p.setFileName("ciao" + System.currentTimeMillis());
		p.setSvnUrl("SvnUrl");
		p.setBuildNumber("23");
		p.setVersion("1.0.");

		session.save(p);

		session.getTransaction().commit();
		session.close();
	}

}
