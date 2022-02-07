package com.strixrs.model;

import java.io.Serializable;

public interface ReportComponent extends Serializable {

    Report getReport();

    String getComponentType();

    String getName();
}
