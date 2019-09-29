package sentence;

import java.util.Objects;

import constant.SentenceType;
import number.BasicCell;
import number.NumberCell;
import util.Utils;

/**
 * 货物积分价值问题构造器
 * 继承自AbstractQuestion，实现了判断语句、回答问题等方法
 */
public class UnitCreditQuestionBuilder extends AbstractQuestion {
    /**
     * 问句结尾符号
     */
    private static String questionEndOfChar = "?";
    /**
     * 货物积分价值提问
     */
    private static String question4CoreWords = "how many Credits is";

    /**
     * 货物积分价值对象
     */
    private MoneyUnitBuilder moneyUnitBuilder;

    public UnitCreditQuestionBuilder(MoneyUnitBuilder moneyUnitBuilder) {
        this.moneyUnitBuilder = moneyUnitBuilder;
    }

    /**
     * 是否为货物等于多少积分这种疑问句
     * glob prok Silver
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
        // 最后一个关键词必须为货物单位，否则无法识别
        if (!moneyUnitBuilder.containsKey(lessWords[lessWords.length - 1])) {
            return null;
        }

        StringBuilder nikeNameString = new StringBuilder(36);
        StringBuilder cellString = new StringBuilder(12);

        // 遍历关键词，到倒数第二个
        for (int i = 0; i < lessWords.length - 1; i++) {
            // 其余关键词为昵称
            BasicCell basicCell = moneyUnitBuilder.getNikeNameBuilder().getCellByNikeName(lessWords[i]);
            if (Objects.isNull(basicCell)) {
                return null;
            }

            // 拼接昵称组成的数字
            nikeNameString.append(lessWords[i]).append(" ");
            // 拼接罗马符号数字串
            cellString.append(basicCell.getName());
        }
        SentenceModel sentenceModel = new SentenceModel();
        sentenceModel.setCellString(cellString.toString());
        sentenceModel.setNikeNameString(nikeNameString.toString().trim());
        // 设置货物单位
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
            // 返回字符组合非法的提示
            return e.getMessage();
        }

        // 一货物等于多少积分
        double unitCredit = moneyUnitBuilder.get(sentenceModel.getMoneyUnit());

        // 答案 信用分
        double credits = numberCell.getNumber() * unitCredit;

        /**
         * 回答模板
         */
        String answerSentence = "%s %s is %s Credits";
        return String.format(answerSentence, sentenceModel.getNikeNameString(), sentenceModel.getMoneyUnit(), Utils.doubleTransform(credits));
    }

    /**
     * 问题特征词
     *
     * @return
     */
    @Override
    public String getQuestionKeyWord() {
        return question4CoreWords;
    }

    /**
     * 问题结尾词
     *
     * @return
     */
    @Override
    public String getQuestionEndOfChar() {
        return  questionEndOfChar;
    }

}
