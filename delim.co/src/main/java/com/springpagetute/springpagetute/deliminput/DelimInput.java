package com.springpagetute.springpagetute.deliminput;

import lombok.Data;

public @Data class DelimInput {
    private String input;
    private boolean tidyUp;
    private boolean attackClones;
    private String explode;
    private String quotes;
    private String delimiter;
    private String openTag;
    private String closeTag;
    private int interval;
    private String intervalOpenWrap;
    private String intervalCloseWrap;

}
