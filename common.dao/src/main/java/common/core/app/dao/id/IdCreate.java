package common.core.app.dao.id;


import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: oyxy
 * @Date: 2018/10/29 20:49
 * @Description: ID生成器
 */
public class IdCreate {


    private IdCreate() {
    }

    private static SnowFlake idCreate = null;

    static {
        idCreate = new SnowFlake(1l, ipToLong(IpAdrressUtil.resolveLocalIps().iterator().next()));
    }

    public static synchronized SnowFlake getIdCreate() {
        if (idCreate == null) {
            idCreate = new SnowFlake(1l, ipToLong(IpAdrressUtil.resolveLocalIps().iterator().next()));
        }
        return idCreate;
    }

    private static long ipToLong(String ip) {
        String[] ipArray = ip.split("\\.");
        List ipNums = new ArrayList();
        for (int i = 0; i < ipArray.length; ++i) {
            ipNums.add(Long.valueOf(Long.parseLong(ipArray[i].trim())));
        }
        long ZhongIPNumTotal = ((Long) ipNums.get(0)).longValue();

        return ZhongIPNumTotal;
    }

}
