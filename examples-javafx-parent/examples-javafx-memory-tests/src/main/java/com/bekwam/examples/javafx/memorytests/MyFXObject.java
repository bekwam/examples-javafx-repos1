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
package com.bekwam.examples.javafx.memorytests;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author carl
 *
 */
public class MyFXObject {

	private LongProperty id = new SimpleLongProperty();
	private StringProperty data = new SimpleStringProperty();
	
	public Long getId() { return id.get(); }
	public void setId(Long id) { this.id.set(id); }

	public LongProperty idProperty() { return id; }
	
	public String getData() { return data.get(); }
	public void setData(String data) { this.data.set(data); }
	
	public StringProperty dataProperty() { return data; }
	
	public MyFXObject() {}
	
	public MyFXObject(Long id, String data) {
		setId( id );
		setData( data );
	}
}
