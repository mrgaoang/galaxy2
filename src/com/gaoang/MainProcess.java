package com.gaoang;

import java.util.Scanner;

import constant.SentenceType;
import sentence.MoneyUnitBuilder;
import sentence.NikeNameBuilder;
import sentence.SentenceModel;
import sentence.SentenceSelect;

public class MainProcess {
    /**
     * 昵称构造器
     */
    public static NikeNameBuilder nikeNameBuilder = new NikeNameBuilder();
    /**
     * 单位货币构造器
     */
    public static MoneyUnitBuilder moneyUnitBuilder = new MoneyUnitBuilder(nikeNameBuilder);
    /**
     * 语义选择器
     */
    public static SentenceSelect sentenceSelect = new SentenceSelect(moneyUnitBuilder);

    /**
     * Test input:
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
     * <p>
     * Test Output:
     * pish tegj glob glob is 42
     * glob prok Silver is 68 Credits
     * glob prok Gold is 57800 Credits
     * glob prok Iron is 782 Credits
     * I have no idea what you are talking about
     *
     * @param args
     */
    public static void main(String[] args) {
        while (true) {
            System.out.println("please input :");
            Scanner sc = new Scanner(System.in);
            String sentence = sc.nextLine();
            if ("exit".equals(sentence)) {
                System.out.println("exit ");
                break;
            }
            if ("init".equals(sentence)) {
                nikeNameBuilder.clear();
                moneyUnitBuilder.clear();
                System.out.println("init success !");
                continue;
            }
            System.out.println(sentence);
            inputOneLine(sentence);

        }

        // 打印货币等价的积分
//        for (String s : moneyUnitBuilder.keySet()) {
//            System.out.println(s + " = " + moneyUnitBuilder.get(s) + " credits");
//        }

    }

    /**
     * 接受一行输入
     *
     * @param sentence
     */
    public static void inputOneLine(String sentence) {
        SentenceModel model = sentenceSelect.select(sentence);
        int type = model.getSentenceType();
        switch (type) {
            // 昵称构造
            case SentenceType.NIKE_NAME:
                nikeNameBuilder.makeFromSentence(model);
                break;
            // 货币价值构造
            case SentenceType.INIT_CREDIT:
                String errorMsg = moneyUnitBuilder.setUnitWithSentence(model);
                if (errorMsg != null) {
                    System.out.println(errorMsg);
                }
                break;
            case SentenceType.QUESTION_CREDIT:
                System.out.println(sentenceSelect.getSimpleCreditQuestionBuilder().answer(model));
                break;
            case SentenceType.QUESTION_UNIT_CREDIT:
                System.out.println(sentenceSelect.getUnitCreditQuestionBuilder().answer(model));
                break;
            default:
                System.out.println("I have no idea what you are talking about");
        }
    }


}