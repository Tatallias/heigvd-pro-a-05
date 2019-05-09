package desktop;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.IOException;
import java.net.*;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Enumeration;

public class QRCodeGenerator {

    private static final String QR_CODE_IMAGE_PATH = "./img/qr.jpg";
    private static final String PORT = "6666";

    /**
     * Genère le QR code contenant les IPs du serveur ainsi que le port
     * Source : https://www.callicoder.com/generate-qr-code-in-java-using-zxing/
     * @param width  :  int, largeur du QR code
     * @param height : int, hauteur du QR code
     * @throws WriterException :
     * @throws IOException     :
     */
    public static void generateQRCodeImageWithIPsAndPort(int width, int height)
            throws WriterException, IOException {
        String text = getServerIPs() + "\n" + PORT;
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        Path path = FileSystems.getDefault().getPath(QR_CODE_IMAGE_PATH);
        MatrixToImageWriter.writeToPath(bitMatrix, "jpg", path);
    }

    /**
     * Retourne un String contenant toutes les IPs du serveur.
     * retour à la ligne.
     * Source : https://stackoverflow.com/questions/9481865/getting-the-ip-address-of-the-current-machine-using-java
     * @return : String, IPs du serveur, séparée par "\n"
     */
    private static String getServerIPs() {
        String serverIPs = "";

        try {
            Enumeration networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while(networkInterfaces.hasMoreElements())
            {
                NetworkInterface n = (NetworkInterface) networkInterfaces.nextElement();
                Enumeration addresses = n.getInetAddresses();
                while (addresses.hasMoreElements())
                {
                    InetAddress i = (InetAddress) addresses.nextElement();

                    // Garde seulement les adresses IPv4
                    if(i instanceof Inet4Address && !i.isLoopbackAddress()) {
                        serverIPs += i.getHostAddress() + " ";
                    }
                }
            }
        }
        catch (SocketException e) {
            e.printStackTrace();
        }
        return serverIPs;
    }
}
