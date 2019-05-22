package heig.pro.a05.desktop;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;
import heig.pro.a05.server.MultiThreadServer;

public class App {
    public static void main(String[] args) {
        AppGameContainer apgc;
        try {
            MultiThreadServer server = new MultiThreadServer(8384, 4);
            server.serveClients();
            apgc = new AppGameContainer(heig.pro.a05.desktop.Server.getInstance(), 1280, 960, false);
            apgc.setTargetFrameRate(60);
            apgc.start();


        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
