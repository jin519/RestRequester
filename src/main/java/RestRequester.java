import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.util.LinkedHashMap;

/**
 * 인자로 받은 request 정보를 바탕으로<br>
 * REST API를 제공하는 서버와 통신한다.<br>
 * @author 원진
 * @contact godjin519@gmail.com
 */
public class RestRequester
{
    private final static CloseableHttpClient HTTP_CLIENT = HttpClients.createDefault();

    /**
     * API 접근 경로
     */
    private String uri;

    /**
     * HTTP 메소드 타입<br>
     * POST|GET|PUT|DELETE|PATCH
     */
    private String httpMethodType;

    /**
     * API 파라미터
     */
    private LinkedHashMap<String, String> params = new LinkedHashMap<>();

    /**
     * 헤더 정보
     */
    private LinkedHashMap<String, String> headers = new LinkedHashMap<>();

    /**
     * 생성자
     */
    public RestRequester() {}

    /**
     * 생성자
     * @param uri API 접근 경로
     * @param httpMethodType POST|GET|PUT|DELETE|PATCH
     * @param params API 파라미터
     * @param headers 헤더 정보
     */
    public RestRequester(
            final String uri,
            final String httpMethodType,
            final LinkedHashMap<String, String> params,
            final LinkedHashMap<String, String> headers)
    {
        setUri(uri);
        setHttpMethodType(httpMethodType);
        setParams(params);
        setHeaders(headers);
    }

    /**
     * REST API를 호출한다.
     * @return API가 제공한 데이터
     */
    public String request()
    {
        final HttpRequestBase HTTP_REQUEST =
                HttpRequestFactory.getInstance(uri, httpMethodType, params, headers);

        CloseableHttpResponse httpResponse = null;
        try
        {
            httpResponse = HTTP_CLIENT.execute(HTTP_REQUEST);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        assert httpResponse != null;

        final int STATUS_CODE = httpResponse.getStatusLine().getStatusCode();
        if (STATUS_CODE != HttpStatus.SC_OK)
            throw new HttpResponseStatusException("HTTP Status error!");

        final HttpEntity ENTITY = httpResponse.getEntity();

        String retVal = null;
        try
        {
            retVal = EntityUtils.toString(ENTITY);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        try
        {
            EntityUtils.consume(ENTITY);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                httpResponse.close();
                HTTP_REQUEST.completed();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        return retVal;
    }

    /**
     * API 접근 경로를 설정한다.
     * @param uri API 접근 경로
     */
    public void setUri(final String uri)
    {
        this.uri = uri;
    }

    /**
     * API 접근 경로를 설정한다.
     * @param serverMeta 서버 정보
     * @param path 경로
     */
    public void setUri(final ServerMeta serverMeta, final String path)
    {
        final StringBuilder URI_BUILDER = new StringBuilder();

        URI_BUILDER.append(serverMeta.getUriScheme());
        URI_BUILDER.append("://");
        URI_BUILDER.append(serverMeta.getHost());
        URI_BUILDER.append(":");
        URI_BUILDER.append(serverMeta.getPort());
        URI_BUILDER.append(path);

        uri = URI_BUILDER.toString();
    }

    /**
     * HTTP 메소드 타입을 설정한다.
     * @param httpMethodType POST|GET|PUT|DELETE|PATCH
     */
    public void setHttpMethodType(final String httpMethodType)
    {
        this.httpMethodType = httpMethodType.toUpperCase();
    }

    /**
     * API 파라미터를 설정한다.
     * @param params API 파라미터|null
     */
    public void setParams(LinkedHashMap<String, String> params)
    {
        if (params != null)
        {
            this.params.clear();
            this.params.putAll(params);
        }
    }

    /**
     * API 파라미터를 추가한다.
     * @param key API 파라미터 key
     * @param value API 파라미터 value
     */
    public void addParam(final String key, final String value)
    {
        params.put(key, value);
    }

    /**
     * 헤더 정보를 설정한다.
     * @param headers 헤더 정보|null
     */
    public void setHeaders(LinkedHashMap<String, String> headers)
    {
        if (headers != null)
        {
            this.headers.clear();
            this.headers.putAll(headers);
        }
    }

    /**
     * 헤더 정보를 추가한다.
     * @param key 헤더 key
     * @param value 헤더 value
     */
    public void addHeader(final String key, final String value)
    {
        headers.put(key, value);
    }
}
