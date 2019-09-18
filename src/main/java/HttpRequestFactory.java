import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.message.BasicNameValuePair;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * HTTP Request 객체를 생성한다.
 * @author 원진
 * @contact godjin519@gmail.com
 */
public class HttpRequestFactory
{
    /**
     * 메소드 타입에 해당하는 HTTP Request 객체를 생성한다.
     * @param uri 데이터 접근 경로
     * @param methodType 메소드 타입
     * @param params 파라미터
     * @param headers 헤더 정보
     * @return 메소드 타입에 해당하는 HTTP Request 객체
     */
    public static HttpRequestBase getInstance(
            final String uri,
            final String methodType,
            final LinkedHashMap<String, String> params,
            final LinkedHashMap<String, String> headers)
    {
        HttpRequestBase retVal = null;

        switch (methodType)
        {
            case "GET":
                retVal = createHttpGet(uri, params);
                break;
            case "POST":
                retVal = createHttpPost(uri, params);
        }

        if (retVal != null)
        {
            for (String key : headers.keySet())
                retVal.addHeader(key, headers.get(key));
        }

        return retVal;
    }

    /**
     * HTTP GET request 객체를 생성한다.
     * @param uri 데이터 접근 경로
     * @param params 파라미터
     * @return HTTP GET request 객체
     */
    private static HttpGet createHttpGet(
            final String uri,
            final LinkedHashMap<String, String> params)
    {
        if (params == null)
            return new HttpGet(uri);

        StringBuilder paramBuilder = new StringBuilder();

        params.forEach((KEY, VALUE) ->
        {
            paramBuilder.append(paramBuilder.length() > 0 ? "&" : "");
            try
            {
                paramBuilder.append(String.format("%s=%s",
                        URLEncoder.encode(KEY, "UTF-8"), URLEncoder.encode(VALUE, "UTF-8")));
            }
            catch (UnsupportedEncodingException e)
            {
                e.printStackTrace();
            }
        });

        return new HttpGet(uri + paramBuilder.toString());
    }

    /**
     * HTTP POST request 객체를 생성한다.
     * @param uri 데이터 접근 경로
     * @param params 파라미터
     * @return HTTP POST request 객체
     */
    private static HttpPost createHttpPost(
            final String uri,
            final LinkedHashMap<String, String> params)
    {
        HttpPost retVal = new HttpPost(uri);

        List<NameValuePair> paramList = new ArrayList<>();
        params.forEach((KEY, VALUE) -> paramList.add(new BasicNameValuePair(KEY, VALUE)));

        try
        {
            retVal.setEntity(new UrlEncodedFormEntity(paramList));
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        return retVal;
    }
}
