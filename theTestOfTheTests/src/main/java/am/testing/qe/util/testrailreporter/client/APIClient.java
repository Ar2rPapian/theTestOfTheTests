package am.testing.qe.util.testrailreporter.client;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

class APIClient {

    private HttpClient httpClient;
    private String url;

    APIClient(String url, String user, String password) {
        try {
            List<Header> headerList = new ArrayList<>();
            headerList.add(new BasicHeader("Content-Type", "application/json"));
            headerList.add(new BasicHeader("Authorization", "Basic " + getAuthorization(user, password)));

            httpClient = HttpClientBuilder.create().setDefaultHeaders(headerList).build();
            this.url = url + "/index.php?/api/v2/";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String getAuthorization(String user, String password) {
        return getBase64((user + ":" + password).getBytes(StandardCharsets.UTF_8));
    }

    private String getBase64(byte[] buffer) {
        final char[] map = {
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
                'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
                'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd',
                'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
                'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
                'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7',
                '8', '9', '+', '/'
        };

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < buffer.length; i++) {
            byte b0 = buffer[i++], b1 = 0, b2 = 0;

            int bytes = 3;
            if (i < buffer.length) {
                b1 = buffer[i++];
                if (i < buffer.length) {
                    b2 = buffer[i];
                } else {
                    bytes = 2;
                }
            } else {
                bytes = 1;
            }

            int total = (b0 << 16) | (b1 << 8) | b2;

            switch (bytes) {
                case 3:
                    sb.append(map[(total >> 18) & 0x3f]);
                    sb.append(map[(total >> 12) & 0x3f]);
                    sb.append(map[(total >> 6) & 0x3f]);
                    sb.append(map[total & 0x3f]);
                    break;

                case 2:
                    sb.append(map[(total >> 18) & 0x3f]);
                    sb.append(map[(total >> 12) & 0x3f]);
                    sb.append(map[(total >> 6) & 0x3f]);
                    sb.append('=');
                    break;

                case 1:
                    sb.append(map[(total >> 18) & 0x3f]);
                    sb.append(map[(total >> 12) & 0x3f]);
                    sb.append('=');
                    sb.append('=');
                    break;
            }
        }

        return sb.toString();
    }

    String invokeHttpGet(String uriSuffix) throws IOException, ClientException {
        HttpGet httpGet = new HttpGet(url + uriSuffix);
        return consumeResponse(httpClient.execute(httpGet));
    }

    String invokeHttpPost(String uriSuffix, String jsonData) throws IOException, ClientException {
        HttpPost httpPost = new HttpPost(url + uriSuffix);
        StringEntity reqEntity = new StringEntity(jsonData);
        httpPost.setEntity(reqEntity);
        return consumeResponse(httpClient.execute(httpPost));
    }

    private String consumeResponse(HttpResponse response) throws ClientException, IOException {
        int status = response.getStatusLine().getStatusCode();
        HttpEntity entity = response.getEntity();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        entity.writeTo(os);
        String content = os.toString("UTF-8");
        if (status != 200) {
            throw new ClientException("Received status code " + status + " with content '" + content + "'");
        }

        return content;
    }

}