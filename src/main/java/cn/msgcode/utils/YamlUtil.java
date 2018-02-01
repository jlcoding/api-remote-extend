package cn.msgcode.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * yaml配置读取
 * Created by jason on 17-3-28.
 */
public class YamlUtil implements PropertySourceFactory {

    private final static Logger logger = LoggerFactory.getLogger(YamlUtil.class);

    private static Map<String, Object> propertiesMap = new HashMap<>();

    private static List<String> readFiles = new ArrayList<>();

    private static String profile = null;


    public static void refresh() {
        for(String fileName : readFiles) {
            loadYaml(fileName);
        }
    }

    public static Object getProperty(String name) {
        return propertiesMap.get(name);
    }

    public static String getPropertyAsString(String name) {
        return (String)propertiesMap.get(name);
    }

    public static Integer getPropertyAsInteger(String name) {
        return (Integer)propertiesMap.get(name);
    }

    private static void loadYaml(String fileName) {
        YamlPropertySourceLoader loader = new YamlPropertySourceLoader();
        URL url = YamlUtil.class.getClassLoader().getResource(fileName);
        PropertySource propertySource = null;
        try {
            propertySource = loader.load(fileName, new FileSystemResource(url.getFile()), null);
            propertiesMap.putAll((Map<? extends String, ? extends String>) propertySource.getSource());
            logger.info("load ".concat(fileName).concat(" success"));

        } catch (IOException e) {
            logger.error("load yaml fail", e);
        }

    }

    private PropertySource loadYaml(String fileName, Resource resource) {
        YamlPropertySourceLoader loader = new YamlPropertySourceLoader();
        PropertySource propertySource = null;
        try {
            propertySource = loader.load(fileName, resource, null);
            propertiesMap.putAll((Map<? extends String, ? extends String>) propertySource.getSource());
            logger.info("load ".concat(fileName).concat(" success"));
            return propertySource;
        } catch (IOException e) {
            logger.error("load yaml fail", e);
        }
        return null;
    }

    @Override
    public PropertySource<?> createPropertySource(String s, EncodedResource encodedResource) throws IOException {
        Resource resource = encodedResource.getResource();
        return loadYaml(resource.getFilename(), resource);
    }
}
