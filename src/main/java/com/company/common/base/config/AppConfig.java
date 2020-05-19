package com.company.common.base.config;

import java.util.Iterator;
import org.apache.commons.configuration.Configuration;

/**
 * Application Config.
 */
public interface AppConfig {

  String getString(String key, String defaultValue);

  String getString(String key);

  int getInt(String key, int defaultValue);

  int getInt(String key);

  long getLong(String key, long defaultValue);

  long getLong(String key);

  double getDouble(String key, double defaultValue);

  double getDouble(String key);

  boolean getBoolean(String key, boolean defaultValue);

  boolean getBoolean(String key);

  Iterator<String> getKeys(String prefix);

  Configuration getUnderlyingConfig();

  /**
   * Sets an instance-level override. This will trump everything including dynamic properties and
   * system properties. Useful for tests.
   *
   * @param key the specified key.
   * @param value the specified value.
   */
  void setOverrideProperty(String key, Object value);
}
