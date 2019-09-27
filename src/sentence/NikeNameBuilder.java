package sentence;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import constant.SentenceType;
import galaxy.BasicCell;

public class NikeNameBuilder extends HashMap<String, BasicCell> {

    /**
     * 根据文本对象设置昵称
     *
     * @param sentenceModel
     * @return
     */
    public void makeFromSentence(SentenceModel sentenceModel) {
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
        List<BasicCell> basicCells = Arrays.asList(BasicCell.values());
        String[] words = sentence.split(" ");
        // 定义昵称 语义特征：三个单词，以特定价值符号结尾
        if (words.length == 3
                && "is".equals(words[1])
                && basicCells.contains(BasicCell.getWithString(words[words.length - 1]))) {
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
        return (BasicCell) this.get(nikeName);
    }
}
