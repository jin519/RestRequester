import data_structure.ServerMeta;
import java.util.LinkedHashMap;

/**
 * 인자로 받은 request 정보를 바탕으로<br>
 * REST API를 제공하는 서버와 통신한다.<br>
 *
 * @author 원진
 * @contact godjin519@gmail.com
 */
public class RestRequester
{
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
     * API 접근 경로를 반환한다.
     * @return API 접근 경로
     */
    public String getUri()
    {
        return uri;
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
     * @param params API 파라미터
     */
    public void setParams(LinkedHashMap<String, String> params)
    {
        this.params.clear();
        this.params.putAll(params);
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
     * @param headers 헤더 정보
     */
    public void setHeaders(LinkedHashMap<String, String> headers)
    {
        this.headers.clear();
        this.headers.putAll(headers);
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
