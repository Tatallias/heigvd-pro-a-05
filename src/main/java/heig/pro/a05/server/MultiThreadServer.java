package heig.pro.a05.server;

import heig.pro.a05.desktop.Game;
import heig.pro.a05.desktop.LogIn;
import heig.pro.a05.desktop.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MultiThreadServer {
    final static Logger LOG = Logger.getLogger(MultiThreadServer.class.getName());

    int port;
    int maxClients;

    /**
     * Constructor taking an ip address and a port to start
     * a multi-threaded server for the game.
     *
     * @param port the port for the server
     * @param maxClients the maximum amount of players
     */
    public MultiThreadServer(int port, int maxClients) {
        this.maxClients = maxClients;
        this.port = port;
    }

    /**
     * Starts a new Thread for the reception of potential players.
     */
    public void serveClients() {
        LOG.info("Starting the Receptionist Worker on a new thread...");
        new Thread(new ReceptionistWorker()).start();
    }


    private class ReceptionistWorker implements Runnable {
        /**
         * A receptionist for all the potential players it does the following:
         * Opens a server socket on a given port.
         * Wait for a player to request a connexion and delegates the conversation
         * to a child-thread.
         */
        @Override
        public void run() {
            ServerSocket serverSocket;
            int nbClient = 0;
            try {
                serverSocket = new ServerSocket(port);
            } catch (IOException ex) {
                LOG.log(Level.SEVERE, null, ex);
                return;
            }

            while (nbClient < 4) {
                LOG.log(Level.INFO, "Waiting (blocking) for a new client on port {0}", port);
                try {
                    Socket clientSocket = serverSocket.accept();
                    LOG.log(Level.INFO, "A potential client has arrived.");

                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    String line;
                    if((line = in.readLine()) != null) {
                        if (line.equals("READY")) {
                            LOG.log(Level.INFO, "A new client has arrived. Number of client {0} with ip " + clientSocket.getInetAddress().getHostAddress(), nbClient);

                            new Thread(new ServantWorker(clientSocket, nbClient)).start();
                            nbClient++;
                            LogIn.setNbPlayers(nbClient);
                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(MultiThreadServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

        /**
         * A ServantWorker is a thread delegated to serve a player and listen
         * to his queries and transmit it to the Game state.
         */
        private class ServantWorker implements Runnable {
            int id;
            Socket clientSocket;
            BufferedReader in = null;
            PrintWriter out = null;

            /**
             * A ServantWorker constructor taking a players Socket and his id
             * to start communicating on said socket.
             *
             * @param clientSocket the socket associated to the player
             * @param id the players unique id
             */
            public ServantWorker(Socket clientSocket, int id) {
                this.id = id;
                try {
                    this.clientSocket = clientSocket;
                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    out = new PrintWriter(clientSocket.getOutputStream());
                } catch (IOException ex) {
                    Logger.getLogger(MultiThreadServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            /**
             * Continuously waits for a query, parses it and transmits it to the Game
             */
            @Override
            public void run() {
                String line;
                boolean shouldRun = true;

                try {
                    LOG.info("Reading until client sends EXIT or closes the connection...");
                    while (shouldRun) {
                        if((line = in.readLine()) != null) {
                            System.out.println(line);
                            ((Game) Server.getInstance().getState(1)).parse(id, line.getBytes());
                        }
                    }

                    LOG.info("Cleaning up resources...");
                    clientSocket.close();
                    in.close();
                    out.close();

                } catch (IOException ex) {
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException ex1) {
                            LOG.log(Level.SEVERE, ex1.getMessage(), ex1);
                        }
                    }
                    if (out != null) {
                        out.close();
                    }
                    if (clientSocket != null) {
                        try {
                            clientSocket.close();
                        } catch (IOException ex1) {
                            LOG.log(Level.SEVERE, ex1.getMessage(), ex1);
                        }
                    }
                    LOG.log(Level.SEVERE, ex.getMessage(), ex);
                }
            }
        }
    }
}