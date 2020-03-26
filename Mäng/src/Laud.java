import java.util.Arrays;

public class Laud {

    private String[][] kivid;
    private int tühiKohtX;
    private int tühiKohtY;

    public Laud(Numbrisekvents kivide_järjekord) {//Konstruktor, juhusliku väljaga mängulaua loomiseks.
        //Viimaks salvestame genereeritud jada 4*4 maatriksina.
        String[] järjestus = kivide_järjekord.getJärjestus();

        tühiKohtX=kivide_järjekord.getTühi_koht()[0];
        tühiKohtY=kivide_järjekord.getTühi_koht()[1];

        this.kivid = new String[][]{{järjestus[0], järjestus[1], järjestus[2], järjestus[3]},
                {järjestus[4], järjestus[5], järjestus[6], järjestus[7]},
                {järjestus[8], järjestus[9], järjestus[10], järjestus[11]},
                {järjestus[12], järjestus[13], järjestus[14], järjestus[15]}};

    }
    public void kuvaLaud(){ //Kuvab mängulaua
        System.out.println("-------------");
        System.out.printf("|%2s %2s %2s %2s| \n", kivid[0][0], kivid[0][1], kivid[0][2], kivid[0][3]);
        System.out.printf("|%2s %2s %2s %2s| \n", kivid[1][0], kivid[1][1], kivid[1][2], kivid[1][3]);
        System.out.printf("|%2s %2s %2s %2s| \n", kivid[2][0], kivid[2][1], kivid[2][2], kivid[2][3]);
        System.out.printf("|%2s %2s %2s %2s| \n", kivid[3][0], kivid[3][1], kivid[3][2], kivid[3][3]);
        System.out.println("-------------");
    }


    /**Meetod liigutab tühjast kohast vastavas suunas asuva kivi tühja kohta
     *
     * @param suund kujul a/s/d/w, mille alusel liigutame vwastavat kivi
     * meetod tagastab maatriksi, kus on vastavas suunas liikumine ära tehtud.
     */
    public void liigutaKivi(String suund){
        System.out.print("\n\n\n\n\n\n\n"); //NB! Kui Win PowerShellis mängida siis oli vahel ilus!
        switch (suund) {
            case "s":
                if (tühiKohtX == 3) System.out.println("Alt pole elementi võtta");
                else {
                    kivid[tühiKohtX][tühiKohtY] = kivid[tühiKohtX + 1][tühiKohtY];
                    kivid[tühiKohtX + 1][tühiKohtY] = " ";
                    tühiKohtX += 1;
                }
                kuvaLaud();
                break;
            case "w":
                if (tühiKohtX == 0) System.out.println("Ülevalt pole elementi võtta");
                else {
                    kivid[tühiKohtX][tühiKohtY] = kivid[tühiKohtX - 1][tühiKohtY];
                    kivid[tühiKohtX - 1][tühiKohtY] = " ";
                    tühiKohtX -= 1;
                }
                kuvaLaud();
                break;
            case "d":
                if (tühiKohtY == 3) System.out.println("Paremalt pole elementi võtta");
                else {
                    kivid[tühiKohtX][tühiKohtY] = kivid[tühiKohtX][tühiKohtY + 1];
                    kivid[tühiKohtX][tühiKohtY + 1] = " ";
                    tühiKohtY += 1;
                }
                kuvaLaud();
                break;
            case "a":
                if (tühiKohtY == 0) System.out.println("vasakult pole elementi võtta");
                else {
                    kivid[tühiKohtX][tühiKohtY] = kivid[tühiKohtX][tühiKohtY - 1];
                    kivid[tühiKohtX][tühiKohtY - 1] = " ";
                    tühiKohtY -= 1;
                }
                kuvaLaud();
                break;
            default:
                System.out.println("Vigane sisend. Liigutamiseks on wasd");
                break;
        }
    }
    public boolean onLahendatud(){
        return(Arrays.deepEquals(kivid, new String[][]{{"1", "2", "3", "4"}, {"5", "6", "7", "8"}, {"9", "10", "11", "12"}, {"13", "14", "15", " "}}));
    }
}
