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
package com.bekwam.examples.javafx.accesscontrol;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author carl_000
 */
public class MainViewController {

    private Logger log = LoggerFactory.getLogger(MainViewController.class);

    @FXML MenuItem miA;
    @FXML MenuItem miB;
    @FXML MenuItem miC;

    @FXML Button tbA;
    @FXML Button tbB;
    @FXML Button tbC;

    @FXML Button btnA;
    @FXML Button btnB;
    @FXML Button btnC;

    @FXML ChoiceBox<UserSecurity> cbUser;

    enum AccessType { HIDE, DISABLE, SHOW }

    enum CommandType {A, B, C}

    enum NodePropertiesKeyType { commandType }

    @FXML
    public void initialize() {

        miA.getProperties().put(NodePropertiesKeyType.commandType, CommandType.A );
        tbA.getProperties().put(NodePropertiesKeyType.commandType, CommandType.A );
        btnA.getProperties().put(NodePropertiesKeyType.commandType, CommandType.A );

        miB.getProperties().put(NodePropertiesKeyType.commandType, CommandType.B );
        tbB.getProperties().put(NodePropertiesKeyType.commandType, CommandType.B );
        btnB.getProperties().put(NodePropertiesKeyType.commandType, CommandType.B );

        miC.getProperties().put(NodePropertiesKeyType.commandType, CommandType.C );
        tbC.getProperties().put(NodePropertiesKeyType.commandType, CommandType.C);
        btnC.getProperties().put(NodePropertiesKeyType.commandType, CommandType.C);

        Map<CommandType, AccessType> access1 = new LinkedHashMap<>();
        access1.put(CommandType.A, AccessType.HIDE);
        access1.put(CommandType.B, AccessType.HIDE);
        access1.put(CommandType.C, AccessType.HIDE);

        UserSecurity s1 = new UserSecurity("Carl (No Access)", access1 );

        Map<CommandType, AccessType> access2 = new LinkedHashMap<>();
        access2.put( CommandType.A, AccessType.SHOW );
        access2.put( CommandType.B, AccessType.HIDE );
        access2.put( CommandType.C, AccessType.DISABLE );

        UserSecurity s2 = new UserSecurity("Ralph (Some Access)", access2 );

        Map<CommandType, AccessType> access3 = new LinkedHashMap<>();
        access3.put( CommandType.A, AccessType.SHOW );
        access3.put(CommandType.B, AccessType.SHOW);
        access3.put(CommandType.C, AccessType.SHOW);

        UserSecurity s3 = new UserSecurity("Jim (All Access)", access3 );

        cbUser.setConverter(new StringConverter<UserSecurity>() {

            @Override
            public String toString(UserSecurity object) {
                return object.getName();
            }

            @Override
            public UserSecurity fromString(String string) {
                if (string == null) return null;
                if (string.equalsIgnoreCase("Carl (No Access)")) {
                    return s1;
                }
                if (string.equalsIgnoreCase("Ralph (Some Access)")) {
                    return s2;
                }
                if (string.equalsIgnoreCase("Jim (All Access)")) {
                    return s3;
                }
                return null;
            }
        });

        cbUser.getItems().addAll(s1, s2, s3);
        cbUser.getSelectionModel().select(s1);
        cbUser.getSelectionModel().selectedItemProperty().addListener(
        		(ov, oldV,newV) -> applySecurity(cbUser.getScene())
        		);
    }

    /**
     * Must be called after @FXML initialize() b/c Scene won't be available
     */
    public void init() {
        applySecurity(cbUser.getScene());
    }

    class UserSecurity {

        private final String name;
        private final Map<CommandType, AccessType> access;

        public UserSecurity(String name, Map<CommandType, AccessType> access) {
            this.name = name;
            this.access = access;
        }

        public String getName() { return name; }

        public AccessType getAccess(CommandType command) {
            Objects.requireNonNull(access);
            AccessType a = access.get( command );
            Objects.requireNonNull(a);
            return a;
        }
    }

    private void applySecurity(Scene s) {
        if( log.isDebugEnabled() ) {
            log.debug("[APPLY]");
        }
        applySecurity(s, cbUser.getSelectionModel().getSelectedItem(), s.getRoot());
    }

    private void applySecurity(Scene s, UserSecurity security, Node n) {

        if( n == null ) return;

        if( n.getProperties().containsKey(NodePropertiesKeyType.commandType) ) {

            //
            // This is a Node that should have security applied
            //
            CommandType command = (CommandType) n.getProperties().get(NodePropertiesKeyType.commandType );
            AccessType access = security.getAccess(command);

            if( log.isDebugEnabled() ) {
                log.debug("[APPLY] command={}, access={}", command, access);
            }

            switch( security.getAccess(command) ) {

                case SHOW:
                    n.setVisible(true);
                    n.setDisable(false);
                    n.setManaged(true);
                    break;
                case HIDE:
                    n.setVisible(false);
                    n.setDisable(true);
                    n.setManaged(false);
                    break;
                case DISABLE:
                    n.setVisible(true);
                    n.setDisable(true);
                    n.setManaged(true);
                    break;
            }
        }

        //
        // Menus and MenuItems are not Nodes
        //
        if( n instanceof MenuBar ) {
            MenuBar mb = (MenuBar)n;
            for( Menu toplevel : mb.getMenus() ) {
                applySecurity( s, security, toplevel );
            }
        }

        if( n instanceof Parent) {
            Parent p = (Parent)n;
            for( Node childNode : p.getChildrenUnmodifiable() ) {
                applySecurity( s, security, childNode );
            }
            p.layout();
        }
    }

    private void applySecurity(Scene s, UserSecurity security, MenuItem mi) {

        if( mi == null ) return;

        if( mi.getProperties().containsKey(NodePropertiesKeyType.commandType) ) {

            //
            // This is a Node that should have security applied
            //
            CommandType command = (CommandType) mi.getProperties().get(NodePropertiesKeyType.commandType );
            AccessType access = security.getAccess(command);

            if( log.isDebugEnabled() ) {
                log.debug("[APPLY] command={}, access={}", command, access);
            }

            switch( security.getAccess(command) ) {

                case SHOW:
                    mi.setVisible(true);
                    mi.setDisable(false);
                    break;
                case HIDE:
                    mi.setVisible(false);
                    mi.setDisable(true);
                    break;
                case DISABLE:
                    mi.setVisible(true);
                    mi.setDisable(true);
                    break;
            }
        }

        if( mi instanceof Menu ) {
            Menu m = (Menu)mi;
            for( MenuItem childMI : m.getItems() ) {
                applySecurity( s, security, childMI );
            }
        }
    }

    @FXML
    public void close() {
        Platform.exit();
    }
}
