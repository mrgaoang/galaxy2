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
 * 简单积分问题构造器
 */
public class SimpleCreditQuestionBuilder extends AbstractQuestion {
    /**
     * 问句结尾符号
     */
    private static String questionEndOfChar = "?";

    /**
     * 货币积分价值对象
     */
    private MoneyUnitBuilder moneyUnitBuilder;

    public SimpleCreditQuestionBuilder(MoneyUnitBuilder moneyUnitBuilder) {
        this.moneyUnitBuilder = moneyUnitBuilder;
    }

    /**
     * 简单积分问题特征
     */
    private static String question3CoreWords = "how much is ";

    /**
     * 回答模板
     */
    private static String answerSentence = "%s is %s";

    /**
     * 是否为简单积分提问
     * how much is pish tegj glob glob ?
     *
     * @param sentence
     * @return
     */
    @Override
    public SentenceModel isMyTypeSentence(String sentence) {
        SentenceModel sentenceModel = new SentenceModel();
        String[] words = sentence.split(" ");
        // 基本数值集合
        List<BasicCell> basicCells = Arrays.asList(BasicCell.values());
        if (!sentence.contains(question3CoreWords)) {
            return null;
        }
        // 必须以问号结尾
        if (!questionEndOfChar.equals(sentence.substring(sentence.length() - 1, sentence.length()))) {
            return null;
        }
        // 替换特征语句
        String[] lessWords = sentence.replace(question3CoreWords, "").replace(questionEndOfChar, "").split(" ");
        if (lessWords.length < 2) {
            return null;
        }
        StringBuilder nikeNameString = new StringBuilder(36);
        StringBuilder cellString = new StringBuilder(36);

        // 昵称匹配
        for (int i = 0; i < lessWords.length; i++) {
            BasicCell basicCell = moneyUnitBuilder.getNikeNameBuilder().getCellByNikeName(lessWords[i]);
            if (Objects.isNull(basicCell) || !basicCells.contains(basicCell)) {
                return null;
            }
            nikeNameString.append(lessWords[i]).append(" ");
            cellString.append(basicCell.getName());

        }
        sentenceModel.setCellString(cellString.toString());
        sentenceModel.setNikeNameString(nikeNameString.toString().trim());
        sentenceModel.setSentenceType(SentenceType.QUESTION_CREDIT);
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
        if (sentenceModel.getSentenceType() != SentenceType.QUESTION_CREDIT) {
            return "I have no idea what you are talking about";
        }
        // 银河系数值对象
        NumberCell numberCell = null;
        try {
            numberCell = new NumberCell(sentenceModel.getCellString());
        } catch (Exception e) {
            return e.getMessage();
        }
        return String.format(answerSentence, sentenceModel.getNikeNameString(), Utils.doubleTransform(numberCell.getNumber()));
    }
}
