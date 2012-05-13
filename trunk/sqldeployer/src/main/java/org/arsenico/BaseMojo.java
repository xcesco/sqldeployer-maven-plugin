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

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.arsenico.core.LoggerImpl;

import java.io.File;

/**
 *  Classe base dei mojo
 */
public abstract class BaseMojo extends AbstractMojo {

	/**
	 * @parameter default-value="${buildNumber}"
	 */
	protected String buildNumber;
	
	/**
	 * @parameter
	 * @required
	 */
	protected String databasePwd;
		
	/**
	 * @parameter default-value="oracle10"
	 * @required
	 */
	protected String databaseType;
	
	/**
	 * @parameter
	 * @required
	 */
	protected String databaseUrl;
	
	/**
	 * @parameter
	 * @required
	 */
	protected String databaseUser;

	/**
	 * @parameter default-value="*.sql"
	 * @required
	 */
	protected String fileNameFilter;

	/**
	 * @parameter default-value="${project.version}"
	 * @required
	 */
	protected String version;
	
	/**
	 * @parameter default-value=""
	 */
	protected String ignoredErrors;

	/**
	 * @parameter default-value="src/main/sql/"
	 * @required
	 */
	protected String sqlDirectory;
	
	/**
	 * @parameter default-value="${project.scm.connection}"
	 * @required
	 */
	protected String svnUrl;
	
	/* (non-Javadoc)
	 * @see org.apache.maven.plugin.AbstractMojo#execute()
	 */
	public void execute(Executor executor) throws MojoExecutionException {
		Log log=getLog();
		
		Settings s=Settings.getInstance();
		
		s.setDatabaseUrl(databaseUrl);
		s.setDatabaseUsername(databaseUser);
		s.setDatabasePassword(databasePwd);
		s.setDatabaseType(databaseType);
		
		 
		s.setSvnUrl(svnUrl);
		s.setVersion(version);
		s.setBuildNumber(buildNumber);		
				
		s.setDirectorySource(sqlDirectory);		
		s.setFileNamePattern(fileNameFilter);
		
		s.setIgnoredErrors(ignoredErrors);
		s.setLogger(new LoggerImpl(log));
		
		try 
		{			
			log.info("Build number = "+this.buildNumber);
			log.info("DB Url = "+databaseUrl);
			log.info("DB User = "+databaseUser);
			log.info("DB Type = "+databaseType);
			log.info("SCM Url = "+svnUrl);
			log.info("Version = "+version);
			log.info("Sql Directory (relative) = "+sqlDirectory);
			log.info("Sql Directory (absolute) = "+(new File(sqlDirectory)).getAbsolutePath());
			
			executor.execute();
			log.info("Esecuzione terminata correttamente");
		} catch (Exception e) {			
			//log.error(e.getMessage());
			throw(new MojoExecutionException("Errore durante l'esecuzione del plugin SqlDeployer!", e));
		}					
	}

	/**
	 * setter dell'attributo buildNumber
	 * @param buildNumber
	 */
	public void setBuildNumber(String buildNumber) {
		this.buildNumber = buildNumber;
	}
	
	/**
	 * setter dell'attributo databasePwd
	 * @param databasePwd
	 */
	public void setDatabasePwd(String databasePwd) {
		this.databasePwd = databasePwd;
	}

	/**
	 * setter dell'attributo databaseType
	 * @param databaseType
	 */
	public void setDatabaseType(String databaseType) {
		this.databaseType = databaseType;
	}

	/**
	 * setter dell'attributo databaseUrl
	 * @param databaseUrl
	 */
	public void setDatabaseUrl(String databaseUrl) {
		this.databaseUrl = databaseUrl;
	}

	/**
	 * setter dell'attributo databaseUser
	 * @param databaseUser
	 */
	public void setDatabaseUser(String databaseUser) {
		this.databaseUser = databaseUser;
	}

	/**
	 * setter dell'attributo fileNameFilter
	 * @param fileNameFilter
	 */
	public void setFileNameFilter(String fileNameFilter) {
		this.fileNameFilter = fileNameFilter;
	}
	
	/**
	 * setter dell'attributo ignoredErrors
	 * @param ignoredErrors
	 */
	public void setIgnoredErrors(String ignoredErrors) {
		this.ignoredErrors = ignoredErrors;
	}
	
	/**
	 * setter dell'attributo sqlDirectory
	 * @param sqlDirectory
	 */
	public void setSqlDirectory(String sqlDirectory) {
		this.sqlDirectory = sqlDirectory;
	}
	
	/**
	 * setter dell'attributo svnUrl
	 * @param svnUrl
	 */
	public void setSvnUrl(String svnUrl) {
		this.svnUrl = svnUrl;
	}

}
