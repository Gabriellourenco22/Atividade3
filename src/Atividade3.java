/**
 * Created by Gabriel on 29/03/2017.
 */
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Atividade3 {

    int HistAcum (int v, int[]array){
        int last = 0;
        //int tons = 0;
        for(int i = 0; i < v; i++){
            //      tons ++;
            last += array[i];
            //  System.out.println(i +":  " + last);
        }
        //System.out.println(v +" "+last);
        return last;
    }

    BufferedImage Equalize(BufferedImage img, int[] hist){

        BufferedImage out = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);

        Color npixel;
        //int tons = 256;
        int hmin = 0;
        int hv = 0;


        //Get numero de tons e cor minima
        for(int i = 0; i < 256; i++){
            if(hist[i] != 0)
            {
                hmin = hist[i];
                break;
            }

        }

        for(int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {

                int cor = img.getRGB(x,y);

                Color pixel = new Color(cor);


                hv = Math.round ((HistAcum(pixel.getRed(), hist) - hmin) /
                            /*(img.getHeight() * img.getWidth()*/
                        (HistAcum(255, hist) - hmin))
                        * (256 - 1);


                if(hv > 255){
                    hv = 255;
                }
                if (hv < 0){
                    hv = 0;
                }
                npixel = new Color(hv,hv,hv);

                out.setRGB(x,y,npixel.getRGB());
            }
        }

        return out;
    }

    BufferedImage histogram(BufferedImage img){
        int[] hista = new int[256];
        BufferedImage out = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        for(int i = 0; i < 255; i++){
            hista[i] = 0;
        }

        //Histograma
        for(int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {

                int cor = img.getRGB(x, y);

                Color pixel = new Color(cor);

                int r = pixel.getRed();
                hista[r]++;
            }
        }
        //Histograma acomulado
        int[] ha = new int[256];
        int hmin = 0;
        ha[0] = hista[0];

        for(int i = 1; i < 256; i++){
            ha[i] = ha[i -1] + hista[i];

        }

        //Descobre minimo valor
        for(int i = 0; i < 256; i++){
            if(hista[i] != 0)
            {
                hmin = hista[i];
                break;
            }

        }


        //System.out.println(tons);
        int[] hv= new int[256];
        for(int i = 0; i < 255; i++){

            hv[i] = (int) (Math.round(((ha[i] - hmin) / ((float)img.getHeight() * img.getWidth()))* (256 - 1)) );

            if(hv[i] > 255){
                hv[i] = 254;
            }
            if (hv[i] < 0){
                hv[i] = 0;
            }
        }


        for(int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {

                int cor = img.getRGB(x, y);

                Color newPixel = new Color(cor);

                //System.out.println("Px: "+newPixel.getRed());
                //System.out.println("HV: "+(hv[newPixel.getRed()]));

                newPixel = new Color(hv[newPixel.getRed()],hv[newPixel.getRed()],hv[newPixel.getRed()]);

                out.setRGB(x,y,newPixel.getRGB());
            }
        }

        return out;
    }

    public void run() throws IOException {
        File PATH = new File("C:\\Users\\Gabriel Lourenço\\Desktop\\Java- Atividades\\Imagens\\img\\gray");
        BufferedImage arquivo = ImageIO.read(new File(PATH, "montanha.jpg"));
        // int[] hist = histogram(arquivo);


        BufferedImage imgHistogram = histogram(arquivo); //Equalize(arquivo,histogram(arquivo));
        ImageIO.write(imgHistogram, "png", new File("motanhahisto.png"));
    }

    public static void main (String[] args) throws IOException {
        Atividade3 aula = new Atividade3();
        aula.run();
    }

}
