package sentence;

import java.util.HashMap;

import constant.Credits;
import constant.SentenceType;
import galaxy.NumberCell;

/**
 * 货币种类价值构造
 */
public class MoneyUnitBuilder extends HashMap<String, Double> {
    public static String accessChar = "is";

    private NikeNameBuilder nikeNameBuilder;

    public NikeNameBuilder getNikeNameBuilder() {
        return nikeNameBuilder;
    }

    public MoneyUnitBuilder(NikeNameBuilder nikeNameBuilder) {
        super();
        this.nikeNameBuilder = nikeNameBuilder;
    }

    /**
     * glob glob Silver is 34 Credits
     * glob prok Gold is 57800 Credits
     * pish pish Iron is 3910 Credits
     * <p>
     * 根据文本对象设置货币定义
     *
     * @param sentenceModel
     */
    public String  setUnitWithSentence(SentenceModel sentenceModel) {
        NumberCell numberCell;
        try {
            numberCell = new NumberCell(sentenceModel.getCellString());
        } catch (Exception e) {
            return e.getMessage();
        }
        this.put(sentenceModel.getMoneyUnit(), sentenceModel.getCredits() / numberCell.getNumber());
        return null;
    }

    /**
     * 是否为货币定义语义
     *
     * @param sentence
     * @return
     */
    public SentenceModel isMoneyUnitSentence(String sentence) {
        SentenceModel sentenceModel = new SentenceModel();
        String[] words = sentence.split(" ");
        // 不以Credits 结尾直接返回
        if (!words[words.length - 1].equals(Credits.CREDITS)) {
            return null;
        }
        int charUnitIndex = 0;
        StringBuilder nikeNameString = new StringBuilder(36);
        StringBuilder cellString = new StringBuilder(36);

        for (int i = 0; i < words.length; i++) {
            // 前n个字符取昵称
            if (nikeNameBuilder.containsKey(words[i])) {
                nikeNameString.append(words[i]).append(" ");
                cellString.append(nikeNameBuilder.getCellByNikeName(words[i]).getName());
            }
            // 第一个昵称的字符为货币名称
            else {
                sentenceModel.setMoneyUnit(words[i]);
                charUnitIndex = i;
                break;
            }
        }
        // 货币种类后接is
        if (!words[charUnitIndex + 1].equals(accessChar)) {
            return null;
        }
        // is 后接积分数量
        try {
            Double credits = Double.parseDouble(words[charUnitIndex + 2]);
            // 设置积分数量
            sentenceModel.setCredits(credits);
        } catch (NumberFormatException e) {
            return null;
        }
        sentenceModel.setCellString(cellString.toString());
        sentenceModel.setNikeNameString(nikeNameString.toString().trim());
        sentenceModel.setSentenceType(SentenceType.INIT_CREDIT);
        return sentenceModel;
    }
}
