package med.voll.api.model;

import java.util.Arrays;

public enum Especialidade {
    ORTOPEDIA("Ortopedia"),
    CARDIOLOGIA("Cardiologia"),
    GINECOLOGIA("Ginecologia"),
    DERMATOLOGIA("Dermatologia");

    private final String especialidade;

    Especialidade(String especialidade){
        this.especialidade = especialidade;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public static boolean ehEspecialidade(Especialidade especialidade){
        return Arrays.stream(Especialidade.values())
                .anyMatch(esp -> esp.getEspecialidade().equalsIgnoreCase(especialidade.getEspecialidade()));
    }
}
