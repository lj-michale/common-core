package common.core.app.dao;

import common.core.app.dao.id.IdCreate;
import common.core.common.util.DigestUtils;
import common.core.common.util.StringUtil;
import common.core.common.util.UuidUtil;

public class DefaultIdBuilder {

    public static String build() {

        return new StringBuffer().append(StringUtil.convertString16To32(Long.toHexString(System.currentTimeMillis()))).append(DigestUtils.md5With8(UuidUtil.generateFullUuid())).toString();
    }


    public static void main(String[] args) {
        System.out.println(DefaultIdBuilder.build());
    }


}
