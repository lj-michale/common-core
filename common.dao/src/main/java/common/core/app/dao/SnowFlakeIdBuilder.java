package common.core.app.dao;

import common.core.app.dao.id.IdCreate;
import common.core.common.util.DigestUtils;
import common.core.common.util.StringUtil;
import common.core.common.util.UuidUtil;

import java.net.InetAddress;

/**
 * @Auther: oyxy
 * @Date: 2018/11/6 14:31
 * @Description:
 */
public class SnowFlakeIdBuilder {

    public static String build() {
        return String.valueOf(IdCreate.getIdCreate().nextId());
    }

    public static void main(String[] args) {

        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            System.out.println(SnowFlakeIdBuilder.build());
        }

        System.out.println(System.currentTimeMillis() - start);
    }

}
