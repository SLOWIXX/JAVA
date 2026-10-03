package com.iim.projet;

import java.util.List;

public record CourseResultat(List<ResultatCoureur> coureurs, int tours, boolean egaliteParfaite, String gagnant) {
}
