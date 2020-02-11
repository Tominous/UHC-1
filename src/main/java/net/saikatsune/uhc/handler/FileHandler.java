package net.saikatsune.uhc.handler;

import java.io.File;

public class FileHandler {

    public boolean deleteFiles(File path) {
        if (path.exists()) {
            File[] files = path.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteFiles(file);
                } else {
                    file.delete();
                }
            }
        }
        return (path.delete());
    }

    public boolean isNumeric(String string) {
        try {
            Double d = Double.parseDouble(string);
        } catch (NumberFormatException nfe) {
            double d;
            return false;
        }
        return true;
    }


}
