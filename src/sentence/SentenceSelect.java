package sentence;

import java.util.Objects;

import constant.SentenceType;
import galaxy.NumberCell;

/**
 * 数据选择器
 */
public class SentenceSelect {

    /**
     * 货币积分价值对象
     */
    private MoneyUnitBuilder moneyUnitBuilder;

    /**
     * 简单积分问题构造器
     */
    private SimpleCreditQuestionBuilder simpleCreditQuestionBuilder;

    /**
     * 货币积分价值问题构造器
     */
    private UnitCreditQuestionBuilder unitCreditQuestionBuilder;

    public SimpleCreditQuestionBuilder getSimpleCreditQuestionBuilder() {
        return simpleCreditQuestionBuilder;
    }

    public UnitCreditQuestionBuilder getUnitCreditQuestionBuilder() {
        return unitCreditQuestionBuilder;
    }

    public SentenceSelect(MoneyUnitBuilder moneyUnitBuilder) {
        this.moneyUnitBuilder = moneyUnitBuilder;
        this.simpleCreditQuestionBuilder = new SimpleCreditQuestionBuilder(moneyUnitBuilder);
        this.unitCreditQuestionBuilder = new UnitCreditQuestionBuilder(moneyUnitBuilder);
    }

    /**
     * 判断文本的语法类型
     *
     * @param sentence
     * @return
     */
    public SentenceModel select(String sentence) {
        SentenceModel sentenceModel = new SentenceModel();
        if (sentence.isEmpty()) {
            sentenceModel.setSentenceType(SentenceType.ERROR);
            return sentenceModel;
        }
        // 替换多余的空格，清理首位空格
        sentence = sentence.replaceAll("\\s{2,}", " ").trim();

        String[] words = sentence.split(" ");
        if (words.length < 3) {
            sentenceModel.setSentenceType(SentenceType.ERROR);
            return sentenceModel;
        }

        // 定义昵称 语义特征：三个单词，以特定价值符号结尾
        if (Objects.nonNull(sentenceModel = NikeNameBuilder.isNikeNameSentence(sentence))) {
            return sentenceModel;
        }
        // 简单提问等价积分
        if (Objects.nonNull(sentenceModel = simpleCreditQuestionBuilder.isMyTypeSentence(sentence))) {
            return sentenceModel;
        }
        // 带货币单位的积分提问
        if (Objects.nonNull(sentenceModel = unitCreditQuestionBuilder.isMyTypeSentence(sentence))) {
            return sentenceModel;
        }
        // 定义货币的积分价值 特征：以N个昵称开头+ 货币单位 + 积分数量 以Credits 结尾
        if (Objects.nonNull(sentenceModel = moneyUnitBuilder.isMoneyUnitSentence(sentence))) {
            return sentenceModel;
        }
        sentenceModel = new SentenceModel();
        sentenceModel.setSentenceType(SentenceType.WRONG);
        return sentenceModel;

    }

    public static void main(String[] args) throws Exception {
        String sentence = "XLII";
        NumberCell numberCell = new NumberCell(sentence);
        System.out.println(numberCell.toString());
    }
}
