package com.airtribe.meditrack.interfaces;

public interface Searchable {
    boolean matches(Object criteria);
    String getSearchableInfo();
}

