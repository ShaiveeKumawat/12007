package com.example.ankit.navigationdrawer;

/**
 * Created by Ankit on 10-03-2018.
 */

public class LibraryClass {
    String bName;
    String bNumber;
    String bIssuedDate;
    String bSubmissionDate;

    public LibraryClass(String bName, String bNumber, String bIssuedDate, String bSubmissionDate) {
        this.bName = bName;
        this.bNumber = bNumber;
        this.bIssuedDate = bIssuedDate;
        this.bSubmissionDate = bSubmissionDate;
    }

    public String getbName() {
        return bName;
    }

    public String getbNumber() {
        return bNumber;
    }

    public String getbIssuedDate() {
        return bIssuedDate;
    }
    public String getbSubmissionDate() {
        return bSubmissionDate;
    }
}
