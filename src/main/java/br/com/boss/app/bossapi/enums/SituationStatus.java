package br.com.boss.app.bossapi.enums;

public enum SituationStatus {
    DISPONIVEL,
    EM_TRANSITO,
    INDISPONIVEL;

    public static SituationStatus fromString(String situation) {
        return SituationStatus.valueOf(situation.toUpperCase());
    }

    @Override
    public String toString() {
        return name();
    }
}
