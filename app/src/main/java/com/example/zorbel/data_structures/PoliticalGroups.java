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

    public Section getSection(int id_political_party, int section) {
        PoliticalParty plp = mlistOfPoliticalParties.get(id_political_party);
        Section root = plp.getmSectionRoot();

        Section sect = root.getlSections().get(section / 1000000); // Get section of level 1

        if (section % 10000 != 0) { // level 2

            int index2 = (section % 1000000) / 10000; // Get number of section of level 2
            sect = sect.getlSections().get(index2); // Get section of level 2

            if (section % 1000 != 0) { // level 3

                int index3 = (section % 10000) / 100; // Get number of section of level 3
                sect = sect.getlSections().get(index3); // Get section of level 3

                if (section % 100 != 0) { // level 4

                    int index4 = section % 100; // Get number of section of level 4
                    sect = sect.getlSections().get(index4); // Get section of level 4
                }
            }
        }

        return sect;
    }
}
