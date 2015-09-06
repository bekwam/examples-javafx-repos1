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
package com.bekwam.examples.threading.sync;

/**
 * @author carl_000
 */
public class SyncDAOMain {

    public static void main(String[] args) {

        System.out.println("****** blocking queue");
        SyncDAOWrapper dao = new SyncDAOWrapper();
        try {
            for( int i=0; i<10; i++ ) {
                String data = dao.fetchData();
                System.out.println("data retrieved=" + data);
            }
        } catch(SyncDAOException exc) {
            exc.printStackTrace();
        }

        System.out.println("****** busy loop");
        AsyncDAO.reset();

        SyncDAOWrapperBusy daoBusy = new SyncDAOWrapperBusy();
        try {
            for( int i=0; i<10; i++ ) {
                String data = daoBusy.fetchData();
                System.out.println("data retrieved=" + data);
            }
        } catch(SyncDAOException exc) {
            exc.printStackTrace();
        }

        System.out.println("****** wait/notify");
        AsyncDAO.reset();

        SyncDAOWrapperWaitNotify daoWaitNotify = new SyncDAOWrapperWaitNotify();
        try {
            for( int i=0; i<10; i++ ) {
                String data = daoWaitNotify.fetchData();
                System.out.println("data retrieved=" + data);
            }
        } catch(SyncDAOException exc) {
            exc.printStackTrace();
        }

    }
}
