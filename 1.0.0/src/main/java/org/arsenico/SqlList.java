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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.arsenico.core.Logger;
import org.arsenico.database.LogRegistry;
import org.hibernate.Session;

/**
 * @author Francesco Benincasa
 * 
 */
public class SqlList extends SqlDeployer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.arsenico.SqlDeployer#execute()
	 */
	@Override
	public void execute() throws Exception {
		Session session;

		final Settings s = Settings.getInstance();

		// Verifica se il database contiene la tabella di registro. Se non
		// esiste, viene creato
		session = createSession();
		final Logger log = s.getLogger();

		try {
			// recupero elenco dei file che devono essere ancora analizzati
			List<LogRegistry> listaFileAnalizzati = retrieveParsedSqlFileList(session);
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss z");

			System.out.println("\n");
			System.out.println("Elenco dei file gia' analizzati");
			System.out.println("--------------------------");

			for (LogRegistry item : listaFileAnalizzati) {
				System.out.println(String.format("Data: %s, File: %s", df.format(item.getInsertDate()),
						item.getFileName()));
			}
			
			System.out.println("\n");

		} catch (Exception e) {
			log.error(e.getMessage());
			throw (e);
		} finally {
			session.close();
		}
	}

}
