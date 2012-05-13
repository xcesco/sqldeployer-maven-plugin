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

/**
 * Tipi di database supportati. Per ogni database supportato viene definita la
 * stringa "alias", il driver jdbc e la stringa che identifica il dialetto
 * hibernate da utilizzare.
 * 
 * @author Francesco Benincasa
 * 
 */
public enum DatabaseSupportedType {
	HLSQLDB("hsqldb", "org.hibernate.dialect.HSQLDialect","org.hsqldb.jdbcDriver"), 
	ORACLE_8("oracle8",	"org.hibernate.dialect.Oracle8iDialect", "oracle.jdbc.driver.OracleDriver"), 
	ORACLE_9("oracle9", "org.hibernate.dialect.Oracle9iDialect", "oracle.jdbc.driver.OracleDriver"), 
	ORACLE_10("oracle10", "org.hibernate.dialect.Oracle10gDialect",	"oracle.jdbc.driver.OracleDriver"), 
	DB2("db2","org.hibernate.dialect.DB2Dialect", "com.ibm.db2.jcc.DB2Driver"), 
	DERBY("derby", "org.hibernate.dialect.DerbyDialect","org.apache.derby.jdbc.EmbeddedDriver"), 
	H2("h2","org.hibernate.dialect.H2Dialect", "org.h2.Driver"), 
	MYSQL("mysql",	"org.hibernate.dialect.MySQL5Dialect", "com.mysql.jdbc.Driver"), 
	POSTGRESQL("postgresql", "org.hibernate.dialect.PostgreSQLDialect",	"org.postgresql.Driver"),
	SQL_SERVER("sqlserver",	"org.hibernate.dialect.SQLServerDialect", "com.microsoft.sqlserver.jdbc.SQLServerDriver");

	/**
	 * identificativo del tipo di database
	 */
	private String id;

	/**
	 * dialetto
	 */
	private String dialect;

	/**
	 * driver jdbc
	 */
	private String driver;

	/**
	 * costruttore.
	 * 
	 * @param id
	 *            alias del tipo database
	 * @param dialect
	 *            dialetto hibernate associato
	 * @param driver
	 *            driver jdbc associato
	 */
	private DatabaseSupportedType(String id, String dialect, String driver) {
		this.id = id.toUpperCase();
		this.dialect = dialect;
		this.driver = driver;
	}

	/**
	 * getter dell'attributo dialect
	 * 
	 * @return dialect
	 */
	public String getDialect() {
		return dialect;
	}

	/**
	 * getter dell'attributo id
	 * 
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * getter dell'attributo driver
	 * 
	 * @return driver
	 */
	public String getDriver() {
		return driver;
	}

	/**
	 * data una stringa, provvede a recuperare il tipo di database. Se non viene
	 * riconosciuta la stringa, viene restituito <code>null</code>.
	 * 
	 * @param input
	 *            stringa in input.
	 * @return tipo di database o null.
	 */
	public static DatabaseSupportedType parseString(String input) {
		input = input.toUpperCase();

		for (DatabaseSupportedType item : DatabaseSupportedType.values()) {
			if (item.getId().equals(input))
				return item;
		}

		return null;
	}

}
