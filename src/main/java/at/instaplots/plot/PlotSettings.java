package at.instaplots.plot;

public class PlotSettings {



    public enum Permission {
        BORDER_WATER_FLOW("BORDER_WATER_FLOW"),
        FIRE("FIRE"),
        MOB_VS_MOB("MOB_VS_MOB"),
        EXPLOSIONS("EXPLOSIONS"),
        BORDER_PISTONS("BORDER_PISTONS"),
        MOB_SPAWNING("MOB_SPAWNING"),
        MOB_BLOCK_DAMAGE("MOB_BLOCK_DAMAGE");

        public final String label;

        private Permission(String label) {
            this.label = label;
        }

        @Override
        public String toString() {
            return this.label;
        }
    }

}
