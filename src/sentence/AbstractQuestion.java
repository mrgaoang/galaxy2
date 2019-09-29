package sentence;

import sentence.SentenceModel;

public abstract class AbstractQuestion {


    /**
     * 判断问题句型
     *
     * @param sentence
     * @return
     */
    public abstract SentenceModel getMyModel(String sentence);

    /**
     * 回答此问题
     *
     * @param sentenceModel
     * @return
     */
    public abstract String answer(SentenceModel sentenceModel) throws Exception;


    /**
     * 问题特征词
     * @return
     */
    public abstract String getQuestionKeyWord();

    /**
     * 问题结尾词
     * @return
     */
    public abstract String getQuestionEndOfChar();

    /**
     * 判断提问类型并提取关键词
     * @param sentence 文本模型
     * @return
     */
    public SentenceModel isMyTypeSentence(String sentence){

        // step 1 特征匹配
        if (!sentence.contains(getQuestionKeyWord())) {
            return null;
        }
        // 必须以问号结尾
        if (!getQuestionEndOfChar().equals(sentence.substring(sentence.length() - 1, sentence.length()))) {
            return null;
        }

        // step 2 替换特征
        String lessWord = sentence.replace(getQuestionKeyWord(), "").replace(getQuestionEndOfChar(), "").trim();

        // step 3 提取模型
        return getMyModel(lessWord);
    }

}

