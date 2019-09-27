package com.gaoang;

import java.util.Scanner;

import constant.SentenceType;
import sentence.MoneyUnitBuilder;
import sentence.NikeNameBuilder;
import sentence.SentenceModel;
import sentence.SentenceSelect;

public class MainProcess {

    /**
     * 语义选择器
     */
    public static SentenceSelect sentenceSelect = new SentenceSelect();

    /**
     * @param args
     * @author 高昂
     * @date 2019-9-27
     * @mobile 18511077193
     */
    public static void main(String[] args) {
        System.out.println("===========galaxy===========");
        System.out.println("input 'exit' to end program ");
        System.out.println("input 'reSet' to init program ");
        System.out.println("input 'help' to print example input ");
        System.out.println("input 'test' to execute test model ");
        System.out.println("# if you want copy a lot of sentence , please end with the char '\\n' ! ");
        System.out.println("please input :");
        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()) {
            String sentence = sc.nextLine();
            if ("exit".equals(sentence)) {
                sc.close();
                System.out.println("exit success !");
                break;
            }
            if ("help".equals(sentence)) {
                help();
                continue;
            }
            if ("test".equals(sentence)) {
                TestProcess.test();
                continue;
            }
            if ("reSet".equals(sentence)) {
                sentenceSelect.reSet();
                continue;
            }
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
        // 解析文本提取关键词
        SentenceModel model = sentenceSelect.select(sentence);
        // 根据文本类型交由相关的执行对象
        int type = model.getSentenceType();
        switch (type) {
            // 昵称构造
            case SentenceType.NIKE_NAME:
                sentenceSelect.getNikeNameBuilder().makeFromSentence(model);
                break;
            // 货币价值构造
            case SentenceType.INIT_CREDIT:
                String errorMsg = sentenceSelect.getMoneyUnitBuilder().setUnitWithSentence(model);
                if (errorMsg != null) {
                    System.out.println(errorMsg);
                }
                break;
            // 简单积分问题
            case SentenceType.QUESTION_CREDIT:
                System.out.println(sentenceSelect.getSimpleCreditQuestionBuilder().answer(model));
                break;
            // 货币积分价值问题
            case SentenceType.QUESTION_UNIT_CREDIT:
                System.out.println(sentenceSelect.getUnitCreditQuestionBuilder().answer(model));
                break;
            default:
                System.out.println("I have no idea what you are talking about");
        }
    }

    public static void help() {
        System.out.println("glob is I\n" +
                "prok is V\n" +
                "pish is X\n" +
                "tegj is L\n" +
                "glob glob Silver is 34 Credits\n" +
                "glob prok Gold is 57800 Credits\n" +
                "pish pish Iron is 3910 Credits\n" +
                "how much is pish tegj glob glob ?\n" +
                "how many Credits is glob prok Silver ?\n" +
                "how many Credits is glob prok Gold ?\n" +
                "how many Credits is glob prok Iron ?\n");

    }


}