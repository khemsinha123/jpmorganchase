package com.jpmorgan.integration;

import io.restassured.RestAssured;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.util.Properties;


public class IntegrationTestBase {

    @Value("${base_url}")
    private static final Properties m_properties = new Properties();

        protected static String getProperty(String key) {
            return getProperty(key, null);
        }

        protected static String getProperty(String key, String defaultValue) {
            //TODO: Load both the properties once.
            try (InputStream fis = IntegrationTestBase.class.getResourceAsStream("/build.properties")) {
                loadProperty(fis);
            } catch (FileNotFoundException exp) {
                System.err.println("Error while loading file 'build.properties'. The file may not exixts.");
                System.exit(0);
            } catch (IOException e) {
                System.err.println("Error while loading file 'build.properties'.");
                System.exit(0);
            }

            return m_properties.getProperty(key, defaultValue);
        }

        private synchronized static void loadProperty(InputStream fis) throws IOException {
            m_properties.load(fis);
        }


}

