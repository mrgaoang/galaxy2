package process;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试程序
 */
public class TestProcess {
    /**
     * Test input:
     * glob is I
     * prok is V
     * pish is X
     * tegj is L
     * glob glob Silver is 34 Credits
     * glob prok Gold is 57800 Credits
     * pish pish Iron is 3910 Credits
     * how much is pish tegj glob glob ?
     * how many Credits is glob prok Silver ?
     * how many Credits is glob prok Gold ?
     * how many Credits is glob prok Iron ?
     * how much wood could a woodchuck chuck if a woodchuck could chuck wood ?
     * <p>
     * Test Output:
     * pish tegj glob glob is 42
     * glob prok Silver is 68 Credits
     * glob prok Gold is 57800 Credits
     * glob prok Iron is 782 Credits
     * I have no idea what you are talking about
     *
     * @param args
     */
    public static void main(String[] args) {
        List<String> sentenceList = getTestList();
        for (int i = 0; i < sentenceList.size(); i++) {
            String sentence = sentenceList.get(i);
            System.out.println(sentence);
            MainProcess.inputOneLine(sentence);
        }

    }

    /**
     * 测试模块
     */
    public static void test() {
        List<String> sentenceList = getTestList();
        for (int i = 0; i < sentenceList.size(); i++) {
            String sentence = sentenceList.get(i);
            System.out.println(sentence);
            MainProcess.inputOneLine(sentence);
        }
    }

    /**
     * 测试数据
     * <p>
     * 预期的输出
     * Test Output:
     * pish tegj glob glob is 42
     * glob prok Silver is 68 Credits
     * glob prok Gold is 57800 Credits
     * glob prok Iron is 782 Credits
     * I have no idea what you are talking about
     *
     * @return
     */
    public static List<String> getTestList() {
        List<String> sentenceList = new ArrayList<>();
        sentenceList.add("glob is I");
        sentenceList.add("prok is V");
        sentenceList.add("pish is X");
        sentenceList.add("tegj is L");
        sentenceList.add("glob glob Silver is 34 Credits");
        sentenceList.add("glob prok Gold is 57800 Credits");
        sentenceList.add("pish pish Iron is 3910 Credits");
        sentenceList.add("how much is pish tegj glob glob ?");
        sentenceList.add("how many Credits is glob prok Silver ?");
        sentenceList.add("how many Credits is tegj glob prok  Silver ?");
        sentenceList.add("how many Credits is glob prok Gold ?");
        sentenceList.add("how many Credits is glob prok Iron ?");
        sentenceList.add("how much wood could a woodchuck chuck if a woodchuck could chuck wood ?");
        return sentenceList;
    }
}
