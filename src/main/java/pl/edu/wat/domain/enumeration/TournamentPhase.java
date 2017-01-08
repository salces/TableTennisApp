package pl.edu.wat.domain.enumeration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The TournamentPhase enumeration.
 */
public enum TournamentPhase {
    FINAL(1),
    SEMIFINAL(2),
    QUARTERFINAL(4),
    ONE_EIGHT(8),
    ONE_SIXTEENTH(16),
    ONE_THIRTY_SECOND(32),
    ONE_SIXTY_FOURTH(64);

    private int lvl;

    TournamentPhase(int lvl) {
        this.lvl = lvl;
    }

    private static Map<Integer,TournamentPhase> getPhaseToIntMapping(){
        Map<Integer,TournamentPhase> map = new HashMap<>();

        for(TournamentPhase phase : TournamentPhase.values()){
            map.put(phase.lvl,phase);
        }

        return map;
    }


    public TournamentPhase getNextPhase(){
        int nextStageLvl = this.lvl/2;

        return fromInt(nextStageLvl);
    }

    public static TournamentPhase fromInt(int lvl) {
        return getPhaseToIntMapping().get(lvl);
    }
    public static List<Integer> getAllLvls(){
        List<Integer> lvls = new ArrayList<>();
        for (TournamentPhase phase : TournamentPhase.values()){
            lvls.add(phase.lvl);
        }
        return lvls;
    }

    public int getLvl() {
        return lvl;
    }
}
