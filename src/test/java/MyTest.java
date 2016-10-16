import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;

/**
 * Created by weizhiwen on 2016/8/19.
 */
public class MyTest {
    public static void main(String[] args) throws UnsupportedEncodingException {
        System.out.println(new String(Base64.decodeBase64("UG93ZXJlZEJ5"), "utf-8"));
    }
}
