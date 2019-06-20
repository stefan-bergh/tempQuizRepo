package Rest;

import Rest.dto.BaseRequestDTO;
import com.google.gson.Gson;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public abstract class RESTClientBase {
    private final Gson gson = new Gson();

    abstract String getBaseURL();

    private <T> T executeRequest(HttpUriRequest request, Class<T> clazz) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(request)) {
            return gson.fromJson(EntityUtils.toString(response.getEntity()), clazz);
        } catch (Exception exc) {
            return null;
        }
    }

    <T> T executeQueryGet(String queryGet, Class<T> clazz) {
        HttpGet httpGet = new HttpGet(getBaseURL() + queryGet);
        return executeRequest(httpGet, clazz);
    }

    <T> T executeQueryPost(BaseRequestDTO request, String queryPost, Class<T> clazz) {
        HttpPost httpPost = new HttpPost(getBaseURL() + queryPost);
        httpPost.addHeader("content-type", "application/json");
        try {
            httpPost.setEntity(new StringEntity(gson.toJson(request)));
        } catch (Exception exc) {
            return null;
        }
        return executeRequest(httpPost, clazz);
    }

    <T> T executeQueryPut(BaseRequestDTO request, String queryPut, Class<T> clazz) {
        HttpPut httpPut = new HttpPut(getBaseURL() + queryPut);
        httpPut.addHeader("content-type", "application/json");
        try {
            httpPut.setEntity(new StringEntity(gson.toJson(request)));
        } catch (Exception exc) {
            return null;
        }
        return executeRequest(httpPut, clazz);
    }

    <T> T executeQueryDelete(String queryDelete, Class<T> clazz) {
        HttpDelete httpDelete = new HttpDelete(getBaseURL() + queryDelete);
        return executeRequest(httpDelete, clazz);
    }
}
