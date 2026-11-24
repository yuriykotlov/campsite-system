package org.example.managers;

import java.io.*;

public class DataPersistence {
    private static final String campsiteSystemSave = "CampsiteSystem.dat";

    public static void saveCampsiteSystem(CampsiteSystem campsiteSystem) throws IOException{
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(campsiteSystemSave))){
            out.writeObject(campsiteSystem);
        }
    }

    public static CampsiteSystem loadCampsiteSystem() throws IOException, ClassNotFoundException{
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(campsiteSystemSave))){
            return (CampsiteSystem)in.readObject();
        }
    }
}
