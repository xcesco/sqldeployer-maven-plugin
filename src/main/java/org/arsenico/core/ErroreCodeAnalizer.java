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

import java.sql.SQLException;

import org.arsenico.Settings;



/**
 * Dato un'eccezzione, cerca di capire se la posso ignorare o meno.
 * 
 * @author Francesco Benincasa
 * 
 */
public abstract class ErroreCodeAnalizer {

	/**
	 * Verfica se posso ignorare l'errore in quanto elencato tra quelli da
	 * ignorare
	 * 
	 * @param exception
	 * @return
	 */
	public static boolean canIgnore(SQLException exception) {
		String type = Settings.getInstance().getDatabaseType().toLowerCase();
		Logger log = Settings.getInstance().getLogger();

		if (type == null)
			type = "";

		// parte dinamica
		boolean dynamicErrorToIgnore = findDynamicError(exception);

		if (dynamicErrorToIgnore) {
			log.debug("L'errore " + exception.getMessage().replace("\n", " ")
					+ " è in dynamic ignore list, quindi non è bloccante!");
			return true;
		}

		return false;

	}

	/**
	 * Recupera dall'eccezione sql il codice d'errore vender e generico e 
	 * verifica se uno dei due rientra tra quelli da ignorare
	 * @param exception
	 * @return
	 */
	public static boolean findDynamicError(SQLException exception) {
		if (DynamicIgnoredErrorCode.getErrors() == null)
			return false;
		String msg = exception.getMessage().toLowerCase().trim();

		for (String item : DynamicIgnoredErrorCode.getErrors()) {
			item = item.toLowerCase();

			// verifichiamo che il messaggio di errore non inizi con il codice
			// fornito (caso ad esempio ORA-01430: column being added alre
			if (msg.startsWith(item))
				return true;
		}

		String sqlCode = exception.getSQLState().toLowerCase();

		for (String item : DynamicIgnoredErrorCode.getErrors()) {
			item = item.toLowerCase();

			// verifichiamo che il messaggio di errore non inizi con il codice
			// fornito (caso ad esempio ORA-01430: column being added alre
			// controllamo che non sia stato dato un codice d'errore definito
			if (sqlCode.equals(item)) {
				return true;
			}
		}

		return false;

	}

}
