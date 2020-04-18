package com.company.common.base.services.config;

import com.company.common.base.config.AppConfig;
import com.google.common.base.Optional;
import com.netflix.config.ConcurrentCompositeConfiguration;
import com.netflix.config.ConfigurationManager;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.PropertyWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration.Configuration;

import java.util.Iterator;
import java.util.function.BiFunction;

/**
 * App config backed by Archaius.
 */
@Slf4j
public class ArchaiusAppConfig implements AppConfig {

  public ArchaiusAppConfig() {
  }

  @Deprecated
  @Override
  public String getString(final String key, final String defaultValue) {
    return getAndWarn(key, defaultValue, (x, y) -> DynamicPropertyFactory.getInstance().getStringProperty(x, y));
  }

  public String getString(final String key) {
    return get(key, "", (x, y) -> DynamicPropertyFactory.getInstance().getStringProperty(x, y));
  }

  @Override
  public int getInt(final String key, final int defaultValue) {
    return getAndWarn(key, defaultValue, (x, y) -> DynamicPropertyFactory.getInstance().getIntProperty(x, y));
  }

  @Override
  public int getInt(final String key) {
    return get(key, 0, (x, y) -> DynamicPropertyFactory.getInstance().getIntProperty(x, y));
  }

  @Override
  public long getLong(final String key, final long defaultValue) {
    return getAndWarn(key, defaultValue, (x, y) -> DynamicPropertyFactory.getInstance().getLongProperty(x, y));
  }

  @Override
  public long getLong(final String key) {
    return get(key, 0L, (x, y) -> DynamicPropertyFactory.getInstance().getLongProperty(x, y));
  }

  @Override
  public double getDouble(final String key, final double defaultValue) {
    return getAndWarn(key, defaultValue, (x, y) -> DynamicPropertyFactory.getInstance().getDoubleProperty(x, y));
  }

  @Override
  public double getDouble(final String key) {
    return get(key, 0.0, (x, y) -> DynamicPropertyFactory.getInstance().getDoubleProperty(x, y));
  }

  @Override
  public boolean getBoolean(final String key, final boolean defaultValue) {
    return getAndWarn(key, defaultValue, (x, y) -> DynamicPropertyFactory.getInstance().getBooleanProperty(x, y));
  }

  @Override
  public boolean getBoolean(final String key) {
    return get(key, false, (x, y) -> DynamicPropertyFactory.getInstance().getBooleanProperty(x, y));
  }

  @Override
  public Iterator<String> getKeys(String prefix) {
    return ConfigurationManager.getConfigInstance().getKeys(prefix);
  }

  @Override
  public Configuration getUnderlyingConfig() {
    return ConfigurationManager.getConfigInstance();
  }

  /**
   * Sets an instance-level override. This will trump everything including
   * dynamic properties and system properties. Useful for tests.
   *
   * @param key   the specified key.
   * @param value the specified value.
   */
  @Override
  public void setOverrideProperty(final String key, final Object value) {
    ((ConcurrentCompositeConfiguration) ConfigurationManager.getConfigInstance()).setOverrideProperty(key, value);
  }

  private <T> T get(final String key, final T defaultValue, BiFunction<String, T, PropertyWrapper<T>> function) {
    PropertyWrapper<T> property = function.apply(key, defaultValue);

    Optional<?> cachedValue = property.getDynamicProperty().getCachedValue(getType(defaultValue));
    if (!cachedValue.isPresent()) {
      String message = "No value for key <" + key + ">";
      log.warn(message);
      throw new IllegalStateException(message);
    }

    return property.getValue();
  }

  private <T> T getAndWarn(final String key, final T defaultValue, BiFunction<String, T, PropertyWrapper<T>> function) {
    PropertyWrapper<T> property = function.apply(key, defaultValue);

    Optional<?> cachedValue = property.getDynamicProperty().getCachedValue(getType(defaultValue));
    if (!cachedValue.isPresent()) {
      log.warn("A default config value, <" + defaultValue + "> was provided for <" + key + ">");
    }

    return property.getValue();
  }

  private <T> Class<?> getType(T defaultValue) {
    if (defaultValue != null) {
      return defaultValue.getClass();
    }
    return null;
  }
}
