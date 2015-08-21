package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class PropertyManager {

	private static PropertyManager manager = null;
	private static Object managerLock = new Object();
	//当文件在src/目录下时：
	//private static String propertyName = "/config/model.properties";
	private static String propertyName = System.getProperty("user.dir") + "\\config\\model.properties";

	
    private Properties properties = null;
    private Object propertiesLock = new Object();
    private String resourceURI;
    
    private PropertyManager(String resourceURI) {
        this.resourceURI = resourceURI;
    }

    
	private PropertyManager() {
		
	}
	
	public static String getProperty(String name) {
		if (manager == null) {
            synchronized(managerLock) {
                if (manager == null) {
                    manager = new PropertyManager(propertyName);
                }
            }
        }
        return manager.getProp(name);
	}
	
    public static void setProperty(String name, String value) {
        if (manager == null) {
            synchronized(managerLock) {
                if (manager == null) {
                    manager = new PropertyManager(propertyName);
                }
            }
        }
        manager.setProp(name, value);
    }
	
    protected String getProp(String name) {
        //If properties aren't loaded yet. We also need to make this thread
        //safe, so synchronize...
        if (properties == null) {
            synchronized(propertiesLock) {
                //Need an additional check
                if (properties == null) {
                    loadProps();
                }
            }
        }
        String property = properties.getProperty(name);
        if (property == null) {
            return null;
        }
        else {
            return property.trim();
        }
    }
    
    protected void setProp(String name, String value) {
        //Only one thread should be writing to the file system at once.
        synchronized (propertiesLock) {
            //Create the properties object if necessary.
            if (properties == null) {
                loadProps();
            }
            properties.setProperty(name, value);
            saveProps();
        }
    }
    
    private void loadProps() {
        properties = new Properties();
        InputStream in = null;
        try {
        	//当文件在src/目录下时：
            //in = getClass().getResourceAsStream(resourceURI);
            in = new FileInputStream(resourceURI);
            properties.load(in);
        }
        catch (Exception e) {
            System.err.println("Error reading Yazd properties in PropertyManager.loadProps() " + e);
            e.printStackTrace();
        }
        finally {
            try {
                in.close();
            } catch (Exception e) { }
        }
    }
    
    private void saveProps() {
        //Now, save the properties to disk. In order for this to work, the user
        //needs to have set the path field in the properties file. Trim
        //the String to make sure there are no extra spaces.
        String path = properties.getProperty("path").trim();
        OutputStream out = null;
        try {
            out = new FileOutputStream(path);
            properties.store(out, "yazd.properties -- " + (new java.util.Date()));
        }
        catch (Exception ioe) {
            System.err.println("There was an error writing yazd.properties to " + path + ". " +
                "Ensure that the path exists and that the Yazd process has permission " +
                "to write to it -- " + ioe);
            ioe.printStackTrace();
        }
        finally {
            try {
               out.close();
            } catch (Exception e) { }
        }
    }
    
    public static void main(String[] args) {
    	 
    	Properties properties = new Properties();
        InputStream in = null;
        PropertyManager class1 = new PropertyManager("");
        
        try {
            in = PropertyManager.class.getClass().getResourceAsStream(System.getProperty("user.dir") + "\\config\\model.properties");
            in = PropertyManager.class.getClass().getResourceAsStream("F:/workspace/smstransmitting/config/model.properties");
        
            in = new FileInputStream(System.getProperty("user.dir") + "\\config\\model.properties");
        
            properties.load(in);
        }
        catch (Exception e) {
           e.printStackTrace();
        }
        finally {
            try {
                in.close();
            } catch (Exception e) { }
        }
    }
}
