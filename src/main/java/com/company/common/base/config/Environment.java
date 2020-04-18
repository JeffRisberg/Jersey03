package com.company.common.base.config;

import com.netflix.config.ConfigurationManager;

import java.util.Optional;

/**
 */
public class Environment {
    private static final String PROD    = "prod";
    private static final String STAGING = "staging";
    private static final String TEST    = "test";
    private static final String DEV     = "dev";

    /**
     * Return the current environment String (set by -Darchaius.deployment.environment).
     * When you run unit/integration tests, this is set to 'test' in the Maven test config
     * (see pom.xml in the root project).
     *
     * @return the current environment String
     */
    public static String safeGetEnvironment() {
        return Optional.ofNullable(ConfigurationManager.getDeploymentContext().getDeploymentEnvironment())
            .orElseThrow(() -> new IllegalStateException(
                "No deployment environment found. Call ConfigInitializer.getInstance().init() first."));
    }

    /**
     * Return the current environment String (set by -Darchaius.deployment.environment).
     * When you run unit/integration tests, this is set to 'test' in the Maven test config
     * (see pom.xml in the root project).
     *
     * @return the current environment String
     */
    static String forceGetEnvironment() {
        return ConfigurationManager.getDeploymentContext().getDeploymentEnvironment();
    }

    /**
     * Return true if the current environment has a 'prod' prefix; false otherwise.
     *
     * @return true if the current environment has a 'prod' prefix; false otherwise.
     */
    public static boolean isProduction() {
        return safeGetEnvironment().startsWith(PROD);
    }

    /**
     * Return true if the current environment has a 'staging' prefix; false otherwise.
     *
     * @return true if the current environment has a 'staging' prefix; false otherwise.
     */
    public static boolean isStaging() {
        return safeGetEnvironment().startsWith(STAGING);
    }

    /**
     * Return true if the current environment has a 'test' prefix; false otherwise.
     *
     * @return true if the current environment has a 'test' prefix; false otherwise.
     */
    public static boolean isTest() {
        return safeGetEnvironment().startsWith(TEST);
    }

    /**
     * Return true if the current environment has a 'dev' prefix; false otherwise.
     *
     * @return true if the current environment has a 'dev' prefix; false otherwise.
     */
    public static boolean isDev() {
        return safeGetEnvironment().startsWith(DEV);
    }
}
