package galaxy;

import sentence.SentenceModel;

public abstract class AbstractQuestion {

    /**
     * 判断问题句型
     *
     * @param sentence
     * @return
     */
    public abstract SentenceModel isMyTypeSentence(String sentence);

    /**
     * 回答此问题
     *
     * @param sentenceModel
     * @return
     */
    public abstract String answer(SentenceModel sentenceModel) throws Exception;

}

