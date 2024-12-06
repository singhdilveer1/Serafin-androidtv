package org.jellyfin.sera.ui.browsing;

import org.jellyfin.sera.ui.livetv.TvManager;
import org.jellyfin.sera.ui.presentation.CardPresenter;

public class BrowseScheduleFragment extends EnhancedBrowseFragment {

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void setupQueries(final RowLoader rowLoader) {
        TvManager.getScheduleRowsAsync(this, null, new CardPresenter(true), mRowsAdapter);
    }
}
