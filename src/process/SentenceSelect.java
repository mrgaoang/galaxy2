package process;

import java.util.Objects;

import constant.SentenceType;
import sentence.*;

/**
 * 文本选择器
 */
public class SentenceSelect {

    /**
     * 货物积分价值对象
     */
    private MoneyUnitBuilder moneyUnitBuilder;

    /**
     * 简单积分问题构造器
     */
    private SimpleCreditQuestionBuilder simpleCreditQuestionBuilder;

    /**
     * 货物积分价值问题构造器
     */
    private UnitCreditQuestionBuilder unitCreditQuestionBuilder;

    /**
     * 昵称构造器
     */
    private NikeNameBuilder nikeNameBuilder;

    public SimpleCreditQuestionBuilder getSimpleCreditQuestionBuilder() {
        return simpleCreditQuestionBuilder;
    }

    public UnitCreditQuestionBuilder getUnitCreditQuestionBuilder() {
        return unitCreditQuestionBuilder;
    }

    public MoneyUnitBuilder getMoneyUnitBuilder() {
        return moneyUnitBuilder;
    }

    public NikeNameBuilder getNikeNameBuilder() {
        return nikeNameBuilder;
    }

    public SentenceSelect() {
        this.nikeNameBuilder = new NikeNameBuilder();
        this.moneyUnitBuilder = new MoneyUnitBuilder(nikeNameBuilder);
        this.simpleCreditQuestionBuilder = new SimpleCreditQuestionBuilder(moneyUnitBuilder);
        this.unitCreditQuestionBuilder = new UnitCreditQuestionBuilder(moneyUnitBuilder);
    }

    /**
     * 重置昵称和货物
     */
    public void reSet() {
        nikeNameBuilder.clear();
        moneyUnitBuilder.clear();
        System.out.println("reSet success !");

    }

    /**
     * 判断文本的语法类型
     *
     * @param sentence
     * @return
     */
    public SentenceModel select(String sentence) {
        SentenceModel sentenceModel = new SentenceModel();
        if (sentence == null || sentence.isEmpty()) {
            sentenceModel.setSentenceType(SentenceType.ERROR);
            return sentenceModel;
        }
        // 替换多余的空格，清理首尾空格
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
        // 带货物单位的积分提问
        if (Objects.nonNull(sentenceModel = unitCreditQuestionBuilder.isMyTypeSentence(sentence))) {
            return sentenceModel;
        }
        // 定义货物的积分价值 特征：以N个昵称开头+ 货物单位 + 积分数量 以Credits 结尾
        if (Objects.nonNull(sentenceModel = moneyUnitBuilder.isMoneyUnitSentence(sentence))) {
            return sentenceModel;
        }
        sentenceModel = new SentenceModel();
        sentenceModel.setSentenceType(SentenceType.WRONG);
        return sentenceModel;

    }

}
