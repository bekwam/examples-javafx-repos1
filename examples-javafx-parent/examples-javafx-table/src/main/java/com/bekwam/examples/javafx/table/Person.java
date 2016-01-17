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

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author carl_000
 */
public class Person {

	private final static int MAX_FIRST_NAME_LEN = 50;
	private final static int MAX_LAST_NAME_LEN = 50;
	private final static int MAX_EMAIL_LEN = 100;
	
	private StringProperty personType = new SimpleStringProperty("Friend");
	private LongProperty personId = new SimpleLongProperty(-1L);
    private StringProperty firstName = new SimpleStringProperty("firstName");
    private StringProperty lastName = new SimpleStringProperty("lastName");
    private StringProperty email = new SimpleStringProperty("email");

    public Person() {
    }

    public String getPersonType() { 
    	return personType.get(); 
    }
    
    public void setPersonType(String personType) { 
    	this.personType.setValue(personType); 
    }
    
    public StringProperty personTypeProperty() { return personType; }
    
    public Long getPersonId() { 
    	return personId.get(); 
    }
    
    public void setPersonId(Long personId) { 
    	this.personId.setValue(personId); 
    }
    
    public LongProperty personIdProperty() { return personId; }
    
    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.setValue(firstName);
    }

    public StringProperty firstNameProperty() { return firstName; }
    
    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.setValue(lastName);
    }

    public StringProperty lastNameProperty() { return lastName; }
    
    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.setValue(email);
    }

    public StringProperty emailProperty() { return email; }

    public String validate() {
    
    	if( firstName.get() == null || firstName.get().isEmpty() ) {
    		return "First name is required";
    	}
    	
    	if( firstName.get().length() > MAX_FIRST_NAME_LEN ) {
    		return "First name cannot contain more than " + MAX_FIRST_NAME_LEN + " characters.";
    	}
    	
    	if( lastName.get() == null || lastName.get().isEmpty() ) {
    		return "Last name is required";
    	}

    	if( lastName.get().length() > MAX_LAST_NAME_LEN ) {
    		return "Last name cannot contain more than " + MAX_LAST_NAME_LEN + " characters.";
    	}

    	if( email.get() == null || email.get().isEmpty() ) {
    		return "Email is required";
    	}

    	if( email.get().length() > MAX_EMAIL_LEN ) {
    		return "Email cannot contain more than " + MAX_EMAIL_LEN + " characters.";
    	}

    	return "";
    }
    
	@Override
	public String toString() {
		return "Person [personId=" + personId + ", firstName=" + firstName + ", lastName=" + lastName + ", email="
				+ email + "]";
	}       
}
