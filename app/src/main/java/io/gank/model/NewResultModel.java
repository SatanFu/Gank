package io.gank.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by satan on 2015/8/14.
 */
public class NewResultModel implements Serializable {
    @SerializedName("error")
    private boolean error;

    @SerializedName("results")
    private NewModel results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public NewModel getResults() {
        return results;
    }

    public void setResults(NewModel results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "NewResultModel{" +
                "error=" + error +
                ", results=" + results.toString() +
                '}';
    }
}
