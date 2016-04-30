package ruanjianbei.wifi.com.ViewPagerinfo.ui.filechoose;

import java.util.ArrayList;
import java.util.List;

/**
 * 存放已经选择的文件路径类
 * Created by zhouyonglong on 2016/4/30.
 */
public class FragmentChoose {
    private static List<String> fileChoose = new ArrayList<String>();

    public static List<String> getFileChoose() {
        return fileChoose;
    }

    public static void setFileChoose(List<String> fileChoose) {
        FragmentChoose.fileChoose = fileChoose;
    }
}
