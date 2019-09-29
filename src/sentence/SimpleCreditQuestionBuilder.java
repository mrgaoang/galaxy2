package sentence;

import java.util.Objects;

import constant.SentenceType;
import number.BasicCell;
import number.NumberCell;
import util.Utils;

/**
 * 简单积分问题构造器
 * 继承自AbstractQuestion，实现了判断语句、回答问题等方法
 */
public class SimpleCreditQuestionBuilder extends AbstractQuestion {
    /**
     * 问句结尾符号
     */
    private static String questionEndOfChar = "?";

    /**
     * 货物积分价值对象
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
    public SentenceModel getMyModel(String sentence) {
        // 分割
        String[] lessWords = sentence.split(" ");
        if (lessWords.length < 2) {
            return null;
        }
        StringBuilder cellString = new StringBuilder(12);
        SentenceModel sentenceModel = new SentenceModel();

        // 昵称匹配
        for (int i = 0; i < lessWords.length; i++) {
            BasicCell basicCell = moneyUnitBuilder.getNikeNameBuilder().getCellByNikeName(lessWords[i]);
            if (Objects.isNull(basicCell)) {
                return null;
            }
            // 拼接罗马符号数字串
            cellString.append(basicCell.getName());

        }
        sentenceModel.setCellString(cellString.toString());
        sentenceModel.setNikeNameString(sentence);
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
        NumberCell numberCell;
        try {
            numberCell = new NumberCell(sentenceModel.getCellString());
        } catch (Exception e) {
            return e.getMessage();
        }
        return String.format(answerSentence, sentenceModel.getNikeNameString(), Utils.doubleTransform(numberCell.getNumber()));
    }

    /**
     * 问题特征词
     *
     * @return
     */
    @Override
    public String getQuestionKeyWord() {
        return question3CoreWords;
    }

    /**
     * 问题结尾词
     *
     * @return
     */
    @Override
    public String getQuestionEndOfChar() {
        return questionEndOfChar;
    }


}
