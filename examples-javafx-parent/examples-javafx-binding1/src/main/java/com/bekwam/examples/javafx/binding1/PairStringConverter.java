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
package com.bekwam.examples.javafx.binding1;

import java.util.List;
import java.util.Optional;

import com.google.common.base.Preconditions;

import javafx.util.Pair;
import javafx.util.StringConverter;

/**
 * A StringConverter for a JavaFX Pair object of Strings
 * 
 * @author carlwalker
 *
 */
public class PairStringConverter extends StringConverter<Pair<String, String>> {

	private final List<Pair<String, String>> lookup;
	
	public PairStringConverter(List<Pair<String,String>> lookup) {
		Preconditions.checkNotNull( lookup );
		this.lookup = lookup;
	}
	
	@Override
	public String toString(Pair<String, String> object) {
		return object.getValue();
	}

	@Override
	public Pair<String, String> fromString(String string) {
		
		Optional<Pair<String, String>> ret_p = lookup
			.stream()
			.filter((p) -> p.getKey().equalsIgnoreCase(string))
			.findFirst();
		
		return ret_p.orElse(null);
	}
	
}