/**
 * HTTP response status 예외를 처리한다.
 * @author 원진
 * @contact godjin519@gmail.com
 */
public class HttpResponseStatusException extends RuntimeException
{
    public HttpResponseStatusException() {}

    public HttpResponseStatusException(final String message)
    {
        super(message);
    }
}