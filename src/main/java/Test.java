public class Test
{
    public static void main(String[] args)
    {
        final String GET_RESULT =  new RestRequester(
                "http://swapi.co/api/planets/1/",
                "GET",
                null,
                null).request();

        System.out.println("GET RESULT#######################");
        System.out.println(GET_RESULT);

        final String POST_RESULT = new RestRequester(
                "http://nghttp2.org/httpbin/post",
                "POST",
                null,
                null).request();

        System.out.println("POST RESULT######################");
        System.out.println(POST_RESULT);
    }
}
