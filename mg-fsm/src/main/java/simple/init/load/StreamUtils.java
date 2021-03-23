package simple.init.load;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author qianniu
 * @date 2020/9/22 9:57 上午
 * @desc
 */
public class StreamUtils {

    public static String convertStreamToString(InputStream is)
    {
        if (is == null) {
            throw new IllegalArgumentException("Invalid input stream, cannot be null");
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null)
                sb.append(new StringBuilder().append(line).append("\n").toString());
        }
        catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
