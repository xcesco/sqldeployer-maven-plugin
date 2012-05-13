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

import org.arsenico.core.Logger;


/**
 * Parametri di configurazione
 * 
 * @author Francesco Benincasa
 *
 */
public class Settings {
	
	/**
	 * istanza. Unica per tutta l'applicazione
	 */
	private static Settings instance;
	
	/**
	 * getter d'istanza
	 * 
	 * @return
	 * 		istanza
	 */
	public static Settings getInstance()
	{
		if (instance==null)
		{
			instance=new Settings();
		}
		
		return instance;
	}
	
	/**
	 * attributo buildNumber
	 */
	private String buildNumber;
	
	/**
	 * password del db
	 */
	private String databasePassword;

	/**
	 * attributo databaseType. I valori che può assumere sono gli id dei database supportati
	 * 
	 * @see DatabaseSupportedType
	 */
	private String databaseType;

	/**
	 * username del db
	 */
	private String databaseUrl;

	private String databaseUsername;

	/**
	 * cartella contenente i sorgenti da analizzare
	 */
	private String directorySource;

	/**
	 * pattern che i file da analizzare devono rispettare. Utilizza il carattere * per indicare 'qualsiasi carattere'.
	 */
	private String fileNamePattern;
	
	/**
	 * lista di errori (vendor code o generic code) da considerare come trascurabili
	 */
	private String ignoredErrors;

	/**
	 * logger da utilizzare per visualizzare gli errori
	 */
	private Logger logger;

	/**
	 * url svn
	 */
	private String svnUrl;

	/**
	 * versione del progetto maven
	 */
	private String version;

	/**
	 * costruttore
	 */
	private Settings()
	{
		
	}
	
	/**
	 * @return the buildNumber
	 */
	public String getBuildNumber() {
		return buildNumber;
	}

	/**
	 * @return the databasePassword
	 */
	public String getDatabasePassword() {
		return databasePassword;
	}

	/**
	 * @return the databaseType
	 */
	public String getDatabaseType() {
		return databaseType;
	}

	/**
	 * @return the databaseUrl
	 */
	public String getDatabaseUrl() {
		return databaseUrl;
	}

	/**
	 * @return the databaseUsername
	 */
	public String getDatabaseUsername() {
		return databaseUsername;
	}

	/**
	 * @return the directorySource
	 */
	public String getDirectorySource() {
		return directorySource;
	}

	/**
	 * @return the fileNamePattern
	 */
	public String getFileNamePattern() {
		return fileNamePattern;
	}

	/**
	 * @return the ignoredErrors
	 */
	public String getIgnoredErrors() {
		return ignoredErrors;
	}

	/**
	 * @return the logger
	 */
	public Logger getLogger() {
		return logger;
	}

	/**
	 * @return the svnUrl
	 */
	public String getSvnUrl() {
		return svnUrl;
	}

	/**
	 * getter dell'attributo version
	 * @return
	 * 		version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param buildNumber the buildNumber to set
	 */
	public void setBuildNumber(String buildNumber) {
		this.buildNumber = buildNumber;
	}

	/**
	 * @param databasePassword the databasePassword to set
	 */
	public void setDatabasePassword(String databasePassword) {
		this.databasePassword = databasePassword;
	}

	/**
	 * @param databaseType the databaseType to set
	 */
	public void setDatabaseType(String databaseType) {
		this.databaseType = databaseType;
	}

	/**
	 * @param databaseUrl the databaseUrl to set
	 */
	public void setDatabaseUrl(String databaseUrl) {
		this.databaseUrl = databaseUrl;
	}

	/**
	 * @param databaseUsername the databaseUsername to set
	 */
	public void setDatabaseUsername(String databaseUsername) {
		this.databaseUsername = databaseUsername;
	}

	/**
	 * @param directorySource the directorySource to set
	 */
	public void setDirectorySource(String directorySource) {
		this.directorySource = directorySource;
	}

	/**
	 * @param fileNamePattern the fileNamePattern to set
	 */
	public void setFileNamePattern(String fileNamePattern) {
		this.fileNamePattern = fileNamePattern;
	}

	/**
	 * @param ignoredErrors the ignoredErrors to set
	 */
	public void setIgnoredErrors(String ignoredErrors) {
		this.ignoredErrors = ignoredErrors;
	}

	/**
	 * @param logger the logger to set
	 */
	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	/**
	 * @param svnUrl the svnUrl to set
	 */
	public void setSvnUrl(String svnUrl) {
		this.svnUrl = svnUrl;
	}
	
	/**
	 * setter dell'attributo version
	 * 
	 * @param version
	 */
	public void setVersion(String version) {
		this.version = version;
	}
	
	

	
}
