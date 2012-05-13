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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Classe di utilità
 * 
 * @author Francesco Benincasa
 *
 */
public abstract class Utils {

	/**
	 * Dati due set di stringhe, ottiene un terzo set di stringhe date da a - b
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static Set<String> diff(Set<String> a, Set<String> b) {
		Set<String> c = new HashSet<String>();

		for (String item : a) {
			if (!b.contains(item)) {
				c.add(item);
			}
		}
		return c;
	}

	/**
	 * Dati due set di stringhe, ottiene un terzo set di stringhe date da a - b
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static List<String> convertToOrderedList(Set<String> a) {
		List<String> orderList = new ArrayList<String>(a.size());

		for (String item : a) {
			orderList.add(item);
		}
		
		Collections.sort(orderList);
		
		return orderList;
	}

}
