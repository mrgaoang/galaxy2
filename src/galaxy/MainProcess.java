package galaxy;

import constant.SentenceType;

import java.util.ArrayList;
import java.util.List;

public class MainProcess {

    public static void main(String[] args) {

        // 昵称构造器
        NikeNameBuilder nikeNameBuilder = new NikeNameBuilder();
        // 单位货币构造器
        MoneyUnitBuilder moneyUnitBuilder = new MoneyUnitBuilder(nikeNameBuilder);
        // 语义选择器 TODO 需增加返回的数值
        SentenceSelect sentenceSelect = new SentenceSelect(moneyUnitBuilder);
        List<String> sentenceList = new ArrayList<>();
        sentenceList.add("");
        for (int i = 0; i < sentenceList.size(); i++) {
            String sentence = sentenceList.get(i);
            int type = sentenceSelect.select(sentence);
            switch (type) {
                // 昵称构造
                case SentenceType
                        .NIKE_NAME:
                    nikeNameBuilder.makeFromSentence(sentence);
                    break;
                // 货币价值构造
                case SentenceType.INIT_CREDIT:
                    moneyUnitBuilder.setUnitWithSentence(sentence);
                    break;
                case SentenceType.QUESTION_CREDIT:
//                    NumberCell numberCell = new NumberCell(sentence);

                    break;
                case SentenceType.QUESTION_UNIT_CREDIT:
                    moneyUnitBuilder.setUnitWithSentence(sentence);
                    break;

            }
        }
    }
}