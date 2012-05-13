/**
 * 
 */
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

import org.apache.maven.plugin.logging.Log;

/**
 * Implementazione per il mojo del logger
 * @author Francesco Benincasa
 * 
 */
public class LoggerImpl implements Logger {
	/**
	 * logger
	 */
	private Log log;

	/**
	 * costruttore
	 * @param log
	 */
	public LoggerImpl(Log log) {
		this.log = log;
	}

	/* (non-Javadoc)
	 * @see org.arsenico.core.Logger#info(java.lang.String)
	 */
	public void info(String msg) {				
		log.info(msg);
	}

	/* (non-Javadoc)
	 * @see org.arsenico.core.Logger#debug(java.lang.String)
	 */
	public void debug(String msg) {
		log.debug(msg);		
	}

	/* (non-Javadoc)
	 * @see org.arsenico.core.Logger#error(java.lang.String)
	 */
	public void error(String msg) {
		log.error(msg);		
	}
	
	/* (non-Javadoc)
	 * @see org.arsenico.core.Logger#warn(java.lang.String)
	 */
	public void warn(String msg) {
		log.warn(msg);		
	}
}
