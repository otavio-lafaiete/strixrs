package com.strixrs.data;

import com.strixrs.model.Research;

import java.util.ArrayList;
import java.util.List;

public class Researchs {

    private List<Research> researchs;

    public Researchs(){

        researchs = new ArrayList<>();

        for(int i = 1; i <= 30; i++)
            researchs.add(new Research(String.format("%s %d", "Pesquisa", i), ""));
    }

    public List<Research> getResearchs() {
        return researchs;
    }

    public void add(Research research) {
    }
}
