package desktop;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.StandardProtocolFamily;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogIn extends BasicGameState {

    private Image qrCode, start;
    private final int PORT = 5000;
    final static Logger LOG = Logger.getLogger(Handler.class.getName());
    private Map<SocketAddress, Integer> IDAddresses = new HashMap<>();
    private int nbThread = 0;
    private boolean stopThread;

    @Override
    public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
        try {
            QRCodeGenerator.generateQRCodeImageWithIPsAndPort(300, 300);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        qrCode = new Image("img/qr.jpg");
        start = new Image("img/Start.png");
        if(nbThread < 1) {
            // Anonymous class, implements Runnable
            // Manage the connexion of the players
            new Thread(
                    new Runnable () {
                private LogIn parent = LogIn.this;
                private int listenPort = PORT;

                @Override
                public void run() {
                    DatagramChannel channel;
                    ByteBuffer receivingBuffer;

                    try {
                        channel = DatagramChannel.open(StandardProtocolFamily.INET);
                        channel.bind(new InetSocketAddress(listenPort));
                        channel.configureBlocking(false);

                        receivingBuffer = ByteBuffer.allocate(200);
                    } catch (IOException ex) {
                        LOG.log(Level.SEVERE, null, ex);
                        return;
                    }

                    while (!parent.getStopThread()) {
                        LOG.log(Level.INFO, "Waiting for a new client on port {0}", listenPort);
                        try {
                            SocketAddress senderAddress;
                            while (!parent.getStopThread()) {
                                senderAddress = channel.receive(receivingBuffer);
                                if (senderAddress != null) {
                                    int action = Byte.toUnsignedInt(receivingBuffer.get(0));

                                    if (action == 1) {
                                        parent.addNewPlayer(senderAddress);
                                    }
                                }
                                receivingBuffer.clear();
                                receivingBuffer.put(new byte[200]);
                                receivingBuffer.clear();
                            }
                        } catch (IOException ex) {
                            LOG.log(Level.SEVERE, ex.getMessage(), ex);
                        }
                    }

                    LOG.log(Level.INFO, "Thread terminated");
                }
            }).start();
            nbThread++;
        }
        stopThread = false;
    }

    @Override
    public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) {
        g.drawString("Connexion", arg0.getWidth() / 2 - 50, 50);
        qrCode.draw(arg0.getWidth() / 2 - 150, 75);
        start.draw(arg0.getWidth() / 2 - 50, 385);
        g.drawString("Players connected : " + IDAddresses.size() + "/4",
                arg0.getWidth() / 2 - 100, 440);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame arg1, int arg2) {
        int x = Mouse.getX();
        int y = gc.getHeight() - Mouse.getY();

        // Start button is pressed
        if((x > gc.getWidth() / 2 - 50 && x < gc.getWidth() / 2 + 50) && (y > 400 && y < 450)) {
            if(Mouse.isButtonDown(0)) {
                stopThread = true;
                arg1.enterState(1);
            }
        }
    }

    @Override
    public int getID() {
        return 3;
    }

    private void addNewPlayer(SocketAddress senderAddress) {
        IDAddresses.putIfAbsent(senderAddress, IDAddresses.size());
        if(IDAddresses.size() >= 4) {
            stopThread = true;
        }
    }

    private boolean getStopThread() {
        return stopThread;
    }
}
