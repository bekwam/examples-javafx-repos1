/*
 * Copyright 2016 Bekwam, Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bekwam.examples.javafx.table;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * @author carl
 *
 */
public class PersonDAOImpl implements PersonDAO {

	private Logger logger = Logger.getLogger("com.bekwam.examples.javafx.table");
	
	private Long nextPersonId = 1L;
	
	/* (non-Javadoc)
	 * @see com.bekwam.examples.javafx.table.PersonDAO#addPerson(com.bekwam.examples.javafx.table.Person)
	 */
	@Override
	public Long addPerson(Person p) {
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException ignore) {}
		Long retval = -1L;
		synchronized(nextPersonId) {
			retval = nextPersonId++;
			if( logger.isLoggable(Level.FINE) ) {
				logger.fine("[ADD] adding p=" + p.getLastName() + ", " + p.getFirstName() + ", id=" + retval);
			}
		}
		return retval;
	}

	/* (non-Javadoc)
	 * @see com.bekwam.examples.javafx.table.PersonDAO#deletePersons(java.util.List)
	 */
	@Override
	public void deletePersons(List<Long> personIds) {
		if( personIds == null ) {
			return;
		}
		personIds.forEach((p) -> {
			if( logger.isLoggable(Level.FINE) ) {
				logger.fine("[DEL] deleting personId=" + p);
			}
		});
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException ignore) {}

	}

	/* (non-Javadoc)
	 * @see com.bekwam.examples.javafx.table.PersonDAO#updatePerson(com.bekwam.examples.javafx.table.Person)
	 */
	@Override
	public void updatePerson(Person p) {
		if( logger.isLoggable(Level.FINE) ) {
			logger.fine("[UPD] updating person=" + p);
		}
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException ignore) {}

	}

}
