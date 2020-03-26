public class Numbrisekvents {

    private String[] järjestus;

    public Numbrisekvents(){
        String[] ajutineJärjestus = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9",
                "10", "11", "12", "13", "14", "15", " "};
        do {
            int i = 15;
            while (!(i < 0)) {
                int j = (int) Math.floor(Math.random() * (i + 1));
                String tempi = ajutineJärjestus[i];
                String tempj = ajutineJärjestus[j];
                ajutineJärjestus[i] = tempj;
                ajutineJärjestus[j] = tempi;
                i--;
            }
        }
        while (!onLahenduv(ajutineJärjestus));
        järjestus=ajutineJärjestus;
    }

    public Numbrisekvents(String[] järjestus) {
        this.järjestus = järjestus;
    }

    public boolean onLahenduv(){//https://www.geeksforgeeks.org/check-instance-15-puzzle-solvable/
        int tühja_Koha_rida=0;
        int inv_loendur = 0;
        for (int i = 0; i < 15 ; i++) {
            for (int j = i+1; j < 16; j++) {
                if (järjestus[i].equals(" ")|| järjestus[j].equals(" ")){
                    tühja_Koha_rida = i/4;
                }
                else if (Integer.parseInt(järjestus[i])>Integer.parseInt(järjestus[j])){
                    inv_loendur++;
                }
            }
        }
        return((inv_loendur+tühja_Koha_rida)%2!=0);
    }

    public boolean onLahenduv(String[] järjestus){//https://www.geeksforgeeks.org/check-instance-15-puzzle-solvable/
        int tühja_Koha_rida=0;
        int inv_loendur = 0;
        for (int i = 0; i < 15 ; i++) {
            for (int j = i+1; j < 16; j++) {
                if (järjestus[i].equals(" ")|| järjestus[j].equals(" ")){
                    tühja_Koha_rida = i/4;
                }
                else if (Integer.parseInt(järjestus[i])>Integer.parseInt(järjestus[j])){
                    inv_loendur++;
                }
            }
        }
        System.out.println(inv_loendur);
        System.out.println(tühja_Koha_rida);
        return((inv_loendur+tühja_Koha_rida)%2!=0);
    }

    public String[] getJärjestus() {
        return järjestus;
    }

    public int[] getTühi_koht(){
        for (int i = 0; i < järjestus.length; i++) {
            if (järjestus[i].equals(" ")){
                return new int[]{i/4,i-i/4*4};
            }
        }
        return null;
    }
}


