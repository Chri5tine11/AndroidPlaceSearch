package com.example.christine.placessearch;


import android.content.Context;
import android.graphics.Typeface;
import android.text.style.CharacterStyle;
import android.text.style.ClickableSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class PlaceAutocompleteAdapter
        extends ArrayAdapter<AutocompletePrediction> implements Filterable {

    private static final String TAG = "PlaceAutoCompleteAd";
    private static final CharacterStyle STYLE_BOLD = new StyleSpan(Typeface.BOLD);
    /**
     * Current results returned by this adapter.
     */
    private List<AutocompletePrediction> mResultList;

    /**
     * The bounds used for Places Geo Data autocomplete API requests.
     */
    private RectangularBounds mBounds;

    /**
     * Initializes with a resource for text rows and autocomplete query bounds.
     *
     * @see android.widget.ArrayAdapter#ArrayAdapter(android.content.Context, int)
     */
    public PlaceAutocompleteAdapter(Context context,
                                    RectangularBounds bounds) {
        super(context, android.R.layout.simple_expandable_list_item_2, android.R.id.text1);
        mBounds = bounds;
    }

    /**
     * Sets the bounds for all subsequent queries.
     */
    public void setBounds(RectangularBounds bounds) {
        mBounds = bounds;
    }

    /**
     * Returns the number of results received in the last autocomplete query.
     */
    @Override
    public int getCount() {
        return mResultList.size();
    }

    /**
     * Returns an item from the last autocomplete query.
     */
    @Override
    public AutocompletePrediction getItem(int position) {
        return mResultList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = super.getView(position, convertView, parent);

        // Sets the primary and secondary text for a row.
        // Note that getPrimaryText() and getSecondaryText() return a CharSequence that may contain
        // styling based on the given CharacterStyle.

        AutocompletePrediction item = getItem(position);

        TextView textView1 = (TextView) row.findViewById(android.R.id.text1);
        TextView textView2 = (TextView) row.findViewById(android.R.id.text2);
        textView1.setText(item.getPrimaryText(STYLE_BOLD));
        textView2.setText(item.getSecondaryText(STYLE_BOLD));

        return row;
    }

    /**
     * Returns the filter for the current set of autocomplete results.
     */
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();

                // We need a separate list to store the results, since
                // this is run asynchronously.
                List<AutocompletePrediction> filterData = new ArrayList<>();

                // Skip the autocomplete query if no constraints are given.
                if (constraint != null) {
                    // Query the autocomplete API for the (constraint) search string.
                    filterData = getAutocomplete(constraint);
                }

                results.values = filterData;
                if (filterData != null) {
                    results.count = filterData.size();
                } else {
                    results.count = 0;
                }

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                if (results != null && results.count > 0) {
                    // The API returned at least one result, update the data.
                    mResultList = (List<AutocompletePrediction>) results.values;
                    notifyDataSetChanged();
                } else {
                    // The API did not return any results, invalidate the data set.
                    notifyDataSetInvalidated();
                }
            }

            @Override
            public CharSequence convertResultToString(Object resultValue) {
                // Override this method to display a readable result in the AutocompleteTextView
                // when clicked.
                if (resultValue instanceof AutocompletePrediction) {
                    return ((AutocompletePrediction) resultValue).getFullText(null);
                } else {
                    return super.convertResultToString(resultValue);
                }
            }
        };
    }


    private List<AutocompletePrediction> getAutocomplete(CharSequence constraint) {
        Log.i(TAG, "Starting autocomplete query for: " + constraint);

        // Initialize Places.
        Places.initialize(getContext(), "AIzaSyDrEPbyNtggObaP38p1Ldhh6wTgRLnj8pg");

        // Create a new Places client instance.
        PlacesClient placesClient = Places.createClient(getContext());


        // Submit the query to the autocomplete API and retrieve a PendingResult that will
        // contain the results when the query completes.
        Task<FindAutocompletePredictionsResponse> results =
                placesClient.findAutocompletePredictions(
                        FindAutocompletePredictionsRequest.builder().setLocationRestriction(mBounds).setQuery(constraint.toString()).build());
        ;

        try {
            FindAutocompletePredictionsResponse findAutocompletePredictionsResponse = Tasks.await(results);
            return findAutocompletePredictionsResponse.getAutocompletePredictions();
        } catch (ExecutionException e) {
            // task failed
            return null;
        } catch (InterruptedException e) {
            // an interrupt occurred while waiting for the task to finish
            return null;
        }

    }

    ClickableSpan clickableSpan = new ClickableSpan() {
        @Override
        public void onClick(@NonNull View view) {

        }
    };
}
