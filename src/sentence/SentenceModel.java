package sentence;

/**
 * 文本对象
 */
public class SentenceModel {
    @Override
    public String toString() {
        return "SentenceModel{" +
                "sentenceType=" + sentenceType +
                ", nikeNameString='" + nikeNameString + '\'' +
                ", cellString='" + cellString + '\'' +
                ", credits=" + credits +
                ", moneyUnit='" + moneyUnit + '\'' +
                '}';
    }

    /**
     * 文本类型
     */
    private int sentenceType;
    /**
     * 文本型数值
     * eg. II
     */
    private String nikeNameString;

    /**
     * 罗马字符
     */
    private String cellString;

    /**
     * 积分
     */
    private double credits;

    /**
     * 货币单位
     */
    private String moneyUnit;


    public int getSentenceType() {
        return sentenceType;
    }

    public void setSentenceType(int sentenceType) {
        this.sentenceType = sentenceType;
    }

    public String getCellString() {
        return cellString;
    }

    public void setCellString(String cellString) {
        this.cellString = cellString;
    }

    public String getNikeNameString() {
        return nikeNameString;
    }

    public void setNikeNameString(String nikeNameString) {
        this.nikeNameString = nikeNameString;
    }

    public double getCredits() {
        return credits;
    }

    public void setCredits(double credits) {
        this.credits = credits;
    }

    public String getMoneyUnit() {
        return moneyUnit;
    }

    public void setMoneyUnit(String moneyUnit) {
        this.moneyUnit = moneyUnit;
    }
}
