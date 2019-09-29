package sentence;

import java.util.HashMap;

import constant.SentenceType;
import number.BasicCell;

/**
 * 昵称构造器
 * 该对象继承自HashMap，本身存有昵称和罗马符号之间的映射
 */
public class NikeNameBuilder extends HashMap<String, BasicCell> {

    /**
     * 根据文本对象设置昵称
     *
     * @param sentenceModel
     * @return
     */
    public void setNikeNameWithSentence(SentenceModel sentenceModel) {
        this.put(sentenceModel.getNikeNameString(), BasicCell.getWithString(sentenceModel.getCellString()));
    }

    /**
     * 是否为定义昵称的句型
     *
     * @param sentence
     * @return
     */
    public static SentenceModel isNikeNameSentence(String sentence) {
        SentenceModel sentenceModel = new SentenceModel();
        // 基本数值集合
        String[] words = sentence.split(" ");
        // 定义昵称 语义特征：三个单词，以特定价值符号结尾
        if (words.length == 3
                && "is".equals(words[1])
                && BasicCell.cellSet.contains(BasicCell.getWithString(words[words.length - 1]))) {
            sentenceModel.setSentenceType(SentenceType.NIKE_NAME);
            sentenceModel.setNikeNameString(words[0]);
            sentenceModel.setCellString(words[words.length - 1]);
            return sentenceModel;
        }
        return null;
    }

    /**
     * 根据昵称获取单位对象
     *
     * @param nikeName
     * @return
     */
    public BasicCell getCellByNikeName(String nikeName) {
        return this.get(nikeName);
    }
}
