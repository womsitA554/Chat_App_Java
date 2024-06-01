package com.example.chat_app_java.FCM;

import android.util.Log;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.common.collect.Lists;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class AccessToken {
    private static final String firebaseMessagingScope = "https://www.googleapis.com/auth/firebase.messaging";

    public static String getAccessToken(){
        try {
            String jsonString= "{\n" +
                    "  \"type\": \"service_account\",\n" +
                    "  \"project_id\": \"chat-app-d377d\",\n" +
                    "  \"private_key_id\": \"d1a1eb633fa914dbc1393a6887839e1c074be4b0\",\n" +
                    "  \"private_key\": \"-----BEGIN PRIVATE KEY-----\\nMIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDYrx7KtdHq9AIg\\nOms5NoCJm0kY3TuHWhGrjuvxY5oj3HqSzl1/s3IAONQezkXF9nyz6JapRgEBuET9\\ni+HzLCyI3OiGDVrDtueSNw/3Sd22Jelefb4ADAKoi2TGthcQy5fHemKO46GrBFPg\\nMht9mOnOsu65MggR4n29nN3JSLOuZKmAfo8a195MhR9C6k5EH1Boka5zhlxiFtYQ\\nGvZjHezv36Soko+v+nMT9e/Jxu80/njRj2c1Xxi8lxBsZhY8bcYhdbRZwZsIHAgP\\nqtEQgkdT3xLouCWNmPy/HxjKkT0N8woRT98eY6WyM/XdV4zKrkKm+dIYlKbpqVLe\\nuWzt7UdhAgMBAAECggEATBevSgNwrMA0sOmi9sv/dpNsJ4mlWoJUOzls/O971UUb\\nXEuRBQPYsGBEiW7pjLvjDfDLW7VkLBzwSVOirNkh6IwfMdglvB4Sk0O33SD49Xq5\\nyb+BhhWxp0uQP8H5NqIwclI1ozUn9F0IMW6XPdOBo98tOybpM5Kkkn4wWnun8cDt\\nY9qS6XSLhlQML7843cUjlK+EIVL9JE1TZf7fugZNI9ViJzMIbz0EHjzWSXpu9BzO\\nTsxGk40zYrWdwxEHyTKp3VgTGd/Lfw7seznqZmXm66wnlWe1513MzU3lCHvMuwdc\\nYxH0IbCZ+DqM9Hbuq0vC6Eg3bv/zvtb9vKcLbL/LgwKBgQDsX7PdUSykbXzK21PF\\nkpQwmO8KXean7J+rFsxX0oCqVP5GMXQP6z1I9BFTf3zqG1V0FjgR2hWRkAx8j8M4\\n8Gs7p7wrhl3P77tTfo2NQD7zExDVcKU1VlShla/GHYInm58eAQxW06QjGt9pxIs9\\neQ5orWmN5lsjh2d3oNCoDEEm2wKBgQDqrOWxkg7KwsGI1lEwdQJ9ygzfJtBTZfAE\\nHFCnCsV7CzLnYfGR8ly4c5YNEpd6h5nrWNTI5y70cihAniFjstz7Y6anMAyQnY8z\\nZ9NIF7G7KSDq6+Sx4R6RZgQx8O4qzMbjoft+r3C1xC3/OsxO8Cf9H9fqX0Ut0EsG\\n3cWeQftpcwKBgQDXQLvJ4p81yaLoFN1vwXED5HFizmo7J8erhSJnI4IaGzZHc8eO\\nq5lusxyIHkQB6Qxjn8xFrYUQvs4wLLcKRh5RAAni1QTfVCASA+Ah7oykzQr6ZA9+\\nx/RgBbFBSCP7r8MyDqBO/YKumU00w1BNlc0iVDEYkNsUOr1lemQpdKdsGwKBgHhM\\nFxa2k4SW31TA4C+h25wVxhFiOSp3N9kzlph6ag8QB5Ea4RV6ktMu1T1wSVxTRLCj\\nAbjW4XgHSOEO7njICIWJaLWNaPN9ARpN3721/7K8e5ZRRBN3Hg9Up1PeeIhSHqYz\\ns9MWJgXXQejzACZ671TPTQRkv/ajLsKfTp04n6NHAoGAAhI9zlyP7tXIqYy+J65b\\n6PT4PAOCLPrWkYEc2yOnc1UHMszSPvGGlOC7XC9M2epEBR2GTENC6mVdGgg7fILY\\nxaMZKoomswoIge7gZR7qkfgOMHRjBtB5vlCsmP6h+3qSRfoWVLT/AEfTeHm/Qi1h\\nn1QoIypYrU7c/XP34QJxq+U=\\n-----END PRIVATE KEY-----\\n\",\n" +
                    "  \"client_email\": \"firebase-adminsdk-mozz1@chat-app-d377d.iam.gserviceaccount.com\",\n" +
                    "  \"client_id\": \"106328712192289092716\",\n" +
                    "  \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",\n" +
                    "  \"token_uri\": \"https://oauth2.googleapis.com/token\",\n" +
                    "  \"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\",\n" +
                    "  \"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/firebase-adminsdk-mozz1%40chat-app-d377d.iam.gserviceaccount.com\",\n" +
                    "  \"universe_domain\": \"googleapis.com\"\n" +
                    "}\n";

            InputStream stream = new ByteArrayInputStream(jsonString.getBytes(StandardCharsets.UTF_8));
            GoogleCredentials googleCredentials = GoogleCredentials.fromStream(stream).createScoped(Lists.newArrayList(firebaseMessagingScope));
            googleCredentials.refresh();
            return googleCredentials.getAccessToken().getTokenValue();
        } catch (IOException e){
            Log.e("Error","" + e.getMessage());
            return null;
        }
    }
}
