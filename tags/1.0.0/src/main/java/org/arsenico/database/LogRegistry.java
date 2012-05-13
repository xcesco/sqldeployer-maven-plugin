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

import java.sql.Timestamp;

/**
 * @author Francesco Benincasa
 * 
 */
public class LogRegistry {

	private String directory;
	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}

	private String buildNumber;

	private Long errors;

	private String fileName;
	
	private Long id;

	private Timestamp insertDate;

	private String messages;

	private String svnUrl;
	private String version;
	public String getBuildNumber() {
		return buildNumber;
	}

	public Long getErrors() {
		return errors;
	}

	public String getFileName() {
		return fileName;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	public Timestamp getInsertDate() {
		return insertDate;
	}

	public String getMessages() {
		return messages;
	}

	public String getSvnUrl() {
		return svnUrl;
	}

	public String getVersion() {
		return version;
	}

	public void setBuildNumber(String buildNumber) {
		this.buildNumber = buildNumber;
	}

	public void setErrors(Long errors) {
		this.errors = errors;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	public void setInsertDate(Timestamp insertDate) {
		this.insertDate = insertDate;
	}

	public void setMessages(String messages) {
		this.messages = messages;
	}

	public void setSvnUrl(String svnUrl) {
		this.svnUrl = svnUrl;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
