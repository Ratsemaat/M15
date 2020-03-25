import org.w3c.dom.ls.LSOutput;

public class Laud {

    private String[][] numbrid;
    private int tühiKohtX;
    private int tühiKohtY;


    public Laud() {//Konstruktor, juhusliku väljaga mängulaua loomiseks.
        // Fisher-Yatesi segamine loomaks juhuslik järjend numbritest 1...15.
        String[] templ = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", " "};
        int i = 15;
        int c = 0;
        while (!(i < 0)) {
            int j = (int) Math.floor(Math.random() * (i + 1));
            String tempi = templ[i];
            String tempj = templ[j];
            templ[i] = tempj;
            templ[j] = tempi;
            //Selleks, et tsükli lõpuks oleks meil teadmine, kus täpselt on tühi koht laual, siis rakendame järgmist
            if (tempi.equals(" ")) {
                tühiKohtX = j / 4;
                tühiKohtY = j - 4 * tühiKohtX;
            } else if (tempj.equals(" ")) {
                tühiKohtX = i / 4;
                tühiKohtY = i - 4 * tühiKohtX;
            }
            i--;
        }
        //Viimaks salvestame genereeritud jada 4*4 maatriksina.
        this.numbrid = new String[][]{{templ[0], templ[1], templ[2], templ[3]},
                {templ[4], templ[5], templ[6], templ[7]},
                {templ[8], templ[9], templ[10], templ[11]},
                {templ[12], templ[13], templ[14], templ[15]}};

    }
    public void kuvaLaud(){ //Kuvab mängulaua
        System.out.println("-------------");
        System.out.printf("|%2s %2s %2s %2s| \n",numbrid[0][0], numbrid[0][1], numbrid[0][2], numbrid[0][3]);
        System.out.printf("|%2s %2s %2s %2s| \n",numbrid[1][0], numbrid[1][1], numbrid[1][2], numbrid[1][3]);
        System.out.printf("|%2s %2s %2s %2s| \n",numbrid[2][0], numbrid[2][1], numbrid[2][2], numbrid[2][3]);
        System.out.printf("|%2s %2s %2s %2s| \n",numbrid[3][0], numbrid[3][1], numbrid[3][2], numbrid[3][3]);
        System.out.println("-------------");
    }


    /**Meetod liigutab tühjast kohast vastavas suunas asuva kivi tühja kohta
     *
     * @param suund kujul a/s/d/w, mille alusel liigutame vwastavat kivi
     * meetod tagastab maatriksi, kus on vastavas suunas liikumine ära tehtud.
     */
    public void liigutaKivi(String suund){
        if(suund.equals("w")){
            if (tühiKohtX == 3) System.out.println("Alt pole elementi võtta");
            else{
                numbrid[tühiKohtX][tühiKohtY] = numbrid[tühiKohtX+1][tühiKohtY] ;
                numbrid[tühiKohtX+1][tühiKohtY] =" ";
                tühiKohtX += 1;
            }
            kuvaLaud();
        }

        else if(suund.equals("s")) {
            if (tühiKohtX == 0) System.out.println("Ülevalt pole elementi võtta");
            else {
                numbrid[tühiKohtX][tühiKohtY] = numbrid[tühiKohtX - 1][tühiKohtY];
                numbrid[tühiKohtX - 1][tühiKohtY] = " ";
                tühiKohtX -= 1;
            }
            kuvaLaud();
        }

        else if(suund.equals("a")) {
            if (tühiKohtY == 3) System.out.println("Paremalt pole elementi võtta");
            else {
                numbrid[tühiKohtX][tühiKohtY] = numbrid[tühiKohtX][tühiKohtY+1];
                numbrid[tühiKohtX][tühiKohtY+1] = " ";
                tühiKohtY += 1;
            }
            kuvaLaud();
        }

        else if(suund.equals("d")) {
            if (tühiKohtY == 0) System.out.println("vasakult pole elementi võtta");
            else {
                numbrid[tühiKohtX][tühiKohtY] = numbrid[tühiKohtX][tühiKohtY-1];
                numbrid[tühiKohtX][tühiKohtY-1] = " ";
                tühiKohtY -= 1;
            }
            kuvaLaud();
        }
        else
            System.out.println("Vigane sisend. Liigutamiseks on wasd");

    }


}
