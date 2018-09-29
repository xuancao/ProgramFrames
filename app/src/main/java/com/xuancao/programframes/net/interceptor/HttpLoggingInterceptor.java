package com.xuancao.programframes.net.interceptor;


import com.xuancao.programframes.BuildConfig;
import com.xuancao.programframes.utils.LogUtil;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Connection;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * 请求日志拦截器
 */
public class HttpLoggingInterceptor implements Interceptor {
    private static final Charset UTF8 = Charset.forName("UTF-8");

    public enum Level {
        /**
         * No logs.
         */
        NONE,
        /**
         * Logs request and response lines.
         * <p>
         * Example:
         * <pre>{@code
         * --> POST /greeting HTTP/1.1 (3-byte body)
         *
         * <-- HTTP/1.1 200 OK (22ms, 6-byte body)
         * }</pre>
         */
        BASIC,
        /**
         * Logs request and response lines and their respective headers.
         * <p>
         * Example:
         * <pre>{@code
         * --> POST /greeting HTTP/1.1
         * Host: zhangzhongyun.com
         * Content-Type: plain/text
         * Content-Length: 3
         * --> END POST
         *
         * <-- HTTP/1.1 200 OK (22ms)
         * Content-Type: plain/text
         * Content-Length: 6
         * <-- END HTTP
         * }</pre>
         */
        HEADERS,
        /**
         * Logs request and response lines and their respective headers and bodies (if present).
         * <p>
         * Example:
         * <pre>{@code
         * --> POST /greeting HTTP/1.1
         * Host: zhangzhongyun.com
         * Content-Type: plain/text
         * Content-Length: 3
         *
         * Hi?
         * --> END GET
         *
         * <-- HTTP/1.1 200 OK (22ms)
         * Content-Type: plain/text
         * Content-Length: 6
         *
         * Hello!
         * <-- END HTTP
         * }</pre>
         */
        BODY
    }

    public interface Logger {
        void log(String message);

        /**
         * A {@link Logger} defaults output appropriate for the current platform.
         */
        Logger DEFAULT = new Logger() {
            @Override
            public void log(String message) {
                //                Platform.get().log(message);
                LogUtil.e(message);
            }
        };
    }

    public HttpLoggingInterceptor() {
        this(Logger.DEFAULT);
    }

    public HttpLoggingInterceptor(Logger logger) {
        this.logger = logger;
    }

    private final Logger logger;

    private volatile Level level = Level.NONE;

    /**
     * Change the level at which this interceptor logs.
     */
    public void setLevel(Level level) {
        this.level = level;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Level level = this.level;
        if (!BuildConfig.DEBUG) {
            level = Level.NONE;
        }
        Request request = chain.request();

//        if (requestPath(request.url()).indexOf("novels/catalog/") != -1)  return chain.proceed(request);
        if (level == Level.NONE) {
            return chain.proceed(request);
        }
        boolean logBody = level == Level.BODY;
        boolean logHeaders = logBody || level == Level.HEADERS;

        RequestBody requestBody = request.body();
        boolean hasRequestBody = requestBody != null;

        Connection connection = chain.connection();
        Protocol protocol = connection != null ? connection.protocol() : Protocol.HTTP_1_1;

        StringBuilder outputStringBuilder = new StringBuilder();

        String requestStartMessage =
                "--> " + request.method() + ' ' + request.url();
        if (!logHeaders && hasRequestBody) {
            requestStartMessage += " (" + requestBody.contentLength() + "-byte body)";
        }
        outputStringBuilder.append(requestStartMessage).append("\n");

        if (logHeaders) {
//            logger.log(String.format("send request head for %s", request.headers()));
            //            Headers headers = request.headers();
            //            for (int i = 0, count = headers.size(); i < count; i++) {
            //                logger.log(headers.name(i) + ": " + headers.value(i));
            //            }
            outputStringBuilder.append(String.format("send request head for \n%s", request.headers())).append("\n");
            String endMessage = "--> END " + request.method();
            if (logBody && hasRequestBody) {
                Buffer buffer = new Buffer();
                requestBody.writeTo(buffer);

                Charset charset = UTF8;
                MediaType contentType = requestBody.contentType();
                if (contentType != null) {
                    contentType.charset(UTF8);
                }

//                logger.log(buffer.readString(charset));
                outputStringBuilder.append(buffer.readString(charset)).append("\n");

                endMessage += " (" + requestBody.contentLength() + "-byte body)";
            }
//            logger.log(endMessage);
            outputStringBuilder.append(endMessage).append("\n");
        }

        long startNs = System.nanoTime();
        Response response = chain.proceed(request);
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);

        ResponseBody responseBody = response.body();

        outputStringBuilder.append("<-- " + protocol(response.protocol()) + ' ' + response.code() + ' '
                + response.message() + " (" + tookMs + "ms"
                + (logHeaders ? ", " + responseBody.contentLength() + "-byte body" : "") + ')').append("\n");
        logger.log("response.code() : "+response.code());
//        long startNs = System.nanoTime();
//        Response response = chain.proceed(request);
//        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);
//
//        ResponseBody responseBody = response.body();
//        long contentLength = responseBody.contentLength();
//        String bodySize = contentLength != -1 ? contentLength + "-byte" : "unknown-length";
//        logger.log("<-- "
//                + response.code()
//                + ' '
//                + response.message()
//                + ' '
//                + response.request().url()
//                + " ("
//                + tookMs
//                + "ms"
//                + (!logHeaders ? ", " + bodySize + " body" : "")
//                + ')');
        if (logHeaders) {
            outputStringBuilder.append(String.format("Received response head for %s", response.headers())).append("\n");
            //            Headers headers = response.headers();
            //            for (int i = 0, count = headers.size(); i < count; i++) {
            //                logger.log(headers.name(i) + ": " + headers.value(i));
            //            }

            String endMessage = "<-- END HTTP";
            if (logBody) {
                BufferedSource source = responseBody.source();
                source.request(Long.MAX_VALUE); // Buffer the entire body.
                Buffer buffer = source.buffer();

                Charset charset = UTF8;
                MediaType contentType = responseBody.contentType();
                if (contentType != null) {
                    charset = contentType.charset(UTF8);
                }

                if (responseBody.contentLength() != 0) {
                    outputStringBuilder.append(unicodeToString(buffer.clone().readUtf8())).append("\n");
                }

                endMessage += " (" + buffer.size() + "-byte body)";
            }
            outputStringBuilder.append(endMessage).append("\n");
        }
        logger.log(outputStringBuilder.toString());
        return response;
    }

    private static String protocol(Protocol protocol) {
        return protocol == Protocol.HTTP_1_0 ? "HTTP/1.0" : "HTTP/1.1";
    }

    private static String requestPath(HttpUrl url) {
        String path = url.encodedPath();
        String query = url.encodedQuery();
        return query != null ? (path + '?' + query) : path;
    }


    public static String unicodeToString(String str) {

        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
        Matcher matcher = pattern.matcher(str);
        char ch;
        while (matcher.find()) {
            ch = (char) Integer.parseInt(matcher.group(2), 16);
            str = str.replace(matcher.group(1), ch + "");
        }
        return str;
    }
}