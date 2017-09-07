/**
 * Created by fy on 17-9-5.
 */
import com.fy.TransferAnalysis.NewsGet.TransferNews;
import org.junit.Test;

public class TransferNewsTest {

    @Test
    public void test()throws Exception{
        TransferNews.getInstance().getTransferNews();
        TransferNews.getInstance().splitNewsContent();
    }
}
