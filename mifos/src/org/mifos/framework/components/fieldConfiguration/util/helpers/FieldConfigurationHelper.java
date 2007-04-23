package org.mifos.framework.components.fieldConfiguration.util.helpers;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;

import org.mifos.application.configuration.business.MifosConfiguration;
import org.mifos.application.configuration.exceptions.ConfigurationException;

public class FieldConfigurationHelper {

	public static String getLocalSpecificFieldNames(String fieldName,
			Locale locale) {
		try {
			String configuredLabel = getConfiguredFieldName(fieldName, locale);
			if (configuredLabel != null) {
				return configuredLabel;
			}
			PropertyResourceBundle propertyString = 
				(PropertyResourceBundle) PropertyResourceBundle
					.getBundle(
							FieldConfigurationConstant.FIELD_CONF_PROPERTYFILE,
							locale);
			return propertyString.getString(fieldName);
		}
		catch (MissingResourceException e) {
			/* I think the theory here is that it is better to show
			   the user something, than just make it an internal
			   error. Not sure whether that is what is going on for
			   sure, though. */
			return fieldName;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String getConfiguredFieldName(String fieldName, Locale locale) {
		try {
			String labelName = fieldName.substring(fieldName.indexOf(".") + 1);
			labelName = MifosConfiguration.getInstance().getLabel(labelName,
					locale);
			if (labelName != null) {
				return labelName;
			}
			else {
				return null;
			}
		}
		catch (ConfigurationException e) {
			throw new RuntimeException(e);
		}
	}

}
