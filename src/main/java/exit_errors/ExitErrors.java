package exit_errors;

public enum ExitErrors {

    NOT_ENOUGH_VALUES(10,
            "Not enough values in input file provided."),
    FILE_NOT_FOUND(20,
            "This file cannot be downloaded. Please check if you typed in correct url."),
    FILE_PARSING(21,
            "Error while parsing file. Please check if file is in correct Json format (JSON-stat)."),
    FILE_READING(22,
            "Error while reading file. Please check if file is in correct Json format (JSON-stat)."),
    MISSING_ATTRIBUTE_IN_FILE(23,
            "File has not correct structure.");

    private int errorCode;
    private String errorMsg;

    ExitErrors(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
