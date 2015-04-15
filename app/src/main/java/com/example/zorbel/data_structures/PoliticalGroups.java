package com.example.zorbel.data_structures;

import java.util.Collections;
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

    public Section getSection(int index_political_party, int section) {
        PoliticalParty plp = mlistOfPoliticalParties.get(index_political_party);
        Section root = plp.getmSectionRoot();

        Section sect = root.getlSections().get((section / 1000000) - 1); // Get section of level 1 (array starts in zero)

        if (section % 1000000 != 0) { // level 2

            int index2 = ((section % 1000000) / 10000) - 1; // Get number of section of level 2
            sect = sect.getlSections().get(index2); // Get section of level 2

            if (section % 10000 != 0) { // level 3

                int index3 = ((section % 10000) / 100) - 1; // Get number of section of level 3
                sect = sect.getlSections().get(index3); // Get section of level 3

                if (section % 100 != 0) { // level 4

                    int index4 = (section % 100) - 1; // Get number of section of level 4
                    sect = sect.getlSections().get(index4); // Get section of level 4
                }
            }
        }

        return sect;
    }

    public PoliticalParty getPoliticalParty(int id) {

        /*for (PoliticalParty p : mlistOfPoliticalParties) {
            if (id == p.getmId())
                return p;
        }*/

        PoliticalParty p = null;

        for (int i = 0; i < mlistOfPoliticalParties.size(); i++) {
            PoliticalParty aux = mlistOfPoliticalParties.get(i);
            if (id == aux.getmId())
                p = aux;
        }

        return p;
    }
}
