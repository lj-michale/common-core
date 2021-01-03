package common.core.apollo;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;

/**
 * 初始化Apollo运行环境
 */
public class InitEnvListener implements ApplicationListener<ApplicationStartedEvent> {

    public static final String LOCAL_PROPERTIES_CLASSPATH = "/local.properties";
    public static final String APOLLO_CACHE_DIR_KEY = "apollo.cacheDir";
    public static final String APOLLO_CACHE_FOLDER = "apolloConfig";

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {

        // 判断 JVM system property 'env' 中是否有值，没有则设置为 dev

        if (StringUtils.isEmpty(System.getProperty("env"))) {
            System.setProperty("env", "dev");
        }


        // 若是 dev环境，尝试读取 local.properties 中的参数加载到 jvm system property 中
        if ("dev".equalsIgnoreCase(System.getProperty("env"))) {
            try {
                Resource resource = new PathMatchingResourcePatternResolver().getResource(LOCAL_PROPERTIES_CLASSPATH);
                if (resource.exists()) {
                    Properties properties = new Properties();
                    properties.load(resource.getInputStream());

                    Iterator<String> names = properties.stringPropertyNames().iterator();
                    String name;
                    while (names.hasNext()) {
                        name = names.next();
                        System.setProperty(name, properties.getProperty(name));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 如果是prod环境，尝试设置Apollo缓存路径到jar包所在文件夹
        if ("prod".equalsIgnoreCase(System.getProperty("env"))
                && StringUtils.isEmpty(System.getProperty(APOLLO_CACHE_DIR_KEY))) {
            String appPath = getAppPath();
            System.out.println(appPath);
            if (!StringUtils.isEmpty(appPath)) {
                System.setProperty(APOLLO_CACHE_DIR_KEY, appPath + "/" + APOLLO_CACHE_FOLDER);
            }
        }

    }

    /**
     * 获取当前运行路径
     *
     * @return
     */
    public static String getAppPath() {
        String realPath = null;
        try {
            Class cls = InitEnvListener.class;
            ClassLoader loader = cls.getClassLoader();
            //获得类的全名，包括包名
            String clsName = cls.getName() + ".class";
            //获得传入参数所在的包
            Package pack = cls.getPackage();
            String path = "";
            //如果不是匿名包，将包名转化为路径
            if (pack != null) {
                String packName = pack.getName();
                //在类的名称中，去掉包名的部分，获得类的文件名
                clsName = clsName.substring(packName.length() + 1);
                //判定包名是否是简单包名，如果是，则直接将包名转换为路径，
                if (packName.indexOf(".") < 0) {
                    path = packName + "/";
                } else {//否则按照包名的组成部分，将包名转换为路径
                    int start = 0, end = 0;
                    end = packName.indexOf(".");
                    while (end != -1) {
                        path = path + packName.substring(start, end) + "/";
                        start = end + 1;
                        end = packName.indexOf(".", start);
                    }
                    path = path + packName.substring(start) + "/";
                }
            }
            //调用ClassLoader的getResource方法，传入包含路径信息的类文件名
            java.net.URL url = loader.getResource(path + clsName);
            //从URL对象中获取路径信息
            realPath = url.getPath();
            //去掉路径信息中的协议名"file:"
            int pos = realPath.indexOf("file:");
            if (pos > -1) {
                realPath = realPath.substring(pos + 5);
            }
            //去掉路径信息最后包含类文件信息的部分，得到类所在的路径
            pos = realPath.indexOf(path + clsName);
            realPath = realPath.substring(0, pos - 1);
            //如果类文件被打包到JAR等文件中时，去掉对应的JAR等打包文件名
            if (realPath.endsWith("!")) {
                realPath = realPath.substring(0, realPath.lastIndexOf("/"));
            }

            // 获取运行jar包路径
            realPath = realPath.substring(0, realPath.indexOf(".jar"));
            realPath = realPath.substring(0, realPath.lastIndexOf("/"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return realPath;
    }

}
