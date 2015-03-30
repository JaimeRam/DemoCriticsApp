package com.example.zorbel.data_structures;

import java.util.List;

/**
 * Created by jaime on 30/03/15.
 */
public class PoliticalGroups {

    private List<PoliticalParty> mlistOfPoliticalParties;
    private static PoliticalGroups INSTANCE = new PoliticalGroups();

    private PoliticalGroups() {

    }

    public static PoliticalGroups getInstance() {
        return INSTANCE;
    }

    public void setMlistOfPoliticalParties(List<PoliticalParty> mlistOfPoliticalParties) {
        this.mlistOfPoliticalParties = mlistOfPoliticalParties;
    }

    public List<PoliticalParty> getMlistOfPoliticalParties() {
        return mlistOfPoliticalParties;
    }
}
