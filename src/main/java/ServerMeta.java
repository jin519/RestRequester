/**
 * 서버 정보를 저장한다.
 * @author 원진
 * @contact godjin519@gmail.com
 */
public class ServerMeta
{
    private String uriScheme;
    private String host;
    private String port;

    public ServerMeta() {}

    public ServerMeta(final String uriScheme, final String host, final String port)
    {
        setUriScheme(uriScheme);
        setHost(host);
        setPort(port);
    }

    public String getUriScheme()
    {
        return uriScheme;
    }

    public void setUriScheme(final String uriScheme)
    {
        this.uriScheme = uriScheme;
    }

    public String getHost()
    {
        return host;
    }

    public void setHost(final String host)
    {
        this.host = host;
    }

    public String getPort()
    {
        return port;
    }

    public void setPort(final String port)
    {
        this.port = port;
    }
}
