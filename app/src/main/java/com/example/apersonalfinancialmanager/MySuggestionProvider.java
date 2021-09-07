package com.example.apersonalfinancialmanager;

import android.content.SearchRecentSuggestionsProvider;

public class MySuggestionProvider extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY = "com.example.amarhisabdemo.MySuggestionProvider";
    public static final int MODE = DATABASE_MODE_QUERIES;

    public MySuggestionProvider()
    {
        setupSuggestions(AUTHORITY,MODE);
    }
}
