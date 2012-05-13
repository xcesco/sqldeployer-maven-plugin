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

import java.util.List;

/**
 * Elenco dinamico degli errori che devono essere ignorati.
 * 
 * Durante l'esecuzione del codice sql si possono generare degli errori. Nel caso in 
 * cui tali errori siano tra quelli da ignorare, il programma va avanti.
 * 
 * Questa classe rappresenta appunto gli errori che sono considerati come "buoni"
 * e che non bloccano l'esecuzione del plugin.
 * 
 * @author Francesco Benincasa
 *
 */
public abstract class DynamicIgnoredErrorCode {

	/**
	 * elenco degli errori da ignorare.
	 */
	private static List<String> errors;

	/**
	 * @return
	 */
	public static List<String> getErrors() {
		return errors;
	}

	public static void setErrors(List<String> errors) {
		DynamicIgnoredErrorCode.errors = errors;
	}

	
}
