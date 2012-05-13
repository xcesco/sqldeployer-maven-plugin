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
 * Interfaccia logger
 * 
 * @author Francesco Benincasa
 * 
 */
public interface Logger {
	/**
	 * visualizza info
	 * 
	 * @param msg
	 */
	public void info(String msg);

	/**
	 * visualizza debug
	 * 
	 * @param msg
	 */
	public void debug(String msg);

	/**
	 * visualizza error
	 * 
	 * @param msg
	 */
	public void error(String msg);

	/**
	 * visualizza warn
	 * 
	 * @param msg
	 */
	public void warn(String msg);
}
