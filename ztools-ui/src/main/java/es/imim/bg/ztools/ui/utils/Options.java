package es.imim.bg.ztools.ui.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Options {

	/*private static final String appPath = 
		System.getProperty("user.dir", ".");*/
	
	private static final String userPath = 
		System.getProperty("user.home", ".");
	
	private static final String configPath =
		userPath + File.separator + ".ztools";
	
	private static final String configFileName = "ztools-ui.xml";
	
	private static final String configFile = 
		configPath + File.separator + configFileName;
	
	private static final Options instance = new Options();
	
	private Properties props;
	
	public static Options instance() {
		return instance;
	}
	
	private Options() {
		props = load(configFile, getDefaults());
	}
	
	private Properties getDefaults() {
		Properties defaults = new Properties();
		return defaults;
	}
	
	public Properties load(String file, Properties defaults) {
		Properties config = new Properties();
		try {
			config.loadFromXML(
					new BufferedInputStream(
							new FileInputStream(file)));
		} catch (FileNotFoundException e) {
			System.err.println("Configuration file doesn't exist: " + configFile);
			System.err.println("Created one with defaults.");
			save(defaults);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Properties properties = new Properties(System.getProperties());
		properties.putAll(defaults);
		properties.putAll(config);
		return properties;
	}
	
	public void save() {
		save(props);
	}
	
	public void save(Properties properties) {
		try {
			File path = new File(configPath);
			if (!path.exists())
				path.mkdirs();
			
			properties.storeToXML(
					new FileOutputStream(configFile), "Options");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getLastPath() {
		return props.getProperty("lastpath", userPath);
	}

	public void setLastPath(String lastPath) {
		props.setProperty("lastpath", lastPath);
	}

}
