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
package com.bekwam.examples.javafx.dynamic.app_core;

/**
 * @author carl
 *
 */
public class SubAppItem {

	private final String subAppName;
	private final String subAppJAR;
	
	public SubAppItem(String subAppName, String subAppJAR) {
		this.subAppName = subAppName;
		this.subAppJAR = subAppJAR;
	}

	public String getSubAppName() {
		return subAppName;
	}

	public String getSubAppJAR() {
		return subAppJAR;
	}	
}
