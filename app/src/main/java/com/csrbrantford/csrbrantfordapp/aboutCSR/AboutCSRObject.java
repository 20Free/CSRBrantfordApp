package com.csrbrantford.csrbrantfordapp.aboutCSR;

/**
 *  Created by Jonathan on 12/28/2016.
 */

class AboutCSRObject {

    private String aboutCSRName;
    private String aboutCSRImageRef;
    private String aboutCSRDesc;
    private String aboutCSRFunFact;

    AboutCSRObject(String aboutCSRName, String aboutCSRImageRef, String aboutCSRDesc, String aboutCSRFunFact) {
        this.aboutCSRName = aboutCSRName;
        this.aboutCSRImageRef = aboutCSRImageRef;
        this.aboutCSRDesc = aboutCSRDesc;
        this.aboutCSRFunFact = aboutCSRFunFact;
    }

    String getAboutCSRName() {
        return aboutCSRName;
    }

    String getAboutCSRImageRef() {
        return aboutCSRImageRef;
    }

    String getAboutCSRDesc() {
        return aboutCSRDesc;
    }

    String getAboutCSRFunFact() {
        return aboutCSRFunFact;
    }
}