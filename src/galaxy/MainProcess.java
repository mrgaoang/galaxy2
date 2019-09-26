package galaxy;

import constant.SentenceType;

import java.util.ArrayList;
import java.util.List;

public class MainProcess {

    public static void main(String[] args) {

        NikeNameBuilder nikeNameBuilder = new NikeNameBuilder();
        MoneyUnitBuilder moneyUnitBuilder = new MoneyUnitBuilder(nikeNameBuilder);
        SentenceSelect sentenceSelect = new SentenceSelect(moneyUnitBuilder);
        List<String> sentenceList = new ArrayList<>();
        sentenceList.add("");
        for (int i = 0; i < sentenceList.size(); i++) {
            String sentence = sentenceList.get(i);
            int type = sentenceSelect.select(sentence);
            switch (type) {
                case SentenceType
                        .NIKE_NAME:
                    nikeNameBuilder.makeFromSentence(sentence);
                    break;
                case SentenceType.INIT_CREDIT:
                    moneyUnitBuilder.setUnitWithSentence(sentence);
                    break;
                case SentenceType.QUESTION_CREDIT:
                    NumberCell numberCell = new NumberCell(sentence);

            }
        }
    }
}