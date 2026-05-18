package com.utilities;

public class Utilities {
    public static String  propertyFileReader(String Data,String Module) {
        String osName=System.getProperty("os.name").toLowerCase();
        PropertyFileReader prop;
        String KeyValue;
        if(osName.contains("mac") || osName.contains("linux")){
            prop = new PropertyFileReader(".//properties//testdata//"+Module+".properties");
            KeyValue = prop.getproperty(Data);
        }else {
            prop = new PropertyFileReader(".\\properties\\testdata\\"+Module+".properties");
            KeyValue = prop.getproperty(Data);
        }
        return KeyValue;
    }
}
