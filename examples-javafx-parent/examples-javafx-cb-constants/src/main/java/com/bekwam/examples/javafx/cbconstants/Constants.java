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

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;

/**
 * A collection of constants accessible throughout the app
 * 
 * @author carlwalker
 *
 */
public class Constants {

	public final static Pair<String, String> PAIR_NULL = new Pair<>(null, "");
	public final static Pair<String, String> PAIR_YES = new Pair<>("Y", "YES");
	public final static Pair<String, String> PAIR_NO = new Pair<>("N", "NO");
	
	public final static ObservableList<Pair<String, String>> LIST_YES_NO =
			FXCollections.observableArrayList();
	
	public final static ObservableList<Pair<String, String>> LIST_YES_NO_OPT =
			FXCollections.observableArrayList();

	static {
		LIST_YES_NO.add( PAIR_NO );
		LIST_YES_NO.add( PAIR_YES );
		
		LIST_YES_NO_OPT.add( PAIR_NULL );
		LIST_YES_NO_OPT.add( PAIR_NO );
		LIST_YES_NO_OPT.add( PAIR_YES );
	}
}
