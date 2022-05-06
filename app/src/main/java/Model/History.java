package Model;

import java.io.Serializable;

public class History implements Serializable {

    private String codeFrom;
    private String valueInput;
    private String codeTo;
    private String valueOutput;

    public History() {
    }

    public History(String codeFrom, String valueInput, String codeTo, String valueOutput) {
        this.codeFrom = codeFrom;
        this.valueInput = valueInput;
        this.codeTo = codeTo;
        this.valueOutput = valueOutput;
    }

    public String getCodeFrom() {
        return codeFrom;
    }

    public void setCodeFrom(String codeFrom) {
        this.codeFrom = codeFrom;
    }

    public String getValueInput() {
        return valueInput;
    }

    public void setValueInput(String valueInput) {
        this.valueInput = valueInput;
    }

    public String getCodeTo() {
        return codeTo;
    }

    public void setCodeTo(String codeTo) {
        this.codeTo = codeTo;
    }

    public String getValueOutput() {
        return valueOutput;
    }

    public void setValueOutput(String valueOutput) {
        this.valueOutput = valueOutput;
    }

    @Override
    public String toString() {
        return "History{" +
                "codeFrom='" + codeFrom + '\'' +
                ", valueInput='" + valueInput + '\'' +
                ", codeTo='" + codeTo + '\'' +
                ", valueOutput='" + valueOutput + '\'' +
                '}';
    }
}
