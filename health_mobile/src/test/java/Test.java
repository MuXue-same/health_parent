import com.aliyuncs.exceptions.ClientException;
import com.lxh.util.SMSUtils;

public class Test {

    public static void main(String[] args) {
        try {
            SMSUtils.sendShortMessage("SMS_159620392","13812345678","1234");
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
