package common.core.apollo;

import com.ctrip.framework.apollo.core.enums.Env;
import com.ctrip.framework.apollo.core.spi.MetaServerProvider;
import com.ctrip.framework.apollo.core.utils.ResourceUtils;
import com.google.common.base.Strings;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * For legacy meta server configuration use, i.e. apollo-env.properties
 */
public class YtcMetaServerProvider implements MetaServerProvider {
    // make it as lowest as possible, yet not the lowest
    public static final int ORDER = MetaServerProvider.HIGHEST_PRECEDENCE;
    private static final Map<String, String> domains = new HashMap<>();

    public YtcMetaServerProvider() {
        initialize();
    }

    private void initialize() {
        Properties prop = new Properties();
        prop = ResourceUtils.readConfigFile("apollo-env.properties", prop);

        domains.put("dev", getMetaServerAddress(prop, "dev_meta", "dev.meta"));
        domains.put("test", getMetaServerAddress(prop, "test_meta", "test.meta"));
        domains.put("pre", getMetaServerAddress(prop, "pre_meta", "pre.meta"));
        domains.put("prod", getMetaServerAddress(prop, "prod_meta", "prod.meta"));
    }

    private String getMetaServerAddress(Properties prop, String sourceName, String propName) {
        // 1. Get from System Property.
        String metaAddress = System.getProperty(sourceName);
        if (Strings.isNullOrEmpty(metaAddress)) {
            // 2. Get from OS environment variable, which could not contain dot and is normally in UPPER case,like DEV_META.
            metaAddress = System.getenv(sourceName.toUpperCase());
        }
        if (Strings.isNullOrEmpty(metaAddress)) {
            // 3. Get from properties file.
            metaAddress = prop.getProperty(propName);
        }
        return metaAddress;
    }

    @Override
    public String getMetaServerAddress(Env targetEnv) {

        String env = System.getProperty("env");

        if (!Strings.isNullOrEmpty(env)) {
            env = env.toLowerCase();
        }

        String metaServerAddress = domains.get(env);
        return metaServerAddress == null ? null : metaServerAddress.trim();
    }

    @Override
    public int getOrder() {
        return ORDER;
    }
}
