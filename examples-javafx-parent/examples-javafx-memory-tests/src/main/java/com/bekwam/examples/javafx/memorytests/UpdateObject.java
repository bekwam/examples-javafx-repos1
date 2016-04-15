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

/**
 * @author carl
 *
 */
class UpdateObject {
	
	private final Long id;
	private final String data;
	private final String oldData;
	
	public UpdateObject(Long id, String data) {
		this(id, data, "");
	}

	public UpdateObject(Long id, String data, String oldData) {
		this.id = id;
		this.data = data;
		this.oldData = oldData;		
	}
	
	public Long getId() {
		return id;
	}

	public String getData() {
		return data;
	}

	public String getOldData() {
		return oldData;
	}

	@Override
	public String toString() {
		return "UpdateObject [id=" + id + ", data=" + data + ", oldData=" + oldData + "]";
	}
};
