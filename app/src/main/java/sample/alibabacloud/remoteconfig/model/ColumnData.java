package sample.alibabacloud.remoteconfig.model;

/**
 * Created by saisarathp on 12/7/2017.
 */

public class ColumnData {
    private String charCount;
    private String minAge;
    private String maxAge;
    private String ImageNum;

    public ColumnData(String charCount, String minAge, String maxAge, String imageNum) {
        this.charCount = charCount;
        this.minAge = minAge;
        this.maxAge = maxAge;
        ImageNum = imageNum;
    }

    public ColumnData() {
    }

    public String getCharCount() {
        return charCount;
    }

    public void setCharCount(String charCount) {
        this.charCount = charCount;
    }

    public String getMinAge() {
        return minAge;
    }

    public void setMinAge(String minAge) {
        this.minAge = minAge;
    }

    public String getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(String maxAge) {
        this.maxAge = maxAge;
    }

    public String getImageNum() {
        return ImageNum;
    }

    public void setImageNum(String imageNum) {
        ImageNum = imageNum;
    }
}
