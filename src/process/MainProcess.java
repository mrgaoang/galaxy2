package process;

import java.util.Scanner;

import constant.CommandWord;
import constant.SentenceType;
import sentence.SentenceModel;

/**
 * 主程序入口
 * 接收用户输入
 * 并调用SentenceSelect解析用户输入，并交给对应的执行对象，响应用户的输入
 * 详细介绍请查看readme
 */
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
            switch (sentence) {
                // 执行退出
                case CommandWord.EXIT:
                    sc.close();
                    System.out.println("exit success !");
                    return;
                // 执行帮助
                case CommandWord.HELP:
                    help();
                    break;
                // 执行测试数据
                case CommandWord.TEST:
                    TestProcess.test();
                    break;
                // 重置昵称和货物单位
                case CommandWord.RESET:
                    sentenceSelect.reSet();
                    break;
                // 解析输入的正常文本
                default:
                    inputOneLine(sentence);
            }
        }
    }

    /**
     * 接受一行输入
     * 调用SentenceSelect 解析文本类型并提取SentenceModel
     * 再调用响应的执行对象
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
                sentenceSelect.getNikeNameBuilder().setNikeNameWithSentence(model);
                break;
            // 货物价值构造
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
            // 货物积分价值问题
            case SentenceType.QUESTION_UNIT_CREDIT:
                System.out.println(sentenceSelect.getUnitCreditQuestionBuilder().answer(model));
                break;
            default:
                System.out.println("I have no idea what you are talking about");
        }
    }

    /**
     * 输出帮助
     */
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