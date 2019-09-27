package sentence;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import constant.SentenceType;
import galaxy.AbstractQuestion;
import galaxy.BasicCell;
import galaxy.NumberCell;
import util.Utils;

/**
 * 货币积分价值问题构造器
 */
public class UnitCreditQuestionBuilder extends AbstractQuestion {
    /**
     * 问句结尾符号
     */
    private static String questionEndOfChar = "?";
    /**
     * 货币积分价值提问
     */
    private static String question4CoreWords = "how many Credits is";

    /**
     * 回答模板
     */
    private static String answerSentence = "%s %s is %s Credits";

    /**
     * 货币积分价值对象
     */
    private MoneyUnitBuilder moneyUnitBuilder;

    public UnitCreditQuestionBuilder(MoneyUnitBuilder moneyUnitBuilder) {
        this.moneyUnitBuilder = moneyUnitBuilder;
    }

    /**
     * 是否为货币等于多少积分这种疑问句
     * how many Credits is glob prok Silver ?
     *
     * @param sentence
     * @return
     */
    @Override
    public SentenceModel isMyTypeSentence(String sentence) {
        SentenceModel sentenceModel = new SentenceModel();

        // 基本数值集合
        List<BasicCell> basicCells = Arrays.asList(BasicCell.values());
        if (!sentence.contains(question4CoreWords)) {
            return null;
        }
        // 必须以问号结尾
        if (!questionEndOfChar.equals(sentence.substring(sentence.length() - 1, sentence.length()))) {
            return null;
        }

        // 替换特征语句
        String[] lessWords = sentence.replace(question4CoreWords, "").replace(questionEndOfChar, "").trim().split(" ");
        if (lessWords.length < 2) {
            return null;
        }
        // 最后一个关键词必须为货币单位，否则无法识别
        if (!moneyUnitBuilder.containsKey(lessWords[lessWords.length - 1])) {
            return null;
        }

        StringBuilder nikeNameString = new StringBuilder(36);
        StringBuilder cellString = new StringBuilder(36);

        for (int i = 0; i < lessWords.length - 1; i++) {

            // 其余关键词为昵称
            BasicCell basicCell = moneyUnitBuilder.getNikeNameBuilder().getCellByNikeName(lessWords[i]);
            if (Objects.isNull(basicCell) || !basicCells.contains(basicCell)) {
                return null;
            }
            nikeNameString.append(lessWords[i]).append(" ");
            cellString.append(basicCell.getName());

        }
        sentenceModel.setCellString(cellString.toString());
        sentenceModel.setNikeNameString(nikeNameString.toString().trim());
        // 设置货币单位
        sentenceModel.setMoneyUnit(lessWords[lessWords.length - 1]);
        // 设置文本类型
        sentenceModel.setSentenceType(SentenceType.QUESTION_UNIT_CREDIT);
        return sentenceModel;


    }

    /**
     * 回答问题
     *
     * @param sentenceModel
     * @return
     */
    @Override
    public String answer(SentenceModel sentenceModel) {
        if (sentenceModel.getSentenceType() != SentenceType.QUESTION_UNIT_CREDIT) {
            return "I have no idea what you are talking about";
        }

        // 银河系数值对象
        NumberCell numberCell;
        try {
            numberCell = new NumberCell(sentenceModel.getCellString());
        } catch (Exception e) {
            return e.getMessage();
        }

        // 一货币等于多少积分
        double unitCredit = moneyUnitBuilder.get(sentenceModel.getMoneyUnit());

        double credits = numberCell.getNumber() * unitCredit;

        return String.format(answerSentence, sentenceModel.getNikeNameString(), sentenceModel.getMoneyUnit(), Utils.doubleTransform(credits));
    }
}
