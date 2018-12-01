package enums;

public enum Actions {

    CHECK_TEXT("checkText","CheckTextRequest", "https://speller.yandex.net/services/spellservice.json/checkText"),
    CHECK_TEXTS("checkTexts","CheckTextsRequest", "https://speller.yandex.net/services/spellservice.json/checkTexts");

    private String method;
    private String reqName;
    private String URI;

    public String getMethod() {
        return method;
    }

    public String getReqName() {
        return reqName;
    }

    public String getURI() {
        return URI;
    }

    Actions(String action, String reqName, String uri) {
        this.method = action;
        this.reqName = reqName;
        this.URI = uri;
    }
}
