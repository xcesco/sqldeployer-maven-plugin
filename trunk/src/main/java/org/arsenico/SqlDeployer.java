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

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.arsenico.core.DatabaseSupportedType;
import org.arsenico.core.DynamicIgnoredErrorCode;
import org.arsenico.core.ErroreCodeAnalizer;
import org.arsenico.core.FileHarvester;
import org.arsenico.core.FileParser;
import org.arsenico.core.Logger;
import org.arsenico.core.Utils;
import org.arsenico.database.HibernateUtil;
import org.arsenico.database.LogRegistry;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;

/**  
 * Oggetto che implementa la logica del plugin relativa al goal sqldeploy.
 * 
 * @author Francesco Benincasa
 * 
 */
public class SqlDeployer implements Executor {

	/**
	 * Esegue il task principale:
	 * <ul>
	 * <li>
	 * Verifica se il database contiene la tabella di registro. Se non esiste,
	 * viene creato</li>
	 * <li>Recupero elenco dei file che devono essere ancora analizzati.</li>
	 * <li>Per ogni statement presente nei file eseguo e memorizzo
	 * l'informazione su db</li>
	 * </ul>
	 * 
	 * @throws Exception
	 */
	public void execute() throws Exception {				
		Session session;

		final Settings s = Settings.getInstance();				
		
		// fix su svnUrl
		String svnUrl=s.getSvnUrl();
		
		if (svnUrl==null) svnUrl="";
		svnUrl=svnUrl.replace("scm:svn:","");
		s.setSvnUrl(svnUrl);
		
		buildDynamicIgnoredErrors();

		// Verifica se il database contiene la tabella di registro. Se non
		// esiste, viene creato
		session = createSession();
		final Logger log = s.getLogger();

		try {
			// recupero elenco dei file che devono essere ancora analizzati
			List<String> listaFileDaAnalizzare = retrieveFileToAnalyze(session);
			Timestamp now=new Timestamp(System.currentTimeMillis());

			final Session sqlSession = session;

			for (String item : listaFileDaAnalizzare) {

				final LogRegistry registryLog = new LogRegistry();
				
				// inizialmente il numero di errori = 0
				registryLog.setErrors(0L);
				
				final String fileName = item;
				final StringBuilder buffer = new StringBuilder();
				final List<String> sql = FileParser.getSplittedContents(new File(s.getDirectorySource(), item), "/");
				
				log.debug(" ");
				log.debug("-----------------------------");
				log.info("Analisi file "+fileName);

				session.doWork(new Work() {
					/*
					 * (non-Javadoc)
					 * 
					 * @see org.hibernate.jdbc.Work#execute(java.sql.Connection)
					 */
					public void execute(Connection connection) throws SQLException {

						SqlType sqlType;
						// per ogni file, apriamo un file ed eseguiamo le varie
						// righe
						for (String linea : sql) {
							final String currentSql = linea;
							sqlType = determineType(currentSql);

							if (currentSql.trim().length() > 0) {
								try {																	
									Statement st = connection.createStatement();
									
									log.debug("SQL (" + sqlType + ") = " + currentSql);
									
									st.execute(currentSql);
									
									log.debug("Esecuzione... OK");									
									buffer.append("/* ESEGUITO CON SUCCESSO */\n");									
								} catch (SQLException e) {
									// nel caso di DDL si può provare ad andare avanti
									// negli altri casi segnaliamo un errore.
									
									
									if (ErroreCodeAnalizer.canIgnore(e))
									{
										String messaggioErrore=e.getMessage().replace("\n", "").trim();
										// incrementiamo il numero di errori dentro il bean
										registryLog.setErrors(registryLog.getErrors()+1);										
										log.warn("SQL ERR (" + sqlType + "), SQL-CODE "+e.getSQLState()+" = " + currentSql);
										log.warn("Errore NON BLOCCANTE durante l'esecuzione di un " + sqlType + ": " +messaggioErrore );
										log.warn("VENDOR SQL ERROR CODE = "+e.getErrorCode());
										log.warn("GENERIC SQL ERROR CODE = "+e.getSQLState());
										
										buffer.append("/* NON ESEGUITO CON ERROR-CODE = "+e.getErrorCode()+" ("+messaggioErrore+") */\n");
									} else {
										log.error("SQL ERR (" + sqlType + "), SQL-CODE "+e.getSQLState()+" = " + currentSql);
										log.error("Errore durante l'esecuzione di un " + sqlType + ": "
												+ e.getMessage());
										log.error("VENDOR SQL ERROR CODE = "+e.getErrorCode());
										log.error("GENERIC SQL ERROR CODE = "+e.getSQLState());
										throw (e);
									}
								}
																								
								buffer.append(currentSql);
								buffer.append("\n/\n");
								
							}
						}
					}

				});
				

				registryLog.setSvnUrl(s.getSvnUrl());
				
				registryLog.setVersion(s.getVersion());
				
				registryLog.setBuildNumber(s.getBuildNumber());
				registryLog.setDirectory(s.getDirectorySource());				
				registryLog.setFileName(fileName);
				registryLog.setMessages(buffer.toString());				
				
				registryLog.setInsertDate(now);

				// registriamo voce in registry
				session.beginTransaction();
				sqlSession.save(registryLog);
				session.getTransaction().commit();													
			}

		} catch (Exception e) {
			log.error(e.getMessage());
			throw (e);
		} finally {
			session.close();
		}
	}

	/**
	 * crea la lista di errori da ignorare in modo dinamico
	 */
	private void buildDynamicIgnoredErrors() {
		Settings s=Settings.getInstance();
		
		if (s.getIgnoredErrors()!=null)
		{
			String[] list=s.getIgnoredErrors().split(",");
			
			List<String> listaErroriDaIgnorare=new ArrayList<String>();
			
			for(String item:list)
			{
				if (item==null) continue;
				item=item.toLowerCase().trim();
				item=item.replace("\n", "");
				
				if (item.length()>0)
				{
					listaErroriDaIgnorare.add(item);
				}
			}
			
			DynamicIgnoredErrorCode.setErrors(listaErroriDaIgnorare);
			
		}
		
	}

	public enum SqlType {
		UNKNOWN,
		/**
		 * insert ed update
		 */
		DATA_MANIPOLATION,
		/**
		 * creazione di tabelle, trigger
		 */
		DATA_DEFINITION;
	}

	/**
	 * Restituisce un oggetto di tipo {@link SqlType}, in base alla query che
	 * viene passata in input.
	 * 
	 * @param sql
	 * @return
	 */
	private SqlType determineType(String sql) {
		if (sql == null)
			sql = "";

		sql = sql.toLowerCase().trim();

		if (sql.length() == 0)
			return SqlType.UNKNOWN;

		// DML: select, update, delete, insert
		String[] stringheDML = { "select", "update", "delete", "insert" };
		for (String item : stringheDML) {
			if (sql.startsWith(item))
				return SqlType.DATA_MANIPOLATION;
		}

		return SqlType.DATA_DEFINITION;
	}

	/**
	 * Recupero elenco dei file che devono essere ancora analizzati.
	 * 
	 * @param session
	 * @return
	 * @throws Exception
	 */
	private List<String> retrieveFileToAnalyze(Session session) throws Exception {
		Logger log = Settings.getInstance().getLogger();
		// Recupera l'elenco dei file presenti nella cartella sql e che
		// soddisfano il filePattern
		Set<String> validFiles = retrieveSqlFiles();

		// recupera dal database l'elenco dei file già analizzati con successo
		Set<String> parsed = retrieveParsedSqlFileSet(session);
		log.debug("Trovati " + parsed.size() + " file gia' analizzati.");

		// fa la differenza tra file trovati e file già analizzati
		Set<String> ret = Utils.diff(validFiles, parsed);
		log.info("Individuati " + ret.size() + " file ancora da analizzare.");

		// crea la lista di file ancora da analizzare
		List<String> retList = Utils.convertToOrderedList(ret);

		for (String item : retList) {
			log.debug("  File da analizzare: " + item);
		}

		return retList;
	}

	/**
	 * Verifica se il database contiene la tabella di registro. Se non esiste,
	 * viene creato.
	 * 
	 * @return
	 * @throws HibernateException
	 * @throws Exception
	 */
	protected Session createSession() throws HibernateException, Exception {
		Settings s = Settings.getInstance();
		
		//HibernateUtil.retrieveDatabaseInfo();
		 
		Session session;
		session = HibernateUtil.createSessionFactory(s.getDatabaseUrl(), s.getDatabaseUsername(), s.getDatabasePassword(), false,
				DatabaseSupportedType.parseString(s.getDatabaseType())).openSession();
		
		boolean exist = HibernateUtil.tableExists(session, "MAVEN_SQL_LOG");

		if (!exist) {
			session.close();
			session = HibernateUtil.createSessionFactory(s.getDatabaseUrl(), s.getDatabaseUsername(), s.getDatabasePassword(), true,
					DatabaseSupportedType.parseString(s.getDatabaseType())).openSession();
		}

		return session;
	}

	/**
	 * Recupera l'elenco dei file presenti nella cartella sql e che soddisfano
	 * il filePattern
	 * 
	 * @return
	 * @throws Exception
	 */
	private Set<String> retrieveSqlFiles() throws Exception {
		Settings s = Settings.getInstance();

		File path = new File(s.getDirectorySource());

		FileHarvester fh = new FileHarvester();
		Set<String> files = fh.parse(path.getAbsolutePath(), s.getFileNamePattern());

		return files;
	}

	/**
	 * Recupera dal database l'elenco dei file già analizzati con successo
	 * 
	 * @param session
	 * @return
	 */
	private Set<String> retrieveParsedSqlFileSet(Session session) {
		List<LogRegistry> lista = retrieveParsedSqlFileList(session);
		Set<String> ret = new HashSet<String>();

		for (LogRegistry item : lista) {
			// log.debug(item.getFileName());
			ret.add(item.getFileName());
						
		}

		return ret;
	}
	
	/**
	 * Recupera dal database l'elenco dei file già analizzati con successo
	 * 
	 * @param session
	 * @return
	 */
	protected List<LogRegistry> retrieveParsedSqlFileList(Session session) {
		@SuppressWarnings("unchecked")
		
		List<LogRegistry> lista = session.createQuery("from LogRegistry order by insertDate").list();
		
		return lista;
	}

}
