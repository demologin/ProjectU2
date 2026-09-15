package com.javarush.khmelov.lesson17.p10_strategy;

import java.io.File;

class FileAuth implements AuthStrategy{
    
    private final File file;

    FileAuth(File file) {
        this.file = file;
    }
    

    @Override
    public boolean checkLogin(String name, String password) {
        System.out.println("Checking with file...");
        return checkFromFile();
    }
    
    private boolean checkFromFile(){
         // считывание из файла данных
        
        return true;
    }
 
}
