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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.arsenico.core.DatabaseSupportedType;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.jdbc.Work;

/**
 * Classe di utilità per la creazione di sessioni hibernate.
 * 
 * 
 * @author Francesco Benincasa
 *
 */
public class HibernateUtil {

	/**
	 * session factory
	 */
	private static SessionFactory sessionFactory;

	/**
	 * A partire dai parametri passati, crea una session factory
	 * 
	 * @param url
	 * @param username
	 * @param password
	 * @param createTables
	 * 		se true mette il parametro <code>hbm2ddl.auto</code> a <code>create</code>, altrimenti a <code>verify</code>
	 * @param type 
	 * 		tipo di database	
	 * @return
	 * 		sessionFactory
	 * @throws Exception
	 * 
	 * @see {@link DatabaseSupportedType}
	 */
	public static SessionFactory createSessionFactory(String url, String username, String password,
			boolean createTables, DatabaseSupportedType type) throws Exception {
		Configuration config = new Configuration();

		config.addClass(LogRegistry.class);
		//
		config.setProperty("hibernate.connection.url", url);
		config.setProperty("hibernate.connection.driver_class", type.getDriver());
		config.setProperty("hibernate.connection.username", username);
		
		password=(password==null)?"":password;
		config.setProperty("hibernate.connection.password", password);
		config.setProperty("hibernate.connection.pool_size", "1");
		config.setProperty("hibernate.cache.provider_class", "org.hibernate.cache.NoCacheProvider");
		// config.setProperty("hibernate.show_sql", "true");
		config.setProperty("hibernate.show_sql", "false");

		// creiamo le tabella in base al flag dato come parametro
		if (createTables)
			config.setProperty("hibernate.hbm2ddl.auto", "create");
		else
			config.setProperty("hibernate.hbm2ddl.auto", "verify");
		config.setProperty("hibernate.connection.pool_size", "1");
		config.setProperty("hibernate.dialect", type.getDialect());

		sessionFactory = config.buildSessionFactory();

		return sessionFactory;
	}

	/**
	 * Classe di utilità  per il metodo {@link #tableExist}
	 * @author Francesco Benincasa
	 *
	 */
	public static class TableCheck {
		/**
		 * indica se la tabella esiste
		 */
		private boolean tableExist;

		/**
		 * getter
		 * @return
		 */
		public boolean isTableExist() {
			return tableExist;
		}

		/**
		 * setter
		 * @param tableExist
		 */
		public void setTableExist(boolean tableExist) {
			this.tableExist = tableExist;
		}
	}

	/**
	 * indica se la tabella passata come argomento esiste sul db su cui è aperta la sessione
	 * @param session
	 * 		sessione
	 * @param tableName
	 * 		tabella da verificare
	 * @return
	 * 		true se la tabella esiste.
	 * @throws HibernateException
	 * @throws SQLException
	 */
	public static boolean tableExists(final Session session, final String tableName) throws HibernateException,
			SQLException {
		final TableCheck result = new TableCheck();
		session.doWork(new Work() {

			public void execute(Connection connection) throws SQLException {
				if (connection != null) {
					ResultSet tables = connection.getMetaData().getTables(null, null, tableName, null);
					while (tables.next()) {
						String currentTableName = tables.getString("TABLE_NAME");
						if (currentTableName.equals(tableName)) {
							result.setTableExist(true);
						}
					}
					tables.close();
				}

			}
		});

		return result.isTableExist();
	}

}