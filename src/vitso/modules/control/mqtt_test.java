package vitso.modules.control;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class mqtt_test {
	private static final String BUNDLE_NAME = "vitso.modules.control.mqtt_test"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	private mqtt_test() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
