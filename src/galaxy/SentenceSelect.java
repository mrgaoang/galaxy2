package galaxy;

import com.sun.xml.internal.ws.util.StringUtils;
import constant.Credits;
import constant.SentenceType;

import java.util.Arrays;
import java.util.List;

/**
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
 */
public class SentenceSelect {

    private MoneyUnitBuilder moneyUnitBuilder;

    /**
     * 积分问题特征
     */
    private static String question3CoreWords = "how much is ";
    /**
     * 货币积分价值提问
     */
    private static String question4CoreWords = "how many Credits is ";
    /**
     * 截断字符
     */
    public static String accessChar = "is";
    /**
     * 问句结尾符号
     */
    private static String questionEndOfChar = "?";


    public SentenceSelect(MoneyUnitBuilder moneyUnitBuilder) {
        this.moneyUnitBuilder = moneyUnitBuilder;
    }

    public int select(String sentence) {
        if (sentence.isEmpty()) {
            return SentenceType.ERROR;
        }
        sentence = sentence.replaceAll("\\s{2,}", " ").trim();

        String[] words = sentence.split(" ");
        if (words.length < 3) {
            return SentenceType.WRONG;
        }
        // 基本数值集合
        List<BasicCell> basicCells = Arrays.asList(BasicCell.values());

        // 定义昵称 语义特征：三个单词，以特定价值符号结尾
        if (words.length == 3
                && "is".equals(words[1])
                && basicCells.contains(BasicCell.getWithString(words[words.length - 1]))) {
            return SentenceType.NIKE_NAME;
        }
        // 简单提问等价积分
        else if (sentence.indexOf(question3CoreWords) > 0) {
            // 替换特征语句
            String[] lessWords = sentence.replace(question3CoreWords, "").split(" ");
            if (lessWords.length < 2) {
                return SentenceType.WRONG;
            }
            // 昵称匹配
            for (int i = 0; i < lessWords.length; i++) {
                // 最后字符为?
                if (i == lessWords.length - 1 && !moneyUnitBuilder.containsKey(lessWords[i])) {
                    return SentenceType.WRONG;
                }
                if (!basicCells.contains(moneyUnitBuilder.getNikeNameBuilder().getCellByNikeName(lessWords[i]))) {
                    return SentenceType.WRONG;
                }
            }
            return SentenceType.QUESTION_CREDIT;
        }
        // 带货币单位的积分提问
        else if (sentence.indexOf(question4CoreWords) > 0) {
            // 替换特征语句
            String[] lessWords = sentence.replace(question4CoreWords, "").split(" ");
            if (lessWords.length < 2) {
                return SentenceType.WRONG;
            }
            for (int i = 0; i < lessWords.length; i++) {
                // 最后字符为?
                if (i == lessWords.length - 1 && !moneyUnitBuilder.containsKey(lessWords[i])) {
                    return SentenceType.WRONG;
                }
                // 最后一个关键词必须为货币单位，否则无法识别
                if (i == lessWords.length - 2 && !moneyUnitBuilder.containsKey(lessWords[i])) {
                    return SentenceType.WRONG;
                }
                // 其余关键词为昵称
                if (!basicCells.contains(moneyUnitBuilder.getNikeNameBuilder().getCellByNikeName(lessWords[i]))) {
                    return SentenceType.WRONG;
                }
            }
            return SentenceType.QUESTION_UNIT_CREDIT;

        }
        // 定义货币的积分价值 特征：以N个昵称开头+ 货币单位 + 积分数量 以Credits 结尾
        else if (words[words.length - 1].equals(Credits.CREDITS)
                && basicCells.contains(BasicCell.getWithString(words[0]))) {
            int charUnitIndex = 0;
            String unit = null;
            double price = 0;
            for (int i = 0; i < words.length; i++) {
                // 取昵称
                if (moneyUnitBuilder.getNikeNameBuilder().containsKey(words[i])) {
                    continue;
                }
                // 取到货币种类 名称
                else {
                    charUnitIndex = i;
                    break;
                }
            }
            // 货币种类后接is
            if (words[charUnitIndex + 1] != accessChar) {
                return SentenceType.WRONG;
            }
            // is 后接积分数量
            try {
                Double.parseDouble(words[charUnitIndex + 2]);
            } catch (NumberFormatException e) {
                return SentenceType.WRONG;
            }
            // 以credits结尾
            if (!Credits.CREDITS.equals(words[words.length - 1])) {
                return SentenceType.WRONG;
            }
            return SentenceType.INIT_CREDIT;
        }
        return SentenceType.WRONG;

    }

    public static void main(String[] args) {
        String sentence = " abc   dsfsd  sdfds    lkjdsf   ";
        sentence = sentence.replaceAll("\\s{2,}", " ").trim();
        System.out.println(sentence);
    }
}
