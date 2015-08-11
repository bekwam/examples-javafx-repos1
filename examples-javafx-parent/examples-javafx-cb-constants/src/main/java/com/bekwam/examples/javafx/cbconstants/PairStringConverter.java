/*
 * Copyright 2015 Bekwam, Inc
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
package com.bekwam.examples.javafx.cbconstants;

import javafx.util.Pair;
import javafx.util.StringConverter;

/**
 * Renders a JavaFX Pair object into a ChoiceBox
 * 
 * A nullable flag supports two variations of the Pair lists: one that allows
 * null values and one that doesn't.
 * 
 * @author carlwalker
 *
 */
public class PairStringConverter extends StringConverter<Pair<String, String>> {

	private final Boolean nullable;
	
	public PairStringConverter() {
		this.nullable = false;
	}

	public PairStringConverter(Boolean nullable) {
		this.nullable = nullable;
	}
	
	@Override
	public String toString(Pair<String, String> pair) {
		
		if( !nullable && pair.getKey() == null ) {
			throw new IllegalArgumentException("non-null converter received null pair key; set the nullable flag?");
		}
		
		return pair.getValue();
	}

	@Override
	public Pair<String, String> fromString(String string) {

		if( string == null && !nullable ) {
			throw new IllegalArgumentException("non-null converter received null pair key; set the nullable flag?");
		}

		if( string == Constants.PAIR_NULL.getValue() ) {
			return Constants.PAIR_NULL;
		}
		
		if( string.equalsIgnoreCase(Constants.PAIR_NO.getValue()) ) {
			return Constants.PAIR_NO;
		}
		
		if( string.equalsIgnoreCase(Constants.PAIR_YES.getValue()) ) {
			return Constants.PAIR_YES;
		}
		
		throw new IllegalArgumentException("unrecognized pair parse value '" + string + "'");
	}

}
