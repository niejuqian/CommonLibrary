package com.laonie.common.enuns;

/**
 * @AUTHOR : niejiuqian
 * @DATETIME: 2017-08-08 11:15
 * @DESCRIPTION:
 *          服务器地址
 */

public enum ServiceEnvEnum {
    DEV("dev","192.168.1.34"),
    TEST("test","cpsyshtest.cunpiao.com"),
    PER("per","cpsyshtest.cunpiao.com"),
    ONLINE("online","cpsyshtest.cunpiao.com"),
    ;
    String key;
    String host;
    ServiceEnvEnum(String key, String host) {
        this.key = key;
        this.host = host;
    }

    public static ServiceEnvEnum getByKey(String key) {
        ServiceEnvEnum env = ONLINE;
        for (ServiceEnvEnum httpEnv : ServiceEnvEnum.values()) {
            if (httpEnv.getKey().equalsIgnoreCase(key)){
                env = httpEnv;
                break;
            }
        }
        return env;
    }

    public String getKey() {
        return key;
    }

    public String getHost() {
        return host;
    }
}
